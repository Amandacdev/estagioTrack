package br.edu.ifpb.pweb2.estagiotrack.utils.validation;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartException;

public class ComprovanteEnderecoValidator {

    private static final String PDF_CONTENT_TYPE = "application/pdf";

    public static void validatePdf(MultipartFile file) throws MultipartException {
        if (file.isEmpty()) {
            throw new MultipartException("O arquivo é vazio.");
        }
        if (!PDF_CONTENT_TYPE.equals(file.getContentType())) {
            throw new MultipartException("O arquivo deve ser um PDF.");
        }
    }
}
