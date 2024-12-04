package FitsMe.FitsMe.Services.Impl;



import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuariosPorMesResponse;
import FitsMe.FitsMe.Entities.RolesEntity;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import FitsMe.FitsMe.Repositories.UsuarioJpaRepository;
import FitsMe.FitsMe.Services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<UsuarioResponse> getUsuarios() {
        List<UsuarioEntity> usuarioEntities = usuarioJpaRepository.findAll();
        List<UsuarioResponse> usuarioResponses = new ArrayList<>();
        for(UsuarioEntity usuarioEntity : usuarioEntities){
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setConstraseña(usuarioEntity.getContraseña());
            usuarioResponse.setEmail(usuarioEntity.getEmail());
            usuarioResponse.setId(usuarioEntity.getUsuarioId());
            usuarioResponse.setNombre(usuarioEntity.getNombre());
            usuarioResponse.setFecNac(usuarioEntity.getFecNac());
            usuarioResponses.add(usuarioResponse);
        }
        return usuarioResponses;
    }

    @Override
    public UsuarioResponse Login(LogInRequest logInRequest) {
        List<UsuarioEntity> usuarioEntities = usuarioJpaRepository.findAll();
        for(UsuarioEntity usuarioEntity : usuarioEntities){
            if (usuarioEntity.getEmail().equals(logInRequest.getEmail()) && usuarioEntity.getContraseña().equals(logInRequest.getConstraseña())){
                UsuarioResponse response =  new UsuarioResponse();
                response.setUsername(usuarioEntity.getUsername());
                response.setEmail(usuarioEntity.getEmail());
                response.setNombre(usuarioEntity.getNombre());
                response.setApellido(usuarioEntity.getApellido());
                response.setPais(usuarioEntity.getPais());
                response.setRole(usuarioEntity.getRole().intValue());
                response.setFecNac(usuarioEntity.getFecNac());
                response.setId(usuarioEntity.getUsuarioId());
                response.setConstraseña(usuarioEntity.getContraseña());
                return response;
            }
        }
        throw new RuntimeException("Credenciales incorrectas");
    }

    @Override
    public UsuarioResponse PostUsuario(UsuarioRequest usuarioRequest) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNombre(usuarioRequest.getNombre());
        usuarioEntity.setApellido(usuarioRequest.getApellido());
        usuarioEntity.setContraseña(usuarioRequest.getConstraseña());
        usuarioEntity.setEmail(usuarioRequest.getEmail());
        usuarioEntity.setFecNac(usuarioRequest.getFecNac());
        usuarioEntity.setFecCreacion(LocalDate.now());
        usuarioEntity.setUsername(usuarioRequest.getUsername());
        usuarioEntity.setRole(2L);
        usuarioJpaRepository.save(usuarioEntity);
        UsuarioResponse response = new UsuarioResponse();
        UsuarioEntity user = usuarioEntity;
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setApellido(user.getApellido());
        response.setNombre(user.getNombre());
        response.setRole(user.getRole().intValue());
        response.setPais(user.getPais());
        response.setConstraseña(user.getContraseña());
        response.setFecNac(user.getFecNac());
        response.setId(user.getUsuarioId());
        return response;
    }

    @Override
    public UsuarioResponse PutUsuario(UsuarioRequest usuarioRequest , Long id) {
        Optional<UsuarioEntity> usuarioEntity = usuarioJpaRepository.findById(id);
        if (usuarioEntity.get().getNombre().isEmpty()) {
            throw new RuntimeException("El usuario con Id " + id + "no existe");
        }
        usuarioEntity.get().setNombre(usuarioRequest.getNombre());
        usuarioEntity.get().setContraseña(usuarioRequest.getConstraseña());
        usuarioEntity.get().setEmail(usuarioRequest.getEmail());
        usuarioEntity.get().setPais(usuarioRequest.getPais());
        usuarioEntity.get().setApellido(usuarioRequest.getApellido());
        usuarioEntity.get().setFecNac(usuarioRequest.getFecNac());
        usuarioEntity.get().setUsername(usuarioRequest.getUsername());
        usuarioJpaRepository.save(usuarioEntity.get());
        UsuarioResponse response = new UsuarioResponse();
        UsuarioEntity user = usuarioEntity.get();
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setNombre(user.getNombre());
        response.setRole(user.getRole().intValue());
        response.setApellido(user.getApellido());
        response.setPais(user.getPais());
        response.setConstraseña(user.getContraseña());
        response.setFecNac(user.getFecNac());
        response.setId(user.getUsuarioId());
        return response;
    }

    @Override
    public List<UsuarioResponse> getUsuariobyUsername(String username , Long id) {
        UsuarioEntity usuarioEntity = usuarioJpaRepository.findById(id).get();
        List<UsuarioEntity> usuarios = usuarioJpaRepository.findUsuarioEntitiesByUsernameStartingWith(username);
        UsuarioResponse response = new UsuarioResponse();
        response.setUsername(usuarioEntity.getUsername());
        response.setEmail(usuarioEntity.getEmail());
        response.setNombre(usuarioEntity.getNombre());
        response.setPais(usuarioEntity.getPais());
        response.setAmigos(false);
        response.setRole(usuarioEntity.getRole().intValue());
        response.setConstraseña(usuarioEntity.getContraseña());
        response.setFecNac(usuarioEntity.getFecNac());
        response.setId(usuarioEntity.getUsuarioId());
        List<UsuarioResponse> responseList = new ArrayList<>();
        if(usuarios.isEmpty()){throw new RuntimeException("El usuario no existe");}
        for(UsuarioEntity user : usuarios){
            UsuarioResponse response1 = new UsuarioResponse();
            if(usuarioEntity.getAmigos().contains(user)){
                response1.setAmigos(true);
            }else {
                response1.setAmigos(false);
            }
            response1.setUsername(user.getUsername());
            response1.setEmail(user.getEmail());
            response1.setNombre(user.getNombre());
            response1.setPais(user.getPais());
            response1.setRole(user.getRole().intValue());
            response1.setConstraseña(user.getContraseña());
            response1.setFecNac(user.getFecNac());
            response1.setId(user.getUsuarioId());
            responseList.add(response1);
        }
        if(responseList.contains(response)){responseList.remove(response);}
        return responseList;

    }

    @Override
    public List<UsuarioResponse> getAmigos(Long id) {
        UsuarioEntity usuarioEntity = usuarioJpaRepository.findById(id).get();
        List<UsuarioResponse> amigos = new ArrayList<>();
        for(UsuarioEntity amigo : usuarioEntity.getAmigos())
        {
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setUsername(amigo.getUsername());
            usuarioResponse.setEmail(amigo.getEmail());
            usuarioResponse.setNombre(amigo.getNombre());
            usuarioResponse.setPais(amigo.getPais());
            usuarioResponse.setAmigos(true);
            usuarioResponse.setConstraseña(amigo.getContraseña());
            usuarioResponse.setFecNac(amigo.getFecNac());
            usuarioResponse.setRole(amigo.getRole().intValue());
            usuarioResponse.setId(amigo.getUsuarioId());
            amigos.add(usuarioResponse);
        }
        return amigos;

    }

    @Override
    public List<UsuariosPorMesResponse> getUsuariosPorMes(String dateFrom, String dateTo) {
        LocalDate dateFromLocal = LocalDate.parse(dateFrom);
        LocalDate dateToLocal = LocalDate.parse(dateTo);
        List<UsuarioEntity> users = usuarioJpaRepository.findUsuarioEntitiesByFecCreacionBetween(dateFromLocal, dateToLocal);

        Map<YearMonth, List<UsuarioResponse>> groupedByMonth = new HashMap<>();

        for (UsuarioEntity user : users) {
            YearMonth month = YearMonth.from(user.getFecCreacion());
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setUsername(user.getUsername());
            usuarioResponse.setEmail(user.getEmail());
            usuarioResponse.setNombre(user.getNombre());
            usuarioResponse.setApellido(user.getApellido());
            usuarioResponse.setPais(user.getPais());
            usuarioResponse.setAmigos(false);
            usuarioResponse.setConstraseña(user.getContraseña());
            usuarioResponse.setFecNac(user.getFecNac());
            usuarioResponse.setRole(user.getRole().intValue());
            usuarioResponse.setId(user.getUsuarioId());

            groupedByMonth.computeIfAbsent(month, k -> new ArrayList<>()).add(usuarioResponse);
        }

        List<UsuariosPorMesResponse> response = new ArrayList<>();

        // Lista de nombres de meses en español
        String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        for (Map.Entry<YearMonth, List<UsuarioResponse>> entry : groupedByMonth.entrySet()) {
            UsuariosPorMesResponse usuariosPorMes = new UsuariosPorMesResponse();
            YearMonth yearMonth = entry.getKey();
            String monthName = monthNames[yearMonth.getMonthValue() - 1]; // Obtiene el nombre del mes correspondiente

            usuariosPorMes.setMes(monthName );
            usuariosPorMes.setAno(yearMonth.getYear());
            usuariosPorMes.setUsuarios(entry.getValue());
            usuariosPorMes.setAmount(entry.getValue().size());
            response.add(usuariosPorMes);
        }

        return response;
    }


}
