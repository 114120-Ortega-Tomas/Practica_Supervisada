package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Estilos")
@Data@AllArgsConstructor@NoArgsConstructor
public class EstilosEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long estiloId;

   @Column
   private String descripcion;

}
