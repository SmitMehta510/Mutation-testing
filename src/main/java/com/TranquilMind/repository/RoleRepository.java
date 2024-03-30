package com.TranquilMind.repository;


import com.TranquilMind.model.Role;
import com.TranquilMind.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleName(RoleName roleName);


}
