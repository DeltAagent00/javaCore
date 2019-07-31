package Lesson_6.chat.client;

import Lesson_6.chat.server.SystemCommands;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    TextField loginField;

    @FXML
    TextField passwordField;

    @FXML
    ListView<String> clientList;

    private boolean isAuthorized;

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if(!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
        }
    }

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith(SystemCommands.AuthOk.getValue())) {
                                setAuthorized(true);
                                break;
                            } else {
                                textArea.appendText(str + "\n");
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.equals(SystemCommands.ServerClose.getValue())) break;
                            if (str.startsWith(SystemCommands.ClientList.getValue())) {
                                String[] tokens = str.split(" ");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        clientList.getItems().clear();
                                        for (int i = 1; i < tokens.length; i++) {
                                            clientList.getItems().add(tokens[i]);
                                        }
                                    }
                                });
                            } else {
                                textArea.appendText(str + "\n");
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Dispose() {
        System.out.println("Отправляем сообщение о закрытии");
        try {
            if(out != null) {
                out.writeUTF(SystemCommands.Close.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if(socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF(SystemCommands.Auth.getValue() + " " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectClient(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1) {
            final String clientNick = clientList.getSelectionModel().getSelectedItem();
            textField.setText(SystemCommands.PrivateMessage.getValue() + " " + clientNick);
        }
    }

//    @FXML
//    private void closeButtonAction(){
//        // get a handle to the stage
//        Stage stage = (Stage) closeButton.getScene().getWindow();
//        out.writeUTF("/close")
//        // do what you have to do
//        stage.close();
//    }
}
