/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.helper;

import java.io.InputStream;
import static java.lang.System.out;
import java.util.Properties;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wei.Cheng
 */
public class PropertiesReader {

    private static final Logger log = LoggerFactory.getLogger(PropertiesReader.class);
    private static PropertiesReader p;

    private String testMail, mailServerUsername, mailServerPassword, mailServerLocation, mailServerPort;

    private double testStandard, babStandard, balanceDiff;

    private JSONObject systemAbnormalAlarmMailCC;
    private String systemAbnormalAlarmMailTo;

    private int numLampMaxTestRequiredPeople, numLampGroupStart, numLampGroupEnd;
    private int maxTestTable, babSaveToRecordStandardQuantity, balanceRoundingDigit;

    private boolean writeToDB, saveToOldDB, sendMailAlarmUser;

    private String endpointQuartzTrigger;

    private PropertiesReader() throws Exception {
        dataInit();
    }

    public static PropertiesReader getInstance() {
        if (p == null) {
            try {
                p = new PropertiesReader();
            } catch (Exception ex) {
                out.println("Can't read the property file.");
                log.error("Can't read the property file.");
            }
        }
        return p;
    }

    private void dataInit() throws Exception {
        String configFile = "/options.properties";
        Properties properties = new Properties();
        InputStream is = this.getClass().getResourceAsStream(configFile);
        properties.load(is);

        loadParams(properties);

        is.close();
        properties.clear();
    }

    private void loadParams(Properties properties) {
        maxTestTable = convertStringToInteger(properties.getProperty("test.maxTable"));
        numLampMaxTestRequiredPeople = convertStringToInteger(properties.getProperty("numLamp.test.maxRequiredPeople"));
        numLampGroupStart = convertStringToInteger(properties.getProperty("numLamp.balanceDetect.groupStart"));
        numLampGroupEnd = convertStringToInteger(properties.getProperty("numLamp.balanceDetect.groupEnd"));
        babSaveToRecordStandardQuantity = convertStringToInteger(properties.getProperty("bab.saveToRecord.quantity"));
        testStandard = convertStringToDouble(properties.getProperty("test.productivity.standard"));
        babStandard = convertStringToDouble(properties.getProperty("bab.lineBalance.standard"));
        balanceRoundingDigit = convertStringToInteger(properties.getProperty("bab.lineBalance.roundingDigit"));
        balanceDiff = convertStringToDouble(properties.getProperty("bab.lineBalance.difference"));

        testMail = properties.getProperty("mail.testMail");
        mailServerUsername = properties.getProperty("mail.server.username");
        mailServerPassword = properties.getProperty("mail.server.password");
        mailServerPort = properties.getProperty("mail.server.port");
        mailServerLocation = properties.getProperty("mail.server.location");

        writeToDB = convertStringToBoolean(properties.getProperty("result.write.to.database"));
        saveToOldDB = convertStringToBoolean(properties.getProperty("result.save.to.oldServer"));
        sendMailAlarmUser = convertStringToBoolean(properties.getProperty("send.mail.alarm.user"));
        endpointQuartzTrigger = properties.getProperty("endpoint.quartz.trigger");

        systemAbnormalAlarmMailTo = properties.getProperty("systemAbnormalAlarm.mailTo");
        systemAbnormalAlarmMailCC = new JSONObject(properties.getProperty("systemAbnormalAlarm.mailCC"));

        logTheSystemSetting();
    }

    private void logTheSystemSetting() {
        out.println("Set test lineType standard is : " + testStandard);
        out.println("Set bab lineType standard is : " + babStandard);
        out.println("Set balanceDiff(Need to send mail when balance is diff to prev input bab) is : " + balanceDiff);
        out.println("The mail info setting -> : "
                + new JSONObject()
                .put("mailServerUsername", mailServerUsername)
                .put("mailServerPassword", mailServerPassword)
                .put("mailServerLocation", mailServerLocation)
                .put("mailServerPort", mailServerPort)
                .toString()
        );
        out.println("Need to send mail when system abnormal? : " + sendMailAlarmUser);
        out.println("Abnormal mail setting : "
                + new JSONObject()
                .put("systemAbnormalAlarmMailTo", systemAbnormalAlarmMailTo)
                .put("systemAbnormalAlarmMailCC", systemAbnormalAlarmMailCC)
        );
        out.println("The max table setting in test lineType is : " + maxTestTable);
        out.println("The max test required people in test lineType is  : " + numLampMaxTestRequiredPeople);
        out.println("The minimum data collection need to save to database : " + babSaveToRecordStandardQuantity);
        out.println("The balance rounding digit is : " + balanceRoundingDigit);
        out.println("Other save result setting : "
                + new JSONObject()
                .put("writeToDB", writeToDB)
                .put("saveToOldDB", saveToOldDB)
        );

        out.println("The endpoint quartz trigger : " + endpointQuartzTrigger);
    }

    private int convertStringToInteger(String number) {
        return (number != null && !"".equals(number)) ? Integer.parseInt(number) : 0;
    }

    private double convertStringToDouble(String number) {
        return (number != null && !"".equals(number)) ? Double.parseDouble(number) : 0;
    }

    private boolean convertStringToBoolean(String str) {
        return (str != null && !"".equals(str)) ? str.equals("true") : false;
    }

    public double getTestStandard() {
        return testStandard;
    }

    public double getBabStandard() {
        return babStandard;
    }

    public double getBalanceDiff() {
        return balanceDiff;
    }

    public String getSystemAbnormalAlarmMailTo() {
        return systemAbnormalAlarmMailTo;
    }

    public JSONObject getSystemAbnormalAlarmMailCC() {
        return systemAbnormalAlarmMailCC;
    }

    public String getTestMail() {
        return testMail;
    }

    public int getMaxTestTable() {
        return maxTestTable;
    }

    public int getNumLampMaxTestRequiredPeople() {
        return numLampMaxTestRequiredPeople;
    }

    public int getNumLampGroupStart() {
        return numLampGroupStart;
    }

    public int getNumLampGroupEnd() {
        return numLampGroupEnd;
    }

    public int getBabSaveToRecordStandardQuantity() {
        return babSaveToRecordStandardQuantity;
    }

    public int getBalanceRoundingDigit() {
        return balanceRoundingDigit;
    }

    public boolean isWriteToDB() {
        return writeToDB;
    }

    public boolean isSaveToOldDB() {
        return saveToOldDB;
    }

    public String getMailServerUsername() {
        return mailServerUsername;
    }

    public String getMailServerPassword() {
        return mailServerPassword;
    }

    public String getMailServerLocation() {
        return mailServerLocation;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public boolean isSendMailAlarmUser() {
        return sendMailAlarmUser;
    }

    public String getEndpointQuartzTrigger() {
        return endpointQuartzTrigger;
    }

}
