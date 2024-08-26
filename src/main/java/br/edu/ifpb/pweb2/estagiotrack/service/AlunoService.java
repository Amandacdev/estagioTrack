package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public void salvarAluno(Aluno aluno, List<String> competencias) {
        aluno.setCompetencias(competencias);
        alunoRepository.save(aluno);
    }

    public List<Aluno> listAll() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> findById(Integer id) {
        return alunoRepository.findById(id);
    }

    public Optional<Aluno> findByEmail(String email) {
        return alunoRepository.findByEmail(email);
    }

    public void deleteAluno(Integer id) {
        alunoRepository.deleteById(id);
    }
}

