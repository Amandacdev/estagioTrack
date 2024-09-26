package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Aluno> listAll(Pageable pageable) {
        return alunoRepository.findAll(pageable);
    }

    public Optional<Aluno> findById(Integer id) {
        return alunoRepository.findById(id);
    }

    public Optional<Aluno> findByEmail(String email) {
        return alunoRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return alunoRepository.findByEmail(email).isPresent();
    }

    public boolean existsByNomeUsuario(String nomeUsuario) {
        return alunoRepository.findByNomeUsuario(nomeUsuario).isPresent();
    }

    public void deleteAluno(Integer id) {
        alunoRepository.deleteById(id);
    }

    public boolean validarAluno(Aluno aluno) {
        return aluno.getNome() != null && !aluno.getNome().isEmpty()
                && aluno.getEmail() != null && !aluno.getEmail().isEmpty()
                && aluno.getSenha() != null && !aluno.getSenha().isEmpty()
                && aluno.getCompetencias() != null && !aluno.getCompetencias().isEmpty();
    }

    public Integer findMaxId() {
        return alunoRepository.findMaxId();
    }
}