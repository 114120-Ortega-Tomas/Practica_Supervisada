package FitsMe.FitsMe.Dtos.Eventos;

import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponse {

    private Long eventoId;
    private String descripcion;

    private String fecha;

    private String horario;

    private String usuario;

    private String descripcionOutfit;

    private OutfitResponse outfitResponse;

}
