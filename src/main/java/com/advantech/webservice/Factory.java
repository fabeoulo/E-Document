/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.advantech.webservice;

import com.advantech.converter.Encodeable;
import java.util.HashMap;
import java.util.Map;

/**
 * 不只webservice在用
 *
 * @author Justin.Yeh
 */
public enum Factory implements Encodeable {

    TWM2("M2"),
    TWM3("M3"),
    TWM6("M6"),
    TWM8("M8"),
    TWM9("M9"),
    M9WH("WH"),
    TWM3_OG("M3"),
    TWM9_OG("M9");

    private final String s;
    private static final Map<String, Factory> map = new HashMap<>();//getEnum("PD03") return TWM6

    static {
        for (Factory f : Factory.values()) {
            map.put(f.s, f);
        }
    }

    private Factory(final String s) {
        this.s = s;
    }

    @Override
    public Object token() {
        return this.s;
    }

    @Override
    public String toString() {
        return this.s;
    }

    public static Factory getEnum(String t) {
        Factory f = map.get(t);
        if (f != null) {
            return f;
        }
        throw new IllegalArgumentException("Can't find enum with value " + t);
    }

    public String getName() {
        return super.toString();
    }
}
