package FitsMe.FitsMe.Controllers;


import FitsMe.FitsMe.Dtos.Eventos.EventoRequest;
import FitsMe.FitsMe.Dtos.Eventos.EventoResponse;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitRequest;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitResponse;
import FitsMe.FitsMe.Services.EventosService;
import FitsMe.FitsMe.Services.OutfitService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Evento")
public class EventoController {


    @Autowired
    private EventosService eventosService;


    @CrossOrigin(origins = "*")
    @GetMapping("GetAll")
    public ResponseEntity<List<EventoResponse>> getAll() {
        return ResponseEntity.ok(eventosService.getEventos());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("getEventobyDate")
    public ResponseEntity <List<EventoResponse>> GetOutfitByDate(@RequestParam(name = "date", required = true) String date, @RequestParam(name = "UserId", required = true) Long UserId) throws Exception {
        return ResponseEntity.ok(eventosService.getEventosByDateandUser(date, UserId));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("PostEvento")
    public ResponseEntity <EventoResponse> SaveOutfit(@RequestBody EventoRequest eventoRequest) throws Exception {
        return ResponseEntity.ok(eventosService.postEvento(eventoRequest));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("deleteEvento")
    public ResponseEntity <Boolean> DeleteEvento(@RequestParam Long eventoId) throws Exception {
        return ResponseEntity.ok(eventosService.deleteEvento(eventoId));
    }
    @CrossOrigin(origins = "*")
    @PutMapping("PutEvento")
    public ResponseEntity <EventoResponse> UpdateEvento(@RequestParam Long id,@RequestBody EventoRequest eventoRequest) throws Exception {
        return ResponseEntity.ok(eventosService.UpdateEvento(id,eventoRequest));
    }







}
