package FitsMe.FitsMe.Controllers;


import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadRequest;
import FitsMe.FitsMe.Dtos.SolicitudAmistadDto.SolicitudAmistadResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Entities.SolicitudAmistadEntity;
import FitsMe.FitsMe.Services.SolicitudAmistadService;
import FitsMe.FitsMe.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SolicitudAmistad")
public class SolicitudesAmistadController {


    @Autowired
    private SolicitudAmistadService solicitudAmistadService;




    @CrossOrigin(origins = "*")
    @PostMapping("SendSolicitud")
    public ResponseEntity<SolicitudAmistadEntity> SendSolicitud(@RequestBody SolicitudAmistadRequest request) {
        return ResponseEntity.ok(solicitudAmistadService.sendSolicitud(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetSolicitudesByUser")
    public ResponseEntity<List<SolicitudAmistadResponse>> GetSolicitudesbyUser(@RequestParam Long id) {
        return ResponseEntity.ok(solicitudAmistadService.getSolicitudesbyUser(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("AcceptSolicitud")
    public ResponseEntity<Boolean> AcceptSolicitud(@RequestBody Long solicitudId) {
        return ResponseEntity.ok(solicitudAmistadService.AcceptSolicitud(solicitudId));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("RejectSolicitud")
    public ResponseEntity<Boolean> RejectSolicitud(@RequestBody Long solicitudId) {
        return ResponseEntity.ok(solicitudAmistadService.RejectSolicitud(solicitudId));
    }








}
