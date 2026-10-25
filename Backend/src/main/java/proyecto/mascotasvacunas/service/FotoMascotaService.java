package proyecto.mascotasvacunas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FotoMascotaService {

    @Value("${app.upload.dir:uploads/mascotas}")
    private String directorioFotos;

    public String guardarFoto(MultipartFile archivo) throws IOException {

        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException(
                    "No se ha seleccionado ninguna fotografía");
        }

        String tipoContenido = archivo.getContentType();

        if (tipoContenido == null ||
                !tipoContenido.startsWith("image/")) {
            throw new IllegalArgumentException(
                    "El archivo debe ser una imagen");
        }

        Path directorio = Paths.get(directorioFotos)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(directorio);

        String nombreArchivo = UUID.randomUUID() + "."
                + obtenerExtension(tipoContenido);

        Path destino = directorio.resolve(nombreArchivo).normalize();

        if (!destino.startsWith(directorio)) {
            throw new IllegalArgumentException(
                    "Ruta de archivo no válida");
        }

        Files.copy(
                archivo.getInputStream(),
                destino,
                StandardCopyOption.REPLACE_EXISTING
        );

        return nombreArchivo;
    }

    private String obtenerExtension(String tipoContenido) {

        switch (tipoContenido.toLowerCase()) {
            case "image/jpeg":
                return "jpg";
            case "image/png":
                return "png";
            case "image/webp":
                return "webp";
            case "image/gif":
                return "gif";
            default:
                throw new IllegalArgumentException(
                        "Formato de imagen no admitido");
        }
    }

    // Elimina una fotografía guardada en el directorio de mascotas.
    public void eliminarFoto(String nombreArchivo) throws IOException {
        if (nombreArchivo == null || nombreArchivo.isBlank()) {
            return;
        }

        Path directorio = Paths.get(directorioFotos)
                .toAbsolutePath()
                .normalize();

        Path archivo = directorio.resolve(nombreArchivo)
                .normalize();

        // Comprueba que el archivo está dentro del directorio permitido.
        if (!archivo.startsWith(directorio)) {
            throw new IllegalArgumentException(
                    "Ruta de archivo no válida");
        }

        Files.deleteIfExists(archivo);
    }
}
