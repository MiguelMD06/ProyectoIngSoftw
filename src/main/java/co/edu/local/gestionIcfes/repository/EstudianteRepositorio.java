package co.edu.local.gestionIcfes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.local.gestionIcfes.model.Estudiante;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, String> {

	@Query("""
		    SELECT DISTINCT e
		    FROM Estudiante e
		    LEFT JOIN FETCH e.resultados
		""")
		List<Estudiante> listarEstudiantesConResultados();

	Optional<Estudiante> findByUsuarioUsername(String username);
	List<Estudiante> findByInstitucionId(Long institucionId);
}
