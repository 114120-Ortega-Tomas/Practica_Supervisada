package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Roles")
@Data@AllArgsConstructor@NoArgsConstructor
public class RolesEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long RoleId;

   @Column
   private String descripcion;



}
