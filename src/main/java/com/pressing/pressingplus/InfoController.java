package com.pressing.pressingplus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class InfoController implements Initializable {

    public Label nom_retrait;
    public Label prenom_retrait;
    public Label tel_retrait;
    public TextField nbr_pant_jeans;
    public TextField nbr_pant_tissu;
    public TextField nbr_pant_autre;
    public TextField nbr_chemise;
    public TextField nbr_veste;
    public TextField nbr_chaussure;
    public Label dateDepot_retrait;
    public Label dateRetrait_retrait;
    public TextField nbr_tee_shirt;
    public Label montant;
    public Label divers;
    public Button btnregler_info;
    public Button btnAnnuler_info;
    public Button btn_retour;
    public HBox container;
    public Label type_lavage;
    List<Integer> tab = new ArrayList<Integer>();
    int cod;
    Alert a = new Alert(Alert.AlertType.NONE);
    Calendar calendar = Calendar.getInstance();
    int montant_precedent = 0;
    String idUtilisateur = "";

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource()==btn_retour){
            stage = (Stage) btn_retour.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_retrait.fxml")));
        }

        assert root != null;
        Scene scene = new Scene(root, 1000, 900);
        stage.setScene(scene);
        stage.show();
    }

    public void setCode(int code){
        cod = code;
        try {
            try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                Statement statement = null;
                if (con != null) {
                    statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery("select nbr_vetement from liste_vetement where id_depot = " + code + " and id_proprietaire = '" + idUtilisateur + "'");
                    ResultSet resultSet2 = statement.executeQuery("select type_lavage from depot where id_depot = " + code + " and id_proprietaire = '" + idUtilisateur + "'");
                    while (resultSet.next()) {
                        tab.add(Integer.parseInt(resultSet.getString("nbr_vetement")));
                    }
                    while (resultSet2.next()) {
                        type_lavage.setText(resultSet2.getString("type_lavage"));
                    }
                }
                nbr_pant_jeans.setText(String.valueOf(tab.get(0)));
                nbr_pant_tissu.setText(String.valueOf(tab.get(1)));
                nbr_pant_autre.setText(String.valueOf(tab.get(2)));
                nbr_chemise.setText(String.valueOf(tab.get(3)));
                nbr_veste.setText(String.valueOf(tab.get(4)));
                nbr_chaussure.setText(String.valueOf(tab.get(5)));
                nbr_tee_shirt.setText(String.valueOf(tab.get(6)));

                assert statement != null;
                ResultSet resultSet = statement.executeQuery("select * from depot where id_depot = " + code + " and id_proprietaire = '" + idUtilisateur + "'");
                while (resultSet.next()) {
                    nom_retrait.setText(resultSet.getString("nom_client"));
                    prenom_retrait.setText(resultSet.getString("prenom_client"));
                    tel_retrait.setText(resultSet.getString("tel_client"));
                    dateDepot_retrait.setText(resultSet.getString("date_depot"));
                    dateRetrait_retrait.setText(resultSet.getString("date_retrait"));
                    montant.setText(resultSet.getString("montant"));
                    divers.setText(resultSet.getString("divers"));
                    if (Objects.equals(resultSet.getString("regler"), "true")) {
                        container.setDisable(true);
                        btnregler_info.setDisable(true);
                        btnAnnuler_info.setDisable(true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void regler(ActionEvent actionEvent) {
        try {
            try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                Statement statement = null;
                int max_id = 0;
                if (con != null) {
                    statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery("UPDATE depot SET regler = 'true' WHERE id_depot = " + cod + " and id_proprietaire = '" + idUtilisateur + "'");
                    ResultSet resultSet2 = statement.executeQuery("UPDATE depot SET date_retrait = '" + calendar.get(Calendar.YEAR) + "-" + Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH)+1)) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "' WHERE id_depot = " + cod + " and id_proprietaire = '" + idUtilisateur + "'");
                    container.setDisable(true);
                    btnregler_info.setDisable(true);
                    btnAnnuler_info.setDisable(true);
                    ResultSet resultSet5 = statement.executeQuery("SELECT max(id_caisse) FROM caisse where id_proprietaire = '" + idUtilisateur + "'");
                    while (resultSet5.next()) {
                        max_id = Integer.parseInt(resultSet5.getString("max(id_caisse)"));
                    }
                    ResultSet resultSet3 = statement.executeQuery("SELECT * FROM caisse WHERE id_caisse =" + max_id + " and id_proprietaire = '" + idUtilisateur + "'");
                    while (resultSet3.next()) {
                        montant_precedent = Integer.parseInt(resultSet3.getString("montant"));
                    }
                    ResultSet resultSet4 = statement.executeQuery("UPDATE caisse SET montant = '" + (Integer.parseInt(montant.getText()) + montant_precedent) + "' WHERE id_caisse = " + max_id + " and id_proprietaire = '" + idUtilisateur + "'");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void annuler(ActionEvent actionEvent) {
        try {
            try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                Statement statement = null;
                if (con != null) {
                    statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery("delete from depot WHERE id_depot = " + cod + " and id_proprietaire = '" + idUtilisateur + "'");
                    ResultSet resultSet2 = statement.executeQuery("delete from liste_vetement WHERE id_depot = " + cod + " and id_proprietaire = '" + idUtilisateur + "'");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("confirmation");
                    alert.setHeaderText("");
                    alert.setContentText("depot annuler avec succes!");

                    alert.showAndWait();

                    Stage stage = null;
                    Parent root = null;

                    stage = (Stage) btn_retour.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_retrait.fxml")));

                    assert root != null;
                    Scene scene = new Scene(root, 1000, 900);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}
