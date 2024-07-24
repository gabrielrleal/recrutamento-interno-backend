package gabrielleal.recrutamentointerno.controllers;

import gabrielleal.recrutamentointerno.config.TestSecurityConfig;
import gabrielleal.recrutamentointerno.dtos.LoginDTO;
import gabrielleal.recrutamentointerno.dtos.RegistroUsuarioDTO;
import gabrielleal.recrutamentointerno.models.Usuario;
import gabrielleal.recrutamentointerno.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class) // Importa a configuração de segurança de teste
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setSenha("password");

        doNothing().when(authService).authenticateUser(loginDTO.getEmail(), loginDTO.getSenha());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"senha\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful!"));
    }

    @Test
    public void testAuthenticateUser_Failure() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setSenha("wrongpassword");

        doThrow(new BadCredentialsException("Invalid email or password")).when(authService).authenticateUser(loginDTO.getEmail(), loginDTO.getSenha());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"senha\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));
    }

    @Test
    public void testLogoutUser() throws Exception {
        doNothing().when(authService).logoutUser();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout successful!"));
    }

    @Test
    public void testRegistrarUsuario_Success() throws Exception {
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO();
        registroUsuarioDTO.setNome("Test User");
        registroUsuarioDTO.setEmail("test@example.com");
        registroUsuarioDTO.setSenha("password");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Test User");
        usuario.setEmail("test@example.com");

        when(authService.usuarioExiste(registroUsuarioDTO.getEmail())).thenReturn(false);
        when(authService.registrarUsuario(any(RegistroUsuarioDTO.class))).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Test User\",\"email\":\"test@example.com\",\"senha\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Usuário criado com sucesso! ID: 1"));
    }

    @Test
    public void testRegistrarUsuario_EmailExists() throws Exception {
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO();
        registroUsuarioDTO.setNome("Test User");
        registroUsuarioDTO.setEmail("test@example.com");
        registroUsuarioDTO.setSenha("password");

        when(authService.usuarioExiste(registroUsuarioDTO.getEmail())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Test User\",\"email\":\"test@example.com\",\"senha\":\"password\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email já cadastrado"));
    }
}