package FitsMe.FitsMe.Dtos.Eventos;

import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Entities.OutfitEntity;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {

    private String descripcion;

    private String fecha;

    private String horario;

    private Long usuarioId;

    private Long outfitId;

}
