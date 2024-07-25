package gabrielleal.recrutamentointerno.controllers;

import gabrielleal.recrutamentointerno.dtos.LoginDTO;
import gabrielleal.recrutamentointerno.dtos.RegistroUsuarioDTO;
import gabrielleal.recrutamentointerno.models.Usuario;
import gabrielleal.recrutamentointerno.services.AuthService;
import gabrielleal.recrutamentointerno.services.UserDetailsServiceImpl;
import gabrielleal.recrutamentointerno.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário com email e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginDTO loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getSenha())
        );

        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginDto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Logout do usuário", description = "Realiza o logout do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout bem-sucedido")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalida a sessão
        }
        return ResponseEntity.ok("Logout bem-sucedido!");
    }

    @Operation(summary = "Registrar novo usuário", description = "Registra um novo usuário com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Email já cadastrado")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
        if (authService.usuarioExiste(registroUsuarioDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        Usuario usuario = authService.registrarUsuario(registroUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso! ID: " + usuario.getId());
    }
}