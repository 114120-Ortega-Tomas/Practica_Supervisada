package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

 List<UsuarioEntity> findUsuarioEntitiesByUsernameStartingWith(String username);
 List<UsuarioEntity> findUsuarioEntitiesByFecCreacionBetween(LocalDate dateFrom, LocalDate dateTo);
}
