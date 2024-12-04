package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.ColorEntity;
import FitsMe.FitsMe.Entities.EstilosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstilosJpaRepository extends JpaRepository<EstilosEntity, Long> {


}
