package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.OutfitEntity;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutfitJpaRepository extends JpaRepository<OutfitEntity, Long> {

List<OutfitEntity> findByFavorito(boolean favorito);
}
