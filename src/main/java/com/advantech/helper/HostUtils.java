/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.helper;

import java.util.Map;

/**
 *
 * @author Justin.Yeh
 */
public class HostUtils {

    public static String getHostName() {
        String hostName = "";
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
            hostName = env.get("COMPUTERNAME");
        } else if (env.containsKey("HOSTNAME")) {
            hostName = env.get("HOSTNAME");
        }

        return hostName;
    }

    public static boolean isServer() {
        String hostName = getHostName();
        return hostName.contains("IIS") || hostName.contains("IMS");
    }
}
