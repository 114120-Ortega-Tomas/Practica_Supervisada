package FitsMe.FitsMe.Services;


import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitRequest;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitResponse;
import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OutfitService {


    OutfitResponse getOutfitbyId(Long outfitId);

    List<OutfitResponse> getOutfits();
    boolean DeleteOutfit(Long outfitId);
    OutfitResponse saveOutfit(OutfitRequest outfitRequest);
    List<OutfitResponse> getOutfitsByUserId(Long userId);

    List<OutfitResponse> getFavoriteOutfitsByUserId(Long userId);

    boolean setOutfitFavorito(Long outfitId , Long userId);

}
