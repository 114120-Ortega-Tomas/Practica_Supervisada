package FitsMe.FitsMe.Services.Impl;



import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadRequest;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Entities.RolesEntity;
import FitsMe.FitsMe.Entities.SolicitudAmistadEntity;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import FitsMe.FitsMe.Repositories.SolicitudAmistadJpaRespository;
import FitsMe.FitsMe.Repositories.UsuarioJpaRepository;
import FitsMe.FitsMe.Services.SolicitudAmistadService;
import FitsMe.FitsMe.Services.UsuarioService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudServiceImpl implements SolicitudAmistadService {

    @Autowired
    private SolicitudAmistadJpaRespository solicitudAmistadJpaRespository;
    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public SolicitudAmistadEntity sendSolicitud(SolicitudAmistadRequest request) {
        List<SolicitudAmistadEntity> solicitudesEnviadas = solicitudAmistadJpaRespository.findAll();
        for (SolicitudAmistadEntity sol : solicitudesEnviadas)
        {
            if(sol.getUserFromId().equals(request.getUserIdFrom()) && sol.getUserToId().equals(request.getUserIdTo())){
                throw new RuntimeException("Ya has enviado una solicitud a esta persona");
            }
        }
        SolicitudAmistadEntity solicitudAmistadEntity1 = new SolicitudAmistadEntity();
        Optional<UsuarioEntity> usuarioEntity = usuarioJpaRepository.findById(request.getUserIdFrom());
        if(!usuarioEntity.isPresent()){throw new RuntimeException("El usuario no existe");}
        SolicitudAmistadEntity solicitudAmistadEntity = new SolicitudAmistadEntity();
        solicitudAmistadEntity.setUserFromId(request.getUserIdFrom());
        solicitudAmistadEntity.setUserToId(request.getUserIdTo());
        solicitudAmistadJpaRespository.save(solicitudAmistadEntity);
        return solicitudAmistadEntity1;
    }

    @Override
    public Boolean RejectSolicitud(Long solicitudId) {
        Optional<SolicitudAmistadEntity> solicitudAmistadEntity  = solicitudAmistadJpaRespository.findById(solicitudId);
        if(!solicitudAmistadEntity.isPresent()){throw new RuntimeException("La solicitud no existe");}
        solicitudAmistadEntity.get().setAceptada(false);
        solicitudAmistadJpaRespository.delete(solicitudAmistadEntity.get());
        return true ;
    }

    @Transactional
    public Boolean AcceptSolicitud(Long solicitudId) {
        Optional<SolicitudAmistadEntity> solicitudAmistadEntityOpt = solicitudAmistadJpaRespository.findById(solicitudId);
        if (!solicitudAmistadEntityOpt.isPresent()) {
            throw new RuntimeException("La solicitud no existe");
        }

        SolicitudAmistadEntity solicitudAmistadEntity = solicitudAmistadEntityOpt.get();
        solicitudAmistadEntity.setAceptada(true);
        solicitudAmistadJpaRespository.save(solicitudAmistadEntity);

        Optional<UsuarioEntity> usuarioEntityOpt = usuarioJpaRepository.findById(solicitudAmistadEntity.getUserToId());
        Optional<UsuarioEntity> usuarioEntity2Opt = usuarioJpaRepository.findById(solicitudAmistadEntity.getUserFromId());

        if (!usuarioEntityOpt.isPresent() || !usuarioEntity2Opt.isPresent()) {
            throw new RuntimeException("Uno o ambos usuarios no existen");
        }

        UsuarioEntity usuarioEntity = usuarioEntityOpt.get();
        UsuarioEntity usuarioEntity2 = usuarioEntity2Opt.get();

        usuarioEntity.getAmigos().add(usuarioEntity2);
        usuarioJpaRepository.save(usuarioEntity);

        usuarioEntity2.getAmigos().add(usuarioEntity);
        usuarioJpaRepository.save(usuarioEntity2);

        solicitudAmistadJpaRespository.delete(solicitudAmistadEntity);

        return true;
    }

    @Override
    public List<SolicitudAmistadResponse> getSolicitudesbyUser(Long userId) {
       List<SolicitudAmistadEntity> solicitudAmistadEntities = solicitudAmistadJpaRespository.findAllByUserToId(userId);
       List<SolicitudAmistadResponse> responseList = new ArrayList<>();
       for(SolicitudAmistadEntity user : solicitudAmistadEntities){
           UsuarioEntity usuarioEntity = usuarioJpaRepository.findById(user.getUserFromId()).get();
           SolicitudAmistadResponse response = new SolicitudAmistadResponse();
           response.setUserIdFrom(user.getUserFromId());
           response.setSolicitudId(user.getSolicitudAmistadId());
           response.setUserIdTo(user.getUserToId());
           response.setUsernameFrom(usuarioEntity.getUsername());
           responseList.add(response);
       }
       return  responseList;
    }
}
