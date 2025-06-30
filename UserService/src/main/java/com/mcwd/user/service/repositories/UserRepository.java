package com.mcwd.user.service.repositories;

import com.mcwd.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    //if you want implement any custom method or query
    //write
}
