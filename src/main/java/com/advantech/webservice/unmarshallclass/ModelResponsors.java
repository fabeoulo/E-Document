/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.unmarshallclass;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wei.Cheng
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "NewDataSet")
public class ModelResponsors implements Serializable, QueryResult<ModelResponsor> {

    @XmlElement(name = "Table1", type = ModelResponsor.class)
    private List<ModelResponsor> QryData;

    @Override
    public List<ModelResponsor> getQryData() {
        return QryData;
    }

    @Override
    public void setQryData(List<ModelResponsor> QryData) {
        this.QryData = QryData;
    }
}
