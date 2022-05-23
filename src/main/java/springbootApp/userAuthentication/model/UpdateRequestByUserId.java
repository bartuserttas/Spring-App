package springbootApp.userAuthentication.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "update_requests")
public class UpdateRequestByUserId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    public UpdateRequestByUserId() {
    }

    public UpdateRequestByUserId(Long user_id) {
        this.userId = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return userId;
    }

    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }
}
