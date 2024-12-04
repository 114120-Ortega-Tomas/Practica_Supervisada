package FitsMe.FitsMe.Services;


import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitRequest;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitResponse;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadRequest;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadResponse;
import FitsMe.FitsMe.Entities.SolicitudAmistadEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SolicitudAmistadService {

    SolicitudAmistadEntity sendSolicitud(SolicitudAmistadRequest request);
    Boolean RejectSolicitud(Long solicitudId);

    Boolean AcceptSolicitud(Long solicitudId );
    List<SolicitudAmistadResponse> getSolicitudesbyUser(Long userId);


}
