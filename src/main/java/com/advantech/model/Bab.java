/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.model;

import java.io.Serializable;
import org.json.JSONArray;

/**
 *
 * @author Wei.Cheng
 */
//@Entity
public class Bab implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String PO;
    private String Model_name;
    private int line;
    private int people;
    private int startPosition;
    private int isused;
    private String name;
    private String lineName;
    private String linetype;
    private String btime;
    private String lastUpdateTime;
    private int ispre = 0;

    // check countermeasure is exist or not
    private Integer cm_id; 

    //for saving line balance data, not exist in the database
    private JSONArray babavgs;

    public Bab() {

    }

    public Bab(String PO, String Model_name, int line, int people, int startPosition, int ispre) {
        this.PO = PO;
        this.Model_name = Model_name;
        this.line = line;
        this.people = people;
        this.startPosition = startPosition;
        this.ispre = ispre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPO() {
        return PO;
    }

    public void setPO(String PO) {
        this.PO = PO;
    }

    public String getModel_name() {
        return Model_name;
    }

    public void setModel_name(String Model_name) {
        this.Model_name = Model_name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getIsused() {
        return isused;
    }

    public void setIsused(int isused) {
        this.isused = isused;
    }

    public String getName() {
        return name;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinetype() {
        return linetype;
    }

    public void setLinetype(String linetype) {
        this.linetype = linetype;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getIspre() {
        return ispre;
    }

    public void setIspre(int ispre) {
        this.ispre = ispre;
    }

    public JSONArray getBabavgs() {
        return babavgs;
    }

    public void setBabavgs(JSONArray babavgs) {
        this.babavgs = babavgs;
    }

    public Integer getCm_id() {
        return cm_id;
    }

    public void setCm_id(Integer cm_id) {
        this.cm_id = cm_id;
    }

    public boolean isIsBabClosed() {
        return this.isused != 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bab other = (Bab) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
