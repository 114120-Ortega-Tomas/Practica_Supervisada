package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.ClimaEntity;
import FitsMe.FitsMe.Entities.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimaJpaRepository extends JpaRepository<ClimaEntity, Long> {


}
