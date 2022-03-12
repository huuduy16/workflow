package com.wfl.domains.forms;

import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AssignTaskForm {

    @NotBlank(message = "must indicate task")
    Long taskId;

    Long assigneeId;
}
