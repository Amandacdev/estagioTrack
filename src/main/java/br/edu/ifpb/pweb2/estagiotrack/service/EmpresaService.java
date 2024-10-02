package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.estagiotrack.util.PasswordUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.estagiotrack.util.PasswordUtil;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Page<Empresa> listAll(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findById(Integer id) {
        return empresaRepository.findById(id);
    }

    public Optional<Empresa> findByEmail(String email) {
        return empresaRepository.findByEmail(email);
    }

    public Empresa save(Empresa empresa) {
        empresa.setSenha(PasswordUtil.hashPassword(empresa.getSenha()));
        return empresaRepository.save(empresa);
    }

    public void deleteById(Integer id) {
        empresaRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return empresaRepository.existsByEmail(email);
    }

    public boolean existsByCnpj(String cnpj) {
        return empresaRepository.existsByCnpj(cnpj);
    }

    public Integer findMaxId() {
        return empresaRepository.findMaxId();
    }
}
