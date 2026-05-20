package co.edu.local.gestionIcfes.servicesImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.local.gestionIcfes.model.Docente;
import co.edu.local.gestionIcfes.model.Estudiante;
import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.Usuario;
import co.edu.local.gestionIcfes.repository.DocenteRepositorio;
import co.edu.local.gestionIcfes.repository.EstudianteRepositorio;
import co.edu.local.gestionIcfes.repository.InstitucionRepositorio;
import co.edu.local.gestionIcfes.repository.UsuarioRepositorio;
import co.edu.local.gestionIcfes.services.LogService;

@Service
public class CierreInstitucionScheduler {

    @Autowired
    private InstitucionRepositorio institucionRepositorio;

    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Autowired
    private DocenteRepositorio docenteRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private LogService logService;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void desactivarUsuariosPorCierreInstitucion() {
        List<Institucion> vencidas = institucionRepositorio
                .findByFechaFinalLessThanEqualAndFechaFinalIsNotNull(LocalDate.now());

        for (Institucion institucion : vencidas) {
            int desactivados = 0;

            for (Estudiante est : estudianteRepositorio.findByInstitucionId(institucion.getId())) {
                Usuario u = est.getUsuario();
                if (u != null && Boolean.TRUE.equals(u.getEnabled())) {
                    u.setEnabled(false);
                    usuarioRepositorio.save(u);
                    desactivados++;
                }
            }

            for (Docente doc : docenteRepositorio.findByInstitucionId(institucion.getId())) {
                Usuario u = doc.getUsuario();
                if (u != null && Boolean.TRUE.equals(u.getEnabled())) {
                    u.setEnabled(false);
                    usuarioRepositorio.save(u);
                    desactivados++;
                }
            }

            if (desactivados > 0) {
                logService.registrarLog("institucion",
                        "Cierre automático: " + desactivados + " usuario(s) desactivados — "
                        + institucion.getNombre());
            }
        }
    }
}
