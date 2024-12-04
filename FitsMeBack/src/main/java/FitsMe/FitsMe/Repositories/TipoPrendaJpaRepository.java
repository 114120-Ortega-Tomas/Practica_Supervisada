package FitsMe.FitsMe.Repositories;




import FitsMe.FitsMe.Entities.TipoPrendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPrendaJpaRepository extends JpaRepository<TipoPrendaEntity, Long> {


}
