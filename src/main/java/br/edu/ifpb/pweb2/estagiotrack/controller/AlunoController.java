package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @RequestMapping("/form")
    public String getForm(Aluno aluno, Model model) {
        // Para facilitar os testes, iniciamos o projeto com alguns alunos sendo
        // inseridos ao acessar cadastrar aluno e voltar.
        // Quando tivermos um banco, mover esses inserts para um trigger rodando na
        // criação da tabela. Atualmente os registros são reescritos cada vez que a
        // página de cadastro é aberta. Isso vai dar problema quando tivermos
        // integridade referencial entre as entidades.
        List<String> competencias1 = Arrays.asList("Java", "Spring Boot", "SQL");
        alunoRepository.save(new Aluno(1, "amanda@mail.com", "amandaCruz", "123",
        "Amanda Cruz", "Feminino", competencias1));
        List<String> competencias2 = Arrays.asList("HTML", "CSS", "JavaScript");
        alunoRepository.save(new Aluno(2, "brian@mail.com", "brianRafael", "123",
        "Brian Rafael", "Masculino", competencias2));
        List<String> competencias3 = Arrays.asList("Python", "Ruby", "C#");
        alunoRepository.save(new Aluno(3, "george@mail.com", "georgeLima", "123",
        "George Lima", "Masculino", competencias3));
        List<String> competencias4 = Arrays.asList("C", "C++", "SQL");
        alunoRepository.save(new Aluno(4, "olivia@mail.com", "oliviaOliva", "123",
        "Olivia Oliva", "Feminino", competencias4));

        model.addAttribute("aluno", aluno);
        return "alunos/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        return "alunos/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroAluno(@RequestParam List<String> competencias, Aluno aluno, Model model,
            RedirectAttributes attr) {
        // Validações
        if (aluno.getNome().isEmpty() || aluno.getEmail().isEmpty() || aluno.getSenha().isEmpty()) {
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "alunos/form";
        } else {
            aluno.setCompetencias(competencias); // Define as competências recebidas
            alunoRepository.save(aluno); // Salva o aluno
            attr.addFlashAttribute("success", "Aluno cadastrado com sucesso!");
            return "redirect:/alunos"; // Redireciona para a lista de alunos
        }
    }

    // Parte do workaround para vincular oferta a empresa dinamicamente.
    public Aluno buscarPorEmail(String email) {
        Aluno aluno = alunoRepository.findByEmail(email);
        if (aluno != null) {
            return aluno;
        } else {
            return null;
        }
    }
}
