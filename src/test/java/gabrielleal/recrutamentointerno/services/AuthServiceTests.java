package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.dtos.RegistroUsuarioDTO;
import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Role;
import gabrielleal.recrutamentointerno.models.Usuario;
import gabrielleal.recrutamentointerno.repositories.RoleRepository;
import gabrielleal.recrutamentointerno.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    @Test
    public void testRegistrarUsuario_ComPapelValido() {
        // Dados de registro
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO();
        registroUsuarioDTO.setNome("João Silva");
        registroUsuarioDTO.setEmail("joao.silva@example.com");
        registroUsuarioDTO.setSenha("senha123");
        registroUsuarioDTO.setRole(RoleEnum.CANDIDATO);

        // Simula a existência do papel no repositório
        when(roleRepository.findByNome(RoleEnum.CANDIDATO)).thenReturn(Optional.of(new Role()));

        // Simula a codificação da senha
        when(passwordEncoder.encode("senha123")).thenReturn("senhaCodificada");

        // Simula a salvamento do usuário
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Chama o método de registro
        Usuario usuario = authService.registrarUsuario(registroUsuarioDTO);

        // Verifica se o usuário foi registrado corretamente
        assertNotNull(usuario);
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao.silva@example.com", usuario.getEmail());
        assertEquals("senhaCodificada", usuario.getSenha());
        assertTrue(usuario.getRoles().contains("CANDIDATO"));
        assertNotNull(usuario.getCandidato());

        // Verifica se os métodos do repositório foram chamados corretamente
        verify(roleRepository, times(1)).findByNome(RoleEnum.CANDIDATO);
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testRegistrarUsuario_ComPapelInvalido() {
        // Dados de registro
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO();
        registroUsuarioDTO.setNome("João Silva");
        registroUsuarioDTO.setEmail("joao.silva@example.com");
        registroUsuarioDTO.setSenha("senha123");
        registroUsuarioDTO.setRole(RoleEnum.CANDIDATO);

        // Simula a inexistência do papel no repositório
        when(roleRepository.findByNome(RoleEnum.CANDIDATO)).thenReturn(Optional.empty());

        // Verifica se uma exceção é lançada em caso de papel inválido
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.registrarUsuario(registroUsuarioDTO);
        });

        assertEquals("Papel inválido: CANDIDATO", exception.getMessage());

        // Verifica se os métodos do repositório foram chamados corretamente
        verify(roleRepository, times(1)).findByNome(RoleEnum.CANDIDATO);
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}