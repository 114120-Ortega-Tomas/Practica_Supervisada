package FitsMe.FitsMe.Dtos.SolicitudPrendaDto;

import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudPrendaResponse {

    Long solicitudId;
    Long userIdFrom;
    String usernameFrom;
    Long userIdTo;
    PrendaResponse prenda;

}
