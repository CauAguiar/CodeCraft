package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.RespostasUsuarioQuestionario;
import com.example.demo.repository.RespostasUsuarioQuestionarioRepository;

@Service
public class RespostasUsuarioQuestionarioService {

    @Autowired
    private RespostasUsuarioQuestionarioRepository respostasUsuarioQuestionarioRepository;

    public List<RespostasUsuarioQuestionario> getRespostasByPersonId(Long personId) {
        if(personId == null) {
            throw new IllegalArgumentException("O id da pessoa não pode ser nulo");
        }
        return respostasUsuarioQuestionarioRepository.findByIdPerson(personId);
    }

    public void insertResposta(Long personId, Long perguntaId, Long respostaId) {
        respostasUsuarioQuestionarioRepository.insertResposta(personId, perguntaId, respostaId);
    }
}
