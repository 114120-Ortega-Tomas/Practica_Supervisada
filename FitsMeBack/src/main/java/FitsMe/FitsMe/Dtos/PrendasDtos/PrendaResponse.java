package FitsMe.FitsMe.Dtos.PrendasDtos;

import FitsMe.FitsMe.Dtos.AtributoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class PrendaResponse {

    private Long prendaId;

    private String descripcion;

    private AtributoResponse tipoPrenda;

    private String usuario;
    private int usos;

    private List<AtributoResponse> estilos;

    private List<AtributoResponse> climas;

    private List<AtributoResponse> colores;

    private String base64;

    private boolean privado;

    private boolean favorito;

    private boolean prestada;
    private Long prestadabyUserId;

    private boolean prestamo;
    private Integer genteUsando;


}
