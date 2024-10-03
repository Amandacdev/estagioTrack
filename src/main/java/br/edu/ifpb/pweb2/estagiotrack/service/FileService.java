package br.edu.ifpb.pweb2.estagiotrack.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.model.Estagio;
import br.edu.ifpb.pweb2.estagiotrack.model.ResultadoEstagio;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.estagiotrack.repository.EstagioRepository;

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

        ResultadoEstagio resultadoEstagio = estagioRepository.findDadosEstagioPorId(estagioId);
        if (resultadoEstagio == null) {
            throw new IllegalArgumentException("Estágio não encontrado para o ID: " + estagioId);
        }

        if (estagioOptional.isPresent()) {
            Estagio estagio = estagioOptional.get();

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                Document document = new Document();
                PdfWriter.getInstance(document, byteArrayOutputStream);
                document.open();

                Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
                Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
                Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);

                // Carregar a imagem do cabeçalho
                try {
                    Image headerImage = Image.getInstance("src/main/resources/assets/ifpb.png");
                    headerImage.setAlignment(Element.ALIGN_CENTER);
                    headerImage.scaleToFit(500, 150);
                    document.add(headerImage);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem do cabeçalho: " + e.getMessage());
                }

                document.add(new Paragraph("\n"));

                // Adiciona título e conteúdo
                Paragraph title = new Paragraph("DECLARAÇÃO", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("\n"));

                // Obtém as informações necessárias a partir do resultado do estágio
                String alunoNome = resultadoEstagio.getAlunoNome();
                String ofertanteRazaoSocial = resultadoEstagio.getOfertanteRazaoSocial();
                String ofertanteCnpj = resultadoEstagio.getOfertanteCnpj();
                String dataInicio = estagio.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String dataFim = estagio.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                String content = String.format(
                        "Declaramos para os fins que se fizerem necessários, e por nos haver sido solicitado, " +
                                "que %s, foi estagiário na empresa %s - CNPJ %s, iniciado em %s, com término em %s.",
                        alunoNome,
                        ofertanteRazaoSocial.toUpperCase(),
                        ofertanteCnpj,
                        dataInicio,
                        dataFim
                );

                Paragraph paragraph = new Paragraph();
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                paragraph.add(new Chunk(content, normalFont));
                document.add(paragraph);

                document.add(new Paragraph("\n"));

                String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR")));
                Paragraph footer = new Paragraph("João Pessoa (PB), " + date + ".", normalFont);
                footer.setAlignment(Element.ALIGN_RIGHT);
                document.add(footer);

                document.close();
                return byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                throw new IOException("Erro ao gerar o termo de estágio: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("Estágio não encontrado para o ID: " + estagioId);
        }
    }

}
