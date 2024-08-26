package br.edu.ifpb.pweb2.estagiotrack.validation;

import br.edu.ifpb.pweb2.estagiotrack.repository.AlunoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueConstraintValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        boolean exists = alunoRepository.findBynomeUsuario(value).isPresent() || alunoRepository.findByEmail(value).isPresent();

        return !exists;
    }
}