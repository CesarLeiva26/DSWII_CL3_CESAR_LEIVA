package edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Sevice;

import edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Model.bd.Rol;
import edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Model.bd.Usuario;
import edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.nomUsuario(username);
        return autenticacionUsuario(
                usuario, obtenerListaRolesUsuario(usuario.getRoles()));
    }

    public Usuario findByNomsuario(String usuario){
        return usuarioRepository.nomUsuario(usuario);
    }

    public List<GrantedAuthority> obtenerListaRolesUsuario(Set<Rol> litaRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Rol rol : litaRoles){
            roles.add(new SimpleGrantedAuthority(rol.getNomrol()));
        }
        return new ArrayList<GrantedAuthority>(roles);
    }

    private UserDetails autenticacionUsuario(Usuario usuario,
                                             List<GrantedAuthority> authorityList){
        return new User(usuario.getNomusuario(),
            usuario.getPassword(),
            usuario.getActivo(),
                true,
                true,
                true, authorityList);
        }
}