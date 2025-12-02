package duoc.projects.serena.services.EmotionalRegister;

import duoc.projects.serena.dto.emotionalRegister.EmotionalRegisterRequestDTO;
import duoc.projects.serena.dto.emotionalRegister.EmotionalRegisterResponseDTO;

import java.util.List;

public interface EmotionalRegisterService {

    EmotionalRegisterResponseDTO create(EmotionalRegisterRequestDTO dto);

    EmotionalRegisterResponseDTO findById(Long id);

    List<EmotionalRegisterResponseDTO> findAll();

    EmotionalRegisterResponseDTO update(Long id, EmotionalRegisterRequestDTO dto);

    void delete(Long id);
}
