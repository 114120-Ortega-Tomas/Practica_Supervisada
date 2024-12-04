package FitsMe.FitsMe.Services.Impl;



import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadRequest;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadResponse;
import FitsMe.FitsMe.Dtos.SolicitudPrendaDto.SolicitudPrendaRequest;
import FitsMe.FitsMe.Dtos.SolicitudPrendaDto.SolicitudPrendaResponse;
import FitsMe.FitsMe.Entities.*;
import FitsMe.FitsMe.Repositories.*;
import FitsMe.FitsMe.Services.SolicitudAmistadService;
import FitsMe.FitsMe.Services.SolicitudPrendaService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudPrendaServiceImpl implements SolicitudPrendaService {

    @Autowired
    private SolicitudPrendaJpaRepository solicitudPrendaJpaRepository;
    @Autowired
    private PrendaJpaRepository prendaJpaRepository;
    @Autowired
    private UsuarioPrendaJpaRepository usuarioPrendaJpaRepository;
    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public SolicitudPrendaResponse sendSolicitud(SolicitudPrendaRequest request) {
        List<SolicitudPrendaEntity> solicitudesEnviadas = solicitudPrendaJpaRepository.findAll();
        Usuario_Prenda usuarioPrenda = usuarioPrendaJpaRepository.findByUsuarioAndPrenda
                (usuarioJpaRepository.findById(request.getUserIdTo()).get(),prendaJpaRepository.findById(request.getPrendaId()).get()).get();
        for (SolicitudPrendaEntity sol : solicitudesEnviadas)
        {
           if(sol.getUserFromId().equals(request.getUserIdFrom()) && sol.getUserToId().equals(request.getUserIdTo()) && sol.getUsuarioPrenda().getPrenda().getPrendaId().equals(request.getPrendaId())){
               throw new RuntimeException("Ya has enviado una solicitud de prenda a este usuario");}
        }
        Optional<UsuarioEntity> usuarioEntity = usuarioJpaRepository.findById(request.getUserIdFrom());
        if(!usuarioEntity.isPresent()){throw new RuntimeException("El usuario no existe");}
        SolicitudPrendaEntity solicitudPrendaEntity = new SolicitudPrendaEntity();
        solicitudPrendaEntity.setUserFromId(request.getUserIdFrom());
        solicitudPrendaEntity.setUserToId(request.getUserIdTo());
        solicitudPrendaEntity.setUsuarioPrenda(usuarioPrenda);
        solicitudPrendaJpaRepository.save(solicitudPrendaEntity);
        SolicitudPrendaResponse solicitudPrenda =  new SolicitudPrendaResponse();
        solicitudPrenda.setSolicitudId(solicitudPrenda.getSolicitudId());
        return solicitudPrenda;
    }

    @Override
    public Boolean RejectSolicitud(Long solicitudId) {
        Optional<SolicitudPrendaEntity> solicitudAmistadEntity  = solicitudPrendaJpaRepository.findById(solicitudId);
        if(!solicitudAmistadEntity.isPresent()){throw new RuntimeException("La solicitud no existe");}
        solicitudAmistadEntity.get().setAceptada(false);
        Usuario_Prenda usuarioPrenda = usuarioPrendaJpaRepository.findByUsuarioAndPrenda( usuarioJpaRepository.findById(solicitudAmistadEntity.get().getUserToId()).get(),solicitudAmistadEntity.get().getUsuarioPrenda().getPrenda()).get();
        usuarioPrenda.setPrestada(false);
        usuarioPrendaJpaRepository.save(usuarioPrenda);
        solicitudPrendaJpaRepository.delete(solicitudAmistadEntity.get());
        return true ;
    }

    @Override
    public Boolean AcceptSolicitud(Long solicitudId) {
        Optional<SolicitudPrendaEntity> solicitudPrenda = solicitudPrendaJpaRepository.findById(solicitudId);
        if(!solicitudPrenda.isPresent()){
            throw new RuntimeException("La solicitud no existe");
        }
        SolicitudPrendaEntity solicitudPrendaEntity = solicitudPrenda.get();
        solicitudPrendaEntity.setAceptada(true);
        Usuario_Prenda usuarioPrenda = usuarioPrendaJpaRepository.findByUsuarioAndPrenda( usuarioJpaRepository.findById(solicitudPrendaEntity.getUserToId()).get(),solicitudPrendaEntity.getUsuarioPrenda().getPrenda()).get();
        usuarioPrenda.setPrestada(true);
        Usuario_Prenda usuarioPrenda1 = new Usuario_Prenda();
        usuarioPrenda1.setUsuario(usuarioJpaRepository.findById(solicitudPrendaEntity.getUserFromId()).get());
        usuarioPrenda1.setPrenda(prendaJpaRepository.findById(solicitudPrendaEntity.getUsuarioPrenda().getPrenda().getPrendaId()).get());
        usuarioPrenda1.setPrestada(false);
        usuarioPrenda1.setPrestamo(true);
        usuarioPrenda1.setPrestadabyUserId(solicitudPrendaEntity.getUserToId());
        usuarioPrendaJpaRepository.save(usuarioPrenda);
        usuarioPrendaJpaRepository.save(usuarioPrenda1);
        solicitudPrendaJpaRepository.delete(solicitudPrendaEntity);
        return true;
    }

    @Override
    public List<SolicitudPrendaResponse> getSolicitudesbyUser(Long userId) {
        List<SolicitudPrendaEntity> solicitudPrendaEntities = solicitudPrendaJpaRepository.findAllByUserToId(userId);
        List<SolicitudPrendaResponse> solicitudes = new ArrayList<>();
        for(SolicitudPrendaEntity sol : solicitudPrendaEntities)
        {
            SolicitudPrendaResponse solicitudResponse = new SolicitudPrendaResponse();
            solicitudResponse.setSolicitudId(sol.getSolicitudPrenda());
            PrendaResponse prendaResponse = modelMapper.map(sol.getUsuarioPrenda().getPrenda(), PrendaResponse.class);
            prendaResponse.setBase64(sol.getUsuarioPrenda().getPrenda().getBase64());
            solicitudResponse.setPrenda(prendaResponse);
            String username =  usuarioJpaRepository.findById(sol.getUserFromId()).get().getUsername();
            solicitudResponse.setUsernameFrom(username);
            solicitudes.add(solicitudResponse);
        }
        return solicitudes;
    }

    @Override
    public Boolean ReturnPrenda(Long prendaId , Long userId , Long prestadaByUserId) {
        PrendaEntity prenda = prendaJpaRepository.findById(prendaId).get();
        Usuario_Prenda usuarioPrenda = usuarioPrendaJpaRepository.findByUsuarioAndPrenda(usuarioJpaRepository.findById(userId).get(),prenda).get();
        usuarioPrendaJpaRepository.delete(usuarioPrenda);
        Usuario_Prenda usuarioPrenda1 = usuarioPrendaJpaRepository.findByUsuarioAndPrenda(usuarioJpaRepository.findById(prestadaByUserId).get(),prenda).get();
        usuarioPrenda1.setPrestada(false);
        usuarioPrendaJpaRepository.save(usuarioPrenda1);
        return true;
    }
}
