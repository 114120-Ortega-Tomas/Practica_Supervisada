package FitsMe.FitsMe.Dtos.UsuariosDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class UsuarioResponse {

    private Long id ;

    private String nombre;
    private String apellido;

    private String username;
    private int role;

    private String email;

    private String constraseña;

    private String fecNac;

    private String pais;
    private Boolean amigos;

}
