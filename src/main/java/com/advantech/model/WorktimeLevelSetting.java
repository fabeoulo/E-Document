/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
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
@Table(name = "Worktime_LevelSetting")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorktimeLevelSetting implements java.io.Serializable, WorktimeSettingBase {

    private int id;
    private Worktime worktime;
    private int arFilmAttachment = 0;
    private int seal = 0;
    private int opticalBonding = 0;
    private int cleanPanel = 0;
    private int pi = 0;
    private int highBright = 0;
    private int assy = 0;
    private int bondedSealingFrame = 0;
    private int t1 = 0;
    private int t2 = 0;
    private int t3 = 0;
    private int packing = 0;
    private int upBiRi = 0;
    private int downBiRi = 0;
    private int biCost = 0;

    public WorktimeLevelSetting() {
    }

    public WorktimeLevelSetting(Worktime worktime) {
        this.worktime = worktime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktime_id", nullable = true)
    public Worktime getWorktime() {
        return worktime;
    }

    public void setWorktime(Worktime worktime) {
        this.worktime = worktime;
    }

    @NotNull
    public int getArFilmAttachment() {
        return arFilmAttachment;
    }

    public void setArFilmAttachment(int arFilmAttachment) {
        this.arFilmAttachment = arFilmAttachment;
    }

    @NotNull
    public int getSeal() {
        return seal;
    }

    public void setSeal(int seal) {
        this.seal = seal;
    }

    @NotNull
    public int getOpticalBonding() {
        return opticalBonding;
    }

    public void setOpticalBonding(int opticalBonding) {
        this.opticalBonding = opticalBonding;
    }

    @Column(name = "clean_panel", nullable = false)
    public int getCleanPanel() {
        return cleanPanel;
    }

    public void setCleanPanel(int cleanPanel) {
        this.cleanPanel = cleanPanel;
    }

    @NotNull
    public int getPi() {
        return pi;
    }

    public void setPi(int pi) {
        this.pi = pi;
    }

    @Column(name = "high_bright", nullable = false)
    public int getHighBright() {
        return highBright;
    }

    public void setHighBright(int highBright) {
        this.highBright = highBright;
    }

    @NotNull
    public int getAssy() {
        return assy;
    }

    public void setAssy(int assy) {
        this.assy = assy;
    }

    @Column(name = "bonded_sealing_frame", nullable = false)
    public int getBondedSealingFrame() {
        return bondedSealingFrame;
    }

    public void setBondedSealingFrame(int bondedSealingFrame) {
        this.bondedSealingFrame = bondedSealingFrame;
    }

    @NotNull
    public int getT1() {
        return t1;
    }

    public void setT1(int t1) {
        this.t1 = t1;
    }

    @NotNull
    public int getT2() {
        return t2;
    }

    public void setT2(int t2) {
        this.t2 = t2;
    }

    @NotNull
    public int getT3() {
        return t3;
    }

    public void setT3(int t3) {
        this.t3 = t3;
    }

    @NotNull
    public int getPacking() {
        return packing;
    }

    public void setPacking(int packing) {
        this.packing = packing;
    }

    @Column(name = "up_bi_ri", nullable = false)
    public int getUpBiRi() {
        return upBiRi;
    }

    public void setUpBiRi(int upBiRi) {
        this.upBiRi = upBiRi;
    }

    @Column(name = "down_bi_ri", nullable = false)
    public int getDownBiRi() {
        return downBiRi;
    }

    public void setDownBiRi(int downBiRi) {
        this.downBiRi = downBiRi;
    }

    @Column(name = "bi_cost", nullable = false)
    public int getBiCost() {
        return biCost;
    }

    public void setBiCost(int biCost) {
        this.biCost = biCost;
    }

    public Map<String, Integer> retriveIntPropertiesMap() {
        Map<String, Integer> settingMap = new HashMap();

        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == int.class) {
                try {
                    field.setAccessible(true); // Allow access to private fields
                    settingMap.put(field.getName(), field.getInt(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return settingMap;
    }
}
