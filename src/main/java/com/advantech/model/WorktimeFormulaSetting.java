package com.advantech.model;
// Generated 2017/5/31 下午 04:33:40 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * WorktimeFormulaSetting generated by hbm2java
 */
@Entity
@Table(name = "Worktime_FormulaSetting"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorktimeFormulaSetting implements java.io.Serializable {

    private int id;
    private Worktime worktime;
    private int productionWt = 1;
    private int setupTime = 1;
    private int assyStation = 1;
    private int packingStation = 1;

    public WorktimeFormulaSetting() {
    }

    public WorktimeFormulaSetting(Worktime worktime) {
        this.worktime = worktime;
    }

    public WorktimeFormulaSetting(int id, Worktime worktime, int productionWt, int setupTime, int assyStation, int packingStation) {
        this.id = id;
        this.worktime = worktime;
        this.productionWt = productionWt;
        this.setupTime = setupTime;
        this.assyStation = assyStation;
        this.packingStation = packingStation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktime_id", nullable = true)
    public Worktime getWorktime() {
        return this.worktime;
    }

    public void setWorktime(Worktime worktime) {
        this.worktime = worktime;
    }

    @Column(name = "productionWt", nullable = false)
    public int getProductionWt() {
        return this.productionWt;
    }

    public void setProductionWt(int productionWt) {
        this.productionWt = productionWt;
    }

    @Column(name = "setup_time", nullable = false)
    public int getSetupTime() {
        return this.setupTime;
    }

    public void setSetupTime(int setupTime) {
        this.setupTime = setupTime;
    }

    @Column(name = "assy_station", nullable = false)
    public int getAssyStation() {
        return this.assyStation;
    }

    public void setAssyStation(int assyStation) {
        this.assyStation = assyStation;
    }

    @Column(name = "packing_station", nullable = false)
    public int getPackingStation() {
        return this.packingStation;
    }

    public void setPackingStation(int packingStation) {
        this.packingStation = packingStation;
    }

}
