package com.advantech.model;
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
 * FlowGroup generated by hbm2java
 */
@Entity
@Table(name = "FlowGroup")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FlowGroup implements java.io.Serializable {

    private int id;
    private String name;
    
    @JsonIgnore
    private Set<Flow> flows = new HashSet<Flow>(0);

    public FlowGroup() {
    }

    public FlowGroup(int id) {
        this.id = id;
    }

    public FlowGroup(int id, String name, Set<Flow> flows) {
        this.id = id;
        this.name = name;
        this.flows = flows;
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

    @Column(name = "name", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "flowGroup")
    public Set<Flow> getFlows() {
        return this.flows;
    }

    public void setFlows(Set<Flow> flows) {
        this.flows = flows;
    }

}
