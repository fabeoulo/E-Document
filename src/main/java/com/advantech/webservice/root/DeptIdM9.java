/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.root;

/**
 *
 * @author Justin.Yeh
 */
public enum DeptIdM9 {
    SPE(17),
    EE(53),
    QC(15),
    MPM(65),
    SPE3F(19),
    EE3F(17);

    private final Integer code;

    private DeptIdM9(final Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public static DeptIdM9 getByCode(Integer code) {
        for (DeptIdM9 v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
