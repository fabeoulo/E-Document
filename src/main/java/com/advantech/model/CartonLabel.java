package com.advantech.model;
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
@Table(name = "CartonLabel",
                uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CartonLabel implements java.io.Serializable, Comparable<CartonLabel> {

    private int id;
    private String name;
    
    @JsonIgnore
    private Set<Worktime> worktimes = new HashSet<Worktime>(0);

    public CartonLabel() {
    }

    public CartonLabel(int id) {
        this.id = id;
    }
    
    public CartonLabel(int id, String name, Set<Worktime> worktimes) {
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "labelCartonId")
    public Set<Worktime> getWorktimes() {
        return this.worktimes;
    }

    public void setWorktimes(Set<Worktime> worktimes) {
        this.worktimes = worktimes;
    }

    @Override
    public int compareTo(CartonLabel o) {
        return ObjectUtils.compare(this.id, o.getId());
    }

}
