/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Justin.Yeh
 */
@Entity
@Table(name = "SuggestionWorktime_Down_History")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SuggestionWorktimeHistory implements Serializable {

    private int id;
    private String mesUnit;
    private String modelName;
    private String station;
    private String status;
    private String statusMessage;
    private String workCenter;
    private BigDecimal workTime = BigDecimal.ZERO;
    private BigDecimal workTimeDown = BigDecimal.ZERO;
    private Date createDate;
    private String memo;

    private BigDecimal revisedDownPercent;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    @Column(name = "mes_unit", nullable = false, length = 5)
    public String getMesUnit() {
        return mesUnit;
    }

    public void setMesUnit(String mesUnit) {
        this.mesUnit = mesUnit;
    }

    @NotNull
    @NotEmpty
    @Size(min = 0, max = 50)
    @Column(name = "model_name", nullable = false, length = 50)
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @NotNull
    @NotEmpty
    @Column(name = "station", nullable = false, length = 50)
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @NotNull
    @NotEmpty
    @Column(name = "status", nullable = false, length = 5)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Size(max = 50)
    @Column(name = "status_message", nullable = true, length = 50)
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Column(name = "workCenter", nullable = true, length = 50)
    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    @NotNull
    @Column(name = "workTime", nullable = false, precision = 16, scale = 6)
    public BigDecimal getWorkTime() {
        return workTime;
    }

    public void setWorkTime(BigDecimal workTime) {
        this.workTime = workTime;
    }

    @NotNull
    @Column(name = "workTime_down", nullable = false, precision = 16, scale = 6)
    public BigDecimal getWorkTimeDown() {
        return workTimeDown;
    }

    public void setWorkTimeDown(BigDecimal workTimeDown) {
        this.workTimeDown = workTimeDown;
    }

    @NotNull
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, length = 23, updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "memo", length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Transient
    public BigDecimal getRevisedDownPercent() {
        return revisedDownPercent;
    }

    public void setRevisedDownPercent(BigDecimal revisedDownPercent) {
        this.revisedDownPercent = revisedDownPercent;
    }

}
