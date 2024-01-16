/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.webapi;

import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class CalculatorApiClient extends BaseApiClient{
    
    public Map<String, String>[] getPreAssyModule() {
        return super.getBufferClient(5)
                .get()
                .uri("/Api/PreAssyModule/getModules")
                .retrieve()
                .bodyToMono(Map[].class)
                .block(REQUEST_TIMEOUT);
    }
}
