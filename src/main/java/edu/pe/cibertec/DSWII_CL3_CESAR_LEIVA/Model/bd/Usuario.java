package edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Model.bd;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "email")
    private String email;
    @Column(name = "nomusuario")
    private String nomusuario;
    @Column(name = "password")
    private String password;
    @Column(name = "activo")
    private Boolean activo;
    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER )
    @JoinTable(name = "usuario_rol", joinColumns =
    @JoinColumn(name = "idusuario"),
            inverseJoinColumns = @JoinColumn(name = "idrol"))
    private Set<Rol> roles;
}
