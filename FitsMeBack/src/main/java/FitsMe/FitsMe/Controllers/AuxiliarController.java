package FitsMe.FitsMe.Controllers;


import FitsMe.FitsMe.Dtos.Eventos.EventoResponse;
import FitsMe.FitsMe.Entities.ClimaEntity;
import FitsMe.FitsMe.Entities.ColorEntity;
import FitsMe.FitsMe.Entities.EstilosEntity;
import FitsMe.FitsMe.Entities.TipoPrendaEntity;
import FitsMe.FitsMe.Services.EventosService;
import FitsMe.FitsMe.Services.ServiceAuxiliar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.color.ColorSpace;
import java.util.List;

@RestController
@RequestMapping("/Auxiliar")
public class AuxiliarController {


    @Autowired
    private ServiceAuxiliar serviceAuxiliar;


    @CrossOrigin(origins = "*")
    @GetMapping("GetAllColores")
    public ResponseEntity<List<ColorEntity>> getAllColores() {
        return ResponseEntity.ok(serviceAuxiliar.getAllColores());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetAllClimas")
    public ResponseEntity<List<ClimaEntity>> getAllClimas() {
        return ResponseEntity.ok(serviceAuxiliar.getAllClimas());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetAllEstilos")
    public ResponseEntity<List<EstilosEntity>> getAllEstilos() {
        return ResponseEntity.ok(serviceAuxiliar.getAllEstilos());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetAllTipoPrendas")
    public ResponseEntity<List<TipoPrendaEntity>> getAllTipoPrendas() {
        return ResponseEntity.ok(serviceAuxiliar.getAllTipoPrendas());
    }












}
