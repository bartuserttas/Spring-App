package springbootApp.userAuthentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import springbootApp.userAuthentication.model.Role;
import springbootApp.userAuthentication.model.RoleEnums;
import springbootApp.userAuthentication.model.User;
import springbootApp.userAuthentication.repository.RoleRepository;
import springbootApp.userAuthentication.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    UserRepository user_repository;
    @Autowired
    RoleRepository role_repository;

    @Test
    public void testAddRoleToUser(){
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setUsername("admin");
        user.setPassword("$2a$10$NVzvc8HJAsLgh.0FYCjL1ejboGVISdfG1FXwwCibUXfIoUd9nafh6");

        Role role = role_repository.findByName(RoleEnums.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));;
        user.addUserRole(role);

        User savedUser = user_repository.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }
}
