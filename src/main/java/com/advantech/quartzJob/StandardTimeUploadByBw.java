/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.quartzJob;

import com.advantech.helper.HostUtils;
import com.advantech.helper.MailManager;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.SuggestionWorktimeHistory;
import com.advantech.model.Worktime;
import com.advantech.model3.WorktimeReviseddownHistory;
import com.advantech.service.SqlViewService;
import com.advantech.service.SuggestionWorktimeHistoryService;
import com.advantech.service.WorktimeService;
import com.advantech.service.db3.WorktimeReviseddownHistoryService;
import com.advantech.webservice.port.StandardtimeUploadPort;
import com.advantech.webservice.root.Section;
import com.google.common.collect.Lists;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class StandardTimeUploadByBw extends SendEmailBase {

    private static final Logger log = LoggerFactory.getLogger(StandardTimeUploadByBw.class);

    @Autowired
    private SqlViewService sqlViewService;

    @Autowired
    private WorktimeService worktimeService;

    @Autowired
    private StandardtimeUploadPort port;

    @Autowired
    private SuggestionWorktimeHistoryService suggestionWorktimeHistoryService;

    @Autowired
    private WorktimeReviseddownHistoryService worktimeReviseddownHistoryService;

    private final DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");

    private PageInfo tempInfo;

    @Autowired
    private MailManager mailManager;

    @Value("#{contextParameters[pageTitle] ?: ''}")
    private String pageTitle;

    private List<SuggestionWorktimeHistory> viewUpdated_s, viewUpdated_f;

    private List<String> errorMessages;

    @PostConstruct
    public void init() {
        tempInfo = new PageInfo();
        tempInfo.setRows(-1);
        tempInfo.setSearchField("modelName");
        tempInfo.setSearchOper("in");
    }

    @Transactional
    public void uploadToMes() {
        if (!HostUtils.isServer()) {
            return;
        }

        viewUpdated_s = Lists.newArrayList();
        viewUpdated_f = Lists.newArrayList();
        errorMessages = Lists.newArrayList();

        List<Map> view = GetSuggestionWorkTimeAndSend();

        CheckAndSaveViewLocal(view);

        SaveViewRemoteAndSend();

        this.notifySystem(errorMessages);

        log.info("Upload standardtime job finished.");
    }

    private List<Map> GetSuggestionWorkTimeAndSend() {
        List<Map> view = sqlViewService.findSuggestionWorkTime();
        this.notifyUserOgBw(view);
        return view;
    }

    private void CheckAndSaveViewLocal(List<Map> view) {

        String models = view.stream().map(m -> (String) m.get("modelName"))
                .filter(Objects::nonNull)
                .collect(Collectors.joining(","));

        this.updatePageInfo(models);
        Map<String, Worktime> worktimeMap = worktimeService.findAll(tempInfo)
                .stream()
                .collect(Collectors.toMap(w -> w.getModelName(), w -> w, (a, b) -> a));

        port.initSettings();
        DateTime dt = new DateTime();

        view.forEach(m -> {
            String model = (String) m.get("modelName");
            String bwStation = (String) m.get("station");
            String station = convertStation(bwStation);
            String mesUnit = convertToMesUnit(bwStation);
            BigDecimal wt = (BigDecimal) m.get("standardTime");
            BigDecimal wtSuggest = (BigDecimal) m.get("suggestSt");

            SuggestionWorktimeHistory poj = new SuggestionWorktimeHistory();
            poj.setCreateDate(dt.toDate());
            poj.setMemo("from M6大表");
            poj.setModelName(model);
            poj.setStation(bwStation);
            poj.setMesUnit(mesUnit);
            poj.setWorkTime(wt);
            poj.setWorkTimeDown(wtSuggest);

            Worktime worktime = worktimeMap.get(model);
            if (worktime == null || wt.compareTo(BigDecimal.ZERO) <= 0) {
                poj.setStatus("F");
                poj.setStatusMessage("查無機種或工時為零");
                viewUpdated_f.add(poj);
                return;
            } else {
                poj.setWorkCenter(worktime.getWorkCenter().getName());
            }

            try {
                PropertyDescriptor pd = new PropertyDescriptor(station, worktime.getClass());
                Class type = pd.getPropertyType();
                BigDecimal v = (BigDecimal) pd.getReadMethod().invoke(worktime);

                if (BigDecimal.class.equals(type) && wt.compareTo(v) == 0) {
                    pd.getWriteMethod().invoke(worktime, wtSuggest);

                    worktimeService.updateWithoutMesUpload(worktime);

                    port.update(worktime);
                    log.info("Upload standardtime of model " + worktime.getModelName() + " on station " + station);

                    BigDecimal percent = wt.compareTo(BigDecimal.ZERO) > 0
                            ? wt.subtract(wtSuggest).abs().multiply(BigDecimal.valueOf(100))
                                    .divide(wt, 2, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

                    poj.setRevisedDownPercent(percent);
                    poj.setStatus("S");
                    viewUpdated_s.add(poj);

                } else {
                    poj.setStatus("F");
                    poj.setStatusMessage("工時不符");
                    viewUpdated_f.add(poj);
                }

            } catch (Exception e) {
                String errorMessage = worktime.getModelName() + " on station " + station + " upload fail: " + e.getMessage();
                errorMessages.add(errorMessage);
                log.error(errorMessage);
            }
        });
    }

    private void SaveViewRemoteAndSend() {
        try {
            suggestionWorktimeHistoryService.insert(viewUpdated_s);
            suggestionWorktimeHistoryService.insert(viewUpdated_f);

            this.saveRemote(viewUpdated_s);
            this.saveRemote(viewUpdated_f);

            this.notifyUser();

        } catch (Exception e) {
            String errorMessage = "Db fail: " + e.getMessage();
            log.error(errorMessage);
        }
    }

    private void updatePageInfo(String models) {
        tempInfo.setSearchString(models);
    }

    private String convertStation(String station) {
        String fieldName = "";

        if (station != null) {
            switch (station.toUpperCase()) {
                case "T1":
                    fieldName = "t1";
                    break;
                case "T2":
                    fieldName = "t2";
                    break;
                case "T3":
                    fieldName = "t3";
                    break;
                case "SL":
                    fieldName = "seal";
                    break;
                case "SL1":
                    fieldName = "seal1";
                    break;
                case "ASSY1":
                    fieldName = "bondedSealingFrame";
                    break;
                case "ASSY2":
                    fieldName = "assy2";
                    break;
                case "OPTICAL BONDING":
                    fieldName = "opticalBonding";
                    break;
                case "OB1":
                    fieldName = "opticalBonding1";
                    break;
                case "OB2":
                    fieldName = "opticalBonding2";
                    break;
                case "ASSY":
                    fieldName = "assy";
                    break;
                case "PACKAGE":
                    fieldName = "packing";
                    break;
                case "CLEANPANEL":
                    fieldName = "cleanPanel";
                    break;
                case "PRE_ASSY":
                    fieldName = "pi";
                    break;
                default:
            }
        }
        return fieldName;
    }

    private String convertToMesUnit(String station) {
        String flowName = "";

        if (station != null) {
            switch (station.toUpperCase()) {
                case "T1":
                case "T2":
                case "T3":
                    flowName = Section.TEST.getCode();
                    break;
                case "SL":
                case "SL1":
                case "ASSY1":
                case "ASSY2":
                case "OPTICAL BONDING":
                case "OB1":
                case "OB2":
                case "ASSY":
                case "CLEANPANEL":
                    flowName = Section.BAB.getCode();
                    break;
                case "PACKAGE":
                    flowName = Section.PACKAGE.getCode();
                    break;
                case "PRE_ASSY":
                    flowName = Section.PREASSY.getCode();
                    break;
                default:
            }
        }
        return flowName;
    }

    private void notifyUserOgBw(List<Map> viewBw) {
        String[] to = super.findEmailByNotify("worktime_downgrade_alarm");
        String[] cc = super.findEmailByNotify("worktime_downgrade_alarm_cc");

        String subject = "【" + pageTitle + "系統訊息】MH12工時下修清單";
        String text = generateTextBodyOgBw(viewBw);

        try {
            mailManager.sendMail(to, cc, subject, text, "【" + pageTitle + "系統訊息】");

//            // debug
//            super.sendByApi(to, cc, subject, text, "【" + pageTitle + "系統訊息】");
        } catch (Exception ex) {
            log.error("Send mail fail when upload mes job fail." + ex.toString());
        }
    }

    private String generateTextBodyOgBw(List<Map> viewBw) {
        StringBuilder sb = new StringBuilder();

        //設定mail格式(css...etc)
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<style>");
        sb.append("table {border-collapse: collapse; padding:5px; vertical-align: middle; text-align: center;}");
        sb.append("table, th, td {border: 1px solid black;}");
        sb.append("table th {background-color: #4c68a2; color: White; min-width:100px;}");
        sb.append("#mailBody {font-family: 微軟正黑體;}");
        sb.append(".highlight {background-color: yellow;}");
        sb.append(".m3 {background-color: #FFDAC8;}");
        sb.append(".rightAlign {text-align:right;}");
        sb.append(".total {font-weight: bold;}");
        sb.append("</style>");
        sb.append("<div id='mailBody'>");
        sb.append("<h3>Dears:</h3>");

        sb.append("<table>");
        sb.append("<tr><th colspan='6'>");
        sb.append("<h1>工時下修清單</h1>");
        sb.append("</th></tr>");
        sb.append("<tr>");
        sb.append("<th width='35%'>料號</th>");
        sb.append("<th>站別</th>");
        sb.append("<th>數量</th>");
        sb.append("<th>原標工(min)</th>");
        sb.append("<th>平均工時</th>");
        sb.append("<th>調整後工時</th>");
        sb.append("</tr>");

        for (int i = 0; i < viewBw.size(); i++) {
            Map m = viewBw.get(i);

            sb.append("<tr>");

            sb.append("<td>");
            sb.append(m.get("modelName"));
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.get("station"));
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.get("totalCnt"));
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.get("standardTime"));
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.get("opTime"));
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.get("suggestSt"));
            sb.append("</td>");

            sb.append("</tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }

//    @Transactional("transactionManager3")
    private void saveRemote(List<SuggestionWorktimeHistory> m6History) {
        List<WorktimeReviseddownHistory> l = m6History.stream()
                .map(m6h -> new WorktimeReviseddownHistory(m6h))
                .collect(Collectors.toList());
        worktimeReviseddownHistoryService.insert(l);
    }

    private void notifyUser() {
        String[] to = super.findEmailByNotify("worktime_downgrade_alarm");
        String[] cc = super.findEmailByNotify("worktime_downgrade_alarm_cc");

        String subject = "【" + pageTitle + "系統訊息】工時自動下修";
        String text = generateTextBody();

        try {
            mailManager.sendMail(to, cc, subject, text, "【" + pageTitle + "系統訊息】");

//            // debug
//            super.sendByApi(to, cc, subject, text, "【" + pageTitle + "系統訊息】");
        } catch (Exception ex) {
            log.error("Send mail fail when upload mes job fail." + ex.toString());
        }
    }

    private String generateTextBody() {
        StringBuilder sb = new StringBuilder();

        //設定mail格式(css...etc)
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<style>");
        sb.append("table {border-collapse: collapse; padding:5px; vertical-align: middle; text-align: center;}");
        sb.append("table, th, td {border: 1px solid black;}");
        sb.append("table th {background-color: CornflowerBlue; min-width:100px;}");
        sb.append("#mailBody {font-family: 微軟正黑體;}");
        sb.append(".highlight {background-color: yellow;}");
        sb.append(".m3 {background-color: #FFDAC8;}");
        sb.append(".rightAlign {text-align:right;}");
        sb.append(".total {font-weight: bold;}");
        sb.append("</style>");
        sb.append("<div id='mailBody'>");
        sb.append("<h3>Dears:</h3>");

        sb.append("<table>");
        sb.append("<tr><th colspan='8'>");
        sb.append("<h1>工時自動下修</h1>");
        sb.append("</th></tr>");
        sb.append("<tr>");
        sb.append("<th>修改日期</th>");
        sb.append("<th>製程</th>");
        sb.append("<th>工作中心</th>");
        sb.append("<th width='35%'>料號</th>");
        sb.append("<th>站別</th>");
        sb.append("<th>原標工(min)</th>");
        sb.append("<th>調整後工時</th>");
        sb.append("<th style='background-color: Gold'>REVISED DOWN(%)</th>");
        sb.append("</tr>");

        for (int i = 0; i < viewUpdated_s.size(); i++) {
            SuggestionWorktimeHistory m = viewUpdated_s.get(i);

//            if (i % 2 == 0) {
//                sb.append("<tr class='m3'>");
//            } else {
//                sb.append("<tr>");
//            }
            sb.append("<tr>");

            sb.append("<td>");
            sb.append(df.print(new DateTime(m.getCreateDate())));
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getMesUnit());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getWorkCenter());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getModelName());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getStation());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getWorkTime());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getWorkTimeDown());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getRevisedDownPercent());
            sb.append("</td>");

            sb.append("</tr>");
        }

        sb.append("</table><br/><br/><br/><br/>");

        sb.append("<table>");
        sb.append("<tr><th colspan='4'>");
        sb.append("警示區");
        sb.append("</th></tr>");
        sb.append("<tr>");
        sb.append("<th>工作中心</th>");
        sb.append("<th width='35%'>料號</th>");
        sb.append("<th>工時平台標工</th>");
        sb.append("<th>說明</th>");
        sb.append("</tr>");

        for (int i = 0; i < viewUpdated_f.size(); i++) {
            SuggestionWorktimeHistory m = viewUpdated_f.get(i);

//            if (i % 2 == 0) {
//                sb.append("<tr class='m3'>");
//            } else {
//                sb.append("<tr>");
//            }
            sb.append("<tr>");

            sb.append("<td>");
            sb.append(m.getWorkCenter());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getModelName());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getWorkTime());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(m.getStatusMessage());
            sb.append("</td>");

            sb.append("</tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }

    private void notifySystem(List<String> l) {
        String[] to = super.findEmailByNotify("worktime_ie_alarm_cc");
        String[] cc = super.findEmailByNotify("worktime_ie_alarm_cc");

        String subject = "工時自動下修System";
        String text = generateTextBodySystem(l);

        try {
            mailManager.sendMail(to, cc, subject, text, "【" + pageTitle + "系統訊息】");

//            // debug
//            super.sendByApi(to, cc, subject, text, "【" + pageTitle + "系統訊息】");
        } catch (Exception ex) {
            log.error("Send mail fail when upload mes job fail." + ex.toString());
        }
    }

    private String generateTextBodySystem(List<String> l) {
        StringBuilder sb = new StringBuilder();
        if (l.isEmpty()) {
            sb.append("<p>工時自動下修成功於下列時間</p><p>");
            sb.append(new DateTime());
            sb.append("</p>");
        } else {
            sb.append("<p>工時自動下修發生了異常，訊息清單如下</p>");
            for (String st : l) {
                sb.append("<p>");
                sb.append(st);
                sb.append("</p>");
            }
            sb.append("<p>請相關人員至系統確認大表設定是否正確。</p>");
        }
        return sb.toString();
    }

}
