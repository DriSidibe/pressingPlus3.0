package com.pressing.pressingplus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class HistorieController implements Initializable {
    public Button btnEncour_encour;
    public Button btn_caisse;
    public Button btn_depot;
    public Button btn_retrait;
    public Button btn_encour;
    public VBox container_encour;
    public GridPane grid_encour;
    public ImageView btn_deconnexion;
    String idUtilisateur = "";

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btn_retrait) {
            stage = (Stage) btn_retrait.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_retrait.fxml")));
        } else if (event.getSource() == btn_caisse) {
            stage = (Stage) btn_caisse.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_acceuil.fxml")));
        } else if (event.getSource() == btn_depot) {
            stage = (Stage) btn_depot.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_depot.fxml")));
        }
        else if (event.getSource() == btn_encour) {
            stage = (Stage) btn_encour.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scene_encour.fxml")));
        }

        assert root != null;
        Scene scene = new Scene(root, 1000, 900);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File doc = new File("src/main/resources/files/currentUserId.txt");
        Scanner obj;
        try {
            obj = new Scanner(doc);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        idUtilisateur = obj.nextLine();

        int line = 0;
        try {
            try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                Statement statement = null;
                if (con != null) {
                    statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM depot WHERE regler = 'true' and id_proprietaire = '" + idUtilisateur + "'");
                    while (resultSet.next()) {
                        grid_encour.add(new Label(resultSet.getString("id_depot")), 0, line);
                        grid_encour.add(new Label(resultSet.getString("nom_client")), 1, line);
                        grid_encour.add(new Label(resultSet.getString("prenom_client")), 2, line);
                        grid_encour.add(new Label(resultSet.getString("tel_client")), 3, line);
                        grid_encour.add(new Label(resultSet.getString("date_depot")), 4, line);
                        grid_encour.add(new Label(resultSet.getString("date_retrait")), 5, line);
                        grid_encour.add(new Label(resultSet.getString("montant")), 6, line);
                        grid_encour.add(new Label("reglé"), 7, line);
                        line++;
                    }
                    for (Node l:grid_encour.getChildren()
                    ) {
                        l.setScaleY(1.5);
                    }
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
