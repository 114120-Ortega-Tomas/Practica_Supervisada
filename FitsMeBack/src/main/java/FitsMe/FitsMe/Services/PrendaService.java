package FitsMe.FitsMe.Services;


import FitsMe.FitsMe.Dtos.AtributoResponse;
import FitsMe.FitsMe.Dtos.PrendasDtos.ListaDePrendasResponse;
import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaRequestt;
import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Dtos.PrendasDtos.SavetoUserRequest;
import FitsMe.FitsMe.Entities.PrendaEntity;
import FitsMe.FitsMe.Entities.Usuario_Prenda;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrendaService {

    PrendaResponse savePrenda(PrendaRequestt prendaRequest) throws Exception;
    List<PrendaResponse> getAllPrendas();
    PrendaResponse UpdatePrenda(Long id ,PrendaRequestt prendaRequest);
    String DeletePrenda(Long id ,Long userId);

    PrendaResponse getPrendaById(Long id);
    Usuario_Prenda savePrendatoUser(SavetoUserRequest request);
    List<PrendaResponse> getPrendasbyUserId(Long userId);
    List<PrendaResponse> getPrendasPublicas();

    List<PrendaResponse> getTopsByUser(Long userId);
    List<PrendaResponse> getBottomsByUser(Long userId);
    List<PrendaResponse> getFootWearByUser(Long userId);
    List<PrendaResponse> getOuterWearByUser(Long userId);
    List<PrendaResponse> getDressesByUser(Long userId);
    List<PrendaResponse> getFavoritesByUser(Long userId);
    List<PrendaResponse> getAccesoriesByUser(Long userId);
    PrendaResponse setPrendaFavorite (Long prendaId , Long userId);

    List<PrendaResponse> getPrendasResponseByFilters(List<Long> prendasId, List<Long> coloresId , List<Long> climasId , List<Long> estilosId , boolean favorite ,Long userId);

    List<PrendaResponse> getPrendasForOutfit(Integer amountPrendasm , Long userId);
    List<PrendaResponse> getFamousPrendas();

    Integer CantidadPrendas();
    List<PrendaResponse> Top3MoreUsed();
    List<AtributoResponse> Top3Estilos();
    List<PrendaResponse> Top3LeastUsed();
    Integer PrendasAddedLastMonth();


}
