package apis.controllers;

import domains.entities.Project;
import domains.responses.success.CreatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    @PostMapping("/create-project")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        return new CreatedResponse<>("");
    }
}
