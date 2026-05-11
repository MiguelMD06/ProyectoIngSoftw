package co.edu.local.gestionIcfes.servicesImpl;



import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.ResultadoSimulacro;
import co.edu.local.gestionIcfes.model.Simulacro;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.repository.ResultadoSimulacroRepositorio;
import co.edu.local.gestionIcfes.repository.SimulacroRepositorio;
import co.edu.local.gestionIcfes.services.LogService;
import co.edu.local.gestionIcfes.services.SimulacroService;

@Service
@Transactional
public class SimulacroServiceImpl implements SimulacroService {

	
	@Autowired
	private LogService logService;
	
    private final SimulacroRepositorio simulacroRepositorio;
	
    private final EstudianteRepositorio estudianteRepositorio;

    private final ResultadoSimulacroRepositorio resultadoRepo;

    public SimulacroServiceImpl(SimulacroRepositorio simulacroRepositorio,
                                EstudianteRepositorio estudianteRepositorio,
                                ResultadoSimulacroRepositorio resultadoRepo) {
        this.simulacroRepositorio = simulacroRepositorio;
        this.estudianteRepositorio = estudianteRepositorio;
        this.resultadoRepo = resultadoRepo;
    }

    @Override
    public Simulacro crearSimulacro(Simulacro simulacro) {
    	logService.registrarLog("simulacro", "Nuevo simulacro registrado");
        return simulacroRepositorio.save(simulacro);
    }

    @Override
    public ResultadoSimulacro subirResultado(String documentoIdentidad, Long simulacroId, MultipartFile archivo) {
        Estudiante estudiante = estudianteRepositorio.findById(documentoIdentidad)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Simulacro simulacro = simulacroRepositorio.findById(simulacroId)
                .orElseThrow(() -> new RuntimeException("Simulacro no encontrado"));

        if (!archivo.getContentType().equals("application/pdf")) {
            throw new RuntimeException("Solo se permiten archivos PDF");
        }

        try {
            ResultadoSimulacro resultado = new ResultadoSimulacro();
            resultado.setNombreArchivo(archivo.getOriginalFilename());
            resultado.setTipoArchivo(archivo.getContentType());
            resultado.setDatos(archivo.getBytes()); // ahora dentro del try
            resultado.setEstudiante(estudiante);
            resultado.setSimulacro(simulacro);
            logService.registrarLog("Simulacro", "Nuevo simulacro subido");
            return resultadoRepo.save(resultado);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo PDF", e);
        }
    }

    @Override
    public List<Simulacro> listarSimulacros() {
        return simulacroRepositorio.findAll();
    }
    
    @Override
    public Simulacro obtenerSimulacroPorId(Long simulacroId) {
        return simulacroRepositorio.findById(simulacroId)
                .orElseThrow(() -> new RuntimeException("Simulacro no encontrado"));
    }

    
}
