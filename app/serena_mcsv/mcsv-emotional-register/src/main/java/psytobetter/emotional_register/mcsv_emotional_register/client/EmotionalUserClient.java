package psytobetter.emotional_register.mcsv_emotional_register.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import psytobetter.emotional_register.mcsv_emotional_register.dto.clients.UserDTO;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8091/api/v1/users")
public interface EmotionalUserClient {

    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);

    @GetMapping
    List<UserDTO> getAllUsers();

    @GetMapping("/email/{email}")
    UserDTO getUserByEmail(@PathVariable("email") String email);
}
