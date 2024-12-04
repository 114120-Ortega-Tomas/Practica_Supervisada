package FitsMe.FitsMe.Dtos.OutfitsDto;

import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Entities.PrendaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitRequest {

    private String nombre;

    private String descripcion;
    private Long usuarioId;

    private boolean favorito;

    private List<Long> prendas;



}
