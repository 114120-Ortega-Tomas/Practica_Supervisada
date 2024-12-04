package FitsMe.FitsMe.Dtos.UsuariosDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class UsuarioRequest {

    private String nombre;
    private String apellido;

    private String email;

    private String constraseña;

    private String fecNac;

    private String username;

    private String pais;
}
