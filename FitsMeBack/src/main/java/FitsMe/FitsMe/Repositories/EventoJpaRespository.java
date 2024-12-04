package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.EventosEntity;
import FitsMe.FitsMe.Entities.OutfitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoJpaRespository extends JpaRepository<EventosEntity, Long> {

    List<EventosEntity> findByFechaAndUsuarioEntityUsuarioId(String fecha, Long usuarioId);
    List<EventosEntity> findByOutfitEntity(OutfitEntity outfitEntity);


}
