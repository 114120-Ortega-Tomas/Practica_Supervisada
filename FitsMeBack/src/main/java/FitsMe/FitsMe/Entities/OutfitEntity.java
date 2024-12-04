package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Outfits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long outfitId;

   @Column
   private String nombre;

   @Column
   private String fechaCreacion;

   @Column
   private boolean favorito;

   @ManyToMany
   @JoinTable(
           name = "Outfit_Prendas",
           joinColumns = @JoinColumn(name = "outfit_id"),
           inverseJoinColumns = @JoinColumn(name = "prenda_id")
   )
   @ToString.Exclude
   private List<PrendaEntity> prendaEntities;


   @Column
   private boolean privado;

   @ManyToOne
   @JoinColumn(name = "usuarioId")
   @ToString.Exclude
   private UsuarioEntity usuarioEntity;
}
