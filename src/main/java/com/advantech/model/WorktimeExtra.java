/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Justin.Yeh
 */
@Entity
@Table(name = "Worktime_Extra")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorktimeExtra implements java.io.Serializable {

    private int id;
    private Worktime worktime;
    private String workCenter;
    private WorktimeAutouploadSetting worktimeAutouploadSetting;
    private String process;
    private String item;
    private BigDecimal extraTime = BigDecimal.ZERO;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktime_id", nullable = false)
    public Worktime getWorktime() {
        return worktime;
    }

    public void setWorktime(Worktime worktime) {
        this.worktime = worktime;
    }

    @NotNull
    @Column(name = "workCenter_name", nullable = false, length = 20)
    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktime_autoupload_setting_id", nullable = false)
    public WorktimeAutouploadSetting getWorktimeAutouploadSetting() {
        return worktimeAutouploadSetting;
    }

    public void setWorktimeAutouploadSetting(WorktimeAutouploadSetting worktimeAutouploadSetting) {
        this.worktimeAutouploadSetting = worktimeAutouploadSetting;
    }

    @NotNull
    @Column(name = "process", nullable = false, length = 5)
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @NotNull
    @Column(name = "item", nullable = false, length = 50)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @NotNull()
    @Column(name = "extra_time", nullable = false, precision = 12, scale = 2)
    public BigDecimal getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(BigDecimal extraTime) {
        this.extraTime = extraTime;
    }

}
