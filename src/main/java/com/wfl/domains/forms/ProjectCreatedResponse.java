package com.wfl.domains.forms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectCreatedResponse {
    private Long id;
    private String name;
    private String code;
}
