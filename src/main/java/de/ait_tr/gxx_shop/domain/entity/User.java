package de.ait_tr.gxx_shop.domain.entity;
/*
@date 16.07.2024
@author Sergey Bugaienko
*/

import java.util.Objects;
import java.util.Set;

public class User {
    private Long id;
    private String userName;
    private String password;
    private Set<Role> roles;

    @Override
    public String toString() {
        return String.format("User: id - %d, username - %s, roles - %s",
                id, userName, roles == null ? "[]" : roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(userName);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + Objects.hashCode(roles);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
