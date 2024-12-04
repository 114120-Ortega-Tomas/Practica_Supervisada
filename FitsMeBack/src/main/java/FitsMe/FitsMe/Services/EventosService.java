package FitsMe.FitsMe.Services;


import FitsMe.FitsMe.Dtos.Eventos.EventoRequest;
import FitsMe.FitsMe.Dtos.Eventos.EventoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventosService {



    List<EventoResponse> getEventos();
    EventoResponse postEvento(EventoRequest eventoRequest);
    Boolean deleteEvento(Long eventoId);
    List<EventoResponse> getEventosByDateandUser(String date,Long UserId);

    EventoResponse UpdateEvento(Long id, EventoRequest eventoRequest);
}
