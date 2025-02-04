package sistema.pedidos.init.controller;

import sistema.pedidos.init.model.User;
import sistema.pedidos.init.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/tiendas")
    public ResponseEntity<List<User>> getAllVendedores() {
        List<User> vendedores = userService.getAllVendedores();
        return ResponseEntity.ok(vendedores);
    }
}