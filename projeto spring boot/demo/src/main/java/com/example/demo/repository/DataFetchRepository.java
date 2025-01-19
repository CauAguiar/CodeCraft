package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataFetchRepository {
    private final JdbcTemplate jdbcTemplate;

    public DataFetchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double[][] fetchFeatures(int languageId) {
        /*String query = """
            SELECT GROUP_CONCAT(r.peso ORDER BY r.id_resposta) AS pesos_respostas
            FROM person_curso pc
            JOIN respostas_usuario_questionario ruq ON pc.id_person = ruq.id_person
            JOIN respostas_questionario r ON ruq.id_resposta = r.id_resposta
            WHERE pc.id_curso = r.id_curso AND pc.id_curso = ?
            GROUP BY pc.id_person
        """;*/
        String query = """
            SELECT GROUP_CONCAT(r.peso ORDER BY r.id_resposta) AS pesos_respostas
            FROM person_curso pc
            JOIN respostas_usuario_questionario ruq ON pc.id_person = ruq.id_person
            JOIN respostas_questionario r ON ruq.id_resposta = r.id_resposta
            JOIN perguntas_questionario pq ON r.id_pergunta = pq.id_pergunta
            WHERE pc.id_curso = ?
            GROUP BY pc.id_person
        """;


        List<double[]> featuresList = new ArrayList<>();
        jdbcTemplate.query(query, ps -> ps.setInt(1, languageId), rs -> {
            String[] pesos = rs.getString("pesos_respostas").split(",");
            double[] featureRow = Arrays.stream(pesos).mapToDouble(Double::parseDouble).toArray();
            featuresList.add(featureRow);
        });
        return featuresList.toArray(new double[0][0]);
    }

    public double[][] fetchFeaturesUniqueLevel(int languageId, int userId) {
        String query = """
            SELECT 
                GROUP_CONCAT(r.peso ORDER BY r.id_resposta) AS pesos_respostas
            FROM 
                respostas_usuario_questionario ruq
            JOIN 
                respostas_questionario r ON ruq.id_resposta = r.id_resposta
            JOIN 
                perguntas_questionario pq ON ruq.id_pergunta = pq.id_pergunta
            WHERE 
                ruq.id_person = ? 
                AND pq.id_curso = ?
            GROUP BY 
                ruq.id_person, pq.id_curso;
        """;

        List<double[]> featuresList = new ArrayList<>();
        jdbcTemplate.query(query, ps -> {
            ps.setInt(1, userId);
            ps.setInt(2, languageId);
        }, rs -> {
            String[] pesos = rs.getString("pesos_respostas").split(",");
            double[] featureRow = Arrays.stream(pesos).mapToDouble(Double::parseDouble).toArray();
            featuresList.add(featureRow);
        });
        return featuresList.toArray(new double[0][0]);
    }

    public int[] fetchLabels(int languageId) {
        /*String query = """
            SELECT l.nivel
            FROM person_curso l
            JOIN respostas_usuario_questionario ruq ON l.id_person = ruq.id_person
            JOIN respostas_questionario r ON ruq.id_resposta = r.id_resposta
            WHERE l.id_curso = r.id_curso AND l.id_curso = ?
            GROUP BY l.id_usuario, l.nivel
        """;*/

        String query = """
            SELECT l.nivelamento
            FROM person_curso l
            JOIN respostas_usuario_questionario ruq ON l.id_person = ruq.id_person
            JOIN respostas_questionario r ON ruq.id_resposta = r.id_resposta
            JOIN perguntas_questionario pq ON r.id_pergunta = pq.id_pergunta
            WHERE l.id_curso = ?
            GROUP BY l.id_person, l.nivelamento
        """;


        List<Integer> labelsList = new ArrayList<>();
        jdbcTemplate.query(query, ps -> ps.setInt(1, languageId), rs -> {
            String nivel = rs.getString("level");
            int label = switch (nivel) {
                case "Iniciante" -> 0;
                case "Intermediário" -> 1;
                case "Avançado" -> 2;
                default -> throw new IllegalStateException("Unknown level: " + nivel);
            };
            labelsList.add(label);
        });
        return labelsList.stream().mapToInt(i -> i).toArray();
    }
    
}
