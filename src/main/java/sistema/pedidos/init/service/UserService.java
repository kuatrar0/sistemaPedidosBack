package sistema.pedidos.init.service;

import sistema.pedidos.init.dto.LoginRequest;
import sistema.pedidos.init.dto.LoginResponse;
import sistema.pedidos.init.dto.RegisterRequest;
import sistema.pedidos.init.model.Role;
import sistema.pedidos.init.model.User;
import sistema.pedidos.init.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistema.pedidos.init.security.JwtUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hashear la contraseña
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.COMPRADOR); // Rol por defecto

        userRepository.save(user);
        return "Usuario registrado exitosamente.";
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) { // Comparar hashs
            throw new RuntimeException("Usuario o contraseña inválidos.");
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new LoginResponse("Inicio de sesión exitoso.", token, user.getRole().name(), user.getId());
    }

    public List<User> getAllVendedores() {
        return userRepository.findByRole("VENDEDOR");
    }
}