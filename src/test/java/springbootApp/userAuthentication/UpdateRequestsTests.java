package springbootApp.userAuthentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import springbootApp.userAuthentication.model.UpdateRequestByUserId;
import springbootApp.userAuthentication.model.User;
import springbootApp.userAuthentication.repository.UpdateRequestRepository;
import springbootApp.userAuthentication.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UpdateRequestsTests {

    @Autowired
    UserRepository user_repository;

    @Autowired
    UpdateRequestRepository request_repository;

    @Test
    public void testAddRoleToUser(){
        User user = user_repository.findById(new Long(1))
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));

        UpdateRequestByUserId update_request = new UpdateRequestByUserId();
        update_request.setUser_id(user.getId());

        UpdateRequestByUserId savedRequest = request_repository.save(update_request);

        assertThat(request_repository.findAll().size()).isEqualTo(1);
    }
}
