package FitsMe.FitsMe.Services;


import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaRequestt;
import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Entities.ClimaEntity;
import FitsMe.FitsMe.Entities.ColorEntity;
import FitsMe.FitsMe.Entities.EstilosEntity;
import FitsMe.FitsMe.Entities.TipoPrendaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceAuxiliar {

    List<ClimaEntity> getAllClimas();
    List<EstilosEntity> getAllEstilos();
    List<ColorEntity> getAllColores();
    List<TipoPrendaEntity> getAllTipoPrendas();
}
