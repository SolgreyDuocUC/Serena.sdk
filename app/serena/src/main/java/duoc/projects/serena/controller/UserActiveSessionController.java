package duoc.projects.serena.controller;

import duoc.projects.serena.dto.userActiveSession.UserActiveSessionRequestDTO;
import duoc.projects.serena.dto.userActiveSession.UserActiveSessionResponseDTO;
import duoc.projects.serena.services.UserActiveSession.UserActiveSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-active-sessions")
@CrossOrigin(origins = "*")
public class UserActiveSessionController {

    private final UserActiveSessionService service;

    public UserActiveSessionController(UserActiveSessionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserActiveSessionResponseDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserActiveSessionResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserActiveSessionResponseDTO> create(@RequestBody UserActiveSessionRequestDTO dto) {
        UserActiveSessionResponseDTO created = service.create(dto);
        URI location = URI.create("/api/v1/user-active-sessions/" + created.getSessionId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserActiveSessionResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody UserActiveSessionRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
