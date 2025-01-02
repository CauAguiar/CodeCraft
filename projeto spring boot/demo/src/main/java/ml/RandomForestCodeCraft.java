package ml;

// import smile.classification.RandomForest;
// import smile.data.DataFrame;
// import smile.data.formula.Formula;
// import smile.data.vector.IntVector;

// import java.io.*;
// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

public class RandomForestCodeCraft {

    // public static void main(String[] args) throws Exception {
    //     String url = "jdbc:mysql://localhost:3306/codecraft";
    //     String username = "root";
    //     String password = "159753";

    //     try (Connection conn = DriverManager.getConnection(url, username, password)) {
    //         Statement stmt = conn.createStatement();
    //         String query = """
    //                 SELECT
    //                     u.id_usuario,
    //                     u.nivel,
    //                     GROUP_CONCAT(r.peso ORDER BY r.id_resposta) AS pesos_respostas
    //                 FROM
    //                     usuarios u
    //                 JOIN
    //                     respostas_usuario_questionario ruq ON u.id_usuario = ruq.id_usuario
    //                 JOIN
    //                     respostas_questionario r ON ruq.id_resposta = r.id_resposta
    //                 GROUP BY
    //                     u.id_usuario, u.nivel;
    //                 """;

    //         ResultSet rs = stmt.executeQuery(query);

    //         List<double[]> featuresList = new ArrayList<>();
    //         List<Integer> labelsList = new ArrayList<>();

    //         while (rs.next()) {
    //             String[] pesos = rs.getString("pesos_respostas").split(",");
    //             double[] featureRow = new double[pesos.length];
    //             for (int i = 0; i < pesos.length; i++) {
    //                 featureRow[i] = Double.parseDouble(pesos[i]);
    //             }
    //             featuresList.add(featureRow);

    //             String nivel = rs.getString("nivel");
    //             int label = switch (nivel) {
    //                 case "Iniciante" -> 0;
    //                 case "Intermediário" -> 1;
    //                 case "Avançado" -> 2;
    //                 default -> throw new IllegalArgumentException("Nível desconhecido: " + nivel);
    //             };
    //             labelsList.add(label);
    //         }

    //         double[][] features = featuresList.toArray(new double[0][0]);
    //         int[] labels = labelsList.stream().mapToInt(i -> i).toArray();

    //         Criar o DataFrame para treino
    //         String[] featureNames = new String[features[0].length];
    //         for (int i = 0; i < featureNames.length; i++) {
    //             featureNames[i] = "resposta" + (i + 1);
    //         }
    //         DataFrame trainData = DataFrame.of(features, featureNames);
    //         trainData = trainData.merge(IntVector.of("nivel", labels));

    //         Treinar o modelo
    //         Formula formula = Formula.lhs("nivel");
    //         RandomForest model = RandomForest.fit(formula, trainData);

    //         Salvar o modelo treinado em um arquivo
    //         try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("randomForestModel.ser"))) {
    //             out.writeObject(model);
    //         }

    //         System.out.println("Modelo treinado e salvo com sucesso!");

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}