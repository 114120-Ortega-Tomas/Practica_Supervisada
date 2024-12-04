package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TipoPrendas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPrendaEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long tipoPrendaId;

   @Column
   private String descripcion;
}
