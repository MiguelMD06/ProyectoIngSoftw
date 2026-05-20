package co.edu.local.gestionIcfes.servicesImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.Institucion;
import co.edu.local.gestionIcfes.model.MaterialEstudio;
import co.edu.local.gestionIcfes.repository.MaterialEstudioRepositorio;
import co.edu.local.gestionIcfes.services.InstitucionService;
import co.edu.local.gestionIcfes.services.MaterialEstudioServices;

@Service
@Transactional
public class MaterialEstudioServicesImpl implements MaterialEstudioServices {

    @Autowired
    private MaterialEstudioRepositorio materialRepositorio;

    @Autowired
    private InstitucionService institucionService;

    @Override
    public MaterialEstudio subirMaterial(MultipartFile archivo, String titulo, String descripcion,
                                          Integer semana, Long institucionId) {
        Institucion institucion = institucionService.buscarPorId(institucionId);

        try {
            MaterialEstudio material = new MaterialEstudio();
            material.setTitulo(titulo);
            material.setDescripcion(descripcion);
            material.setSemana(semana);
            material.setFechaSubida(LocalDate.now());
            material.setNombreArchivo(archivo.getOriginalFilename());
            material.setTipoArchivo(archivo.getContentType());
            material.setArchivo(archivo.getBytes());
            material.setInstitucion(institucion);
            return materialRepositorio.save(material);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialEstudio> listarPorInstitucion(Long institucionId) {
        return materialRepositorio.findByInstitucionIdOrderBySemanaDesc(institucionId);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialEstudio descargarMaterial(Long id) {
        return materialRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));
    }

    @Override
    public void eliminarMaterial(Long id) {
        materialRepositorio.deleteById(id);
    }
}
