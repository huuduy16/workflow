package domains.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "t_permission")
public class Permission extends BaseEntity {

    @Column(name = "permission_name")
    private String permissionName;
    @Column(name = "permission_key")
    private String permissionKey;
}
