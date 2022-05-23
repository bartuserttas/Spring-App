package springbootApp.userAuthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springbootApp.userAuthentication.model.Role;
import springbootApp.userAuthentication.model.RoleEnums;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.role_name = ?1")
    Optional<Role> findByName(RoleEnums name);
}
