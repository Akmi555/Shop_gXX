package de.ait_tr.gxx_shop.domain.entity;
/*
@date 06.08.2024
@author Sergey Bugaienko
*/

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "confirm_code")
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, length = 36)
    private String code;

    @Column(name = "expired", nullable = false)
    private LocalDateTime expired;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public ConfirmationCode() {
    }

    public ConfirmationCode(String code, LocalDateTime expired, User user) {
        this.code = code;
        this.expired = expired;
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Confirm Code id - %d, Code - %s, user: %s, expires - %s",
                id,
                code,
                (user == null ? "empty" : user.getUsername()),
                expired);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmationCode that = (ConfirmationCode) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(expired, that.expired) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(code);
        result = 31 * result + Objects.hashCode(expired);
        result = 31 * result + Objects.hashCode(user);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
