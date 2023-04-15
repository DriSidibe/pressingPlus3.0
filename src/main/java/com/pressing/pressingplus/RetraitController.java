package com.pressing.pressingplus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RetraitController implements Initializable {

    @FXML
    public TextField code_retrait;
    public Button btn_recherche;
    public Button btn_historie;
    public ImageView btn_deconnexion;
    @FXML
    private Button btn_encour;
    @FXML
    private Button btn_depot;
    @FXML
    private Button btn_retrait;
    @FXML
    private Button btn_caisse;

    Alert a = new Alert(Alert.AlertType.NONE);
    String idUtilisateur = "";


    public void verify(KeyEvent keyEvent) {
        if (keyEvent.getSource() == code_retrait) {
            if (Pattern.compile("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿×÷¡\"]").matcher(keyEvent.getCharacter()).matches()) {
                code_retrait.setText(code_retrait.getText().replaceAll("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿×÷¡\"]", ""));
            }
        }
    }

    public void code_depotBackgroundColorChanger(MouseEvent mouseEvent) {
        code_retrait.setBackground(new Background(new BackgroundFill(Color.web("#FAFDD6"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void code_depotBackgroundColorChanger2(MouseEvent mouseEvent) {
        code_retrait.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }


    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        boolean find = false;

        if(event.getSource()==btn_depot){
            stage = (Stage) btn_depot.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_depot.fxml")));

            assert root != null;
            Scene scene = new Scene(root, 1000, 900);
            stage.setScene(scene);
            stage.show();
        } else if(event.getSource()==btn_caisse){
            stage = (Stage) btn_depot.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_acceuil.fxml")));

            assert root != null;
            Scene scene = new Scene(root, 1000, 900);
            stage.setScene(scene);
            stage.show();
        }else if(event.getSource()==btn_historie){
            stage = (Stage) btn_historie.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scene_historie.fxml")));

            assert root != null;
            Scene scene = new Scene(root, 1000, 900);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==btn_encour){
            stage = (Stage) btn_depot.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scene_encour.fxml")));

            assert root != null;
            Scene scene = new Scene(root, 1000, 900);
            stage.setScene(scene);
            stage.show();
        }else if(event.getSource()==btn_recherche){
            try {
                try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                    if (con != null) {
                        Statement statement = con.createStatement();
                        ResultSet resultSet = statement.executeQuery("select id_depot from depot where id_proprietaire = '" + idUtilisateur + "'");
                        while (resultSet.next()) {
                            if (Objects.equals(resultSet.getString("id_depot"), code_retrait.getText())) {
                                find = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (find) {
                stage = (Stage) btn_depot.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Scene_info.fxml")));

                root = loader.load();

                InfoController infoController = loader.getController();
                infoController.setCode(Integer.parseInt(code_retrait.getText()));
                assert root != null;
                Scene scene = new Scene(root, 1000, 900);
                stage.setScene(scene);
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("verification");
                alert.setHeaderText("erreur");
                alert.setContentText("ce code n'existe pas");

                alert.showAndWait();
            }
        }
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
