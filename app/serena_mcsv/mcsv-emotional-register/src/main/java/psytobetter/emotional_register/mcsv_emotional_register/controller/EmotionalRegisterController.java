package psytobetter.emotional_register.mcsv_emotional_register.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psytobetter.emotional_register.mcsv_emotional_register.dto.emotionalRegister.EmotionalRegisterRequestDTO;
import psytobetter.emotional_register.mcsv_emotional_register.dto.emotionalRegister.EmotionalRegisterResponseDTO;
import psytobetter.emotional_register.mcsv_emotional_register.service.EmotionalRegisterService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emotional-registers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(
        name = "Emotional Register",
        description = "Gestión de registros emocionales con integración a usuarios y emociones"
)
public class EmotionalRegisterController {

    private final EmotionalRegisterService service;

    @GetMapping
    @Operation(summary = "Listar todos los registros emocionales")
    public ResponseEntity<List<EmotionalRegisterResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un registro emocional por ID")
    public ResponseEntity<EmotionalRegisterResponseDTO> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo registro emocional")
    public ResponseEntity<EmotionalRegisterResponseDTO> create(
            @Valid @RequestBody EmotionalRegisterRequestDTO dto
    ) {
        EmotionalRegisterResponseDTO created = service.create(dto);
        URI location = URI.create("/api/v1/emotional-registers/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un registro emocional existente")
    public ResponseEntity<EmotionalRegisterResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EmotionalRegisterRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro emocional")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

