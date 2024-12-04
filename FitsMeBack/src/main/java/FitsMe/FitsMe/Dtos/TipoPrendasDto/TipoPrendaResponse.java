package FitsMe.FitsMe.Dtos.TipoPrendasDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class TipoPrendaResponse {

    private Long TipoPrendaId;

    private String descripcion;

    private String estilo;

}