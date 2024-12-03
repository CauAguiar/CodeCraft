package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    // Endpoint para registrar uma nova pessoa
    @PostMapping("/register")
    public Person registerPerson(@RequestParam String name, @RequestParam String email,
                                 @RequestParam String password, @RequestParam String telefone) {
                // Envia o código de confirmação
                String codigo = personService.sendConfirmationCode(telefone);

        return personService.registerPerson(name, email, password, telefone, codigo);
    }

    // Endpoint para login com email e senha
    @PostMapping("/login/email")
    public Optional<Person> loginWithEmail(@RequestParam String email, @RequestParam String password) {
        return personService.loginWithEmail(email, password);
    }

    // Endpoint para login com Facebook
    @PostMapping("/login/facebook")
    public Optional<Person> loginWithFacebook(@RequestParam String facebookId) {
        return personService.loginWithFacebook(facebookId);
    }

    // Endpoint para login com Google
    @PostMapping("/login/google")
    public Optional<Person> loginWithGoogle(@RequestParam String googleId) {
        return personService.loginWithGoogle(googleId);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam String telefone, @RequestParam String codigo) {
        boolean isValid = personService.validateConfirmationCode(telefone, codigo);

        if (isValid) {
            return ResponseEntity.ok("Código validado com sucesso");
        } else {
            return ResponseEntity.badRequest().body("Código de confirmação inválido");
        }
    }

    @PostMapping("/checkExists")
    public ResponseEntity<Map<String, Boolean>> checkIfExists(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String telefone = params.get("telefone");
        Map<String, Boolean> response = personService.checkIfExists(email, telefone);
        return ResponseEntity.ok(response);
    }


    // Endpoint para atualizar o perfil de uma pessoa
    @PutMapping("/updateProfile")
    public Person updateProfile(@RequestParam Long personId, @RequestParam(required = false) String secondName,
                                @RequestParam(required = false) Integer age, @RequestParam(required = false) String gender) {
        return personService.updateProfile(personId, secondName, age, gender);
    }
}
