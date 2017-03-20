package com.advantech.model;
// Generated 2017/3/15 上午 09:14:05 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.HashSet;
import java.util.Objects;
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
 * Model generated by hbm2java
 */
@Entity
@Table(name = "Model",
        schema = "dbo",
        catalog = "E_Document"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Model implements java.io.Serializable {

    private int id;
    private String name;

    @JsonIgnore
    private Set<SheetSpe> sheetSpes = new HashSet<SheetSpe>(0);

    @JsonIgnore
    private Set<SheetEe> sheetEes = new HashSet<SheetEe>(0);

    @JsonIgnore
    private Set<SheetIe> sheetIes = new HashSet<SheetIe>(0);

    public Model() {
    }

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Model(int id, String name, Set<SheetSpe> sheetSpes, Set<SheetEe> sheetEes, Set<SheetIe> sheetIes) {
        this.id = id;
        this.name = name;
        this.sheetSpes = sheetSpes;
        this.sheetEes = sheetEes;
        this.sheetIes = sheetIes;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
    public Set<SheetSpe> getSheetSpes() {
        return this.sheetSpes;
    }

    public void setSheetSpes(Set<SheetSpe> sheetSpes) {
        this.sheetSpes = sheetSpes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
    public Set<SheetEe> getSheetEes() {
        return this.sheetEes;
    }

    public void setSheetEes(Set<SheetEe> sheetEes) {
        this.sheetEes = sheetEes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
    public Set<SheetIe> getSheetIes() {
        return this.sheetIes;
    }

    public void setSheetIes(Set<SheetIe> sheetIes) {
        this.sheetIes = sheetIes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
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
        final Model other = (Model) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}