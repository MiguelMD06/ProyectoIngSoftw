package co.edu.local.gestionIcfes.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.MaterialEstudio;

public interface MaterialEstudioServices {

    MaterialEstudio subirMaterial(MultipartFile archivo, String titulo, String descripcion,
                                  Integer semana, Long institucionId);

    List<MaterialEstudio> listarPorInstitucion(Long institucionId);

    MaterialEstudio descargarMaterial(Long id);

    void eliminarMaterial(Long id);
}
