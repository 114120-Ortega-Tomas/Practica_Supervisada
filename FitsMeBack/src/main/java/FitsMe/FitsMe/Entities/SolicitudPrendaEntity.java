package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SolicitudPrenda")
@Data@AllArgsConstructor@NoArgsConstructor
public class SolicitudPrendaEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long solicitudPrenda;
   @Column
   private boolean aceptada;
   @Column
   private Long userToId;
   @Column
   private Long userFromId;
   @ManyToOne
   @JoinColumn(name = "usuarioPrendaId")
   private Usuario_Prenda usuarioPrenda;
   @Column
   private String message;


}
