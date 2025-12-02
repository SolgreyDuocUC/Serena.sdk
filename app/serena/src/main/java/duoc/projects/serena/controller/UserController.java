package duoc.projects.serena.controller;

import duoc.projects.serena.dto.user.UserCreateRequestDTO;
import duoc.projects.serena.dto.user.UserDTO;
import duoc.projects.serena.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserCreateRequestDTO dto) {
        UserDTO created = userService.createUser(dto);
        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserCreateRequestDTO dto) {
        UserDTO updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
