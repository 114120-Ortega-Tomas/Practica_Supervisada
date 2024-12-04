package FitsMe.FitsMe.Repositories;




import FitsMe.FitsMe.Entities.PrendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrendaJpaRepository extends JpaRepository<PrendaEntity, Long> {
    List<PrendaEntity> findAllByPrivadoIsFalse();

    List<PrendaEntity> findByTipoPrendaEntityTipoPrendaId(Long tipoPrendaId);


}
