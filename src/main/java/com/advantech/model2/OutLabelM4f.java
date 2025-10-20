package com.advantech.model2;
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
public class OutLabelM4f implements java.io.Serializable, Comparable<OutLabelM4f> {

    private int id;
    private String name;

    @JsonIgnore
    private Set<WorktimeM4f> worktimes = new HashSet<WorktimeM4f>(0);

    public OutLabelM4f() {
    }

    public OutLabelM4f(int id) {
        this.id = id;
    }

    public OutLabelM4f(int id, String name, Set<WorktimeM4f> worktimes) {
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

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "labelOuterId")
    public Set<WorktimeM4f> getWorktimes() {
        return this.worktimes;
    }

    public void setWorktimes(Set<WorktimeM4f> worktimes) {
        this.worktimes = worktimes;
    }

    @Override
    public int compareTo(OutLabelM4f o) {
        return ObjectUtils.compare(this.id, o.getId());
    }

}
