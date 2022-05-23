package springbootApp.userAuthentication.controller.Update;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springbootApp.userAuthentication.model.UpdateRequestByUserId;
import springbootApp.userAuthentication.model.User;
import springbootApp.userAuthentication.payload.response.MessageResponse;
import springbootApp.userAuthentication.repository.UpdateRequestRepository;
import springbootApp.userAuthentication.repository.UserRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/test")
public class UpdateController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UpdateRequestRepository request_repository;

    @Autowired private PasswordEncoder encoder;

    @PostMapping("/update/{id}")
    public ResponseEntity<?> createRequest(@PathVariable("id") Long user_id){
        if (request_repository.findById(user_id).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Request is already made!"));
        }

        UpdateRequestByUserId new_update_request = new UpdateRequestByUserId();
        new_update_request.setUser_id(user_id);
        request_repository.save(new_update_request);

        return ResponseEntity.ok(new MessageResponse("Request registered successfully!"));
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<UpdateRequestByUserId> getById(@PathVariable("id") Long id) {
        UpdateRequestByUserId request = request_repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found by id: " + id));
        return ResponseEntity.ok(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody User userDetails){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found for this id: " + userId));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(encoder.encode(userDetails.getPassword()));
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("update/{id}")
    public Map<String, Boolean> deleteRequest(@PathVariable("id") Long user_id){
        UpdateRequestByUserId request = request_repository.findByUserId(user_id)
                .orElseThrow(() -> new RuntimeException("Request not found by id: " + user_id));

        System.out.println(request);

        request_repository.delete(request);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
