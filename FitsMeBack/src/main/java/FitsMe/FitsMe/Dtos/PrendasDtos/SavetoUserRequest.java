package FitsMe.FitsMe.Dtos.PrendasDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class SavetoUserRequest {

    private Long userId;
    private Long prendaId;



}
