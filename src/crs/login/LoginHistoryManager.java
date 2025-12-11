package crs.login;

import java.io.*;

public class LoginHistoryManager {

    private static final String HISTORY_FILE = "login_history.txt";

    public void logLogin(String userId) {
        logEvent(userId, "LOGIN");
    }

    public void logLogout(String userId) {
        logEvent(userId, "LOGOUT");
    }

    private void logEvent(String userId, String action) {
        long now = System.currentTimeMillis();
        String binaryTime = Long.toBinaryString(now);

        String recordId = "L" + now; // simple unique id

        String line = recordId + "|" + userId + "|" + action + "|" + now + "|" + binaryTime;

        try (FileWriter fw = new FileWriter(HISTORY_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
