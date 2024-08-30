/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.model.db2;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Wei.Cheng
 */
@Entity
@Table(name = "BusinessGroup")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BusinessGroupM4f implements java.io.Serializable {

    private int id;
    private String name;
    private int disable;
    private String workCenter;
    private Set<WorktimeM4f> worktimes = new HashSet<>(0);

    public BusinessGroupM4f() {
    }

    public BusinessGroupM4f(int id) {
        this.id = id;
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

    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(name = "disable", nullable = false)
    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    @NotNull
    @Column(name = "workCenter", nullable = false, length = 50)
    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    @JsonIgnore
    @JsonIgnoreProperties
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessGroup")
    public Set<WorktimeM4f> getWorktimes() {
        return worktimes;
    }

    public void setWorktimes(Set<WorktimeM4f> worktimes) {
        this.worktimes = worktimes;
    }

}
