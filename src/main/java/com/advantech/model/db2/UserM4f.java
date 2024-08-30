package com.advantech.model.db2;
// Generated 2017/4/7 下午 02:26:06 by Hibernate Tools 4.3.1

import com.advantech.security.State;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = UserM4f.class)
public class UserM4f implements Serializable, Comparable<UserM4f>, IUserM9 {

    private int id;
    private FloorM4f floor;
    private UnitM4f unit;
    private String jobnumber;
    private String password;
    private String username;
    private Integer permission = 0;
    private String email;
    private String state = State.ACTIVE.getState();

    @JsonIgnore
    private Set<UserProfileM4f> userProfiles = new HashSet<UserProfileM4f>(0);

    @JsonIgnore
    private Set<WorktimeM4f> worktimesForEeOwnerId = new HashSet<WorktimeM4f>(0);

    @JsonIgnore
    private Set<WorktimeM4f> worktimesForQcOwnerId = new HashSet<WorktimeM4f>(0);

    @JsonIgnore
    private Set<WorktimeM4f> worktimesForSpeOwnerId = new HashSet<WorktimeM4f>(0);

    @JsonIgnore
    private Set<WorktimeM4f> worktimesForMpmOwnerId = new HashSet<WorktimeM4f>(0);

    @JsonIgnore
    private Set<UserNotificationM4f> userNotifications = new HashSet<UserNotificationM4f>(0);

//    private boolean enabled;
//    private boolean accountNonExpired;
//    private boolean credentialsNonExpired;
//    private boolean accountNonLocked;
//    private Collection<? extends GrantedAuthority> authorities;

    public UserM4f() {
    }

    public UserM4f(int id) {
        this.id = id;
    }

    public UserM4f(int id, FloorM4f floor, UnitM4f unit, String email, String jobnumber, String password, Integer permission, String username, String state, Set<UserProfileM4f> userProfiles, Set<WorktimeM4f> worktimesForSpeOwnerId, Set<WorktimeM4f> worktimesForQcOwnerId, Set<WorktimeM4f> worktimesForEeOwnerId, Set<UserNotificationM4f> userNotifications) {
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
    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    public FloorM4f getFloor() {
        return this.floor;
    }

    public void setFloor(FloorM4f floor) {
        this.floor = floor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    public UnitM4f getUnit() {
        return this.unit;
    }

    public void setUnit(UnitM4f unit) {
        this.unit = unit;
    }

    @Column(name = "jobnumber", length = 50)
    public String getJobnumber() {
        return this.jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    @Column(name = "[password]", length = 50)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "[name]", length = 50)
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
    public Set<WorktimeM4f> getWorktimesForEeOwnerId() {
        return this.worktimesForEeOwnerId;
    }

    public void setWorktimesForEeOwnerId(Set<WorktimeM4f> worktimesForEeOwnerId) {
        this.worktimesForEeOwnerId = worktimesForEeOwnerId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByQcOwnerId")
    public Set<WorktimeM4f> getWorktimesForQcOwnerId() {
        return this.worktimesForQcOwnerId;
    }

    public void setWorktimesForQcOwnerId(Set<WorktimeM4f> worktimesForQcOwnerId) {
        this.worktimesForQcOwnerId = worktimesForQcOwnerId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBySpeOwnerId")
    public Set<WorktimeM4f> getWorktimesForSpeOwnerId() {
        return this.worktimesForSpeOwnerId;
    }

    public void setWorktimesForSpeOwnerId(Set<WorktimeM4f> worktimesForSpeOwnerId) {
        this.worktimesForSpeOwnerId = worktimesForSpeOwnerId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByMpmOwnerId")
    public Set<WorktimeM4f> getWorktimesForMpmOwnerId() {
        return worktimesForMpmOwnerId;
    }

    public void setWorktimesForMpmOwnerId(Set<WorktimeM4f> worktimesForMpmOwnerId) {
        this.worktimesForMpmOwnerId = worktimesForMpmOwnerId;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Profile_REF", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "user_profile_id", nullable = false, insertable = false, updatable = false)})
    public Set<UserProfileM4f> getUserProfiles() {
        return this.userProfiles;
    }

    public void setUserProfiles(Set<UserProfileM4f> userProfiles) {
        this.userProfiles = userProfiles;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Notification_REF", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "user_notification_id", nullable = false, insertable = false, updatable = false)})
    public Set<UserNotificationM4f> getUserNotifications() {
        return this.userNotifications;
    }

    public void setUserNotifications(Set<UserNotificationM4f> userNotifications) {
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
        if (!(obj instanceof UserM4f)) {
            return false;
        }
        UserM4f other = (UserM4f) obj;
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

//    public void addSecurityInfo(boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//        this.enabled = enabled;
//        this.accountNonExpired = accountNonExpired;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.accountNonLocked = accountNonLocked;
//        this.authorities = authorities;
//    }
//
//    @Transient
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.authorities;
//    }
//
//    @Transient
//    @Override
//    public boolean isAccountNonExpired() {
//        return this.accountNonExpired;
//    }
//
//    @Transient
//    @Override
//    public boolean isAccountNonLocked() {
//        return this.accountNonLocked;
//    }
//
//    @Transient
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return this.credentialsNonExpired;
//    }
//
//    @Transient
//    @Override
//    public boolean isEnabled() {
//        return this.enabled;
//    }

    @Override
    public int compareTo(UserM4f o) {
        return ObjectUtils.compare(this.id, o.getId());
    }
}
