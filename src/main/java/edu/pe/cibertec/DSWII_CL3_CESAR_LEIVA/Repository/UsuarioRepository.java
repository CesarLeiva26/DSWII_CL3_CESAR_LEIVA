package edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Repository;

import edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Model.bd.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByNomusuario(String nomusuario);
}
