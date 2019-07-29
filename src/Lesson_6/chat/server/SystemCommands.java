package Lesson_6.chat.server;

public enum SystemCommands {
    ClientList("/clientlist"),
    PrivateMessage("/w"),
    BlackList("/blacklist"),
    Auth("/auth"),
    AuthOk("/authok"),
    ServerClose("/serverclosed"),
    Close("/end")
    ;

   private String value;

    SystemCommands(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
