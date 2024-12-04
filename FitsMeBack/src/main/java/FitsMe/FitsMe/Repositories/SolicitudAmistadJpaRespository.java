package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.ClimaEntity;
import FitsMe.FitsMe.Entities.SolicitudAmistadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudAmistadJpaRespository extends JpaRepository<SolicitudAmistadEntity, Long> {

    List<SolicitudAmistadEntity> findAllByUserToId(Long userToId);

}
