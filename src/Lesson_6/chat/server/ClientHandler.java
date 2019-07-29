package Lesson_6.chat.server;

import javafx.util.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.*;

public class ClientHandler {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private Main server;
    private String nick;

    public ClientHandler(Socket socket, Main server) {
        try {
            this.socket = socket;
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // цикл для авторизации
                        while (true) {
                            String str = in.readUTF();
                            // если сообщение начинается с /auth
                            if(str.startsWith(SystemCommands.Auth.getValue())) {
                                server.log(Level.INFO, "command " + SystemCommands.Auth);
                                String[] tokens = str.split(" ");
                                // Вытаскиваем данные из БД
                                final String newNick = DBService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                final boolean isConnected = server.isConnectedToServer(newNick);
                                if (newNick != null && !isConnected) {
                                    server.log(Level.INFO, nick + " auth success");
                                    // отправляем сообщение об успешной авторизации
                                    sendMsg(SystemCommands.AuthOk.getValue());
                                    nick = newNick;
                                    server.subscribe(ClientHandler.this);
                                    break;
                                } else if (newNick == null) {
                                    sendMsg("Неверный логин/пароль!");
                                    server.log(Level.INFO, "incorrect login");
                                } else {
                                    sendMsg("Пользователь уже подключен!");
                                    server.log(Level.INFO, "already connecting");
                                }
                            }
                        }

                        // блок для отправки сообщений
                        while (true) {
                            String str = in.readUTF();
                            if(str.equals(SystemCommands.Close.getValue())) {
                                server.log(Level.INFO, nick + "command " + SystemCommands.Close);
                                out.writeUTF("/serverClosed");
                                break;
                            }

                            if (str.startsWith(SystemCommands.PrivateMessage.getValue())) {
                                server.log(Level.INFO, nick + "command " + SystemCommands.PrivateMessage);
                                final String[] inputCommand = str.split(" ");

                                if (inputCommand.length > 2) {
                                    final Pair<String, String[]> result = getMessageAndUsersFromCommand(inputCommand, true);
                                    server.sendMsgToUsers(nick, result.getKey(), result.getValue());
                                }
                            } else if (str.startsWith(SystemCommands.BlackList.getValue())) {
                                server.log(Level.INFO, nick + "command " + SystemCommands.BlackList);
                                final String[] inputCommand = str.split(" ");
                                if (inputCommand.length > 2) {
                                    final Pair<String, String[]> result = getMessageAndUsersFromCommand(inputCommand, false);
                                    server.addToBlackList(result.getValue());
                                }
                            } else {
                                server.broadcastMsg(nick + ": " + str);
                                server.log(Level.INFO, nick + " send message \"" + str + "\"");
                            }
                        }
                    }  catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pair<String, String[]> getMessageAndUsersFromCommand(String[] inputCommand, boolean needMessage) {
        final String msg;
        final String[] users;
        final int countUsers;

        if (needMessage) {
            countUsers = inputCommand.length - 2;
            msg = inputCommand[inputCommand.length - 1];
            users = new String[countUsers];
        } else {
            msg = null;
            countUsers = inputCommand.length - 1;
            users = new String[countUsers];
        }
        System.arraycopy(inputCommand, 1, users, 0, countUsers);

        return new Pair<>(msg, users);
    }

    public String getNick() {
        return nick;
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
