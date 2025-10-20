/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.model2;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Justin.Yeh
 */
@Entity
@Table(name = "Worktime_MaterialPropertyDownload_Setting",
        uniqueConstraints = @UniqueConstraint(columnNames = "mat_prop_no")
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorktimeMaterialPropertyDownloadSettingM4f implements java.io.Serializable {

    private int id;
    private String columnName;
    private String matPropNo;
    private String dlColumn;
    private String dlFormula;
    private String dlAffColumn;
    private String dlAffFormula;
    private String dlColumn2;
    private String dlFormula2;

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

    @Column(name = "mat_prop_no", length = 50)
    public String getMatPropNo() {
        return matPropNo;
    }

    public void setMatPropNo(String matPropNo) {
        this.matPropNo = matPropNo;
    }

    @NotNull
    @Column(name = "dl_column", nullable = false, length = 50)
    public String getDlColumn() {
        return dlColumn;
    }

    public void setDlColumn(String dlColumn) {
        this.dlColumn = dlColumn;
    }

    @NotNull
    @Column(name = "dl_formula", nullable = false, length = 500)
    public String getDlFormula() {
        return dlFormula;
    }

    public void setDlFormula(String dlFormula) {
        this.dlFormula = dlFormula;
    }

    @Column(name = "dl_aff_column", length = 50)
    public String getDlAffColumn() {
        return dlAffColumn;
    }

    public void setDlAffColumn(String dlAffColumn) {
        this.dlAffColumn = dlAffColumn;
    }

    @Column(name = "dl_aff_fomula", length = 500)
    public String getDlAffFormula() {
        return dlAffFormula;
    }

    public void setDlAffFormula(String dlAffFormula) {
        this.dlAffFormula = dlAffFormula;
    }


    @NotNull
    @Column(name = "dl_column_2", length = 50)
    public String getDlColumn2() {
        return dlColumn2;
    }

    public void setDlColumn2(String dlColumn2) {
        this.dlColumn2 = dlColumn2;
    }

    @NotNull
    @Column(name = "dl_formula_2", length = 500)
    public String getDlFormula2() {
        return dlFormula2;
    }

    public void setDlFormula2(String dlFormula2) {
        this.dlFormula2 = dlFormula2;
    }

}
