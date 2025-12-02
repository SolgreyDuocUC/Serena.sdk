package duoc.projects.serena.services.User;

import duoc.projects.serena.dto.user.UserDTO;
import duoc.projects.serena.dto.user.UserCreateRequestDTO;
import duoc.projects.serena.exception.UserNotFoundException;
import duoc.projects.serena.model.User;
import duoc.projects.serena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserCreateRequestDTO dto) {
        if (userRepository.existsByUserEmail(dto.getUserEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setUserEmail(dto.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        user.setUserAceptConditions(dto.getUserAceptConditions());
        user.setUserImageUri(dto.getUserImageUri());

        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return toDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return toDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public UserDTO updateUser(Long id, UserCreateRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        user.setUserName(dto.getUserName());
        user.setUserEmail(dto.getUserEmail());
        user.setUserAceptConditions(dto.getUserAceptConditions());
        user.setUserImageUri(dto.getUserImageUri());

        if (dto.getUserPassword() != null && !dto.getUserPassword().isBlank()) {
            user.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        }

        return toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getName());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserAceptConditions(user.getUserAceptConditions());
        dto.setUserImageUri(user.getUserImageUri());
        return dto;
    }
}
