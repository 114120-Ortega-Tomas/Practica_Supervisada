package FitsMe.FitsMe.Services.Impl;



import FitsMe.FitsMe.Dtos.AtributoResponse;
import FitsMe.FitsMe.Dtos.PrendasDtos.ListaDePrendasResponse;
import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaRequestt;
import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Dtos.PrendasDtos.SavetoUserRequest;
import FitsMe.FitsMe.Entities.*;
import FitsMe.FitsMe.Repositories.*;
import FitsMe.FitsMe.Services.PrendaService;
import FitsMe.FitsMe.Services.ServiceAuxiliar;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import javax.swing.text.html.Option;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrendaServiceImpl implements PrendaService {
    @Autowired
    private  UsuarioPrendaJpaRepository usuarioPrendaJpaRepository;
    @Autowired
    private EstilosJpaRepository estilosJpaRepository;
    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;
    @Autowired
    private TipoPrendaJpaRepository tipoPrendaJpaRepository;
    @Autowired
    private PrendaJpaRepository prendaJpaRepository;
    @Autowired
    private ClimaJpaRepository climaJpaRepository;
    @Autowired
    private ColorJpaRepository colorJpaRepository;

    @Autowired
    private ServiceAuxiliar serviceAuxiliar;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public PrendaResponse savePrenda(PrendaRequestt prendaRequest) throws Exception {
        List<ColorEntity> colores = new ArrayList<>();
        List<EstilosEntity> estilos = new ArrayList<>();
        List<ClimaEntity> climas = new ArrayList<>();
        Optional<UsuarioEntity> usuarioEntity = usuarioJpaRepository.findById(prendaRequest.getUserId());
        for(Long color: prendaRequest.getColor()) {
            colores.add(colorJpaRepository.findById(color).get());
        }
        for (Long estilo: prendaRequest.getEstilo()){
            estilos.add(estilosJpaRepository.findById(estilo).get());
        }
        for(Long clima: prendaRequest.getClima()) {
            climas.add(climaJpaRepository.findById(clima).get());
        }
        TipoPrendaEntity tipoPrendaEntity = tipoPrendaJpaRepository.findById(prendaRequest.getTipoPrenda()).get();
        PrendaEntity prendaEntity = new PrendaEntity();
        prendaEntity.setTipoPrendaEntity(tipoPrendaEntity);
        prendaEntity.setColorEntities(colores);
        prendaEntity.setClimaEntities(climas);
        prendaEntity.setEstilosEntity(estilos);
        prendaEntity.setDescripcion(prendaRequest.getDescripcion());
        prendaEntity.setContentType("jpeg");
        prendaEntity.setBase64(prendaRequest.getBase64());
        prendaEntity.setPrivado(prendaRequest.isPrivado());
        Usuario_Prenda usuarioPrenda = new Usuario_Prenda();
        usuarioPrenda.setPrenda(prendaEntity);
        usuarioPrenda.setUsuario(usuarioEntity.get());
        usuarioPrenda.setPrestada(false);
        usuarioPrenda.setPrestamo(false);
        usuarioPrenda.setFavorita(prendaRequest.isFavorito());
        usuarioPrenda.setFechaAdicion(new Date().toString());
        prendaJpaRepository.save(prendaEntity);
        usuarioPrendaJpaRepository.save(usuarioPrenda);
        PrendaResponse prendaResponse = modelMapper.map(prendaEntity,PrendaResponse.class);
        List<AtributoResponse> coloresr = new ArrayList<>();
        List<AtributoResponse> estilosr = new ArrayList<>();
        List<AtributoResponse> climasr = new ArrayList<>();
        for (ColorEntity colorEntity: prendaEntity.getColorEntities()){
            AtributoResponse color = new AtributoResponse();
            color.setId(colorEntity.getColorId());
            color.setDescripcion(colorEntity.getDescripcion());
            coloresr.add(color);
        }
        for (EstilosEntity estilosEntity: prendaEntity.getEstilosEntity()){
            AtributoResponse estilo = new AtributoResponse();
            estilo.setId(estilosEntity.getEstiloId());
            estilo.setDescripcion(estilosEntity.getDescripcion());
            estilosr.add(estilo);
        }
        for (ClimaEntity climaEntity: prendaEntity.getClimaEntities()){
            AtributoResponse clima = new AtributoResponse();
            clima.setId(climaEntity.getClimaId());
            clima.setDescripcion(climaEntity.getDescripcion());
            climasr.add(clima);
        }
        prendaResponse.setColores(coloresr);
        prendaResponse.setEstilos(estilosr);
        prendaResponse.setClimas(climasr);
        prendaResponse.setTipoPrenda(new AtributoResponse(prendaEntity.getTipoPrendaEntity().getTipoPrendaId(),prendaEntity.getTipoPrendaEntity().getDescripcion()));
        prendaResponse.setBase64(prendaRequest.getBase64());
        prendaResponse.setFavorito(prendaRequest.isFavorito());
        prendaResponse.setPrivado(prendaRequest.isPrivado());
        return prendaResponse;
    }

    @Override
    public List<PrendaResponse> getAllPrendas() {
        List<PrendaEntity> prendaEntities = prendaJpaRepository.findAll();
        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (PrendaEntity prenda:prendaEntities)
        {
            PrendaResponse prendaResponse = modelMapper.map(prenda,PrendaResponse.class);
            prendaResponse.setBase64(prenda.getBase64());
            prendaResponses.add(prendaResponse);
        }
        return prendaResponses;
    }

    @Override
    public PrendaResponse UpdatePrenda(Long id, PrendaRequestt prendaRequest) {
        Optional<PrendaEntity> optionalPrendaEntity = prendaJpaRepository.findById(id);
        List<ColorEntity> colores = new ArrayList<>();
        List<EstilosEntity> estilos = new ArrayList<>();
        List<ClimaEntity> climas = new ArrayList<>();
        if(optionalPrendaEntity.get().getColorEntities()!=null)
        {
            for(Long color: prendaRequest.getColor()) {
                colores.add(colorJpaRepository.findById(color).get());
            }
            for (Long estilo: prendaRequest.getEstilo()){
                estilos.add(estilosJpaRepository.findById(estilo).get());
            }
            for(Long clima: prendaRequest.getClima()) {
                climas.add(climaJpaRepository.findById(clima).get());
            }
            optionalPrendaEntity.get().setClimaEntities(climas);
            optionalPrendaEntity.get().setEstilosEntity(estilos);
            optionalPrendaEntity.get().setColorEntities(colores);
            optionalPrendaEntity.get().setDescripcion(prendaRequest.getDescripcion());
            prendaJpaRepository.save(optionalPrendaEntity.get());
            PrendaResponse prendaResponse = modelMapper.map(optionalPrendaEntity,PrendaResponse.class);
            return prendaResponse;
        }
        return null;
    }

    @Override
    public String DeletePrenda(Long id , Long userId) {
        List<Usuario_Prenda>  usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        for(Usuario_Prenda usuarioPrenda: usuarioPrendas){
            if (usuarioPrenda.getPrenda().getPrendaId() == id && usuarioPrenda.getUsuario().getUsuarioId() == userId){
                usuarioPrendaJpaRepository.delete(usuarioPrenda);
                return null;
            }
        }
        return  null;

    }

    @Override
    public PrendaResponse getPrendaById(Long id) {
        Optional<PrendaEntity> prendaEntity = prendaJpaRepository.findById(id);
        if(prendaEntity.isPresent()){
            PrendaResponse prendaResponse = modelMapper.map(prendaEntity,PrendaResponse.class);
            return prendaResponse;
        }
        return null;
    }

    @Override
    public Usuario_Prenda savePrendatoUser(SavetoUserRequest request) {
        Optional<PrendaEntity> prendaEntityOptinal = prendaJpaRepository.findById(request.getPrendaId());
        Optional<UsuarioEntity> usuarioEntity = usuarioJpaRepository.findById(request.getUserId());
        if(!prendaEntityOptinal.isPresent() || !usuarioEntity.isPresent()){
            return null;
        }
        Optional<Usuario_Prenda> usuarioPrenda = usuarioPrendaJpaRepository.findByUsuarioAndPrenda(usuarioEntity.get(),prendaEntityOptinal.get());
        if (usuarioPrenda.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prenda ya existente");
        }
        PrendaEntity prendaEntity = prendaEntityOptinal.get();
        UsuarioEntity usuarioEntity1 = usuarioEntity.get();
        Usuario_Prenda usuarioPrenda1 = new Usuario_Prenda();
        usuarioPrenda1.setPrenda(prendaEntity);
        usuarioPrenda1.setUsuario(usuarioEntity1);
        usuarioPrenda1.setPrestada(false);
        usuarioPrenda1.setPrestamo(false);
        usuarioPrenda1.setVecesUsada(0);
        usuarioPrenda1.setPrestadabyUserId(null);
        usuarioPrendaJpaRepository.save(usuarioPrenda1);
        return usuarioPrenda1;
    }

    @Override
    public List<PrendaResponse> getPrendasbyUserId(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for(Usuario_Prenda usuarioPrenda:usuarioPrendas){
            if(usuarioPrenda.getUsuario().getUsuarioId().equals(userId)){
                PrendaEntity prendaEntity = usuarioPrenda.getPrenda();
                PrendaResponse prendaResponse = new PrendaResponse();
                prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                if(usuarioPrenda.getPrestada()==true)
                {
                    prendaResponse.setPrestada(true);


                } else{
                    prendaResponse.setPrestada(false);
                }
                if(usuarioPrenda.getPrestamo()==true)
                {
                    prendaResponse.setPrestamo(true);
                    prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                } else {
                    prendaResponse.setPrestamo(false);
                }
                prendaResponse.setPrendaId(prendaEntity.getPrendaId());
                prendaResponse.setDescripcion(prendaEntity.getDescripcion());
                prendaResponse.setPrivado(prendaEntity.isPrivado());
                prendaResponse.setBase64(prendaEntity.getBase64());
                List<AtributoResponse> colores = new ArrayList<>();
                List<AtributoResponse> estilos = new ArrayList<>();
                List<AtributoResponse> climas = new ArrayList<>();
                for (ColorEntity colorEntity: prendaEntity.getColorEntities()){
                    AtributoResponse color = new AtributoResponse();
                    color.setId(colorEntity.getColorId());
                    color.setDescripcion(colorEntity.getDescripcion());
                    colores.add(color);
                }
                for (EstilosEntity estilosEntity: prendaEntity.getEstilosEntity()){
                    AtributoResponse estilo = new AtributoResponse();
                    estilo.setId(estilosEntity.getEstiloId());
                    estilo.setDescripcion(estilosEntity.getDescripcion());
                    estilos.add(estilo);
                }
                for (ClimaEntity climaEntity: prendaEntity.getClimaEntities()){
                    AtributoResponse clima = new AtributoResponse();
                    clima.setId(climaEntity.getClimaId());
                    clima.setDescripcion(climaEntity.getDescripcion());
                    climas.add(clima);
                }
                prendaResponse.setColores(colores);
                prendaResponse.setUsuario(userId.toString());
                prendaResponse.setEstilos(estilos);
                prendaResponse.setClimas(climas);
                prendaResponse.setTipoPrenda(new AtributoResponse(prendaEntity.getTipoPrendaEntity().getTipoPrendaId(),prendaEntity.getTipoPrendaEntity().getDescripcion()));
                if(usuarioPrenda.isFavorita()){
                    prendaResponse.setFavorito(true);
                }
                prendaResponses.add(prendaResponse);
            }
        }
        return prendaResponses;

    }

    @Override
    public List<PrendaResponse> getPrendasPublicas() {
        List<PrendaResponse> prendaResponses = new ArrayList<>();
        List<PrendaEntity> prendas = prendaJpaRepository.findAllByPrivadoIsFalse();
        for(PrendaEntity prendaEntity: prendas)
        {
            PrendaResponse prendaResponse = modelMapper.map(prendaEntity,PrendaResponse.class);
            prendaResponse.setBase64(prendaEntity.getBase64());
            prendaResponses.add(prendaResponse);

        }
        return prendaResponses;

    }

    @Override
    public List<PrendaResponse> getTopsByUser(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        Map<PrendaEntity, Usuario_Prenda> prendaFavMap = new HashMap<>();

        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId)) {
                prendaFavMap.put(usuarioPrenda.getPrenda(), usuarioPrenda);
            }
        }

        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (Map.Entry<PrendaEntity, Usuario_Prenda> entry : prendaFavMap.entrySet()) {
            PrendaEntity prendaEntity = entry.getKey();
            Usuario_Prenda usuarioPrenda = entry.getValue();
            boolean isFavorita = usuarioPrenda.isFavorita();

            if (prendaEntity.getTipoPrendaEntity().getTipoPrendaId().equals(3L)) {  // Tops
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());
                prendaResponse.setFavorito(isFavorita);

                // Aplicar la lógica de setPrestada
                prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                if (usuarioPrenda.getPrestada()) {
                    prendaResponse.setPrestada(true);
                } else {
                    prendaResponse.setPrestada(false);
                }

                if (usuarioPrenda.getPrestamo()) {
                    prendaResponse.setPrestamo(true);
                    prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                } else {
                    prendaResponse.setPrestamo(false);
                }

                // Colores
                List<AtributoResponse> colores = new ArrayList<>();
                for (ColorEntity colorEntity : prendaEntity.getColorEntities()) {
                    AtributoResponse color = new AtributoResponse();
                    color.setId(colorEntity.getColorId());
                    color.setDescripcion(colorEntity.getDescripcion());
                    colores.add(color);
                }

                // Estilos
                List<AtributoResponse> estilos = new ArrayList<>();
                for (EstilosEntity estilosEntity : prendaEntity.getEstilosEntity()) {
                    AtributoResponse estilo = new AtributoResponse();
                    estilo.setId(estilosEntity.getEstiloId());
                    estilo.setDescripcion(estilosEntity.getDescripcion());
                    estilos.add(estilo);
                }

                // Climas
                List<AtributoResponse> climas = new ArrayList<>();
                for (ClimaEntity climaEntity : prendaEntity.getClimaEntities()) {
                    AtributoResponse clima = new AtributoResponse();
                    clima.setId(climaEntity.getClimaId());
                    clima.setDescripcion(climaEntity.getDescripcion());
                    climas.add(clima);
                }

                prendaResponse.setColores(colores);
                prendaResponse.setEstilos(estilos);
                prendaResponse.setClimas(climas);

                prendaResponses.add(prendaResponse);
            }
        }

        return prendaResponses;
    }



    @Override
    public List<PrendaResponse> getBottomsByUser(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        Map<PrendaEntity, Usuario_Prenda> prendaFavMap = new HashMap<>();

        // Población del mapa con prendas y su relación con el usuario
        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId)) {
                prendaFavMap.put(usuarioPrenda.getPrenda(), usuarioPrenda);
            }
        }

        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (Map.Entry<PrendaEntity, Usuario_Prenda> entry : prendaFavMap.entrySet()) {
            PrendaEntity prendaEntity = entry.getKey();
            Usuario_Prenda usuarioPrenda = entry.getValue();
            boolean isFavorita = usuarioPrenda.isFavorita();

            // Comprobar si es una prenda de tipo "Bottom" (por ejemplo, pantalones)
            if (prendaEntity.getTipoPrendaEntity().getTipoPrendaId().equals(4L)) {  // Tipo "Bottom"
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());
                prendaResponse.setFavorito(isFavorita);

                // Aplicar la lógica de setPrestada
                prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                if (usuarioPrenda.getPrestada()) {
                    prendaResponse.setPrestada(true);
                } else {
                    prendaResponse.setPrestada(false);
                }

                if (usuarioPrenda.getPrestamo()) {
                    prendaResponse.setPrestamo(true);
                    prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                } else {
                    prendaResponse.setPrestamo(false);
                }

                // Colores
                List<AtributoResponse> colores = new ArrayList<>();
                for (ColorEntity colorEntity : prendaEntity.getColorEntities()) {
                    AtributoResponse color = new AtributoResponse();
                    color.setId(colorEntity.getColorId());
                    color.setDescripcion(colorEntity.getDescripcion());
                    colores.add(color);
                }

                // Estilos
                List<AtributoResponse> estilos = new ArrayList<>();
                for (EstilosEntity estilosEntity : prendaEntity.getEstilosEntity()) {
                    AtributoResponse estilo = new AtributoResponse();
                    estilo.setId(estilosEntity.getEstiloId());
                    estilo.setDescripcion(estilosEntity.getDescripcion());
                    estilos.add(estilo);
                }

                // Climas
                List<AtributoResponse> climas = new ArrayList<>();
                for (ClimaEntity climaEntity : prendaEntity.getClimaEntities()) {
                    AtributoResponse clima = new AtributoResponse();
                    clima.setId(climaEntity.getClimaId());
                    clima.setDescripcion(climaEntity.getDescripcion());
                    climas.add(clima);
                }

                prendaResponse.setClimas(climas);
                prendaResponse.setColores(colores);
                prendaResponse.setEstilos(estilos);
                prendaResponses.add(prendaResponse);
            }
        }

        return prendaResponses;
    }



    @Override
    public List<PrendaResponse> getFootWearByUser(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        Map<PrendaEntity, Usuario_Prenda> prendaFavMap = new HashMap<>();

        // Población del mapa con prendas y su relación con el usuario
        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId)) {
                prendaFavMap.put(usuarioPrenda.getPrenda(), usuarioPrenda);
            }
        }

        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (Map.Entry<PrendaEntity, Usuario_Prenda> entry : prendaFavMap.entrySet()) {
            PrendaEntity prendaEntity = entry.getKey();
            Usuario_Prenda usuarioPrenda = entry.getValue();
            boolean isFavorita = usuarioPrenda.isFavorita();

            // Comprobar si es una prenda de tipo "Footwear" (por ejemplo, calzado)
            if (prendaEntity.getTipoPrendaEntity().getTipoPrendaId().equals(5L)) {  // Tipo "Footwear"
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());
                prendaResponse.setFavorito(isFavorita);

                // Aplicar la lógica de setPrestada
                prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                if (usuarioPrenda.getPrestada()) {
                    prendaResponse.setPrestada(true);
                } else {
                    prendaResponse.setPrestada(false);
                }

                if (usuarioPrenda.getPrestamo()) {
                    prendaResponse.setPrestamo(true);
                    prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                } else {
                    prendaResponse.setPrestamo(false);
                }

                // Colores
                List<AtributoResponse> colores = new ArrayList<>();
                for (ColorEntity colorEntity : prendaEntity.getColorEntities()) {
                    AtributoResponse color = new AtributoResponse();
                    color.setId(colorEntity.getColorId());
                    color.setDescripcion(colorEntity.getDescripcion());
                    colores.add(color);
                }

                // Estilos
                List<AtributoResponse> estilos = new ArrayList<>();
                for (EstilosEntity estilosEntity : prendaEntity.getEstilosEntity()) {
                    AtributoResponse estilo = new AtributoResponse();
                    estilo.setId(estilosEntity.getEstiloId());
                    estilo.setDescripcion(estilosEntity.getDescripcion());
                    estilos.add(estilo);
                }

                // Climas
                List<AtributoResponse> climas = new ArrayList<>();
                for (ClimaEntity climaEntity : prendaEntity.getClimaEntities()) {
                    AtributoResponse clima = new AtributoResponse();
                    clima.setId(climaEntity.getClimaId());
                    clima.setDescripcion(climaEntity.getDescripcion());
                    climas.add(clima);
                }

                prendaResponse.setClimas(climas);
                prendaResponse.setColores(colores);
                prendaResponse.setEstilos(estilos);
                prendaResponses.add(prendaResponse);
            }
        }

        return prendaResponses;
    }



    @Override
    public List<PrendaResponse> getOuterWearByUser(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        Map<PrendaEntity, Usuario_Prenda> prendaFavMap = new HashMap<>();

        // Población del mapa con prendas y su relación con el usuario
        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId)) {
                prendaFavMap.put(usuarioPrenda.getPrenda(), usuarioPrenda);
            }
        }

        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (Map.Entry<PrendaEntity, Usuario_Prenda> entry : prendaFavMap.entrySet()) {
            PrendaEntity prendaEntity = entry.getKey();
            Usuario_Prenda usuarioPrenda = entry.getValue();
            boolean isFavorita = usuarioPrenda.isFavorita();

            // Comprobar si es una prenda de tipo "OuterWear" (por ejemplo, abrigo)
            if (prendaEntity.getTipoPrendaEntity().getTipoPrendaId().equals(2L)) {  // Tipo "OuterWear"
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());
                prendaResponse.setFavorito(isFavorita);

                // Aplicar la lógica de setPrestada
                prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                if (usuarioPrenda.getPrestada()) {
                    prendaResponse.setPrestada(true);
                } else {
                    prendaResponse.setPrestada(false);
                }

                if (usuarioPrenda.getPrestamo()) {
                    prendaResponse.setPrestamo(true);
                    prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                } else {
                    prendaResponse.setPrestamo(false);
                }

                // Colores
                List<AtributoResponse> colores = new ArrayList<>();
                for (ColorEntity colorEntity : prendaEntity.getColorEntities()) {
                    AtributoResponse color = new AtributoResponse();
                    color.setId(colorEntity.getColorId());
                    color.setDescripcion(colorEntity.getDescripcion());
                    colores.add(color);
                }

                // Estilos
                List<AtributoResponse> estilos = new ArrayList<>();
                for (EstilosEntity estilosEntity : prendaEntity.getEstilosEntity()) {
                    AtributoResponse estilo = new AtributoResponse();
                    estilo.setId(estilosEntity.getEstiloId());
                    estilo.setDescripcion(estilosEntity.getDescripcion());
                    estilos.add(estilo);
                }

                // Climas
                List<AtributoResponse> climas = new ArrayList<>();
                for (ClimaEntity climaEntity : prendaEntity.getClimaEntities()) {
                    AtributoResponse clima = new AtributoResponse();
                    clima.setId(climaEntity.getClimaId());
                    clima.setDescripcion(climaEntity.getDescripcion());
                    climas.add(clima);
                }

                prendaResponse.setClimas(climas);
                prendaResponse.setColores(colores);
                prendaResponse.setEstilos(estilos);
                prendaResponses.add(prendaResponse);
            }
        }

        return prendaResponses;
    }

    @Override
    public List<PrendaResponse> getDressesByUser(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        Map<PrendaEntity, Usuario_Prenda> prendaFavMap = new HashMap<>();

        // Población del mapa con prendas y su relación con el usuario
        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId)) {
                prendaFavMap.put(usuarioPrenda.getPrenda(), usuarioPrenda);
            }
        }

        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (Map.Entry<PrendaEntity, Usuario_Prenda> entry : prendaFavMap.entrySet()) {
            PrendaEntity prendaEntity = entry.getKey();
            Usuario_Prenda usuarioPrenda = entry.getValue();
            boolean isFavorita = usuarioPrenda.isFavorita();

            // Comprobar si es un vestido (tipo prenda = 6L)
            if (prendaEntity.getTipoPrendaEntity().getTipoPrendaId().equals(6L)) {
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());
                prendaResponse.setFavorito(isFavorita);

                // Aplicar la lógica de setPrestada y setPrestamo
                prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                prendaResponse.setPrestada(usuarioPrenda.getPrestada());
                if (usuarioPrenda.getPrestamo()) {
                    prendaResponse.setPrestamo(true);
                    prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                } else {
                    prendaResponse.setPrestamo(false);
                }

                // Colores
                List<AtributoResponse> colores = new ArrayList<>();
                for (ColorEntity colorEntity : prendaEntity.getColorEntities()) {
                    AtributoResponse color = new AtributoResponse();
                    color.setId(colorEntity.getColorId());
                    color.setDescripcion(colorEntity.getDescripcion());
                    colores.add(color);
                }

                // Estilos
                List<AtributoResponse> estilos = new ArrayList<>();
                for (EstilosEntity estilosEntity : prendaEntity.getEstilosEntity()) {
                    AtributoResponse estilo = new AtributoResponse();
                    estilo.setId(estilosEntity.getEstiloId());
                    estilo.setDescripcion(estilosEntity.getDescripcion());
                    estilos.add(estilo);
                }

                // Climas
                List<AtributoResponse> climas = new ArrayList<>();
                for (ClimaEntity climaEntity : prendaEntity.getClimaEntities()) {
                    AtributoResponse clima = new AtributoResponse();
                    clima.setId(climaEntity.getClimaId());
                    clima.setDescripcion(climaEntity.getDescripcion());
                    climas.add(clima);
                }

                prendaResponse.setClimas(climas);
                prendaResponse.setColores(colores);
                prendaResponse.setEstilos(estilos);
                prendaResponses.add(prendaResponse);
            }
        }

        return prendaResponses;
    }




    @Override
    public List<PrendaResponse> getFavoritesByUser(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        Map<PrendaEntity, Usuario_Prenda> prendaFavMap = new HashMap<>();

        // Población del mapa con prendas y su relación con el usuario
        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId) && usuarioPrenda.isFavorita()) {
                prendaFavMap.put(usuarioPrenda.getPrenda(), usuarioPrenda);
            }
        }

        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (Map.Entry<PrendaEntity, Usuario_Prenda> entry : prendaFavMap.entrySet()) {
            PrendaEntity prendaEntity = entry.getKey();
            Usuario_Prenda usuarioPrenda = entry.getValue();
            boolean isFavorita = usuarioPrenda.isFavorita();

            PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
            prendaResponse.setBase64(prendaEntity.getBase64());
            prendaResponse.setFavorito(isFavorita);

            // Aplicar la lógica de setPrestada y setPrestamo
            prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
            prendaResponse.setPrestada(usuarioPrenda.getPrestada());
            if (usuarioPrenda.getPrestamo()) {
                prendaResponse.setPrestamo(true);
                prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
            } else {
                prendaResponse.setPrestamo(false);
            }

            // Colores
            List<AtributoResponse> colores = new ArrayList<>();
            for (ColorEntity colorEntity : prendaEntity.getColorEntities()) {
                AtributoResponse color = new AtributoResponse();
                color.setId(colorEntity.getColorId());
                color.setDescripcion(colorEntity.getDescripcion());
                colores.add(color);
            }

            // Estilos
            List<AtributoResponse> estilos = new ArrayList<>();
            for (EstilosEntity estilosEntity : prendaEntity.getEstilosEntity()) {
                AtributoResponse estilo = new AtributoResponse();
                estilo.setId(estilosEntity.getEstiloId());
                estilo.setDescripcion(estilosEntity.getDescripcion());
                estilos.add(estilo);
            }

            // Climas
            List<AtributoResponse> climas = new ArrayList<>();
            for (ClimaEntity climaEntity : prendaEntity.getClimaEntities()) {
                AtributoResponse clima = new AtributoResponse();
                clima.setId(climaEntity.getClimaId());
                clima.setDescripcion(climaEntity.getDescripcion());
                climas.add(clima);
            }

            prendaResponse.setClimas(climas);
            prendaResponse.setColores(colores);
            prendaResponse.setEstilos(estilos);
            prendaResponses.add(prendaResponse);
        }

        return prendaResponses;
    }


    @Override
    public List<PrendaResponse> getAccesoriesByUser(Long userId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        Map<PrendaEntity, Usuario_Prenda> prendaFavMap = new HashMap<>();

        // Población del mapa con prendas y su relación con el usuario
        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId)) {
                prendaFavMap.put(usuarioPrenda.getPrenda(), usuarioPrenda);
            }
        }

        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (Map.Entry<PrendaEntity, Usuario_Prenda> entry : prendaFavMap.entrySet()) {
            PrendaEntity prendaEntity = entry.getKey();
            Usuario_Prenda usuarioPrenda = entry.getValue();

            // Verificar si es accesorio (TipoPrendaId == 1L)
            if (prendaEntity.getTipoPrendaEntity().getTipoPrendaId().equals(1L)) {
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());
                prendaResponse.setFavorito(usuarioPrenda.isFavorita());

                // Aplicar la lógica de setPrestada y setPrestamo
                prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                prendaResponse.setPrestada(usuarioPrenda.getPrestada());
                if (usuarioPrenda.getPrestamo()) {
                    prendaResponse.setPrestamo(true);
                    prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                } else {
                    prendaResponse.setPrestamo(false);
                }

                // Colores
                List<AtributoResponse> colores = new ArrayList<>();
                for (ColorEntity colorEntity : prendaEntity.getColorEntities()) {
                    AtributoResponse color = new AtributoResponse();
                    color.setId(colorEntity.getColorId());
                    color.setDescripcion(colorEntity.getDescripcion());
                    colores.add(color);
                }

                // Estilos
                List<AtributoResponse> estilos = new ArrayList<>();
                for (EstilosEntity estilosEntity : prendaEntity.getEstilosEntity()) {
                    AtributoResponse estilo = new AtributoResponse();
                    estilo.setId(estilosEntity.getEstiloId());
                    estilo.setDescripcion(estilosEntity.getDescripcion());
                    estilos.add(estilo);
                }

                // Climas
                List<AtributoResponse> climas = new ArrayList<>();
                for (ClimaEntity climaEntity : prendaEntity.getClimaEntities()) {
                    AtributoResponse clima = new AtributoResponse();
                    clima.setId(climaEntity.getClimaId());
                    clima.setDescripcion(climaEntity.getDescripcion());
                    climas.add(clima);
                }

                prendaResponse.setClimas(climas);
                prendaResponse.setColores(colores);
                prendaResponse.setEstilos(estilos);
                prendaResponses.add(prendaResponse);
            }
        }

        return prendaResponses;
    }



    @Override
    public PrendaResponse setPrendaFavorite(Long prendaId,Long UserId) {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        for(Usuario_Prenda usuarioPrenda:usuarioPrendas){
            if(usuarioPrenda.getPrenda().getPrendaId().equals(prendaId) && usuarioPrenda.getUsuario().getUsuarioId().equals(UserId)){
                if(usuarioPrenda.isFavorita()){
                    usuarioPrenda.setFavorita(false);
                    usuarioPrendaJpaRepository.saveAndFlush(usuarioPrenda);
                    return modelMapper.map(usuarioPrenda.getPrenda(),PrendaResponse.class);
                }
                usuarioPrenda.setFavorita(true);
                usuarioPrendaJpaRepository.saveAndFlush(usuarioPrenda);
                return modelMapper.map(usuarioPrenda.getPrenda(),PrendaResponse.class);
            }
        }
        return null;
    }

    @Override
    public List<PrendaResponse> getPrendasResponseByFilters(List<Long> prendasId, List<Long> coloresId, List<Long> climasId, List<Long> estilosId, boolean favorite, Long userId) {
        List<PrendaEntity> entities = new ArrayList<>();
        List<PrendaEntity> todas = new ArrayList<>();
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();

        // Obtener todas las prendas del usuario
        for (Usuario_Prenda usuarioPrenda : usuarioPrendas) {
            if (usuarioPrenda.getUsuario().getUsuarioId().equals(userId)) {
                PrendaEntity prendaEntity = usuarioPrenda.getPrenda();
                todas.add(prendaEntity);
            }
        }

        // Filtrar prendas por IDs si se proporcionan
        if (!prendasId.isEmpty()) {
            for (Long prendaId : prendasId) {
                prendaJpaRepository.findById(prendaId).ifPresent(entities::add);
            }
        } else {
            entities = todas;
        }

        List<PrendaResponse> responses = new ArrayList<>();

        // Filtrar y mapear las prendas
        for (PrendaEntity prendaEntity : entities) {
            boolean matchesColors = coloresId.isEmpty() || prendaEntity.getColorEntities().stream().anyMatch(color -> coloresId.contains(color.getColorId()));
            boolean matchesClimas = climasId.isEmpty() || prendaEntity.getClimaEntities().stream().anyMatch(clima -> climasId.contains(clima.getClimaId()));
            boolean matchesEstilos = estilosId.isEmpty() || prendaEntity.getEstilosEntity().stream().anyMatch(estilo -> estilosId.contains(estilo.getEstiloId()));

            // Solo agregar la prenda si cumple con todos los filtros presentes
            if (matchesColors && matchesClimas && matchesEstilos) {
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());

                // Verificar si la prenda es favorita
                Usuario_Prenda usuarioPrenda = usuarioPrendas.stream()
                        .filter(up -> up.getUsuario().getUsuarioId().equals(userId) && up.getPrenda().getPrendaId().equals(prendaEntity.getPrendaId()))
                        .findFirst()
                        .orElse(null);

                if (usuarioPrenda != null) {
                    prendaResponse.setFavorito(usuarioPrenda.isFavorita());
                    prendaResponse.setUsos(usuarioPrenda.getVecesUsada());
                    prendaResponse.setPrestada(usuarioPrenda.getPrestada());
                    if (usuarioPrenda.getPrestamo()) {
                        prendaResponse.setPrestamo(true);
                        prendaResponse.setPrestadabyUserId(usuarioPrenda.getPrestadabyUserId());
                    } else {
                        prendaResponse.setPrestamo(false);
                    }
                }

                // Añadir la prenda a la lista de respuestas
                responses.add(prendaResponse);
            }
        }

        return responses;
    }


    @Override
    public List<PrendaResponse> getPrendasForOutfit(Integer amountPrendas , Long userId) {
        switch (amountPrendas){
            case 3:
                return ThreePrendasOutfit(userId);
            case 4:
                return getFootWearByUser(0L);
            case 5:
                return getOuterWearByUser(0L);
            case 6:
                return getDressesByUser(0L);
            default:
                return getPrendasPublicas();
        }
    }

    @Override
    public List<PrendaResponse> getFamousPrendas() {
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();

        // Agrupar las prendas por su prendaId y contar cuántos usuarios tienen cada prenda
        Map<PrendaEntity, Long> prendaCount = usuarioPrendas.stream()
                .collect(Collectors.groupingBy(Usuario_Prenda::getPrenda, Collectors.counting()));

        // Ordenar las prendas por el número de usuarios en orden descendente y obtener las 5 primeras
        List<PrendaEntity> top5Prendas = prendaCount.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Calcular la suma de usuarios que están usando cada prenda
        Map<PrendaEntity, Long> usuariosUsandoPrenda = new HashMap<>();
        for (PrendaEntity prendaEntity : top5Prendas) {
            Long cantidadUsuarios = usuarioPrendas.stream()
                    .filter(up -> up.getPrenda().equals(prendaEntity))
                    .count();
            usuariosUsandoPrenda.put(prendaEntity, cantidadUsuarios);
        }

        // Convertir las entidades de PrendaEntity a PrendaResponse
        List<PrendaResponse> response = new ArrayList<>();
        for (PrendaEntity prendaEntity : top5Prendas) {
            PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
            prendaResponse.setBase64(prendaEntity.getBase64());
            prendaResponse.setGenteUsando(usuariosUsandoPrenda.getOrDefault(prendaEntity, 0L).intValue());
            response.add(prendaResponse);
        }

        return response;
    }


    @Override
    public Integer CantidadPrendas() {
        List<PrendaEntity> prendas = prendaJpaRepository.findAll();
        return prendas.size();
    }

    @Override
    public List<PrendaResponse> Top3MoreUsed() {
       List<PrendaEntity> prendas = prendaJpaRepository.findAll();
        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (PrendaEntity prendaEntity : prendas) {
            PrendaResponse prendaResponse = modelMapper.map(prendaEntity,PrendaResponse.class);
            prendaResponse.setBase64(prendaEntity.getBase64());
            prendaResponses.add(prendaResponse);
        }
        return prendaResponses;
    }

    public List<AtributoResponse> Top3Estilos() {
        List<PrendaEntity> prendas = prendaJpaRepository.findAll();
        Map<Long, Long> estiloCount = new HashMap<>();

        // Contar la frecuencia de cada estilo
        for (PrendaEntity prendaEntity : prendas) {
            for (EstilosEntity estilo : prendaEntity.getEstilosEntity()) {
                estiloCount.put(estilo.getEstiloId(), estiloCount.getOrDefault(estilo.getEstiloId(), 0L) + 1);
            }
        }

        // Ordenar los estilos por frecuencia y obtener los top 3
        List<Long> top3EstilosIds = estiloCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())) // Orden descendente
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Convertir los IDs de los estilos a AtributoResponse
        List<AtributoResponse> top3Estilos = new ArrayList<>();
        for (Long estiloId : top3EstilosIds) {
            // Suponiendo que tienes un método para convertir un estilo a AtributoResponse
            top3Estilos.add(convertToAtributoResponse(estiloId));
        }

        return top3Estilos;
    }

    private AtributoResponse convertToAtributoResponse(Long estiloId) {
        // Implementa este método según la lógica de tu aplicación
        EstilosEntity estilo = estilosJpaRepository.findById(estiloId).orElse(null);
        return new AtributoResponse(estilo.getEstiloId(), estilo.getDescripcion());
    }
    @Override
    public List<PrendaResponse> Top3LeastUsed() {
        List<PrendaEntity> prendas = prendaJpaRepository.findAll();
        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (PrendaEntity prendaEntity : prendas) {
            PrendaResponse prendaResponse = modelMapper.map(prendaEntity,PrendaResponse.class);
            prendaResponse.setBase64(prendaEntity.getBase64());
            prendaResponses.add(prendaResponse);
        }
        return prendaResponses;
    }

    @Override
    public Integer PrendasAddedLastMonth() {
        // Obtener la fecha actual
        LocalDate now = LocalDate.now();

        // Obtener el primer y el último día del mes pasado
        LocalDate firstDayOfLastMonth = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfLastMonth = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        // Obtener todas las prendas
        List<PrendaEntity> prendas = prendaJpaRepository.findAll();

        // Filtrar y contar las prendas añadidas el mes pasado
        long count = prendas.stream()
                .filter(prenda -> {
                    LocalDate fechaCreacion = prenda.getFechaCreacion();
                    return (fechaCreacion.isEqual(firstDayOfLastMonth) || fechaCreacion.isAfter(firstDayOfLastMonth)) &&
                            (fechaCreacion.isEqual(lastDayOfLastMonth) || fechaCreacion.isBefore(lastDayOfLastMonth));
                })
                .count();

        return (int) count;
    }

    private List<PrendaResponse> ThreePrendasOutfit(Long userId) {
        List<PrendaResponse> prendaResponses = new ArrayList<>();

        // Obtener listas de tops, bottoms y footwear del usuario
        List<PrendaResponse> tops = getTopsByUser(userId);
        List<PrendaResponse> bottoms = getBottomsByUser(userId);
        List<PrendaResponse> footWear = getFootWearByUser(userId);

        // Buscar combinaciones de prendas que cumplan con los criterios
        for (PrendaResponse top : tops) {
            List<Long> estilosTop = new ArrayList<>();
            for(AtributoResponse atributo : top.getEstilos()){
                estilosTop.add(atributo.getId());
            }
            List<Long> coloresTop = new ArrayList<>();
            for(AtributoResponse atributo : top.getColores()){
                coloresTop.add(atributo.getId());
            }
            List<Long> climasTop = new ArrayList<>();
            for(AtributoResponse atributo : top.getClimas()){
                climasTop.add(atributo.getId());
            }

            for (PrendaResponse bottom : bottoms) {
                List<Long> estilosBottom = new ArrayList<>();
                for(AtributoResponse atributo : bottom.getColores()){
                    estilosBottom.add(atributo.getId());
                }
                List<Long> coloresBottom = new ArrayList<>();
                for(AtributoResponse atributo : bottom.getColores()){
                    coloresBottom.add(atributo.getId());
                }
                List<Long> climasBottom = new ArrayList<>();
                for(AtributoResponse atributo : bottom.getColores()){
                    climasBottom.add(atributo.getId());
                }

                for (PrendaResponse footwear : footWear) {
                    List<Long> estilosFootwear = new ArrayList<>();
                    for(AtributoResponse atributo : footwear.getColores()){
                        estilosFootwear.add(atributo.getId());
                    }
                    List<Long> coloresFootwear = new ArrayList<>();
                    for(AtributoResponse atributo : footwear.getColores()){
                        coloresFootwear.add(atributo.getId());
                    }
                    List<Long> climasFootwear = new ArrayList<>();
                    for(AtributoResponse atributo : footwear.getColores()){
                        climasFootwear.add(atributo.getId());
                    }

                    // Verificar si las tres prendas tienen al menos un estilo y clima en común
                    if (!intersect(estilosTop, estilosBottom, estilosFootwear).isEmpty() &&
                            !intersect(climasTop, climasBottom, climasFootwear).isEmpty()) {

                        // Verificar si al menos dos prendas coinciden en algún color
                        if (compartenColor(coloresTop, coloresBottom) ||
                                compartenColor(coloresTop, coloresFootwear) ||
                                compartenColor(coloresBottom, coloresFootwear)) {

                            // Añadir las prendas a la lista de respuesta
                            prendaResponses.add(top);
                            prendaResponses.add(bottom);
                            prendaResponses.add(footwear);
                            return prendaResponses;  // Devolver la primera combinación encontrada
                        }
                    }
                }
            }
        }

        return prendaResponses;  // Devolver lista vacía si no se encontró ninguna combinación
    }

    // Método auxiliar para obtener IDs de una lista de AtributoResponse
    private List<Long> getIds(List<AtributoResponse> atributos) {
        List<Long> ids = new ArrayList<>();
        for (AtributoResponse atributo : atributos) {
            ids.add(atributo.getId());
        }
        return ids;
    }

    // Método auxiliar para verificar si dos listas de colores tienen algún color en común
    private boolean compartenColor(List<Long> colores1, List<Long> colores2) {
        for (Long color1 : colores1) {
            if (colores2.contains(color1)) {
                return true;
            }
        }
        return false;
    }

    // Método auxiliar para encontrar la intersección de tres listas
    private List<Long> intersect(List<Long> list1, List<Long> list2, List<Long> list3) {
        List<Long> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);
        intersection.retainAll(list3);
        return intersection;
    }






}
