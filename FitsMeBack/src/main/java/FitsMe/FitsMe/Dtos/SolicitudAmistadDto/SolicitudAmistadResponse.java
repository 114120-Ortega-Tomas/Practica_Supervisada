package FitsMe.FitsMe.Dtos.SolicitudAmistadDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudAmistadResponse {
    Long solicitudId;
    Long userIdFrom;
    String usernameFrom;

    Long userIdTo;

}
