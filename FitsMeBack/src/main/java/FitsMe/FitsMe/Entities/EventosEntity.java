package FitsMe.FitsMe.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Eventos")
@Data@AllArgsConstructor@NoArgsConstructor
public class EventosEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long eventoId;
   @Column
   private String descripcion;
   @Column
   private String fecha;
   @Column
   private String horario;
   @ManyToOne
   @JoinColumn(name = "Usuario")
   private UsuarioEntity usuarioEntity;
   @ManyToOne
   @JoinColumn(name = "Outfit")
   private OutfitEntity outfitEntity;



}
