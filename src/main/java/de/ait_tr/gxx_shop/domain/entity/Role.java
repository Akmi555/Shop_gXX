package de.ait_tr.gxx_shop.domain.entity;
/*
@date 16.07.2024
@author Sergey Bugaienko
*/

import java.util.Objects;

public class Role {
    private Long id;
    private String title;


    @Override
    public String toString() {
        return String.format("Role: id - %d, name - %s", id, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(title, role.title);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
