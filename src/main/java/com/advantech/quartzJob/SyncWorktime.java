/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.quartzJob;

import com.advantech.helper.MailManager;
import com.advantech.model.User;
import com.advantech.model.M9ieWorktimeView;
import com.advantech.model.Worktime;
import com.advantech.service.UserNotificationService;
import com.advantech.service.M9ieWorktimeViewService;
import com.advantech.service.WorktimeDownloadMesService;
import com.advantech.service.WorktimeService;
import com.advantech.webservice.download.StandardWorkTimeDownload;
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
public class SyncWorktime {

    private static final Logger log = LoggerFactory.getLogger(SyncWorktime.class);

    @Autowired
    private M9ieWorktimeViewService m9ieWorktimeViewService;

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private MailManager mailManager;

    @Autowired
    private WorktimeDownloadMesService worktimeDownloadMesService;

    @Autowired
    private StandardWorkTimeDownload standardWorkTimeDownload;

    @Value("#{contextParameters[pageTitle] ?: ''}")
    private String pageTitle;

    private final int failMaxAllow = 5;

    @Autowired
    private WorktimeService worktimeService;

    // keep all transactions in one session, prevent from detash and extra select, also enable lazy-fetch.
    @Transactional
    public void syncModelFromM9ie() {
        List<String> errorMessages = new ArrayList();

        List<M9ieWorktimeView> views = m9ieWorktimeViewService.findAll();
        Map<String, List> listMap = worktimeDownloadMesService.filterByViewAndSetBg(views);

        List<Worktime> wtIn = listMap.get("wtIn");
        List<Worktime> wtNew = listMap.get("wtNew");
        List<Worktime> wtRemove = listMap.get("wtRemove");

//        log.info("Begin add Worktime : " + wtNew.size() + " datas.");
//        if (!wtNew.isEmpty()) {
//            worktimeDownloadMesService.portParamInit();
//            worktimeDownloadMesService.insertMesDL(wtNew);
//        }
//
        log.info("Begin udpate Worktime standardTime : " + wtIn.size() + " datas.");
        int failCount = 0;
        standardWorkTimeDownload.initOptions();
        for (Worktime w : wtIn) {
            try {
                log.info("Start to download: " + w.getModelName());

                standardWorkTimeDownload.download(w);
//                w.setWorktimeModReason("sync work-time.");

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
        worktimeService.mergeWithoutUpload(wtIn);

//        log.info("Begin delete Worktime : " + wtRemove.size() + " datas.");
//        try {
//            worktimeService.deleteWithMesUpload(wtRemove);
//        } catch (Exception e) {
//            String errorMessage = "Delete Worktime fail: " + e.getMessage();
//            errorMessages.add(errorMessage);
//            log.error(errorMessage);
//        }
//
        this.notifyUser(errorMessages);

        log.info("Sync wtFromM9ie job finished.");
    }

    private void notifyUser(List<String> l) {
        String[] to = getMailByNotification("worktime_sync_alarm");
        String[] cc = new String[0];

        String subject = "【" + pageTitle + "系統訊息】M9_3F大表機種工時下載";
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
