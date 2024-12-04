package FitsMe.FitsMe.Services.Impl;



import FitsMe.FitsMe.Dtos.Eventos.EventoRequest;
import FitsMe.FitsMe.Dtos.Eventos.EventoResponse;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Entities.EventosEntity;
import FitsMe.FitsMe.Entities.OutfitEntity;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import FitsMe.FitsMe.Repositories.EventoJpaRespository;
import FitsMe.FitsMe.Repositories.OutfitJpaRepository;
import FitsMe.FitsMe.Repositories.UsuarioJpaRepository;
import FitsMe.FitsMe.Services.EventosService;
import FitsMe.FitsMe.Services.OutfitService;
import FitsMe.FitsMe.Services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventosServiceImp implements EventosService {

    @Autowired
    private EventoJpaRespository eventoJpaRespository;
    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;
    @Autowired
    private OutfitService outfitService;
    @Autowired
    private OutfitJpaRepository outfitJpaRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<EventoResponse> getEventos() {
        return null;
    }

    @Override
    public EventoResponse postEvento(EventoRequest eventoRequest) {
        Optional<OutfitEntity> outfitEntity = outfitJpaRepository.findById(eventoRequest.getOutfitId());
        if(!outfitEntity.isPresent()){
            throw new RuntimeException("El outfit no existe");
        }

        Optional<UsuarioEntity> usuarioEntity = usuarioJpaRepository.findById(eventoRequest.getUsuarioId());
        if(!usuarioEntity.isPresent()){
            throw new RuntimeException("El usuario no existe");
        }

        EventosEntity eventosEntity = new EventosEntity();

        // Parse the incoming date and add one day
        LocalDate originalDate = LocalDate.parse(eventoRequest.getFecha());
        LocalDate adjustedDate = originalDate.plusDays(1);

        eventosEntity.setFecha(adjustedDate.toString());
        eventosEntity.setHorario(eventoRequest.getHorario());
        eventosEntity.setDescripcion(eventoRequest.getDescripcion());
        eventosEntity.setOutfitEntity(outfitEntity.get());
        eventosEntity.setUsuarioEntity(usuarioEntity.get());

        eventoJpaRespository.save(eventosEntity);

        EventoResponse eventoResponse = new EventoResponse();
        eventoResponse.setFecha(eventosEntity.getFecha());
        eventoResponse.setHorario(eventosEntity.getHorario());
        eventoResponse.setUsuario(eventosEntity.getUsuarioEntity().getNombre());
        eventoResponse.setDescripcion(eventosEntity.getDescripcion());
        eventoResponse.setDescripcionOutfit(eventosEntity.getOutfitEntity().getNombre());
        eventoResponse.setOutfitResponse(outfitService.getOutfitbyId(eventosEntity.getOutfitEntity().getOutfitId()));
        eventoResponse.setUsuario(usuarioEntity.get().getNombre());
        eventoResponse.setDescripcionOutfit(outfitEntity.get().getNombre());

        return eventoResponse;
    }


    @Override
    public Boolean deleteEvento(Long eventoId) {
        Optional<EventosEntity> eventosEntity = eventoJpaRespository.findById(eventoId);
        if(eventosEntity.isPresent()){
            eventoJpaRespository.delete(eventosEntity.get());
            return true;
        }
        throw new RuntimeException("El evento no existe");
    }

    @Override
    public List<EventoResponse> getEventosByDateandUser(String date, Long UserId) {
        List<EventosEntity> eventosEntities;
        List<EventoResponse> eventoResponseList = new ArrayList<>();

        // Convertir la fecha de "dd-MM-yyyy" a "yyyy-MM-dd"
        DateTimeFormatter dateFormatterInput = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dateFormatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(date, dateFormatterInput);
        String fechaFormateada = fecha.format(dateFormatterOutput);

        eventosEntities = eventoJpaRespository.findByFechaAndUsuarioEntityUsuarioId(fechaFormateada, UserId);

        if (eventosEntities == null || eventosEntities.isEmpty()) {
            throw new RuntimeException("No hay eventos para esta fecha");
        } else {
            for (EventosEntity eventosEntity : eventosEntities) {
                EventoResponse eventoResponse = new EventoResponse();
                eventoResponse.setFecha(eventosEntity.getFecha());
                eventoResponse.setEventoId(eventosEntity.getEventoId());
                eventoResponse.setHorario(eventosEntity.getHorario());
                eventoResponse.setUsuario(eventosEntity.getUsuarioEntity().getNombre());
                eventoResponse.setDescripcion(eventosEntity.getDescripcion());
                eventoResponse.setDescripcionOutfit(eventosEntity.getOutfitEntity().getNombre());
                OutfitResponse outfitResponse = outfitService.getOutfitbyId(eventosEntity.getOutfitEntity().getOutfitId());
                outfitResponse.setId(eventosEntity.getOutfitEntity().getOutfitId());
                eventoResponse.setOutfitResponse(outfitResponse);
                eventoResponse.setUsuario(eventosEntity.getUsuarioEntity().getNombre());
                eventoResponseList.add(eventoResponse);
            }
        }
        return eventoResponseList;
    }

    @Override
    public EventoResponse UpdateEvento(Long id, EventoRequest eventoRequest) {
        EventosEntity eventosEntity = eventoJpaRespository.findById(id).get();
        LocalDate originalDate = LocalDate.parse(eventoRequest.getFecha());
        LocalDate adjustedDate = originalDate.plusDays(1);
        eventosEntity.setFecha(adjustedDate.toString());
        eventosEntity.setHorario(eventoRequest.getHorario());
        eventosEntity.setDescripcion(eventoRequest.getDescripcion());
        eventosEntity.setOutfitEntity(outfitJpaRepository.findById(eventoRequest.getOutfitId()).get());
        eventosEntity.setUsuarioEntity(usuarioJpaRepository.findById(eventoRequest.getUsuarioId()).get());
        eventoJpaRespository.save(eventosEntity);
        EventoResponse eventoResponse = new EventoResponse();
        eventoResponse.setFecha(eventosEntity.getFecha());
        eventoResponse.setHorario(eventosEntity.getHorario());
        eventoResponse.setUsuario(eventosEntity.getUsuarioEntity().getNombre());
        eventoResponse.setDescripcion(eventosEntity.getDescripcion());
        eventoResponse.setDescripcionOutfit(eventosEntity.getOutfitEntity().getNombre());
        eventoResponse.setOutfitResponse(outfitService.getOutfitbyId(eventosEntity.getOutfitEntity().getOutfitId()));
        eventoResponse.setUsuario(eventosEntity.getUsuarioEntity().getNombre());
        eventoResponse.setDescripcionOutfit(eventosEntity.getOutfitEntity().getNombre());
        return eventoResponse;
    }
}
