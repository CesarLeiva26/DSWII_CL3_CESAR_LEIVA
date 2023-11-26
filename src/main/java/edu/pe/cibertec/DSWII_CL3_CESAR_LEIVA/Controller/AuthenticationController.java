package edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Controller;

import edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Model.bd.Usuario;
import edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Model.response.UsuarioResponse;
import edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Sevice.DetalleUsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthenticationController {
    private DetalleUsuarioService detalleUsuarioService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(
            @RequestParam("usuario") String usuario,
            @RequestParam("password") String password
    ) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(usuario, password)
                    );
            if (authentication.isAuthenticated()) {
                Usuario objUsuario = detalleUsuarioService.findByNomsuario(usuario);
                String token = generarToken(objUsuario);
                UsuarioResponse usuarioResponse = new UsuarioResponse(
                        objUsuario.getIdusuario(), objUsuario.getNomusuario(), token
                );
                return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("No se pudo Autenticar. Revisa Usuario y/o Contraseña", HttpStatus.UNAUTHORIZED);
        }
    }
    private String generarToken(Usuario usuario){
        String clave = "@CesarLeiva2023";
        List<GrantedAuthority> authorityList = detalleUsuarioService
                .obtenerListaRolesUsuario(usuario.getRoles());
        String token =
                Jwts.builder()
                        .setId(usuario.getIdusuario().toString())
                        .setSubject(usuario.getNomusuario())
                        .claim("authorities",
                                authorityList.stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 600000))
                        .signWith(SignatureAlgorithm.HS512, clave.getBytes())
                        .compact();
        return  token;
    }

}
