package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import br.edu.ifpb.pweb2.estagiotrack.service.FileService;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/comprovante/{empresaId}")
    public String uploadComprovante(@PathVariable Integer empresaId,
            @RequestParam("file") MultipartFile file) {
        try {
            fileService.uploadComprovante(empresaId, file);
            return "redirect:/empresas/detalhes/" + empresaId + "?success=Comprovante enviado com sucesso";
        } catch (IOException e) {
            return "redirect:/empresas/detalhes/" + empresaId + "?error=Erro ao enviar comprovante";
        }
    }

    @GetMapping("/download/comprovante/{empresaId}")
    public ResponseEntity<ByteArrayResource> downloadComprovante(@PathVariable Integer empresaId) {
        byte[] data = fileService.downloadComprovante(empresaId);
        if (data != null) {
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=comprovante_endereco.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download/termo/{estagioId}")
    public ResponseEntity<ByteArrayResource> downloadTermoDeEstagio(@PathVariable Integer estagioId) {
        try {
            byte[] data = fileService.gerarTermoDeEstagio(estagioId);
            if (data != null) {
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=termo_estagio.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DocumentException | IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
