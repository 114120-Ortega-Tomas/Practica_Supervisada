package FitsMe.FitsMe.Controllers;


import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitRequest;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitResponse;
import FitsMe.FitsMe.Services.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Outfit")
public class OutfitController {


    @Autowired
    private OutfitService outfitService;


    @CrossOrigin(origins = "*")
    @GetMapping("GetAllOutfits")
    public ResponseEntity<List<OutfitResponse>> getAll() {
        return ResponseEntity.ok(outfitService.getOutfits());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("SaveOutfit")
    public ResponseEntity<OutfitResponse> SaveOutfit( @RequestBody OutfitRequest outfitRequest) throws Exception {
        return ResponseEntity.ok(outfitService.saveOutfit(outfitRequest));
    }


    @CrossOrigin(origins = "*")
    @GetMapping("GetOutfitsByUserId")
    public ResponseEntity<List<OutfitResponse>> getOutfitsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(outfitService.getOutfitsByUserId(userId));
    }


    @CrossOrigin(origins = "*")
    @GetMapping("SetOutfitFavorito")
    public ResponseEntity setOutfitFavorito(@RequestParam Long outfitId, @RequestParam Long userId) {
        return ResponseEntity.ok(outfitService.setOutfitFavorito(outfitId, userId));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("DeleteOutfit")
    public ResponseEntity DeleteOutfit(@RequestParam Long outfitId) {
        return ResponseEntity.ok(outfitService.DeleteOutfit(outfitId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("GetFavoriteOutfitsByUserId")
    public ResponseEntity setOutfitFavorito(@RequestParam Long userId) {
        return ResponseEntity.ok(outfitService.getFavoriteOutfitsByUserId(userId));
    }









}
