package com.advantech.model;
// Generated 2017/4/7 下午 02:26:06 by Hibernate Tools 4.3.1

import com.advantech.security.State;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Identit generated by hbm2java
 */
@Entity
@Table(name = "[User]",
        uniqueConstraints = @UniqueConstraint(columnNames = "jobnumber")
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = User.class)
public class User implements UserDetails, Comparable<User> {

    private int id;
    private Floor floor;
    private Unit unit;
    private String jobnumber;
    private String password;
    private String username;
    private Integer permission = 0;
    private String email;
    private String state = State.ACTIVE.getState();

    @JsonIgnore
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);

    @JsonIgnore
    private Set<Worktime> worktimesForEeOwnerId = new HashSet<Worktime>(0);

    @JsonIgnore
    private Set<Worktime> worktimesForQcOwnerId = new HashSet<Worktime>(0);

    @JsonIgnore
    private Set<Worktime> worktimesForSpeOwnerId = new HashSet<Worktime>(0);

    @JsonIgnore
    private Set<UserNotification> userNotifications = new HashSet<UserNotification>(0);

    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, Floor floor, Unit unit, String email, String jobnumber, String password, Integer permission, String username, String state, Set<UserProfile> userProfiles, Set<Worktime> worktimesForSpeOwnerId, Set<Worktime> worktimesForQcOwnerId, Set<Worktime> worktimesForEeOwnerId, Set<UserNotification> userNotifications) {
        this.id = id;
        this.floor = floor;
        this.unit = unit;
        this.email = email;
        this.jobnumber = jobnumber;
        this.password = password;
        this.permission = permission;
        this.username = username;
        this.state = state;
        this.userProfiles = userProfiles;
        this.worktimesForSpeOwnerId = worktimesForSpeOwnerId;
        this.worktimesForQcOwnerId = worktimesForQcOwnerId;
        this.worktimesForEeOwnerId = worktimesForEeOwnerId;
        this.userNotifications = userNotifications;
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
    @JoinColumn(name = "unit_id")
    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Column(name = "jobnumber", length = 50)
    public String getJobnumber() {
        return this.jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    @Column(name = "password", length = 50)
    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "name", length = 50)
    @Override
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

    @Column(name = "[state]", length = 50)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByEeOwnerId")
    public Set<Worktime> getWorktimesForEeOwnerId() {
        return this.worktimesForEeOwnerId;
    }

    public void setWorktimesForEeOwnerId(Set<Worktime> worktimesForEeOwnerId) {
        this.worktimesForEeOwnerId = worktimesForEeOwnerId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByQcOwnerId")
    public Set<Worktime> getWorktimesForQcOwnerId() {
        return this.worktimesForQcOwnerId;
    }

    public void setWorktimesForQcOwnerId(Set<Worktime> worktimesForQcOwnerId) {
        this.worktimesForQcOwnerId = worktimesForQcOwnerId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBySpeOwnerId")
    public Set<Worktime> getWorktimesForSpeOwnerId() {
        return this.worktimesForSpeOwnerId;
    }

    public void setWorktimesForSpeOwnerId(Set<Worktime> worktimesForSpeOwnerId) {
        this.worktimesForSpeOwnerId = worktimesForSpeOwnerId;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Profile_REF", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "user_profile_id", nullable = false, insertable = false, updatable = false)})
    public Set<UserProfile> getUserProfiles() {
        return this.userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Notification_REF", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "user_notification_id", nullable = false, insertable = false, updatable = false)})
    public Set<UserNotification> getUserNotifications() {
        return this.userNotifications;
    }

    public void setUserNotifications(Set<UserNotification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", ssoId=" + jobnumber + ", password=" + password
                + ", name=" + username
                + ", email=" + email + ", state=" + state + ", userProfiles=" + userProfiles + "]";
    }

    public void addSecurityInfo(boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public int compareTo(User o) {
        return ObjectUtils.compare(this.id, o.getId());
    }
}
