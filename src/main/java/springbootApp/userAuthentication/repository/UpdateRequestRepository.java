package springbootApp.userAuthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootApp.userAuthentication.model.UpdateRequestByUserId;
import springbootApp.userAuthentication.model.User;

import java.util.Optional;

@Repository
public interface UpdateRequestRepository extends JpaRepository<UpdateRequestByUserId, Long> {
    Optional<UpdateRequestByUserId> findByUserId(Long userId);
}
