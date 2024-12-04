package FitsMe.FitsMe.Dtos.UsuariosDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class UsuariosPorMesResponse {


    private String Mes;
    private Integer Ano;

    private List<UsuarioResponse> usuarios;
    private int amount;


}
