package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @RequestMapping("/form")
    public String getForm(Model model) {
        // Test data setup if needed
        List<String> competencias1 = Arrays.asList("C#", "C++", "CSS");
        alunoService.salvarAluno(new Aluno(null, "amanda@mail.com", "amandaCruz", "123", "Amanda Cruz", "Feminino", competencias1), competencias1);

        List<String> competencias2 = Arrays.asList("HTML", "Java", "JavaScript");
        alunoService.salvarAluno(new Aluno(null, "brian@mail.com", "brianRafael", "123", "Brian Rafael", "Masculino", competencias2), competencias2);

        List<String> competencias3 = Arrays.asList("Python", "Ruby", "SQL");
        alunoService.salvarAluno(new Aluno(null, "george@mail.com", "georgeLima", "123", "George Lima", "Masculino", competencias3), competencias3);

        List<String> competencias4 = Arrays.asList("HTML", "CSS", "JavaScript");
        alunoService.salvarAluno(new Aluno(null, "olivia@mail.com", "oliviaOliva", "123", "Olivia Oliva", "Feminino", competencias4), competencias4);

        model.addAttribute("aluno", new Aluno()); // Prepare an empty aluno for the form
        return "alunos/form";
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("alunos", alunoService.listAll());
        return "alunos/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroAluno(@RequestParam List<String> competencias, Aluno aluno, Model model, RedirectAttributes attr) {
        if (!alunoService.validarAluno(aluno)) {
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "alunos/form";
        } else {
            alunoService.salvarAluno(aluno, competencias);
            attr.addFlashAttribute("success", "Aluno cadastrado com sucesso!");
            return "redirect:/alunos";
        }
    }

    public Aluno buscarPorEmail(String email) {
        return alunoService.findByEmail(email).orElse(null);
    }
}
