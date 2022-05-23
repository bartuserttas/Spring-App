package springbootApp.userAuthentication.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    //properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, length = 45)
    private RoleEnums role_name;

    //Constructors

    public Role() {

    }

    public Role(RoleEnums role_name) {
        this.role_name = role_name;
    }

    public Role(long role_id) {
        this.role_id = role_id;
    }

    public Role(long role_id, RoleEnums role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }

    //Getters

    public long getRole_id() {
        return role_id;
    }

    public RoleEnums getRole_name() {
        return role_name;
    }

    //Setters

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public void setRole_name(RoleEnums role_name) {
        this.role_name = role_name;
    }

    //To String

    @Override
    public String toString() {
        return "UserRoles{" +
                "role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
