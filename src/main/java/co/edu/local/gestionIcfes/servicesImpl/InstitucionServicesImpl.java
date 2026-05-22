package co.edu.local.gestionIcfes.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.local.gestionIcfes.enums.EstadoInstitucion;
import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.MaterialEstudio;
import co.edu.local.gestionIcfes.model.Simulacro;
import co.edu.local.gestionIcfes.model.Usuario;
import co.edu.local.gestionIcfes.repository.AsistenciaRepositorio;
import co.edu.local.gestionIcfes.repository.DocenteRepositorio;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.repository.InstitucionRepositorio;
import co.edu.local.gestionIcfes.repository.MaterialEstudioRepositorio;
import co.edu.local.gestionIcfes.repository.ResultadoSimulacroRepositorio;
import co.edu.local.gestionIcfes.repository.SimulacroRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.LogService;

/**
 * Implementación de {@link co.edu.local.gestionIcfes.services.InstitucionService}.
 * <p>
 * {@link #eliminarInstitucion} ejecuta la eliminación en cascada en el siguiente orden
 * para respetar las restricciones de clave foránea de PostgreSQL:
 * asistencias → resultados de simulacro (por estudiante) → resultados de simulacro (por simulacro)
 * → estudiantes y sus usuarios → docentes y sus usuarios → simulacros → materiales de estudio
 * → usuarios admin vinculados → institución.
 * </p>
 */
@Service
public class InstitucionServicesImpl implements InstitucionService{

	@Autowired
	private InstitucionRepositorio institucionRepository;

	@Autowired
	private AsistenciaRepositorio asistenciaRepositorio;

	@Autowired
	private ResultadoSimulacroRepositorio resultadoRepositorio;

	@Autowired
	private EstudianteRepositorio estudianteRepositorio;

	@Autowired
	private DocenteRepositorio docenteRepositorio;

	@Autowired
	private SimulacroRepositorio simulacroRepositorio;

	@Autowired
	private MaterialEstudioRepositorio materialRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private LogService logService;
	
	@Override
	public List<Institucion> listarInstituciones() {
		return institucionRepository.findAll();
	}
	@Override
    public Institucion guardarInstitucion(Institucion institucion) {
		logService.registrarLog("institucion", "Institución registrada");
        return institucionRepository.save(institucion);
    }
	@Override
	public Institucion buscarPorId(Long id) {
		return institucionRepository.findById(id).get();
	}

	@Override
	public Institucion actualizarInstitucion(Institucion institucion) {
		logService.registrarLog("institucion", "Institución actualizada: " + institucion.getNombre());
		return institucionRepository.save(institucion);
	}

	@Override
	@Transactional
	public void eliminarInstitucion(Long id) {
		Institucion institucion = institucionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Institución no encontrada"));
		String nombre = institucion.getNombre();

		// 1. Asistencias (FK → estudiantes)
		asistenciaRepositorio.deleteByEstudianteInstitucionId(id);

		// 2. Resultados simulacro de los estudiantes de esta institución
		resultadoRepositorio.deleteByEstudianteInstitucionId(id);

		// 3. Resultados simulacro de los simulacros de esta institución (por si quedó alguno)
		resultadoRepositorio.deleteBySimulacroInstitucionId(id);

		// 4. Estudiantes y sus usuarios
		List<Estudiante> estudiantes = estudianteRepositorio.findByInstitucionId(id);
		for (Estudiante est : estudiantes) {
			Long usuarioId = est.getUsuario() != null ? est.getUsuario().getId() : null;
			estudianteRepositorio.delete(est);
			if (usuarioId != null) usuarioRepositorio.deleteById(usuarioId);
		}

		// 5. Docentes y sus usuarios
		List<Docente> docentes = docenteRepositorio.findByInstitucionId(id);
		for (Docente doc : docentes) {
			Long usuarioId = doc.getUsuario() != null ? doc.getUsuario().getId() : null;
			docenteRepositorio.delete(doc);
			if (usuarioId != null) usuarioRepositorio.deleteById(usuarioId);
		}

		// 6. Simulacros
		List<Simulacro> simulacros = simulacroRepositorio.findByInstitucionId(id);
		simulacroRepositorio.deleteAll(simulacros);

		// 7. Material de estudio
		List<MaterialEstudio> materiales = materialRepositorio.findByInstitucionIdOrderBySemanaDesc(id);
		materialRepositorio.deleteAll(materiales);

		// 8. Usuarios admin directamente vinculados a la institución
		List<Usuario> usuariosRestantes = usuarioRepositorio.findByInstitucionId(id);
		usuarioRepositorio.deleteAll(usuariosRestantes);

		// 9. Institución
		institucionRepository.deleteById(id);

		logService.registrarLog("institucion", "Institución eliminada en cascada: " + nombre);
	}

	@Override
	public Long cantidadInstitucionesActivas() {
		return institucionRepository.countByEstado(EstadoInstitucion.ACTIVO);
	}
}
