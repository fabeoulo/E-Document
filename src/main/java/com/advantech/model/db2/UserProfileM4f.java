/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.model.db2;

import com.advantech.security.UserProfileType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Wei.Cheng
 */
@Entity
@Table(
        name = "User_Profile",
        uniqueConstraints = @UniqueConstraint(columnNames = "type")
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserProfileM4f implements java.io.Serializable {

    private int id;
    private String name = UserProfileType.USER.getUserProfileType();
    private String description;
    private Set<UserM4f> users = new HashSet<>(0);

    public UserProfileM4f() {
    }

    public UserProfileM4f(int id) {
        this.id = id;
    }

    public UserProfileM4f(int id, String name, Set<UserM4f> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "type", length = 50, unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "[description]", length = 200, nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Profile_REF", joinColumns = {
        @JoinColumn(name = "user_profile_id", nullable = false, insertable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)})
    public Set<UserM4f> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserM4f> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof UserProfileM4f)) {
            return false;
        }
        UserProfileM4f other = (UserProfileM4f) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserProfile [id=" + id + ",  type=" + name + "]";
    }
}
