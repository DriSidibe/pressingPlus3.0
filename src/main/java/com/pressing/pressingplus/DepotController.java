package com.pressing.pressingplus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Pattern;

public class DepotController implements Initializable {

    @FXML
    public TextField nom_depot;
    public TextField prenom_depot;
    public TextField tel_depot;
    public DatePicker dateDepot_depot;
    public DatePicker dateRetrait_depot;
    public Label radioMontant;
    public TextField code_depot;
    public Button btnEnregistrer_depot;
    public Button btn_code_depot;
    public TextField divers_depot;
    public Button btn_tee_shirt_plus;
    public Button btn_tee_shirt_moin;
    public TextField tee_shirt;
    public Button btn_pant_jeans_moin;
    public Button btn_pant_jeans_plus;
    public TextField nbr_pant_jeans;
    public TextField nbr_pant_tissu;
    public Button btn_pant_tissu_plus;
    public Button btn_pant_tissu_moin;
    public TextField nbr_pant_autre;
    public Button btn_pant_autre_moin;
    public Button btn_pant_autre_plus;
    public TextField nbr_chemise;
    public Button btn_chemise_moin;
    public Button btn_chemise_plus;
    public TextField nbr_veste;
    public Button btn_veste_moin;
    public Button btn_veste_plus;
    public TextField nbr_chaussure;
    public Button btn_chaussure_plus;
    public Button btn_chaussure_moin;
    public TextField nbr_tee_shirt;
    public Button btn_historie;
    public VBox items_container;
    public ComboBox type_lavage;
    public TextField prix_pantalon_jeans;
    public TextField prix_pantalon_tissu;
    public TextField prix_autre_pantalon;
    public TextField prix_chemise;
    public TextField prix_veste;
    public TextField prix_chaussure;
    public TextField prix_tee_shirt;
    public HBox veste_cnt;
    public HBox chaussure_cnt;
    public ImageView btn_deconnexion;
    @FXML
    private Button btn_caisse;
    @FXML
    private Button btn_depot;
    @FXML
    private Button btn_retrait;
    @FXML
    private Button btn_encour;

    Alert a = new Alert(Alert.AlertType.NONE);

    public int autreOldValue;
    List<Integer> tab = new ArrayList<Integer>();
    int last_id = 0;
    Calendar calendar = Calendar.getInstance();
    String idUtilisateur = "";



    //methodes

