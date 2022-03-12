package com.wfl.domains.entities;

import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Entity(name = "t_project")
public class Project {

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

    @NotBlank(message = "project must has a name")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "project must has an upper-case code")
    @Column(name = "code")
    private String code;

    public Boolean isValid() {
        return isValidName() && isValidCode();
    }

    public Boolean isValidName() {
        return name != null;
    }

    public Boolean isValidCode() {
        return StringUtils.hasText(code) && code.equals(code.toUpperCase(Locale.ROOT));
    }
}
