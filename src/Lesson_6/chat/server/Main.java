package Lesson_6.chat.server;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger("");
    private static final Formatter formater = new Formatter() {
        @Override
        public String format(LogRecord record) {
            return record.getLevel() + "\t" + record.getMessage() + "\t" + record.getMillis() + "\n";
        }
    };

    private Vector<ClientHandler> clients;
    private Set<String> blackList;

    public Main() throws SQLException {
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();
        blackList = new HashSet<>();

        try {
            initLogger();
            DBService.connect();

            blackList.addAll(DBService.getBlackList());
            // System.out.println(DBService.getNickByLoginAndPass("login12", "pass1"));

            server = new ServerSocket(8189);
            log(Level.INFO, "Сервер запущен");

            while (true) {
                socket = server.accept();
                log(Level.INFO, "Клиент подключился");
                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
            log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DBService.disconnect();
        }
    }

    // подписываем клиента на рассылку
    public void subscribe(ClientHandler client) {
        clients.add(client);
        sendBroadcastSystemCommand(SystemCommands.ClientList);
    }

    // отписываем клиента от рассылки сообщений
    public void unsubscribe(ClientHandler client){
        clients.remove(client);

        sendBroadcastSystemCommand(SystemCommands.ClientList);
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler o: clients) {
            if (!blackList.contains(o.getNick())) {
                o.sendMsg(msg);
            }
        }
    }

    private void sendBroadcastSystemCommand(SystemCommands commands) {
        final StringBuilder msg = new StringBuilder(commands.getValue());

        switch (commands) {
            case ClientList:
                for (ClientHandler o: clients) {
                    msg.append(" ")
                        .append(o.getNick());
                }
                break;
        }
        broadcastMsg(msg.toString());
    }

    public boolean isConnectedToServer(String nick) {
        for (ClientHandler client: clients) {
            if (client.getNick().toLowerCase().equals(nick.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void addToBlackList(String[] nicks) {
        blackList.addAll(Arrays.asList(nicks));
        for (String nick : nicks) {
            DBService.addToBlackList(nick);
        }
    }

    public void removeFromBlackList(String nick) {
        blackList.remove(nick);
        DBService.removeFromBlackList(nick);
    }

    public void sendMsgToUsers(String userNickSend, String message, String... users) {
        final String msg = userNickSend + ": " + message;
        final Set<String> connectedUsers = new HashSet<>(users.length);
        ClientHandler myClient = null;


        for (ClientHandler client: clients) {
            if (client.getNick().toLowerCase().equals(userNickSend.toLowerCase())) {
                myClient = client;
                continue;
            }
            for (String user: users) {
                if (client.getNick().toLowerCase().equals(user.toLowerCase())) {
                    client.sendMsg(msg);
                    connectedUsers.add(user);
                    break;
                }
            }
        }

        if (!connectedUsers.isEmpty() && myClient != null) {
            final StringBuilder myMessage = new StringBuilder(userNickSend).append(" -> [");
            int i = 0;
            for (String user: connectedUsers) {
                final boolean isLast = (i + 1 == connectedUsers.size());

                myMessage.append(" ");
                myMessage.append(user);

                if (!isLast) {
                    myMessage.append(",");
                } else {
                    myMessage.append("]: ");
                    myMessage.append(message);
                }
                i++;
            }
            myClient.sendMsg(myMessage.toString());
        }
    }

    private void initLogger() throws IOException {
        logger.setLevel(Level.ALL);
        final Handler handler = logger.getHandlers()[0];

        handler.setLevel(Level.ALL);
        handler.setFormatter(formater);

        final Handler fileHandler = new FileHandler("mylog.log", true);
        handler.setLevel(Level.ALL);
        handler.setFormatter(formater);
        logger.addHandler(fileHandler);
    }

    public void log(Level level, String msg) {
        logger.log(level, msg);
    }
}
