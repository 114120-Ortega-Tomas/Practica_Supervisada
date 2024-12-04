package FitsMe.FitsMe.Services;


import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuariosPorMesResponse;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {


    List<UsuarioResponse> getUsuarios();
    UsuarioResponse Login(LogInRequest logInRequest);
    UsuarioResponse PostUsuario(UsuarioRequest usuarioRequest);
    UsuarioResponse PutUsuario(UsuarioRequest usuarioRequest,Long Id);

    List<UsuarioResponse> getUsuariobyUsername(String username,Long id);
    List<UsuarioResponse> getAmigos(Long id);

    List<UsuariosPorMesResponse> getUsuariosPorMes(String dateFrom , String dateTo);

}
