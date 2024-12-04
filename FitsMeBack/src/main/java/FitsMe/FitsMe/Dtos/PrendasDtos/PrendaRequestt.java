package FitsMe.FitsMe.Dtos.PrendasDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrendaRequestt {

    private String base64;

    private String descripcion;

    private String contentType;

    private List<Long> color;
    private Long userId;

    private Long tipoPrenda;

    private List<Long> estilo;

    private List<Long> clima;
    private boolean privado;
    private boolean favorito;

}
