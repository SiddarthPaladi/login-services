package io.movecloud.movecloud_login_service.repository;

import io.movecloud.movecloud_login_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByBusinessEmail(String email);
}
