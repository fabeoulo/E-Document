/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.quartzJob;

import com.advantech.helper.MailManager;
import com.advantech.model.User;
import com.advantech.model2.M9ieWorktimeView;
import com.advantech.model2.WorktimeM4f;
import com.advantech.service.UserNotificationService;
import com.advantech.service.db2.M9ieWorktimeViewService;
import com.advantech.service.db2.WorktimeDownloadMesM4fService;
import com.advantech.service.db2.WorktimeM4fService;
import com.advantech.webservice.download.db2.StandardWorkTimeM4fDownload;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
public class SyncWorktimeM4f {

    private static final Logger log = LoggerFactory.getLogger(SyncWorktimeM4f.class);

    @Autowired
    private M9ieWorktimeViewService m9ieWorktimeViewService;

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private MailManager mailManager;

    @Autowired
    private WorktimeDownloadMesM4fService worktimeDownloadMesM4fService;

    @Autowired
    private StandardWorkTimeM4fDownload standardWorkTimeM4fDownload;

    @Value("#{contextParameters[pageTitle] ?: ''}")
    private String pageTitle;

    private final int failMaxAllow = 5;

    @Autowired
    private WorktimeM4fService worktimeM4fService;

    // keep all transactions in one session, prevent from detash and extra select, also enable lazy-fetch.
    @Transactional
    public void syncModelFromM9ie() {
        List<String> errorMessages = new ArrayList();

        List<M9ieWorktimeView> views = m9ieWorktimeViewService.findAll();
        Map<String, List> listMap = worktimeDownloadMesM4fService.filterByViewAndSetBg(views);

        List<WorktimeM4f> wtIn = listMap.get("wtIn");
        List<WorktimeM4f> wtNew = listMap.get("wtNew");
        List<WorktimeM4f> wtRemove = listMap.get("wtRemove");

        log.info("Begin add WorktimeM4f : " + wtNew.size() + " datas.");
        if (!wtNew.isEmpty()) {
            worktimeDownloadMesM4fService.portParamInit();
            worktimeDownloadMesM4fService.insertMesDL(wtNew);
        }

        log.info("Begin udpate WorktimeM4f standardTime : " + wtIn.size() + " datas.");
        int failCount = 0;
        standardWorkTimeM4fDownload.initOptions();
        for (WorktimeM4f w : wtIn) {
            try {
                standardWorkTimeM4fDownload.download(w);

                log.info("Download standardtime: " + w.getModelName());
            } catch (Exception e) {
                String errorMessage = w.getModelName() + " download fail: " + e.getMessage();
                errorMessages.add(errorMessage);
                log.error(errorMessage);

                failCount++;

                if (failCount == failMaxAllow) {
                    String failMissionMessage = "Reaching maxinum download fail allow count "
                            + failMaxAllow + ", abort mission.";
                    log.error(failMissionMessage);
                    errorMessages.add(failMissionMessage);
                    break;
                }
            }
        }
        worktimeM4fService.mergeWithoutUpload(wtIn);

        log.info("Begin delete WorktimeM4f : " + wtRemove.size() + " datas.");
        try {
            worktimeM4fService.deleteWithMesUpload(wtRemove);
        } catch (Exception e) {
            String errorMessage = "Delete WorktimeM4f fail: " + e.getMessage();
            errorMessages.add(errorMessage);
            log.error(errorMessage);
        }

        this.notifyUser(errorMessages);

        log.info("Sync wtFromM9ie job finished.");
    }

    private void notifyUser(List<String> l) {
        String[] to = getMailByNotification("worktimeM4f_sync_alarm");
        String[] cc = new String[0];

        String subject = "【" + pageTitle + "系統訊息】M9_4F大表機種工時下載";
        String text = generateTextBody(l);

        try {
            mailManager.sendMail(to, cc, subject, text);
        } catch (MessagingException ex) {
            log.error("Send mail fail when upload mes job fail." + ex.toString());
        }
    }

    private String generateTextBody(List<String> l) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>Dear All:</p>");
        if (l.isEmpty()) {
            sb.append("<p>機種工時下載MES至大表成功於下列時間</p><p>");
            sb.append(new DateTime());
            sb.append("</p>");
        } else {
            sb.append("<p>下載MES至大表發生了異常，訊息清單如下</p>");
            for (String st : l) {
                sb.append("<p>");
                sb.append(st);
                sb.append("</p>");
            }
            sb.append("<p>請相關人員至系統確認大表設定是否正確。</p>");
        }
        return sb.toString();
    }

    protected String[] getMailByNotification(String notification) {
        List<User> users = userNotificationService.findUsersByNotification(notification);
        String[] mails = users.stream()
                .filter(u -> u.getEmail() != null || !"".endsWith(u.getEmail()))
                .map(u -> u.getEmail())
                .toArray(String[]::new);
        return mails;
    }
}
