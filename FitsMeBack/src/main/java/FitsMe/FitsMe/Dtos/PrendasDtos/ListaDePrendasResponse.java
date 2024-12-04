package FitsMe.FitsMe.Dtos.PrendasDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class ListaDePrendasResponse {

    private Long UsuarioId;
    private String Usuario;
    private List<PrendaResponse> prendas;


}
