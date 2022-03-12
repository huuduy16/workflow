package com.wfl.repositories;

import com.wfl.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);
    <T> T findByEmail(String email, Class<T> type);
}
