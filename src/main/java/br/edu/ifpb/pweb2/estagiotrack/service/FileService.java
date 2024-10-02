package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.EstagioRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EstagioRepository estagioRepository;

    public void uploadComprovante(Integer empresaId, MultipartFile file) throws IOException {
        Optional<Empresa> empresaOptional = empresaRepository.findById(empresaId);
        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            empresa.setComprovanteEndereco(file.getBytes());
            empresaRepository.save(empresa);
        }
    }

    public byte[] downloadComprovante(Integer empresaId) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(empresaId);
        return empresaOptional.map(Empresa::getComprovanteEndereco).orElse(null);
    }

    public byte[] gerarTermoDeEstagio(Integer estagioId) throws DocumentException, IOException {
        Optional<Estagio> estagioOptional = estagioRepository.findById(estagioId);
        if (estagioOptional.isPresent()) {
            Estagio estagio = estagioOptional.get();

            // Create output stream and document
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);

            Image headerImage = Image.getInstance("assets/logo-ifpb-com-borda.png");
            headerImage.setAlignment(Element.ALIGN_CENTER);
            headerImage.scaleToFit(500, 150);
            document.add(headerImage);


            document.add(new Paragraph("\n"));


            Paragraph title = new Paragraph("DECLARAÇÃO", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            String content = String.format(
                    "Declaramos para os fins que se fizerem necessários, e por nos haver sido solicitado, " +
                            "que %s, matrícula %s, foi estagiário na empresa %s - %s, iniciado em %s, com término em %s.",
                    estagio.getAlunoAprovado().getNome(),
                    estagio.getOfertaSelecionada().getOfertante().getRazaoSocial(),
                    estagio.getOfertaSelecionada().getOfertante().getCnpj(),
                    estagio.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    estagio.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );

            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph.add(new Chunk(content, normalFont));
            document.add(paragraph);


            document.add(new Paragraph("\n"));


            String date = "Joao Pessoa (PB), " + DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR")) + ".";
            Paragraph footer = new Paragraph(date, normalFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);



            document.close();
            return byteArrayOutputStream.toByteArray();
        }

        return null;
    }

}
