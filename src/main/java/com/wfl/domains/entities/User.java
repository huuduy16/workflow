package com.wfl.domains.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "updated_by")
    private Long updatedUserId;
    @Column(name = "is_deleted")
    private Boolean deleted = false;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotBlank(message = "email cannot blank")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "nickname cannot blank")
    @Column(name = "nickname")
    private String nickname;

    @NotBlank(message = "password cannot blank")
    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role", joinColumns = {
        @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public Boolean isValid() {
        return isValidEmail() && isValidPassword();
    }

    public Boolean isValidEmail() {
        return (email != null) && Pattern.compile(
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches();
    }

    public Boolean isValidPassword() {
        return (password != null) && (password.length() >= 6) && (password.indexOf(' ') == -1);
    }
}