    public void depotBackgroundColorChanger(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == nom_depot) {
            nom_depot.setBackground(new Background(new BackgroundFill(Color.web("#FAFDD6"), CornerRadii.EMPTY, Insets.EMPTY)));
        }else if (mouseEvent.getSource() == tel_depot) {
            tel_depot.setBackground(new Background(new BackgroundFill(Color.web("#FAFDD6"), CornerRadii.EMPTY, Insets.EMPTY)));
        }else if (mouseEvent.getSource() == prenom_depot) {
            prenom_depot.setBackground(new Background(new BackgroundFill(Color.web("#FAFDD6"), CornerRadii.EMPTY, Insets.EMPTY)));
        }

    }

    public void depotBackgroundColorChanger2(MouseEvent mouseEvent){
        if (mouseEvent.getSource() == nom_depot) {
            nom_depot.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }else if (mouseEvent.getSource() == tel_depot) {
            tel_depot.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }else if (mouseEvent.getSource() == prenom_depot) {
            prenom_depot.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if(event.getSource()==btn_retrait){
            stage = (Stage) btn_retrait.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_retrait.fxml")));
        }else if(event.getSource()==btn_caisse){
            stage = (Stage) btn_caisse.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_acceuil.fxml")));
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


    public void diversSelected(KeyEvent mouseEvent) {
        if (Objects.equals(divers_depot.getText(), "")) {
            divers_depot.setText("0");
        }
        if (Pattern.compile("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(mouseEvent.getCharacter()).matches()) {
            divers_depot.setText(divers_depot.getText().replaceAll("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
        }
        radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - autreOldValue + Integer.parseInt(divers_depot.getText())));
        if (Objects.equals(divers_depot.getText(), "")) {
            divers_depot.setText("0");
        }
        autreOldValue = Integer.parseInt(divers_depot.getText());
    }

    public void verify(KeyEvent keyEvent) {
        if (keyEvent.getSource() == nom_depot) {
            if (Pattern.compile("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(keyEvent.getCharacter()).matches()) {
                nom_depot.setText(nom_depot.getText().replaceAll("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
            }
        }else if (keyEvent.getSource() == prenom_depot) {
            if (Pattern.compile("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(keyEvent.getCharacter()).matches()) {
                prenom_depot.setText(prenom_depot.getText().replaceAll("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
            }
        }else if (keyEvent.getSource() == tel_depot) {
            if (Pattern.compile("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(keyEvent.getCharacter()).matches()) {
                tel_depot.setText(tel_depot.getText().replaceAll("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
            }
        }
    }

    public void enregistrer(ActionEvent actionEvent) {
        if (!Objects.equals(nom_depot.getText(), "") && !Objects.equals(prenom_depot.getText(), "") && !Objects.equals(tel_depot.getText(), "") && tel_depot.getText().length() == 10 && dateRetrait_depot.getValue() != null) {
            if (!Objects.equals(radioMontant.getText(), "0")) {
                String requete1 = "INSERT INTO depot (nom_client, prenom_client, tel_client, date_depot, date_retrait, montant, divers, regler, type_lavage, id_proprietaire) VALUES (" + "'" + nom_depot.getText() + "'" + ", " + "'" + prenom_depot.getText() + "'" + ", " + "'" + tel_depot.getText() + "'" + ", " + "'" + calendar.get(Calendar.YEAR) + "-" + Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH)+1)) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "'" + ", " + "'" + dateRetrait_depot.getValue() + "'" + "," + "'" + radioMontant.getText() + "'" + "," + "'" + divers_depot.getText() + "','false','" + type_lavage.getValue() + "','" + idUtilisateur + "')";

                try {
                    try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                        if (con != null) {
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(requete1);
                            while (resultSet.next()) {

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String requete3 = "SELECT * FROM depot where id_proprietaire = '" + idUtilisateur + "'";

                try {
                    try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                        if (con != null) {
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(requete3);
                            while (resultSet.next()) {
                                tab.add(Integer.valueOf(resultSet.getString("id_depot")));
                            }

                            for (int i = 0; i < tab.size(); i++) {
                                if (i == tab.size() - 1) {
                                    last_id = tab.get(i);
                                }
                            }
                            tab.clear();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                        if (con != null) {
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery("INSERT INTO liste_vetement (id_depot, nom_vetement, nbr_vetement, id_proprietaire) VALUES (" + "'" + last_id + "'" + ", " + "'" + "Pantalon jeans" + "'" + ", " + "'" + nbr_pant_jeans.getText() + "','" + idUtilisateur + "'),(" + "'" + last_id + "'" + ", " + "'" + "Pantalon tissu" + "'" + ", " + "'" + nbr_pant_tissu.getText() + "','" + idUtilisateur + "'),(" + "'" + last_id + "'" + ", " + "'" + "Autre pantalon" + "'" + ", " + "'" + nbr_pant_autre.getText() + "','" + idUtilisateur + "'),(" + "'" + last_id + "'" + ", " + "'" + "Chemise" + "'" + ", " + "'" + nbr_chemise.getText() + "','" + idUtilisateur + "'),(" + "'" + last_id + "'" + ", " + "'" + "Tee-shirt" + "'" + ", " + "'" + nbr_tee_shirt.getText() + "','" + idUtilisateur + "'),(" + "'" + last_id + "'" + ", " + "'" + "Chaussure" + "'" + ", " + "'" + nbr_chaussure.getText() + "','" + idUtilisateur + "'),(" + "'" + last_id + "'" + ", " + "'" + "Veste" + "'" + ", " + "'" + nbr_veste.getText() + "','" + idUtilisateur + "')");
                            while (resultSet.next()) {

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("confirmation");
                alert.setHeaderText("");
                alert.setContentText("enregistrer avec succes avec le code " + last_id);

                alert.showAndWait();

                clearFileds();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("warnning");
                alert.setHeaderText("");
                alert.setContentText("IMPOSSIBLE D'ENREGISTRER UN MONTANT NUL !");

                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("warnning");
            alert.setHeaderText("");
            alert.setContentText("VEUILLEZ REMPLIR CORECTEMENT TOUS LES CHAMPS CONCERNANTS LE CLIENT ET LES DATES!");

            alert.showAndWait();
        }
    }

    private void clearFileds() {
        nom_depot.setText("");
        prenom_depot.setText("");
        tel_depot.setText("");
        radioMontant.setText("");
        divers_depot.setText("");
        nbr_chaussure.setText("");
        nbr_chemise.setText("");
        nbr_pant_autre.setText("");
        nbr_pant_tissu.setText("");
        nbr_veste.setText("");
        nbr_pant_jeans.setText("");
        nbr_tee_shirt.setText("");
    }

    public void supprime(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn_pant_jeans_moin) {
            if (Integer.parseInt(nbr_pant_jeans.getText()) > 0) {
                nbr_pant_jeans.setText(String.valueOf(Integer.parseInt(nbr_pant_jeans.getText()) - 1));
                radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - 500));
            }
        }else if (actionEvent.getSource() == btn_pant_tissu_moin) {
            if (Integer.parseInt(nbr_pant_tissu.getText()) > 0) {
                nbr_pant_tissu.setText(String.valueOf(Integer.parseInt(nbr_pant_tissu.getText()) - 1));
                radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - 300));
            }
        }else if (actionEvent.getSource() == btn_chaussure_moin) {
            if (Integer.parseInt(nbr_chaussure.getText()) > 0) {
                nbr_chaussure.setText(String.valueOf(Integer.parseInt(nbr_chaussure.getText()) - 1));
                radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - 500));
            }
        }else if (actionEvent.getSource() == btn_pant_autre_moin) {
            if (Integer.parseInt(nbr_pant_autre.getText()) > 0) {
                nbr_pant_autre.setText(String.valueOf(Integer.parseInt(nbr_pant_autre.getText()) - 1));
                radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - 250));
            }
        }else if (actionEvent.getSource() == btn_veste_moin) {
            if (Integer.parseInt(nbr_veste.getText()) > 0) {
                nbr_veste.setText(String.valueOf(Integer.parseInt(nbr_veste.getText()) - 1));
                radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - 1000));
            }
        }else if (actionEvent.getSource() == btn_chemise_moin) {
            if (Integer.parseInt(nbr_chemise.getText()) > 0) {
                nbr_chemise.setText(String.valueOf(Integer.parseInt(nbr_chemise.getText()) - 1));
                radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - 200));
            }
        }else if (actionEvent.getSource() == btn_tee_shirt_moin) {
            if (Integer.parseInt(nbr_tee_shirt.getText()) > 0) {
                nbr_tee_shirt.setText(String.valueOf(Integer.parseInt(nbr_tee_shirt.getText()) - 1));
                radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) - 150));
            }
        }
    }

    public void add(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn_pant_jeans_plus) {
            nbr_pant_jeans.setText(String.valueOf(Integer.parseInt(nbr_pant_jeans.getText()) + 1));
            radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) + Integer.parseInt(prix_pantalon_jeans.getText())));
        }else if (actionEvent.getSource() == btn_pant_tissu_plus) {
            nbr_pant_tissu.setText(String.valueOf(Integer.parseInt(nbr_pant_tissu.getText()) + 1));
            radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) + Integer.parseInt(prix_pantalon_tissu.getText())));
        }else if (actionEvent.getSource() == btn_chaussure_plus) {
            nbr_chaussure.setText(String.valueOf(Integer.parseInt(nbr_chaussure.getText()) + 1));
            radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) + Integer.parseInt(prix_chaussure.getText())));
        }else if (actionEvent.getSource() == btn_pant_autre_plus) {
            nbr_pant_autre.setText(String.valueOf(Integer.parseInt(nbr_pant_autre.getText()) + 1));
            radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) + Integer.parseInt(prix_autre_pantalon.getText())));
        }else if (actionEvent.getSource() == btn_veste_plus) {
            nbr_veste.setText(String.valueOf(Integer.parseInt(nbr_veste.getText()) + 1));
            radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) + Integer.parseInt(prix_veste.getText())));
        }else if (actionEvent.getSource() == btn_chemise_plus) {
            nbr_chemise.setText(String.valueOf(Integer.parseInt(nbr_chemise.getText()) + 1));
            radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) + Integer.parseInt(prix_chemise.getText())));
        }else if (actionEvent.getSource() == btn_tee_shirt_plus) {
            nbr_tee_shirt.setText(String.valueOf(Integer.parseInt(nbr_tee_shirt.getText()) + 1));
            radioMontant.setText(String.valueOf(Integer.parseInt(radioMontant.getText()) + Integer.parseInt(prix_tee_shirt.getText())));
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

        ObservableList<String> liste = FXCollections.observableArrayList();
        liste.addAll("lavage simple à sec","lavage complet à sec","lavage simple à l eau","lavage complet à l eau","simple repassage");
        type_lavage.setItems(liste);
        type_lavage.setValue("lavage complet à sec");
    }
    
    public void setWactchablesPrice(int i1,int i2, int i3, int i4, int i5, int i6, int i7){
        prix_tee_shirt.setText(String.valueOf(i1));
        prix_chemise.setText(String.valueOf(i2));
        prix_chaussure.setText(String.valueOf(i3));
        prix_pantalon_jeans.setText(String.valueOf(i4));
        prix_autre_pantalon.setText(String.valueOf(i5));
        prix_veste.setText(String.valueOf(i6));
        prix_pantalon_tissu.setText(String.valueOf(i7));
        chaussure_cnt.setDisable(false);
        veste_cnt.setDisable(false);
    }

    public void setNonWactchablesPrice(int i1,int i2, int i4, int i5, int i6, int i7){
        prix_tee_shirt.setText(String.valueOf(i1));
        prix_chemise.setText(String.valueOf(i2));
        prix_pantalon_jeans.setText(String.valueOf(i4));
        prix_autre_pantalon.setText(String.valueOf(i5));
        prix_veste.setText(String.valueOf(i6));
        prix_pantalon_tissu.setText(String.valueOf(i7));
        chaussure_cnt.setDisable(true);
        prix_chaussure.setText("0");
        nbr_chaussure.setText("0");
        veste_cnt.setDisable(false);
    }

    public void veste(int i1,int i2, int i4, int i5, int i7){
        prix_tee_shirt.setText(String.valueOf(i1));
        prix_chemise.setText(String.valueOf(i2));
        prix_pantalon_jeans.setText(String.valueOf(i4));
        prix_autre_pantalon.setText(String.valueOf(i5));
        prix_pantalon_tissu.setText(String.valueOf(i7));
        veste_cnt.setDisable(true);
        prix_veste.setText("0");
        nbr_veste.setText("0");
        chaussure_cnt.setDisable(false);
    }

    public void changePrices(ActionEvent actionEvent) {
        if (type_lavage.getValue() == "lavage simple à sec") {
            setWactchablesPrice(150,200,500,500,250,1000,300);
        }else if (type_lavage.getValue() == "lavage simple à l eau") {
            veste(200,250,700,600,400);
        }else if (type_lavage.getValue() == "lavage complet à sec") {
            setWactchablesPrice(250,350,700,700,350,1500,500);
        }else if (type_lavage.getValue() == "lavage complet à l eau") {
            veste(300,350,900,700,500);
        }else if (type_lavage.getValue() == "simple repassage") {
            setNonWactchablesPrice(100,150,500,250,1000,300);
        }
    }

    public void cursorAtTheEnd(MouseEvent mouseEvent) {
        
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