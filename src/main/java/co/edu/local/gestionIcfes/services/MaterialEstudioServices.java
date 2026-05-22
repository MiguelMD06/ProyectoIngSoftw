package co.edu.local.gestionIcfes.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.edu.local.gestionIcfes.model.MaterialEstudio;

/**
 * Contrato del servicio de materiales de estudio.
 * <p>
 * Los archivos se almacenan como {@code BYTEA} en la base de datos.
 * Los métodos de lectura ({@link #listarPorInstitucion} y {@link #descargarMaterial})
 * deben invocarse dentro de una transacción de solo lectura para mantener la sesión
 * de Hibernate abierta al acceder al campo {@code archivo}.
 * </p>
 */
public interface MaterialEstudioServices {

    /**
     * Sube un archivo de material de estudio y lo asocia a una institución y semana.
     *
     * @param archivo       archivo a subir (PDF, imagen, etc.).
     * @param titulo        título descriptivo del material.
     * @param descripcion   descripción del contenido.
     * @param semana        número de semana a la que corresponde el material.
     * @param institucionId identificador de la institución destino.
     * @return el material persistido.
     */
    MaterialEstudio subirMaterial(MultipartFile archivo, String titulo, String descripcion,
                                  Integer semana, Long institucionId);

    /**
     * Lista los materiales de una institución ordenados por semana descendente.
     *
     * @param institucionId identificador de la institución.
     * @return lista de materiales de la institución.
     */
    List<MaterialEstudio> listarPorInstitucion(Long institucionId);

    /**
     * Obtiene un material de estudio por su ID para su descarga.
     *
     * @param id identificador del material.
     * @return el material encontrado (con el campo {@code archivo} cargado).
     * @throws RuntimeException si no existe el material.
     */
    MaterialEstudio descargarMaterial(Long id);

    /**
     * Elimina un material de estudio por su ID.
     *
     * @param id identificador del material.
     */
    void eliminarMaterial(Long id);
}
