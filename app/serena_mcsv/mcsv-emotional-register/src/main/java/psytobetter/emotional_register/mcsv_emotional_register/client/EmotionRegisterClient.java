package psytobetter.emotional_register.mcsv_emotional_register.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import psytobetter.emotional_register.mcsv_emotional_register.dto.clients.EmotionDTO;

@FeignClient(
        name = "emotion-service",
        url = "http://localhost8092/api/v1/emotions"
)
public interface EmotionRegisterClient {

    @GetMapping("/{id}")
    EmotionDTO getEmotionById(@PathVariable("id") Long id);
}

