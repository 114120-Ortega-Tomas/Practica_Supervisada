package FitsMe.FitsMe.Services;


import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadRequest;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadResponse;
import FitsMe.FitsMe.Dtos.SolicitudPrendaDto.SolicitudPrendaRequest;
import FitsMe.FitsMe.Dtos.SolicitudPrendaDto.SolicitudPrendaResponse;
import FitsMe.FitsMe.Entities.SolicitudAmistadEntity;
import FitsMe.FitsMe.Entities.SolicitudPrendaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SolicitudPrendaService {

    SolicitudPrendaResponse sendSolicitud(SolicitudPrendaRequest request);
    Boolean RejectSolicitud(Long solicitudId);

    Boolean AcceptSolicitud(Long solicitudId );
    List<SolicitudPrendaResponse> getSolicitudesbyUser(Long userId);

    Boolean ReturnPrenda(Long prendaId , Long userId , Long prestadaByUserId);

}
