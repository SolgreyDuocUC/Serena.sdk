package duoc.projects.serena.controller;


import duoc.projects.serena.dto.emotion.EmotionRequestDTO;
import duoc.projects.serena.dto.emotion.EmotionResponseDTO;
import duoc.projects.serena.services.Emotion.EmotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emotions")
@CrossOrigin(origins = "*")
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
