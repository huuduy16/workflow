package com.wfl.domains.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "t_project")
public class Project extends BaseEntity {

    @Column(name = "p_name")
    private String name;
    @Column(name = "p_key")
    private String keyName;
    @Column(name = "created_user_id")
    private Long createdUserId;
}
