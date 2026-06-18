/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.quartzJob;

import com.advantech.helper.HostUtils;
import com.advantech.helper.MailManager;
import com.advantech.model.User;
import com.advantech.model.UserNotification;
import com.advantech.service.UserNotificationService;
import com.advantech.webapi.EmailApiClient;
import com.advantech.webapi.model.EmailModel;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Justin.Yeh
 */
public abstract class SendEmailBase {

    @Autowired
    protected MailManager manager;

    @Autowired
    private UserNotificationService notificationService;

    @Autowired
    protected EmailApiClient emailApiClient;

    protected final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/M/d");

    @Value("${app.email.area:3F}")
    protected String titleFloor;

    @Value("${app.base-url.port:8080}")
    private String webAppPort;

    @Autowired
    private ServletContext servletContext;

    protected boolean sendByApi(String[] toAddresses, String[] ccAddresses, String subject, String body) {
        return sendByApi(toAddresses, ccAddresses, subject, body, null);
    }

    protected boolean sendByApi(String[] toAddresses, String[] ccAddresses, String subject, String body, String setFromAddress) {
        if (HostUtils.isServer()) {
            EmailModel emailModel = new EmailModel(toAddresses, ccAddresses, subject, body, setFromAddress);
            return emailApiClient.sendEmail(emailModel);
        }
        return false;
    }

    protected String[] findEmailByNotifyId(Integer id) {
        UserNotification notifi = notificationService.findByPrimaryKey(id);
        Set<User> l = notifi.getUsers();
        return l.stream().map(u -> u.getEmail()).toArray(size -> new String[size]);
    }

    protected String[] findEmailByNotify(String name) {
        List<User> users = notificationService.findUsersByNotification(name);
        return users.stream().map(u -> u.getEmail()).toArray(size -> new String[size]);
    }

    protected String getBaseUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(HostUtils.getHostName());
        sb.append(":");
        sb.append(webAppPort);
        sb.append(servletContext.getContextPath());
        sb.append("/pages/requisition/");

        return sb.toString();
    }
}
