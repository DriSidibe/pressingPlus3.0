package com.pressing.pressingplus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

public class InscriptionController {
    public Button btn_retour;
    public VBox btn_envoi_inscription;
    public TextField nom;
    public TextField prenom;
    public TextField email;
    public TextField tel;
    public TextField nom_pressing;
    public TextField mdp;
    public TextField identifiant;


    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;

        if(event.getSource()==btn_retour){
            stage = (Stage) btn_retour.getScene().getWindow();
            stage.close();
        }
    }

    public void verify(KeyEvent keyEvent) {
        if (keyEvent.getSource() == nom) {
            if (Pattern.compile("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(keyEvent.getCharacter()).matches()) {
                nom.setText(nom.getText().replaceAll("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
            }
        }else if (keyEvent.getSource() == prenom) {
            if (Pattern.compile("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(keyEvent.getCharacter()).matches()) {
                prenom.setText(prenom.getText().replaceAll("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
            }
        }else if (keyEvent.getSource() == tel) {
            if (Pattern.compile("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(keyEvent.getCharacter()).matches()) {
                tel.setText(tel.getText().replaceAll("[a-zA-Zé&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
            }
        }else if (keyEvent.getSource() == nom_pressing) {
            if (Pattern.compile("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]").matcher(keyEvent.getCharacter()).matches()) {
                nom_pressing.setText(nom_pressing.getText().replaceAll("[0-9é&'(è_çà)=$<>,;:!*ù~#{|`^@}ê³²¹Ôâå€þýûîô¶ÎÛðæ±ÊøÂ«»©®ß¬¿\"×÷¡]", ""));
            }
        }else if (keyEvent.getSource() == identifiant) {
            if (Pattern.compile("[ ]").matcher(keyEvent.getCharacter()).matches()) {
                identifiant.setText(identifiant.getText().replaceAll("[ ]", ""));
            }
        }
    }


    public void enregistrer(ActionEvent actionEvent) {
        if (nom.getText().length()<2 || prenom.getText().length()<2 || tel.getText().length()!=10 || nom_pressing.getText().length()<5 || identifiant.getText().length()<3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("");
            alert.setContentText("-la taille du nom et du prenom doit etre compris entre 2 et 10");

            alert.showAndWait();
        }else if(!Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$").matcher(email.getText()).matches()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("");
            alert.setContentText("format de mail invalide");

            alert.showAndWait();
        } else {
            boolean isUserExist = false;
            boolean isUserIdExist = false;
            try {
                try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                    if (con != null) {
                        Statement statement = con.createStatement();
                        String requete1 = "SELECT mail_utilisateur FROM utilisateur";
                        ResultSet resultSet1 = statement.executeQuery(requete1);
                        while (resultSet1.next()) {
                            if (Objects.equals(resultSet1.getString("mail_utilisateur"), email.getText())) {
                                isUserExist = true;
                            }
                        }
                        resultSet1 = statement.executeQuery("select identifiant_utilisateur from utilisateur");
                        while (resultSet1.next()) {
                            if (Objects.equals(resultSet1.getString("identifiant_utilisateur"), identifiant.getText())) {
                                isUserIdExist = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isUserExist && !isUserIdExist) {
                try {
                    try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pressing?user=blacks&password=passe")) {
                        if (con != null) {
                            Statement statement = con.createStatement();
                            String requete = "INSERT INTO utilisateur (nom_utilisateur, prenom_utilisateur, tel_utilisateur, identifiant_utilisateur, mdp_utilisateur, mail_utilisateur, nom_pressing) VALUES (" + "'" + nom.getText() + "','" + prenom.getText() + "','" + tel.getText() + "','" + identifiant.getText() + "','" + mdp.getText() + "','" + email.getText() + "','" + nom_pressing.getText() +"')";
                            ResultSet resultSet = statement.executeQuery(requete);
                            while (resultSet.next()) {

                            }
                            resultSet = statement.executeQuery("SELECT id_utilisateur FROM utilisateur WHERE mail_utilisateur = '" + email.getText() + "'");
                            int id = 0;
                            while (resultSet.next()) {
                                id = Integer.parseInt(resultSet.getString("id_utilisateur"));
                            }
                            resultSet = statement.executeQuery("INSERT INTO caisse (montant, id_proprietaire) VALUES ('0', '" + id +"')");
                            while (resultSet.next()) {

                            }

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("confirmation");
                            alert.setHeaderText("");
                            alert.setContentText("VOUS AVEZ ETE ENREGISTRE AVEC SUCCES");

                            alert.showAndWait();

                            Stage stage = null;

                            stage = (Stage) btn_retour.getScene().getWindow();
                            stage.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("erreur");
                alert.setHeaderText("");
                alert.setContentText("SE UTILISATEUR EXISTE DEJA");

                alert.showAndWait();
            }
        }
    }

    public static void send(String from,String pwd,String to,String sub,String msg){
        //Propriétés
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.socketFactory.port", "465");
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.port", "465");
        //Session
        Session s = Session.getDefaultInstance(p,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(from, pwd);
                    }
                });
        //composer le message
        try {
            MimeMessage m = new MimeMessage(s);
            m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            m.setSubject(sub);
            m.setText(msg);
            //envoyer le message
            Transport.send(m);
            System.out.println("Message envoyé avec succès");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
