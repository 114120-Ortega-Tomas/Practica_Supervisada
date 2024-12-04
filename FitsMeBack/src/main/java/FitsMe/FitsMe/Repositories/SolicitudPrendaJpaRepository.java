package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.SolicitudAmistadEntity;
import FitsMe.FitsMe.Entities.SolicitudPrendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudPrendaJpaRepository extends JpaRepository<SolicitudPrendaEntity, Long> {

    List<SolicitudPrendaEntity> findAllByUserToId(Long userToId);

}
