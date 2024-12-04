package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Usuario_Prenda")
@Data@AllArgsConstructor@NoArgsConstructor
public class Usuario_Prenda {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "usuario_prenda_id")
   private Long usuarioPrendaId;

   @ManyToOne
   @JoinColumn(name = "usuario_id")
   private UsuarioEntity usuario;

   @ManyToOne
   @JoinColumn(name = "prenda_id")
   private PrendaEntity prenda;

   @Column(name = "favorita")
   private boolean favorita;

   @Column(name = "fecha_adicion")
   private String fechaAdicion;

   @Column(name = "fecha_ultima_uso")
   private String fechaUltimaUso;

   @Column(name = "veces_usada")
   private int vecesUsada;
   @Column
   private Boolean prestamo;
   @Column
   private Long prestadabyUserId;
   @Column
   private Boolean prestada;


   @OneToMany(mappedBy = "usuarioPrenda")
   private List<SolicitudPrendaEntity> solicitudes = new ArrayList<>();



}
