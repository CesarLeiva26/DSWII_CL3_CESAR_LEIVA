package edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Sevice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
    private final Path imagesFolder = Paths.get("Images");
    private final Path documentosFolder = Paths.get("Documentos");
    private static final double MAX_SIZE_IMAGES_MB = 1.5;
    private static final double MAX_SIZE_DOCUMENTOS_MB = 1.5;

    public void guardar(MultipartFile archivo, Path folder, double maxSize) throws Exception {
        validarExtension(archivo, folder);
        validarTamañoMaximo(archivo, maxSize);
        Files.copy(archivo.getInputStream(), folder.resolve(archivo.getOriginalFilename()));
    }

    public void guardarArchivosImages(List<MultipartFile> imagenes) throws Exception {
        for (MultipartFile imagen : imagenes) {
            guardar(imagen, imagesFolder, MAX_SIZE_IMAGES_MB);
        }
    }

    public void guardarArchivosDocumentos(List<MultipartFile> documentos) throws Exception {
        for (MultipartFile documento : documentos) {
            guardar(documento, documentosFolder, MAX_SIZE_DOCUMENTOS_MB);
        }
    }

    private void validarExtension(MultipartFile archivo, Path folder) throws Exception {
        String extension = obtenerExtension(archivo.getOriginalFilename());
        if (folder.equals(imagesFolder) && !extension.equalsIgnoreCase("png")) {
            throw new Exception("La extensión del archivo no es PNG.");
        } else if (folder.equals(documentosFolder) && !extension.equalsIgnoreCase("xlsx")) {
            throw new Exception("La extensión del archivo no es XLSX.");
        }
    }

    private void validarTamañoMaximo(MultipartFile archivo, double maxSize) throws Exception {
        double fileSizeInMB = convertirBytesAMegabytes(archivo.getSize());
        if (fileSizeInMB > maxSize) {
            throw new Exception("El tamaño del archivo excede el límite permitido.");
        }
    }

    private String obtenerExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    private double convertirBytesAMegabytes(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }
}