package FitsMe.FitsMe.Dtos.SolicitudPrendaDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudPrendaRequest {

    Long userIdFrom;
    Long userIdTo;
    Long prendaId;

}
