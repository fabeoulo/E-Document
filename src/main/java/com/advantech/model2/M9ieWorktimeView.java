/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Justin.Yeh
 */
@Entity(name = "db2.M9ieWorktimeView")
@Table(name = "vw_Atmc_M9ie_Worktime")
public class M9ieWorktimeView implements java.io.Serializable {

    private int id;
    private String modelName;
    private String workCenter;

    public M9ieWorktimeView() {
    }

    @Id
    @Column(name = "pk_no", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "model_name", length = 50)
    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Column(name = "work_center", length = 20)
    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

}
