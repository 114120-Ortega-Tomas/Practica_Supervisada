package FitsMe.FitsMe.Dtos.OutfitsDto;

import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitResponse {
    private Long id;
    private String nombre;
    private String usuario;
    private boolean favorito;
    private List<PrendaResponse> prendas;


}
