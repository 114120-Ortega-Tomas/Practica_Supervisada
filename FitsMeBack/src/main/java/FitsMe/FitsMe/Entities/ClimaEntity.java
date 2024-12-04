package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Clima")
@Data@AllArgsConstructor@NoArgsConstructor
public class ClimaEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long climaId;

   @Column
   private String descripcion;


}
