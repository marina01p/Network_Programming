import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Server {
    private ServerSocket serverSocket;

    public void start(int port) {
        System.out.println("Server --> STARTED");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new TCPServer(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class TCPServer extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader br;

        public TCPServer(Socket socket) {
            this.clientSocket = socket;
            System.out.println("New Client --> CONNECTED");
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out.println("Welcome Client");
                out.println("\t Help Menu:" +
                        "\n1. 'ALL' -- view all collected data " +
                        "\n2. 'KEY IS VALUE' -- search for key-value" +
                        "\n3. 'STOP' -- close the connection");
                br.close();
                out.close();
                clientSocket.close();
                System.out.println("Session --> FINISHED");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private boolean processInput(String input) {
            DataReader dataReader = new DataReader();
            ArrayList<String> words = new ArrayList<>(Arrays.asList(input.split("\\s+")));
            switch (words.size()) {
                case 2: {
                    if (Pattern.compile(Pattern.quote("stop"), Pattern.CASE_INSENSITIVE).matcher(input).find()) {
                        out.println("Session --> FINISHED");
                        return false;
                    } else if (Pattern.compile(Pattern.quote("all"), Pattern.CASE_INSENSITIVE).matcher(input).find()) {
                        for (String str : URLConnection.dataList) {
                            out.println(str);
                        }
                    }
                    break;
                }
                case 3: {
                    String key = "";
                    String value = "";
                    for (int i = 0; i < words.size(); i++) {
                        if (words.get(i).equals("is")) {
                            key = words.get(i - 1);
                            value = words.get(i + 1);
                        }
                    }
                    List<String> results = dataReader.getKeyAndValue(key, value, URLConnection.dataList);
                    if (results.isEmpty()) {
                        out.println("Warn --> NO RESULTS FOUND \n");
                    } else {
                        for (String rs : results) {
                            out.println(rs);
                        }
                    }

                    break;
                }
                default: {
                    out.println("ERROR: Such option doesn't exist");
                }
            }
            return true;
        }
    }
}
