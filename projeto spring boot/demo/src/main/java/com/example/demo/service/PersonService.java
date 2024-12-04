package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // Registrar uma nova pessoa
    public Person registerPerson(Person person) {
        // Salva a pessoa no banco de dados
        return personRepository.save(person);
    }
    
    // Realizar login usando email e senha
    public Optional<Person> loginWithEmail(String email, String password) {
        Optional<Person> person = personRepository.findByEmail(email);
        if (person.isPresent() && person.get().getPassword().equals(password)) {
            return person;
        }
        return Optional.empty();
    }

    // Login via Facebook
    public Optional<Person> loginWithFacebook(String facebookId) {
        return personRepository.findByFacebookId(facebookId);
    }

    // Login via Google
    public Optional<Person> loginWithGoogle(String googleId) {
        return personRepository.findByGoogleId(googleId);
    }

    public String sendConfirmationCode(String telefone) {
        // Gera um código aleatório de 6 dígitos
        String codigo = String.format("%06d", new Random().nextInt(999999));
        
        // Você pode simular o envio do código aqui (em um sistema real, você usaria um serviço SMS)
        System.out.println("Código de confirmação enviado para " + telefone + ": " + codigo);

        // Retorna o código gerado
        return codigo;
    }

    public boolean validateConfirmationCode(String telefone, String codigo) {
        Optional<Person> userOptional = personRepository.findByTelefone(telefone);
        if (userOptional.isPresent()) {
            Person user = userOptional.get();
            return user.getCodigoConfirmacao().equals(codigo);
        }
        return false;
    }

    public Map<String, Boolean> checkIfExists(String email, String telefone) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("email", personRepository.existsByEmail(email));
        response.put("telefone", personRepository.existsByTelefone(telefone));
        return response;
    }

    // Atualizar o perfil de uma pessoa
    public Person updateProfile(Long personId, String secondName, Integer age, String gender) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setSecondName(secondName);
            person.setAge(age);
            person.setGender(gender);
            return personRepository.save(person);
        }
        return null;
    }
}
