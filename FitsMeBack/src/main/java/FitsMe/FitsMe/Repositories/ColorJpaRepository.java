package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.ColorEntity;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorJpaRepository extends JpaRepository<ColorEntity, Long> {


}
