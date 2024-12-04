package FitsMe.FitsMe.Repositories;





import FitsMe.FitsMe.Entities.ClimaEntity;
import FitsMe.FitsMe.Entities.PrendaEntity;
import FitsMe.FitsMe.Entities.UsuarioEntity;
import FitsMe.FitsMe.Entities.Usuario_Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioPrendaJpaRepository extends JpaRepository<Usuario_Prenda, Long> {

    Optional<Usuario_Prenda> findByUsuarioAndPrenda( UsuarioEntity userId ,PrendaEntity prendaId);


}
