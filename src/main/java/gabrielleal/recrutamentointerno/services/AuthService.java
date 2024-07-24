package gabrielleal.recrutamentointerno.services;

import gabrielleal.recrutamentointerno.dtos.LoginDTO;
import gabrielleal.recrutamentointerno.dtos.RegistroUsuarioDTO;
import gabrielleal.recrutamentointerno.enums.RoleEnum;
import gabrielleal.recrutamentointerno.models.Candidato;
import gabrielleal.recrutamentointerno.models.Role;
import gabrielleal.recrutamentointerno.models.Usuario;
import gabrielleal.recrutamentointerno.repositories.RoleRepository;
import gabrielleal.recrutamentointerno.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository; // Adicione a injeção do RoleRepository

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Autowired
    private JwtUtil jwtUtil;

    public String authenticateUser(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null || !passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return jwtUtil.generateToken(email);

    /*public void authenticateUser(String email, String senha) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*/


    public void logoutUser() {
        SecurityContextHolder.clearContext();
    }

    public boolean usuarioExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public String login(LoginDTO loginDto) {
        try {
            authenticateUser(loginDto.getEmail(), loginDto.getSenha());
            return "Login successful!";
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    public Usuario registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) {

        RoleEnum roleEnum = registroUsuarioDTO.getRole();
        Role role = roleRepository.findByNome(roleEnum)
                .orElseThrow(() -> new IllegalArgumentException("Papel inválido: " + roleEnum));
        Usuario usuario = new Usuario();
        usuario.setNome(registroUsuarioDTO.getNome());
        usuario.setEmail(registroUsuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(registroUsuarioDTO.getSenha()));
        usuario.setRoles(Collections.singleton(registroUsuarioDTO.getRole().name()));

        if (roleEnum == RoleEnum.CANDIDATO) {
            Candidato candidato = new Candidato();
            candidato.setNome(registroUsuarioDTO.getNome());
            candidato.setEmail(registroUsuarioDTO.getEmail());
            candidato.setUsuario(usuario);
            usuario.setCandidato(candidato);
        }

        return usuarioRepository.save(usuario);
    }
}
