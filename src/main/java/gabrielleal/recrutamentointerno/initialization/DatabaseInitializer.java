package gabrielleal.recrutamentointerno.initialization;

import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Role;
import gabrielleal.recrutamentointerno.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByNome(RoleEnum.ADMINISTRADOR)) {
            Role adminRole = new Role();
            adminRole.setNome(RoleEnum.ADMINISTRADOR);
            roleRepository.save(adminRole);
        }

        if (!roleRepository.existsByNome(RoleEnum.CANDIDATO)) {
            Role candidatoRole = new Role();
            candidatoRole.setNome(RoleEnum.CANDIDATO);
            roleRepository.save(candidatoRole);
        }
    }
}
