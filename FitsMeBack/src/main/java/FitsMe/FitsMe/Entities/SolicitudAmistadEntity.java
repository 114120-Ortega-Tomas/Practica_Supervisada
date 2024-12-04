package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SolicitudAmistad")
@Data@AllArgsConstructor@NoArgsConstructor
public class SolicitudAmistadEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long solicitudAmistadId;

   @Column
   private boolean aceptada;
   @Column
   private Long userToId;
   @Column
   private Long userFromId;


}
