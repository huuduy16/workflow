package com.wfl.repositories;

import com.wfl.domains.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Boolean existsByCode(String code);

    @Query(value = "select exists (select * from t_user_project "
        + "where user_id = :userId and project_id = :projectId)", nativeQuery = true)
    Boolean existUserInProject(Long projectId, Long userId);

    @Modifying
    @Query(value = "insert into t_user_project(user_id, project_id) "
        + "value (:userId, :projectId)", nativeQuery = true)
    void addUserProject(Long projectId, Long userId);

    @Modifying
    @Query(value = "delete from t_user_project "
        + "where user_id = :userId and project_id = :projectId", nativeQuery = true)
    void deleteUserProject(Long projectId, Long userId);
}
