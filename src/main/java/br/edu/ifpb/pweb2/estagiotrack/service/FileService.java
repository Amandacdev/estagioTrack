package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.EstagioRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            document.add(new Paragraph("Termo de Estágio"));
            document.add(new Paragraph("Estudante Selecionado: " + estagio.getAlunoAprovado().getNome()));
            document.add(new Paragraph("Oferta: " + estagio.getOfertaSelecionada().getTituloCargo()));
            document.add(new Paragraph("Data de Início: " + estagio.getDataInicio().toString()));
            document.add(new Paragraph("Data de Término: " + estagio.getDataFim().toString()));
            document.add(new Paragraph("Observações: " + estagio.getObservacoes()));

            document.close();

            return byteArrayOutputStream.toByteArray();
        }

        return null;
    }
}
