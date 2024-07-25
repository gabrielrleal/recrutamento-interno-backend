package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.models.Usuario;
import gabrielleal.recrutamentointerno.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        verify(usuarioRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(usuarioRepository.findByEmail("notfound@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("notfound@example.com");
        });

        verify(usuarioRepository, times(1)).findByEmail("notfound@example.com");
    }
}