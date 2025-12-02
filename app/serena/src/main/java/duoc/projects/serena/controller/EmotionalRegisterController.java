package duoc.projects.serena.controller;

import duoc.projects.serena.dto.emotionalRegister.EmotionalRegisterRequestDTO;
import duoc.projects.serena.dto.emotionalRegister.EmotionalRegisterResponseDTO;
import duoc.projects.serena.services.EmotionalRegister.EmotionalRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emotional-registers")
@CrossOrigin(origins = "*")
public class EmotionalRegisterController {

    private final EmotionalRegisterService service;

    public EmotionalRegisterController(EmotionalRegisterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EmotionalRegisterResponseDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmotionalRegisterResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmotionalRegisterResponseDTO> create(@RequestBody EmotionalRegisterRequestDTO dto) {
        EmotionalRegisterResponseDTO created = service.create(dto);
        URI location = URI.create("/api/v1/emotional-registers/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmotionalRegisterResponseDTO> update(
            @PathVariable Long id,
            @RequestBody EmotionalRegisterRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
