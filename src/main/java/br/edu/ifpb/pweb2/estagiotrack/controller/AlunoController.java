package br.edu.ifpb.pweb2.estagiotrack.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
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

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Paginador;
import br.edu.ifpb.pweb2.estagiotrack.service.AlunoService;
import br.edu.ifpb.pweb2.estagiotrack.service.CompetenciasTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Validated
public class AlunoController {

    private final AlunoService alunoService;

    @Autowired
    private CompetenciasTemplateService competenciasTemplateService;

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @GetMapping("/form")
    public ModelAndView showForm(ModelAndView modelAndView) {
        modelAndView.addObject("competenciasTemplate", competenciasTemplateService.findAll());
        modelAndView.addObject("aluno", new Aluno());
        modelAndView.setViewName("alunos/form");
        return modelAndView;
    }

    @GetMapping
    public String getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Aluno> alunosPage = alunoService.listAll(pageable);

        Paginador paginador = new Paginador(
                alunosPage.getNumber(),
                alunosPage.getSize(),
                (int) alunosPage.getTotalElements());

        model.addAttribute("paginador", paginador);
        model.addAttribute("alunos", alunosPage.getContent());

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
        if (aluno.getId() == null) {
            Integer maxId = alunoService.findMaxId();
            System.out.println(maxId);
            aluno.setId(maxId + 1);
        }
        alunoService.salvarAluno(aluno, competencias);

        UserDetails novoUsuario = User.withUsername(aluno.getEmail()).password(aluno.getSenha()).roles("ALUNO").build();
        if (!jdbcUserDetailsManager.userExists(aluno.getEmail())) {
            jdbcUserDetailsManager.createUser(novoUsuario); // Salva o novo usuário no banco de dados
        }
        attr.addFlashAttribute("success", "Estudante cadastrado com sucesso. Faça login para continuar.");

        return "redirect:/auth";
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
