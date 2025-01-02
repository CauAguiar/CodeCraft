package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AtividadeService {

    // @Autowired
    // private QuizRepository quizRepository;

    // @Autowired
    // private ProjetoRepository projetoRepository;

    // @Autowired
    // private ExercicioAbertoRepository exercicioAbertoRepository;

    // @Autowired
    // private VideoRepository videoRepository;

    // public Map<String, List<Object>> processarAtividades(List<Atividade> atividades) {
    //     Map<String, List<Object>> resultado = new HashMap<>();

    //     for (Atividade atividade : atividades) {
    //         System.out.println("Processando atividade: " + atividade.getNome() + " Tipo: " + atividade.getTipo() + " ID Específica: " + atividade.getAtividadeEspecificaId());

    //         switch (atividade.getTipo().toLowerCase()) {
    //             case "quiz" -> {
    //                 Quiz quiz = quizRepository.findById(atividade.getAtividadeEspecificaId()).orElse(null);
    //                 if (quiz != null) {
    //                     resultado.computeIfAbsent("quiz", k -> new ArrayList<>()).add(quiz);
    //                 } else {
    //                     System.out.println("Quiz não encontrado para o ID: " + atividade.getAtividadeEspecificaId());
    //                 }
    //             }

    //             case "projeto" -> {
    //                 Projeto projeto = projetoRepository.findById(atividade.getAtividadeEspecificaId()).orElse(null);
    //                 if (projeto != null) {
    //                     resultado.computeIfAbsent("projeto", k -> new ArrayList<>()).add(projeto);
    //                 } else {
    //                     System.out.println("Projeto não encontrado para o ID: " + atividade.getAtividadeEspecificaId());
    //                 }
    //             }

    //             case "exercicio_aberto" -> {
    //                 ExercicioAberto exercicioAberto = exercicioAbertoRepository.findById(atividade.getAtividadeEspecificaId()).orElse(null);
    //                 if (exercicioAberto != null) {
    //                     resultado.computeIfAbsent("exercicio_aberto", k -> new ArrayList<>()).add(exercicioAberto);
    //                 } else {
    //                     System.out.println("Exercicio Aberto não encontrado para o ID: " + atividade.getAtividadeEspecificaId());
    //                 }
    //             }

    //             case "video" -> {
    //                 Video video = videoRepository.findById(atividade.getAtividadeEspecificaId()).orElse(null);
    //                 if (video != null) {
    //                     resultado.computeIfAbsent("video", k -> new ArrayList<>()).add(video);
    //                 } else {
    //                     System.out.println("Video não encontrado para o ID: " + atividade.getAtividadeEspecificaId());
    //                 }
    //             }

    //             default -> {
    //                 resultado.computeIfAbsent("desconhecido", k -> new ArrayList<>()).add(atividade);
    //             }
    //         }
    //     }

    //     return resultado;
    // }
}
