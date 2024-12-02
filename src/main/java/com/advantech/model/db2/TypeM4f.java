package com.advantech.model.db2;
// Generated 2017/4/7 下午 02:26:06 by Hibernate Tools 4.3.1

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

/**
 * Type generated by hbm2java
 */
@Entity
@Table(name = "[Type]")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TypeM4f implements java.io.Serializable {

    private int id;
    private String name;
    private Set<WorktimeM4f> worktimes = new HashSet<>(0);

    public TypeM4f() {
    }

    public TypeM4f(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeM4f(int id, String name, Set<WorktimeM4f> worktimes) {
        this.id = id;
        this.name = name;
        this.worktimes = worktimes;
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

    @Column(name = "[name]", nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @JsonIgnoreProperties
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    public Set<WorktimeM4f> getWorktimes() {
        return this.worktimes;
    }

    public void setWorktimes(Set<WorktimeM4f> worktimes) {
        this.worktimes = worktimes;
    }

}