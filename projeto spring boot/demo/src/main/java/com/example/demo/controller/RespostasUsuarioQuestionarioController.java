package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.NivelamentoResponse;
import com.example.demo.model.RespostasNivelamentoRequest;
import com.example.demo.model.RespostasUsuarioQuestionario;
import com.example.demo.repository.DataFetchRepository;
import com.example.demo.service.RandomForestService;
import com.example.demo.service.RespostasUsuarioQuestionarioService;

@RestController
@RequestMapping("/api/respostas-usuario-questionario")
public class RespostasUsuarioQuestionarioController {

    @Autowired
    private RespostasUsuarioQuestionarioService respostasUsuarioQuestionarioService;

    @Autowired
    private RandomForestService randomForestService;

    @Autowired
    private DataFetchRepository dataFetchRepository;

    @GetMapping("/getByPersonId")
    public ResponseEntity<List<RespostasUsuarioQuestionario>> getRespostasByPersonId(
            @RequestParam(name = "personId") Long personId) {
        System.out.println("Receive person id: " + personId);
        List<RespostasUsuarioQuestionario> respostas = respostasUsuarioQuestionarioService
                .getRespostasByPersonId(personId);
        return ResponseEntity.ok(respostas);
    }

    @PostMapping("/enviarRespostasNivelamento")
    public ResponseEntity<NivelamentoResponse> addResposta(@RequestBody RespostasNivelamentoRequest request) {
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
        double[][] features = dataFetchRepository.fetchFeaturesUniqueLevel(cursoId.intValue(), respostasList.get(0).getIdPerson().intValue());
    
        
        int[] predictionResult = randomForestService.predict(features, cursoId);

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

        // Create the response object
        NivelamentoResponse nivelamentoResponse = new NivelamentoResponse(nivelamento);

        //return ResponseEntity.ok(nivelamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nivelamentoResponse);
    }
}
