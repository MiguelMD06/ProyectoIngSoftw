package co.edu.local.gestionIcfes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.local.gestionIcfes.model.LogCambio;

@Repository
public interface LogRepository extends JpaRepository<LogCambio, Long>{

	public List<LogCambio> findAllByOrderByIdDesc();
	
}
