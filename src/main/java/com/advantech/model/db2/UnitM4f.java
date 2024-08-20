package com.advantech.model.db2;
// Generated 2017/4/7 下午 02:26:06 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * UserType generated by hbm2java
 */
@Entity
@Table(name = "Unit")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UnitM4f implements java.io.Serializable {

    private int id;
    private String name;
    
    @JsonIgnore
    private Set<UserM4f> users = new HashSet<UserM4f>(0);
    
    @JsonIgnore
    private Set<WorktimeColumnGroupM4f> worktimeColumnGroups = new HashSet<WorktimeColumnGroupM4f>(0);

    public UnitM4f() {
    }

    public UnitM4f(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UnitM4f(int id, String name, Set<UserM4f> users, Set<WorktimeColumnGroupM4f> worktimeColumnGroups) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.worktimeColumnGroups = worktimeColumnGroups;
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

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    public Set<UserM4f> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserM4f> users) {
        this.users = users;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    public Set<WorktimeColumnGroupM4f> getWorktimeColumnGroups() {
        return this.worktimeColumnGroups;
    }

    public void setWorktimeColumnGroups(Set<WorktimeColumnGroupM4f> worktimeColumnGroups) {
        this.worktimeColumnGroups = worktimeColumnGroups;
    }

}
