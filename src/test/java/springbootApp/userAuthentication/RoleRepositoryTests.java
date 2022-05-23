package springbootApp.userAuthentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import springbootApp.userAuthentication.model.Role;
import springbootApp.userAuthentication.model.RoleEnums;
import springbootApp.userAuthentication.repository.RoleRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {
    @Autowired
    RoleRepository repository;

    @Test
    public void testCreateRoles(){
        Role user = new Role(RoleEnums.ROLE_USER);
        Role admin = new Role(RoleEnums.ROLE_ADMIN);

        repository.save(user);
        repository.save(admin);

        List<Role> roles = repository.findAll();

        assertThat(roles.size()).isEqualTo(2);
    }
}