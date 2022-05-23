package springbootApp.userAuthentication.service.UserServices;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springbootApp.userAuthentication.model.AuthorityEnums;
import springbootApp.userAuthentication.model.Role;
import springbootApp.userAuthentication.model.RoleEnums;
import springbootApp.userAuthentication.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole_name().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<AuthorityEnums> auths = getUserAuthorities(user);
        for(AuthorityEnums auth : auths){
            authorities.add(new SimpleGrantedAuthority(auth.name()));
        }
        Set<Role> roles = user.getRoles();
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole_name().toString()));
        }
        return authorities;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user2 = (UserDetailsImpl) o;
        return Objects.equals(this.user.getId(), user2.getId());
    }

    public Set<AuthorityEnums> getRoleAuthorities(Role role){
        Set<AuthorityEnums> authorities = new HashSet<>();

        authorities.add(AuthorityEnums.SIGNIN);
        authorities.add(AuthorityEnums.SIGNUP);

        System.out.println("null");

        if(role.getRole_name().equals(RoleEnums.ROLE_ADMIN)){
            authorities.add(AuthorityEnums.ADMINPAGE);
        }
        else if(role.getRole_name().equals(RoleEnums.ROLE_USER)){
            authorities.add(AuthorityEnums.USERPAGE);
        }
        return authorities;
    }

    public Set<AuthorityEnums> getUserAuthorities(User user){
        Set<AuthorityEnums> authorities = new HashSet<>();
        for(Role role : user.getRoles()){
            authorities.addAll(getRoleAuthorities(role));
        }

        return authorities;
    }
}
