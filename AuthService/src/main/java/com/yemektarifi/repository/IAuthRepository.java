package com.yemektarifi.repository;


import com.yemektarifi.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IAuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findOptionalByUsername(String username);
    Optional<Auth> findOptionalByEmail(String email);

}
