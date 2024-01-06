package com.vti.vtiacademy.repository;

import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>,
        JpaSpecificationExecutor<Account> {
    List<Account> findAllByRole(Role role);

    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

}
