package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Usuarios")
@Data@AllArgsConstructor@NoArgsConstructor
public class UsuarioEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long usuarioId;
   @Column
   private Long role;
   @Column
   private String apellido;
   @Column
   private String nombre;
   @Column
   private String email;
   @Column
   private String contraseña;
   @Column
   private String fecNac;
   @Column
   private LocalDate fecCreacion;
   @Column
   private String username;
   @Column
   private String pais;
   @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<Usuario_Prenda> usuarioPrendas;
   @OneToMany(mappedBy = "usuarioEntity", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<OutfitEntity> outfitEntities;

   @ManyToMany
   @JoinTable(
           name = "amigos",
           joinColumns = @JoinColumn(name = "usuario_id"),
           inverseJoinColumns = @JoinColumn(name = "amigo_id")
   )
   private List<UsuarioEntity> amigos;




}
