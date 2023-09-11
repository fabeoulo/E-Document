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
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author Eric.Hong
 */
@Entity
@Table(name = "OutLabel",
                uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OutLabel implements java.io.Serializable, Comparable<OutLabel> {

    private int id;
    private String name;
    
    @JsonIgnore
    private Set<Worktime> worktimes = new HashSet<Worktime>(0);

    public OutLabel() {
    }

    public OutLabel(int id) {
        this.id = id;
    }
    
    public OutLabel(int id, String name, Set<Worktime> worktimes) {
        this.id = id;
        this.name = name;
        this.worktimes = worktimes;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
       
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "labelOuterId")
    public Set<Worktime> getWorktimes() {
        return this.worktimes;
    }

    public void setWorktimes(Set<Worktime> worktimes) {
        this.worktimes = worktimes;
    }

    @Override
    public int compareTo(OutLabel o) {
        return ObjectUtils.compare(this.id, o.getId());
    }

}
