package com.advantech.model;
// Generated 2017/4/7 下午 02:26:06 by Hibernate Tools 4.3.1

import com.advantech.listener.UserRevisionListener;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.hibernate.envers.DefaultRevisionEntity;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;

/**
 * Identit generated by hbm2java
 */
@Entity
@Table(name = "Identit",
         schema = "dbo",
         catalog = "E_Document"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Identit implements java.io.Serializable {

    private int id;
    private Floor floor;
    private UserType userType;
    private String jobnumber;
    private String password;
    private String username;
    private Integer permission;
    private String email;
    @JsonIgnore
    private Set<Worktime> worktimesForEeOwnerId = new HashSet<Worktime>(0);
    @JsonIgnore
    private Set<Worktime> worktimesForQcOwnerId = new HashSet<Worktime>(0);
    @JsonIgnore
    private Set<Worktime> worktimesForSpeOwnerId = new HashSet<Worktime>(0);

    public Identit() {
    }

    public Identit(int id) {
        this.id = id;
    }

    public Identit(int id, Floor floor, UserType userType, String jobnumber, String password, String username, Integer permission, String email, Set<Worktime> worktimesForEeOwnerId, Set<Worktime> worktimesForQcOwnerId, Set<Worktime> worktimesForSpeOwnerId) {
        this.id = id;
        this.floor = floor;
        this.userType = userType;
        this.jobnumber = jobnumber;
        this.password = password;
        this.username = username;
        this.permission = permission;
        this.email = email;
        this.worktimesForEeOwnerId = worktimesForEeOwnerId;
        this.worktimesForQcOwnerId = worktimesForQcOwnerId;
        this.worktimesForSpeOwnerId = worktimesForSpeOwnerId;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usertype_id")
    public UserType getUserType() {
        return this.userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Column(name = "jobnumber", length = 50)
    public String getJobnumber() {
        return this.jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    @Column(name = "password", length = 50)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "name", length = 50)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "permission")
    public Integer getPermission() {
        return this.permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    @Column(name = "email", length = 100)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "identitByEeOwnerId")
    public Set<Worktime> getWorktimesForEeOwnerId() {
        return this.worktimesForEeOwnerId;
    }

    public void setWorktimesForEeOwnerId(Set<Worktime> worktimesForEeOwnerId) {
        this.worktimesForEeOwnerId = worktimesForEeOwnerId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "identitByQcOwnerId")
    public Set<Worktime> getWorktimesForQcOwnerId() {
        return this.worktimesForQcOwnerId;
    }

    public void setWorktimesForQcOwnerId(Set<Worktime> worktimesForQcOwnerId) {
        this.worktimesForQcOwnerId = worktimesForQcOwnerId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "identitBySpeOwnerId")
    public Set<Worktime> getWorktimesForSpeOwnerId() {
        return this.worktimesForSpeOwnerId;
    }

    public void setWorktimesForSpeOwnerId(Set<Worktime> worktimesForSpeOwnerId) {
        this.worktimesForSpeOwnerId = worktimesForSpeOwnerId;
    }

}
