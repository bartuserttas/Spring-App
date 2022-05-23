package springbootApp.userAuthentication.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootApp.userAuthentication.jwt.JwtUtils;
import springbootApp.userAuthentication.model.Role;
import springbootApp.userAuthentication.model.RoleEnums;
import springbootApp.userAuthentication.model.User;
import springbootApp.userAuthentication.repository.RoleRepository;
import springbootApp.userAuthentication.repository.UserRepository;
import springbootApp.userAuthentication.payload.request.LoginRequest;
import springbootApp.userAuthentication.payload.request.SignupRequest;
import springbootApp.userAuthentication.payload.response.JwtResponse;
import springbootApp.userAuthentication.payload.response.MessageResponse;
import springbootApp.userAuthentication.service.UserServices.UserDetailsImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authentication_manager;

    @Autowired
    UserRepository user_repository;

    @Autowired
    RoleRepository role_repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwt_utils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authentication_manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwt_utils.generateJwtToken(authentication);

        UserDetailsImpl user_details = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = user_details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                user_details.getId(),
                user_details.getUsername(),
                user_details.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (user_repository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (user_repository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> role_strings = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (role_strings == null) {
            System.out.println("boş");
            Role userRole = role_repository.findByName(RoleEnums.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            role_strings.forEach(role -> {
                System.out.println(role);
                if ("admin".equals(role)) {
                    Role admin_role = role_repository.findByName(RoleEnums.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(admin_role);
                } else {
                    Role user_role = role_repository.findByName(RoleEnums.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(user_role);
                }
            });
        }

        user.setRoles(roles);
        user_repository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
