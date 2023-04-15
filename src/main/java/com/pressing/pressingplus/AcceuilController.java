package com.pressing.pressingplus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class AcceuilController implements Initializable {

    public Label solde_actuel;
    public Button btn_historie;
    public LineChart graphique;
    public CategoryAxis x_axis;
    public NumberAxis y_axis;
    public Button btn_decharger;
    public ImageView btn_deconnexion;
    @FXML
    private Button btn_depot;
    @FXML
    private Button btn_retrait;
    @FXML
    private Button btn_encour;

    String date;
    Calendar c = Calendar.getInstance();


    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource()==btn_depot){
            stage = (Stage) btn_depot.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_depot.fxml")));
        } else if(event.getSource()==btn_retrait){
            stage = (Stage) btn_retrait.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_retrait.fxml")));
        }else if(event.getSource()==btn_encour){
            stage = (Stage) btn_encour.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scene_encour.fxml")));
        }else if(event.getSource()==btn_historie){
            stage = (Stage) btn_historie.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scene_historie.fxml")));
        }
        assert root != null;
        Scene scene = new Scene(root, 1000, 900);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void decharger() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("decharge");
        alert.setHeaderText("Etes-vous sur de vouloir dercharger la caisse ?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            File file = new File("src/main/resources/files/currentUserId.txt");
            if (file.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine()) != null)
                {
                    sb.append(line);
                }
                fr.close();
                try(Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")){
                    Statement statement;
                    int mont = 0;
                    if (con != null) {
                        statement = con.createStatement();
                        mont = Integer.parseInt(solde_actuel.getText());
                        ResultSet resultSet3 = statement.executeQuery("INSERT INTO historie (id_proprietaire, montant, date_decharge) VALUES (" + sb + ", " + mont + ", '" + c.get(Calendar.YEAR) + "-" + Integer.parseInt(String.valueOf(c.get(Calendar.MONTH)+1)) + "-" + c.get(Calendar.DAY_OF_MONTH) + "')");
                        while (resultSet3.next()) {

                        }
                        ResultSet resultSet5 = statement.executeQuery("SELECT max(id_caisse) FROM caisse where id_proprietaire = '" + sb + "'");
                        int max_id = 0;
                        while (resultSet5.next()) {
                            max_id = Integer.parseInt(resultSet5.getString("max(id_caisse)"));
                        }
                        ResultSet resultSet4 = statement.executeQuery("UPDATE caisse SET montant = 0 WHERE id_caisse = " + max_id + " and id_proprietaire = '" + sb + "'");
                        while (resultSet4.next()) {

                        }
                        solde_actuel.setText("0");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("le fichier n'a pas été trouvé");
            }
        }
    }

        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String idUtilisateur;

        File doc = new File("src/main/resources/files/currentUserId.txt");
        Scanner obj;
        try {
            obj = new Scanner(doc);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        idUtilisateur = obj.nextLine();

        XYChart.Series series = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        x_axis.setLabel("mois");
        y_axis.setLabel("revenu");
        ArrayList<String> tab = new ArrayList<String>();
        float moyenne = 0;
        int nbr = 0;
        try {
            try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                Statement statement = null;
                if (con != null) {
                    statement = con.createStatement();
                    ResultSet resultSet3 = statement.executeQuery("SELECT max(id_caisse) FROM caisse");
                    while (resultSet3.next()) {

                    }
                    ResultSet resultSet = statement.executeQuery("SELECT montant FROM caisse WHERE id_proprietaire = " + idUtilisateur + "");
                    ResultSet resultSet2 = statement.executeQuery("SELECT * FROM historie WHERE id_proprietaire = " + idUtilisateur + "");
                    while (resultSet.next()) {
                        solde_actuel.setText(resultSet.getString("montant"));
                    }
                    while (resultSet2.next()) {
                        date = resultSet2.getString("date_decharge");
                        moyenne += Integer.parseInt(resultSet2.getString("montant"));
                        series.getData().add(new XYChart.Data(date, Integer.parseInt(resultSet2.getString("montant"))));
                        tab.add(date);
                        nbr += 1;
                    }
                    moyenne /= nbr;
                    for (String m:tab
                         ) {
                        series2.getData().add(new XYChart.Data(m, moyenne));
                    }
                    series2.setName("moyenne");
                    graphique.getData().add(series);
                    graphique.getData().add(series2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deconnexion(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("deconnexion");
        alert.setHeaderText("Etes-vous sur de vouloir vous deconnecter ?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            Stage stage;
            Parent root;

            stage = (Stage) btn_deconnexion.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_connexion.fxml")));

            assert root != null;
            Scene scene = new Scene(root, 1000, 900);
            stage.setScene(scene);
            stage.show();
        }
    }
}
