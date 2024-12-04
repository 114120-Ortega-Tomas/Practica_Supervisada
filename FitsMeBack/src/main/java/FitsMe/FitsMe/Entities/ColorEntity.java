package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Color")
@Data@AllArgsConstructor@NoArgsConstructor
public class ColorEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long colorId;

   @Column
   private String descripcion;


}
