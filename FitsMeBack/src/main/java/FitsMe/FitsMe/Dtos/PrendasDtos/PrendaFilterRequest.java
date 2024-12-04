package FitsMe.FitsMe.Dtos.PrendasDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrendaFilterRequest {

    private List<Long> prendas;
    private List<Long> coloresId;
    private List<Long> climasId;
    private List<Long> estilosId;
    private boolean favorite;
    private Long userId;


}
