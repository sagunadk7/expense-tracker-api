package com.sagun.expense_tracker_api.repository;

import com.sagun.expense_tracker_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {

    Optional<User> findByEmail(String email);

}
