/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.model3;

import com.advantech.model.SuggestionWorktimeHistory;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Justin.Yeh
 */
@Entity
@Table(name = "WORKTIME_REVISEDDOWN_HISTORY")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorktimeReviseddownHistory implements Serializable {

    private Long id;
    private String mesUnit;
    private String modelName;
    private String station;
    private String status;
    private String statusMessage;
    private String workCenter;
    private Double workTimeMes;
    private Double workTime;
    private Double workTimeDown;
    private String createUserName;
    private String memo;

    public WorktimeReviseddownHistory() {
    }

    public WorktimeReviseddownHistory(SuggestionWorktimeHistory m6History) {
        this.mesUnit = m6History.getMesUnit();
        this.modelName = m6History.getModelName();
        this.station = m6History.getStation();
        this.status = m6History.getStatus();
        this.statusMessage = m6History.getStatusMessage();
        this.workCenter = m6History.getWorkCenter();
        this.workTimeMes = m6History.getWorkTime().doubleValue();
        this.workTime = m6History.getWorkTime().doubleValue();
        this.workTimeDown = m6History.getWorkTimeDown().doubleValue();
        this.createUserName = "A-0";
        this.memo = m6History.getMemo();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "UNIT_NO", length = 20)
    public String getMesUnit() {
        return mesUnit;
    }

    public void setMesUnit(String mesUnit) {
        this.mesUnit = mesUnit;
    }

    @Column(name = "ITEM_NO", length = 30)
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Column(name = "STATION_NAME", length = 20)
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "MESSAGE", length = 255)
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Column(name = "WORK_CENTER", length = 20)
    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    @Column(name = "MES_CT", nullable = true)
    public Double getWorkTimeMes() {
        return workTimeMes;
    }

    public void setWorkTimeMes(Double workTimeMes) {
        this.workTimeMes = workTimeMes;
    }

    @Column(name = "CT", nullable = true)
    public Double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Double workTime) {
        this.workTime = workTime;
    }

    @Column(name = "REVISED_DOWN_CT", nullable = true)
    public Double getWorkTimeDown() {
        return workTimeDown;
    }

    public void setWorkTimeDown(Double workTimeDown) {
        this.workTimeDown = workTimeDown;
    }

    @Column(name = "CR_USER_NO", length = 50)
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Column(name = "MEMO", length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
