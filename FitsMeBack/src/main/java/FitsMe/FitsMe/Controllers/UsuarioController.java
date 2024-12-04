package FitsMe.FitsMe.Controllers;


import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuariosPorMesResponse;
import FitsMe.FitsMe.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;


    @CrossOrigin(origins = "*")
    @GetMapping("GetAll")
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        return ResponseEntity.ok(usuarioService.getUsuarios());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("PostUsuario")
    public ResponseEntity<UsuarioResponse> PostUsuario( @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.ok(usuarioService.PostUsuario(usuarioRequest));
    }
    @CrossOrigin(origins = "*")
    @PutMapping("UpdateUsuario")
    public ResponseEntity<UsuarioResponse> UpdateUsuario( @RequestBody UsuarioRequest usuarioRequest , @RequestParam Long id) {
        return ResponseEntity.ok(usuarioService.PutUsuario(usuarioRequest,id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("Login")
    public ResponseEntity<UsuarioResponse> LogIn(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok(usuarioService.Login(logInRequest));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("getUsersByUsername")
    public ResponseEntity<List<UsuarioResponse>> getUsersbyUsername( @RequestParam String username , @RequestParam Long id) {
        return ResponseEntity.ok(usuarioService.getUsuariobyUsername(username,id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getAmigos")
    public ResponseEntity<List<UsuarioResponse>> getAmigos( @RequestParam Long id) {
        return ResponseEntity.ok(usuarioService.getAmigos(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getUserbyMonth")
    public ResponseEntity<List<UsuariosPorMesResponse>> getUsersbyMonth(@RequestParam String from , @RequestParam String to) {
        return ResponseEntity.ok(usuarioService.getUsuariosPorMes(from,to));
    }







}
