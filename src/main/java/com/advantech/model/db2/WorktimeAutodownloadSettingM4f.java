/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.model.db2;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "Worktime_Autodownload_Setting")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorktimeAutodownloadSettingM4f implements java.io.Serializable {

    private int id;
    private String columnName;
    private String formulaColumn;
    private String columnUnit;
    private Integer stationId;
    private int lineId;
    private String formulaTotalct;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "column_name", nullable = false, length = 50)
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Column(name = "formula_column", nullable = false, length = 500)
    public String getFormulaColumn() {
        return formulaColumn;
    }

    public void setFormulaColumn(String formulaColumn) {
        this.formulaColumn = formulaColumn;
    }

    @Column(name = "column_unit", nullable = false, length = 50)
    public String getColumnUnit() {
        return columnUnit;
    }

    public void setColumnUnit(String columnUnit) {
        this.columnUnit = columnUnit;
    }

    @Column(name = "station_id", nullable = false)
    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Column(name = "line_id")
    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    @Column(name = "formula_totalct", nullable = false, length = 500)
    public String getFormulaTotalct() {
        return formulaTotalct;
    }

    public void setFormulaTotalct(String formulaTotalct) {
        this.formulaTotalct = formulaTotalct;
    }

}
