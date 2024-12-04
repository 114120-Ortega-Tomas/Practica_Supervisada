package FitsMe.FitsMe.Controllers;


import FitsMe.FitsMe.Dtos.PrendasDtos.*;
import FitsMe.FitsMe.Entities.PrendaEntity;
import FitsMe.FitsMe.Entities.Usuario_Prenda;
import FitsMe.FitsMe.Services.PrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Prendas")
public class PrendaController {


    @Autowired
    private PrendaService prendaService;



    @CrossOrigin(origins = "*")
    @GetMapping("GetAll")
    public ResponseEntity<List<PrendaResponse>> getAll() {
        return ResponseEntity.ok(prendaService.getAllPrendas());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("PostPrenda")
    public ResponseEntity<PrendaResponse> PostPrenda( @RequestBody PrendaRequestt prendaRequest) throws Exception {
        return ResponseEntity.ok(prendaService.savePrenda(prendaRequest));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetPrendaById")
    public ResponseEntity<PrendaResponse> getPrendaById(@RequestParam (name = "id", required = true) Long id)  {
        return ResponseEntity.ok(prendaService.getPrendaById(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("GetAllPrendas")
    public ResponseEntity<List<PrendaResponse>> GetAllPrendas()  {
        return ResponseEntity.ok(prendaService.getAllPrendas());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetPublicPrendas")
    public ResponseEntity<List<PrendaResponse>> getPublicPrendas()  {
        return ResponseEntity.ok(prendaService.getPrendasPublicas());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetPrendasByUser")
    public ResponseEntity<List<PrendaResponse>> getPrendasByUser(@RequestParam Long id)  {
        return ResponseEntity.ok(prendaService.getPrendasbyUserId(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("savePrendaToUser")
    public ResponseEntity<Usuario_Prenda> savePrendaToUser(@RequestBody SavetoUserRequest request) {
        return ResponseEntity.ok(prendaService.savePrendatoUser(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetTops")
    public ResponseEntity<List<PrendaResponse>> getTops(@RequestParam Long id) {
        return ResponseEntity.ok(prendaService.getTopsByUser(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("GetBottoms")
    public ResponseEntity<List<PrendaResponse>> getBottoms(@RequestParam Long id)  {
        return ResponseEntity.ok(prendaService.getBottomsByUser(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getFootWear")
    public ResponseEntity<List<PrendaResponse>> getFootWear(@RequestParam Long id){
        return ResponseEntity.ok(prendaService.getFootWearByUser(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getOuterWear")
    public ResponseEntity<List<PrendaResponse>> getOuterWear(@RequestParam Long id)  {
        return ResponseEntity.ok(prendaService.getOuterWearByUser(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getAccesories")
    public ResponseEntity<List<PrendaResponse>> getAccesories(@RequestParam Long id)  {
        return ResponseEntity.ok(prendaService.getAccesoriesByUser(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getDresses")
    public ResponseEntity<List<PrendaResponse>> getDresses(@RequestParam Long id) {
        return ResponseEntity.ok(prendaService.getDressesByUser(id));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("setPrendaFavorita")
    public ResponseEntity<PrendaResponse> setPrendaFavorita(@RequestParam Long prendaId , @RequestParam Long userId) {
        return ResponseEntity.ok(prendaService.setPrendaFavorite(prendaId,userId));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getFavorites")
    public ResponseEntity<List<PrendaResponse>> getFavorites(@RequestParam Long id) {
        return ResponseEntity.ok(prendaService.getFavoritesByUser(id));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("deletePrenda")
    public ResponseEntity<String> deletePrenda(@RequestParam Long id ,@RequestParam Long userId) {
        return ResponseEntity.ok(prendaService.DeletePrenda(id , userId));
    }

    @CrossOrigin(origins = "*")
    @PutMapping("UpdatePrenda")
    public ResponseEntity<PrendaResponse> UpdatePrenda(@RequestParam Long prendaId , @RequestBody PrendaRequestt prendaRequest) {
        return ResponseEntity.ok(prendaService.UpdatePrenda(prendaId,prendaRequest));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("getPrendasFiltered")
    public ResponseEntity<List<PrendaResponse>> getPrendasFiltered(@RequestBody PrendaFilterRequest request) {
        return ResponseEntity.ok(prendaService.getPrendasResponseByFilters(
                request.getPrendas(),
                request.getColoresId(),
                request.getClimasId(),
                request.getEstilosId(),
                request.isFavorite(),
                request.getUserId()
        ));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("getPrendasForOutfit")
    public ResponseEntity<List<PrendaResponse>> getPrendasForOutfit(@RequestParam Integer cantidad , @RequestParam Long userId) {
        return ResponseEntity.ok(prendaService.getPrendasForOutfit(cantidad , userId ));
    }
    @CrossOrigin(origins = "*")
    @GetMapping("getFamousPrendas")
    public ResponseEntity<List<PrendaResponse>> getFamousPrendas() {
        return ResponseEntity.ok(prendaService.getFamousPrendas());
    }





}
