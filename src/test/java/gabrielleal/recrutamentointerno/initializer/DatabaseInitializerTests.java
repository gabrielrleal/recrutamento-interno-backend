package gabrielleal.recrutamentointerno.initialization;

import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Role;
import gabrielleal.recrutamentointerno.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class DatabaseInitializerTests {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DatabaseInitializer databaseInitializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun() throws Exception {
        // Mock the behavior of the roleRepository
        when(roleRepository.existsByNome(RoleEnum.ADMINISTRADOR)).thenReturn(false);
        when(roleRepository.existsByNome(RoleEnum.CANDIDATO)).thenReturn(false);

        // Run the initializer
        databaseInitializer.run();

        // Verify that the roles were saved
        verify(roleRepository, times(1)).save(argThat(role -> role.getNome() == RoleEnum.ADMINISTRADOR));
        verify(roleRepository, times(1)).save(argThat(role -> role.getNome() == RoleEnum.CANDIDATO));
    }

    @Test
    public void testRunRolesAlreadyExist() throws Exception {
        // Mock the behavior of the roleRepository
        when(roleRepository.existsByNome(RoleEnum.ADMINISTRADOR)).thenReturn(true);
        when(roleRepository.existsByNome(RoleEnum.CANDIDATO)).thenReturn(true);

        // Run the initializer
        databaseInitializer.run();

        // Verify that the roles were not saved again
        verify(roleRepository, times(0)).save(any(Role.class));
    }
}