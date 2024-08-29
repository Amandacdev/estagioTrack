package br.edu.ifpb.pweb2.estagiotrack.controller;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.CompetenciaTemplate;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.CompetenciasTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Validated
public class AlunoController {

    private final AlunoService alunoService;

    @Autowired
    private CompetenciasTemplateService competenciasTemplateService;

    @GetMapping("/form")
    public ModelAndView showForm(ModelAndView modelAndView) {
        modelAndView.addObject("competenciasTemplate",  competenciasTemplateService.findAll());
        modelAndView.addObject("aluno", new Aluno());
        modelAndView.setViewName("alunos/form");
        return modelAndView;
    }

    @RequestMapping()
    public String getList(Model model) {
        model.addAttribute("alunos", alunoService.listAll());
        return "alunos/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String cadastroAluno(@RequestParam(required = false, defaultValue = "") List<String> competencias,
                                @Valid Aluno aluno,
                                Model model,
                                RedirectAttributes attr) {
        if (!alunoService.validarAluno(aluno)) {
            model.addAttribute("alert", "Por favor preencha todos os campos corretamente.");
            return "alunos/form";
        }

        if (alunoService.existsByEmail(aluno.getEmail()) || alunoService.existsByNomeUsuario(aluno.getNomeUsuario())) {
            model.addAttribute("alert", "Email ou nome de usuário já existente.");
            return "alunos/form";
        }

        alunoService.salvarAluno(aluno, competencias);
        attr.addFlashAttribute("success", "Aluno cadastrado com sucesso!");
        return "redirect:/alunos/detalhes/" + aluno.getId();
    }

    public Aluno buscarPorEmail(String email) {
        return alunoService.findByEmail(email).orElse(null);
    }

    @RequestMapping("/detalhes/{id}")
    public String getDetalhesAluno(@PathVariable("id") Integer id, Model model) {
        Optional<Aluno> aluno = alunoService.findById(id);
        if (aluno.isPresent()) {
            model.addAttribute("aluno", aluno.get());
            return "alunos/detalhes";
        } else {
            model.addAttribute("alert", "Estudante não encontrado");
            return "redirect:/alunos";
        }
    }
}
