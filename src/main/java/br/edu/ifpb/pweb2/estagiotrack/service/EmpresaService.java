package br.edu.ifpb.pweb2.estagiotrack.service;

import br.edu.ifpb.pweb2.estagiotrack.model.Empresa;
import br.edu.ifpb.pweb2.estagiotrack.repository.EmpresaRepository;
import br.edu.ifpb.pweb2.estagiotrack.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findById(Integer id) {
        return empresaRepository.findById(id);
    }

    public Optional<Empresa> findByEmail(String email) {
        return empresaRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return empresaRepository.findByEmail(email).isPresent();
    }

    public boolean existsByCnpj(String cnpj) {
        return empresaRepository.findByCnpj(cnpj).isPresent();
    }

    public Empresa save(Empresa empresa) {
        empresa.setSenha(PasswordUtil.hashPassword(empresa.getSenha()));
        return empresaRepository.save(empresa);
    }

    public void deleteById(Integer id) {
        empresaRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return empresaRepository.existsById(id);
    }

    public Integer findMaxId() {
        return empresaRepository.findMaxId();
    }
}
