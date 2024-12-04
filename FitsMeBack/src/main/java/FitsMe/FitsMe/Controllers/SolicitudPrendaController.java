package FitsMe.FitsMe.Controllers;


import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadRequest;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadResponse;
import FitsMe.FitsMe.Dtos.SolicitudPrendaDto.SolicitudPrendaRequest;
import FitsMe.FitsMe.Dtos.SolicitudPrendaDto.SolicitudPrendaResponse;
import FitsMe.FitsMe.Entities.SolicitudAmistadEntity;
import FitsMe.FitsMe.Services.SolicitudAmistadService;
import FitsMe.FitsMe.Services.SolicitudPrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SolicitudPrenda")
public class SolicitudPrendaController {


    @Autowired
    private SolicitudPrendaService solicitudPrendaService;




    @CrossOrigin(origins = "*")
    @PostMapping("SendSolicitud")
    public ResponseEntity<SolicitudPrendaResponse> SendSolicitud(@RequestBody SolicitudPrendaRequest request) {
        return ResponseEntity.ok(solicitudPrendaService.sendSolicitud(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetSolicitudesByUser")
    public ResponseEntity<List<SolicitudPrendaResponse>> GetSolicitudesbyUser(@RequestParam Long id) {
        return ResponseEntity.ok(solicitudPrendaService.getSolicitudesbyUser(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("AcceptSolicitud")
    public ResponseEntity<Boolean> AcceptSolicitud(@RequestBody Long solicitudId) {
        return ResponseEntity.ok(solicitudPrendaService.AcceptSolicitud(solicitudId));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("RejectSolicitud")
    public ResponseEntity<Boolean> RejectSolicitud(@RequestBody Long solicitudId) {
        return ResponseEntity.ok(solicitudPrendaService.RejectSolicitud(solicitudId));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("DevolverPrenda")
    public ResponseEntity<Boolean> DevolverPrenda(@RequestParam Long prendaId , @RequestParam Long userId , @RequestParam Long prestadaByUserId) {
        return ResponseEntity.ok(solicitudPrendaService.ReturnPrenda(prendaId,userId,prestadaByUserId));
    }









}
