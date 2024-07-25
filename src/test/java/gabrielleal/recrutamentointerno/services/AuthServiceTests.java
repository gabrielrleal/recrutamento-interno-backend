package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.dtos.RegistroUsuarioDTO;
import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Role;
import gabrielleal.recrutamentointerno.models.Usuario;
import gabrielleal.recrutamentointerno.repositories.RoleRepository;
import gabrielleal.recrutamentointerno.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String result = authService.authenticateUser("test@example.com", "password");

        assertEquals("Login successful!", result);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testAuthenticateUser_Failure() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Invalid email or password"));

        assertThrows(BadCredentialsException.class, () -> {
            authService.authenticateUser("test@example.com", "wrongpassword");
        });

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testLogoutUser() {
        SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

        authService.logoutUser();

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testUsuarioExiste() {
        when(usuarioRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = authService.usuarioExiste("test@example.com");

        assertTrue(result);
        verify(usuarioRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    public void testRegistrarUsuario() {
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO();
        registroUsuarioDTO.setNome("Test User");
        registroUsuarioDTO.setEmail("test@example.com");
        registroUsuarioDTO.setSenha("password");
        registroUsuarioDTO.setRole(RoleEnum.CANDIDATO);

        Role role = new Role();
        role.setNome(RoleEnum.CANDIDATO);
        when(roleRepository.findByNome(RoleEnum.CANDIDATO)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(new Usuario());

        Usuario result = authService.registrarUsuario(registroUsuarioDTO);

        assertNotNull(result);
        verify(roleRepository, times(1)).findByNome(RoleEnum.CANDIDATO);
        verify(passwordEncoder, times(1)).encode("password");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testRegistrarUsuario_InvalidRole() {
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO();
        registroUsuarioDTO.setRole(RoleEnum.CANDIDATO);

        when(roleRepository.findByNome(RoleEnum.CANDIDATO)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            authService.registrarUsuario(registroUsuarioDTO);
        });

        verify(roleRepository, times(1)).findByNome(RoleEnum.CANDIDATO);
        verify(passwordEncoder, times(0)).encode(anyString());
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }
}