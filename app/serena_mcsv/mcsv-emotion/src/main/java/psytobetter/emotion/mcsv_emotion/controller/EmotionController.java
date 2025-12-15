package psytobetter.emotion.mcsv_emotion.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import psytobetter.emotion.mcsv_emotion.dto.EmotionRequestDTO;
import psytobetter.emotion.mcsv_emotion.dto.EmotionResponseDTO;
import psytobetter.emotion.mcsv_emotion.service.EmotionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emotions")
@CrossOrigin(origins = "*")
@Validated
@Tag(name= "Microservicio de Emociones", description = "CRUD especializado en generar, actualizar, eliminar y buscar las emociones desarrolladas en Serena")
public class EmotionController {

    private final EmotionService emotionService;

    public EmotionController(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    @GetMapping

    public ResponseEntity<List<EmotionResponseDTO>> listarEmotions() {
        List<EmotionResponseDTO> emotions = emotionService.findAll();
        return ResponseEntity.ok(emotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmotionResponseDTO> obtenerEmotion(@PathVariable Long id) {
        EmotionResponseDTO emotion = emotionService.findById(id);
        return ResponseEntity.ok(emotion);
    }

    @PostMapping
    public ResponseEntity<EmotionResponseDTO> crearEmotion(@RequestBody EmotionRequestDTO request) {
        EmotionResponseDTO creado = emotionService.create(request);
        URI location = URI.create("/api/v1/serena/emotions/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmotionResponseDTO> actualizarEmotion(
            @PathVariable Long id,
            @RequestBody EmotionRequestDTO request) {

        EmotionResponseDTO actualizado = emotionService.update(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmotion(@PathVariable Long id) {
        emotionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
