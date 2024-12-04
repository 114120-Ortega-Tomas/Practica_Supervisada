package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Prendas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrendaEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long prendaId;

   @Column(name = "base64", columnDefinition = "MEDIUMTEXT")
   private String base64;

   @Column
   private LocalDate FechaCreacion;

   @Column
   private String descripcion;

   @Column
   private String contentType;




   @ManyToOne
   @JoinColumn(name = "TipoPrenda")
   @ToString.Exclude
   private TipoPrendaEntity tipoPrendaEntity;

   @ManyToMany
   @JoinTable(
           name = "Prenda_Estilos",
           joinColumns = @JoinColumn(name = "prenda_id"),
           inverseJoinColumns = @JoinColumn(name = "estilo_id")
   )
   private List<EstilosEntity> estilosEntity;

   @ManyToMany
   @JoinTable(
           name = "Prenda_Clima",
           joinColumns = @JoinColumn(name = "prenda_id"),
           inverseJoinColumns = @JoinColumn(name = "clima_id")
   )
   private List<ClimaEntity> climaEntities;

   @ManyToMany
   @JoinTable(
           name = "Prenda_Colores",
           joinColumns = @JoinColumn(name = "prenda_id"),
           inverseJoinColumns = @JoinColumn(name = "color_id")
   )
   private List<ColorEntity> colorEntities;

   @Column
   private boolean privado;

   @OneToMany(mappedBy = "prenda", cascade = CascadeType.ALL, orphanRemoval = true)
   @ToString.Exclude
   private Set<Usuario_Prenda> usuarioPrendas;

   @ManyToMany(mappedBy = "prendaEntities")
   @ToString.Exclude
   private List<OutfitEntity> outfitEntities;
}
