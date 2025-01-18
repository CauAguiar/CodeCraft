package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RespostasNivelamentoRequest;
import com.example.demo.model.RespostasUsuarioQuestionario;
import com.example.demo.service.RandomForestService;
import com.example.demo.service.RespostasUsuarioQuestionarioService;

@RestController
@RequestMapping("/api/respostas-usuario-questionario")
public class RespostasUsuarioQuestionarioController {

    @Autowired
    private RespostasUsuarioQuestionarioService respostasUsuarioQuestionarioService;

    @Autowired
    private RandomForestService randomForestService;

    @GetMapping("/getByPersonId")
    public ResponseEntity<List<RespostasUsuarioQuestionario>> getRespostasByPersonId(
            @RequestParam(name = "personId") Long personId) {
        System.out.println("Receive person id: " + personId);
        List<RespostasUsuarioQuestionario> respostas = respostasUsuarioQuestionarioService
                .getRespostasByPersonId(personId);
        return ResponseEntity.ok(respostas);
    }

    @PostMapping("/enviarRespostasNivelamento")
    public ResponseEntity<String> addResposta(@RequestBody RespostasNivelamentoRequest request) {
        List<RespostasUsuarioQuestionario> respostasList = request.getRespostas();
        Long cursoId = request.getCursoId();

        // Insert each answer into the database
        respostasList.forEach(resposta -> {
            respostasUsuarioQuestionarioService.insertResposta(
                    resposta.getIdPerson(),
                    resposta.getIdPergunta(),
                    resposta.getIdResposta());
        });

        // Extract features for prediction
        double[][] features = respostasList.stream()
                .map(resposta -> new double[] { resposta.getIdResposta() })
                .toArray(double[][]::new);
        
        int[] predictionResult = randomForestService.predict(features);

        // Map the prediction result to the course ID
        String nivelamento;
        switch(predictionResult[0]) {
            case 0 -> nivelamento = "Básico";
            case 1 -> nivelamento = "Intermediário";
            case 2 -> nivelamento = "Avançado";
            default -> nivelamento = "Indefinido";
        }

        //Save the prediction result to the database
        respostasUsuarioQuestionarioService.insertNivelamento(request.getRespostas().get(0).getIdPerson(), cursoId, nivelamento);

        return ResponseEntity.ok(nivelamento);
    }
}
