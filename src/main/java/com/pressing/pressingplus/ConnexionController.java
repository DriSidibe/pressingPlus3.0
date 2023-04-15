package com.pressing.pressingplus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {
    public TextField identifiant;
    public TextField mot_de_passe;
    public Button btn_connexion;
    public Button btn_creer_compte;
    public Button btn_oublie;


    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Parent root = null;

        if(event.getSource()==btn_creer_compte){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_inscription.fxml")));
        }
        assert root != null;
        Scene scene = new Scene(root, 500, 700);

        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(btn_creer_compte.getScene().getWindow());

        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    public void connexion(ActionEvent actionEvent) {
        boolean isUserExist = false;
        boolean isPasswdCorrect = false;
        String id = "";
        try {
            try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                if (con != null) {
                    Statement statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT identifiant_utilisateur from utilisateur");
                    while (resultSet.next()) {
                        if (Objects.equals(resultSet.getString("identifiant_utilisateur"), identifiant.getText())) {
                            isUserExist = true;
                        }
                    }
                    if (isUserExist) {
                        resultSet = statement.executeQuery("SELECT mdp_utilisateur from utilisateur where identifiant_utilisateur ='" + identifiant.getText()+"'");
                        while (resultSet.next()) {
                            if (Objects.equals(resultSet.getString("mdp_utilisateur"), mot_de_passe.getText())) {
                                isPasswdCorrect = true;
                            }
                        }
                        resultSet = statement.executeQuery("SELECT id_utilisateur from utilisateur where identifiant_utilisateur ='" + identifiant.getText()+"'");
                        while (resultSet.next()) {
                            id = resultSet.getString("id_utilisateur");
                        }
                    }
                    if (isUserExist && isPasswdCorrect) {
                        File file = new File("src/main/resources/files/currentUserId.txt");
                        if (file.exists()) {
                            PrintWriter writer = new PrintWriter("src/main/resources/files/currentUserId.txt", StandardCharsets.UTF_8);
                            writer.println(id);
                            writer.close();
                        }

                        Stage stage;
                        Parent root;

                        stage = (Stage) btn_connexion.getScene().getWindow();
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_acceuil.fxml")));

                        assert root != null;
                        Scene scene = new Scene(root, 1000, 900);
                        stage.setScene(scene);
                        stage.show();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("erreur");
                        alert.setHeaderText("");
                        alert.setContentText("IDENTIFIANT OU MOT DE PASSE INCORRECTE ");

                        alert.showAndWait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
