package domains.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "t_category")
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "project_id")
    private Long projectId;
}
