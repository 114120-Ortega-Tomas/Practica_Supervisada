package FitsMe.FitsMe.Services.Impl;



import FitsMe.FitsMe.Dtos.Eventos.EventoRequest;
import FitsMe.FitsMe.Dtos.Eventos.EventoResponse;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitRequest;
import FitsMe.FitsMe.Dtos.OutfitsDto.OutfitResponse;
import FitsMe.FitsMe.Dtos.PrendasDtos.PrendaResponse;
import FitsMe.FitsMe.Entities.*;
import FitsMe.FitsMe.Repositories.*;
import FitsMe.FitsMe.Services.EventosService;
import FitsMe.FitsMe.Services.OutfitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OutfitServiceImpl implements OutfitService {

    @Autowired
    private EventoJpaRespository eventoJpaRespository;
    @Autowired
    private UsuarioPrendaJpaRepository usuarioPrendaJpaRepository;
    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;
    @Autowired
    private OutfitJpaRepository outfitJpaRepository;
    @Autowired
    private PrendaJpaRepository prendaJpaRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public OutfitResponse getOutfitbyId(Long outfitId) {
        Optional<OutfitEntity> outfitEntity = outfitJpaRepository.findById(outfitId);
        if(!outfitEntity.isPresent()){throw new RuntimeException("El outfit no existe");}
        List<PrendaResponse> prendaResponses = new ArrayList<>();
        List<PrendaEntity> prendas = outfitEntity.get().getPrendaEntities();
        for (PrendaEntity prendaEntity: prendas){
            PrendaResponse prendaResponse = modelMapper.map(prendaEntity,PrendaResponse.class);
            prendaResponse.setBase64(prendaEntity.getBase64());
            prendaResponses.add(prendaResponse);
        }
        OutfitResponse outfitResponse = new OutfitResponse();
        outfitResponse.setNombre(outfitEntity.get().getNombre());
        outfitResponse.setFavorito(outfitEntity.get().isFavorito());
        outfitResponse.setPrendas(prendaResponses);
        return outfitResponse;
    }

    @Override
    public List<OutfitResponse> getOutfits() {
        List<OutfitEntity> entities = outfitJpaRepository.findAll();
        List<OutfitResponse> responses = new ArrayList<>();
        for(OutfitEntity entity: entities){
            List<PrendaResponse> prendaResponses = new ArrayList<>();
            List<PrendaEntity> prendas = entity.getPrendaEntities();
            for (PrendaEntity prendaEntity: prendas){
                PrendaResponse prendaResponse = modelMapper.map(prendaEntity,PrendaResponse.class);
                prendaResponse.setBase64(prendaEntity.getBase64());
                prendaResponses.add(prendaResponse);
            }
            OutfitResponse outfitResponse = new OutfitResponse();
            outfitResponse.setNombre(entity.getNombre());
            outfitResponse.setFavorito(entity.isFavorito());
            outfitResponse.setPrendas(prendaResponses);
            responses.add(outfitResponse);
        }
        return responses;

    }

    @Override
    public boolean DeleteOutfit(Long outfitId) {
        Optional<OutfitEntity> outfitEntity = outfitJpaRepository.findById(outfitId);

        if (outfitEntity.isPresent()) {

            List<EventosEntity> eventosEntity = eventoJpaRespository.findByOutfitEntity(outfitEntity.get());

            // Eliminar los eventos
            if (!eventosEntity.isEmpty()) {
                eventoJpaRespository.deleteAll(eventosEntity);
            }

            // Eliminar el Outfit
            outfitJpaRepository.delete(outfitEntity.get());
            return true;
        }

        return false;
    }


    @Override
    public OutfitResponse saveOutfit(OutfitRequest outfitRequest) {
        OutfitEntity outfitEntity = new OutfitEntity();

        // Cargar las prendas
        List<PrendaEntity> prendas = new ArrayList<>();
        for (Long prendaId : outfitRequest.getPrendas()) {
            prendaJpaRepository.findById(prendaId).ifPresent(prendas::add);
        }
        outfitEntity.setPrendaEntities(prendas);
        List<Usuario_Prenda> usuarioPrendas = usuarioPrendaJpaRepository.findAll();
        for(PrendaEntity prendaEntity: prendas){
            for(Usuario_Prenda usuarioPrenda: usuarioPrendas){
                if(prendaEntity.getPrendaId() == usuarioPrenda.getPrenda().getPrendaId()){
                    usuarioPrenda.setVecesUsada(usuarioPrenda.getVecesUsada()+1);
                }
            }
        }
        // Cargar el usuario
        outfitEntity.setUsuarioEntity(usuarioJpaRepository.findById(outfitRequest.getUsuarioId()).orElse(null));
        outfitEntity.setFavorito(outfitRequest.isFavorito());
        outfitEntity.setNombre(outfitRequest.getNombre());
        outfitEntity.setFechaCreacion(new Date().toString());

        // Guardar la entidad
        outfitJpaRepository.save(outfitEntity);

        // Mapear la entidad guardada a la respuesta
        OutfitResponse outfitResponse = new OutfitResponse();

        outfitResponse.setFavorito(outfitEntity.isFavorito());
        outfitResponse.setNombre(outfitEntity.getNombre());
        outfitResponse.setId(outfitEntity.getOutfitId());

        // Mapear las prendas a respuestas
        List<PrendaResponse> prendaResponses = new ArrayList<>();
        for (PrendaEntity prendaEntity : prendas) {
            PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
            prendaResponses.add(prendaResponse);
        }

        outfitResponse.setPrendas(prendaResponses);
        outfitResponse.setUsuario(outfitEntity.getUsuarioEntity().getUsername());

        return outfitResponse;
    }

    @Override
    public List<OutfitResponse> getOutfitsByUserId(Long userId) {
        List<OutfitEntity> entities = outfitJpaRepository.findAll();
        List<OutfitResponse> responses = new ArrayList<>();

        for (OutfitEntity entity : entities) {
            if (entity.getUsuarioEntity().getUsuarioId().equals(userId)) {
                List<PrendaResponse> prendaResponses = new ArrayList<>();
                List<PrendaEntity> prendas = entity.getPrendaEntities();

                for (PrendaEntity prendaEntity : prendas) {
                    PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                    prendaResponse.setBase64(prendaEntity.getBase64());
                    prendaResponses.add(prendaResponse);
                }

                // Ordenar prendaResponses por tipoPrenda.id
                prendaResponses.sort((a, b) -> {
                    Long idA = a.getTipoPrenda().getId();
                    Long idB = b.getTipoPrenda().getId();
                    return Long.compare(idA, idB);
                });

                OutfitResponse outfitResponse = new OutfitResponse();
                outfitResponse.setId(entity.getOutfitId());
                outfitResponse.setNombre(entity.getNombre());
                outfitResponse.setFavorito(entity.isFavorito());
                outfitResponse.setPrendas(prendaResponses);
                responses.add(outfitResponse);
            }
        }

        return responses;
    }

    @Override
    public List<OutfitResponse> getFavoriteOutfitsByUserId(Long userId) {
        // Obtener todos los outfits del usuario
        List<OutfitEntity> entities = outfitJpaRepository.findAll();

        List<OutfitResponse> responses = new ArrayList<>();

        for (OutfitEntity entity : entities) {
            // Filtrar solo los favoritos
            if (entity.isFavorito() && entity.getUsuarioEntity().getUsuarioId().equals(userId)) {
                List<PrendaResponse> prendaResponses = new ArrayList<>();
                List<PrendaEntity> prendas = entity.getPrendaEntities();

                for (PrendaEntity prendaEntity : prendas) {
                    PrendaResponse prendaResponse = modelMapper.map(prendaEntity, PrendaResponse.class);
                    prendaResponse.setBase64(prendaEntity.getBase64());
                    prendaResponses.add(prendaResponse);
                }

                // Ordenar las prendas por tipoPrenda.id
                prendaResponses.sort((a, b) -> Long.compare(
                        a.getTipoPrenda().getId(),
                        b.getTipoPrenda().getId()
                ));

                OutfitResponse outfitResponse = new OutfitResponse();
                outfitResponse.setId(entity.getOutfitId());
                outfitResponse.setNombre(entity.getNombre());
                outfitResponse.setFavorito(entity.isFavorito());
                outfitResponse.setPrendas(prendaResponses);
                responses.add(outfitResponse);
            }
        }

        return responses;
    }


    @Override
    public boolean setOutfitFavorito(Long outfitId, Long userId) {
        List<OutfitEntity> entities = outfitJpaRepository.findAll();
        for (OutfitEntity entity : entities) {
            if (entity.getOutfitId().equals(outfitId) && entity.getUsuarioEntity().getUsuarioId().equals(userId)) {
                if(entity.isFavorito()){
                    entity.setFavorito(false);
                }else{
                    entity.setFavorito(true);
                }
                outfitJpaRepository.save(entity);
                return true;
            }
        }
        return false;
    }


}
