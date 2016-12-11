import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Piotrek on 4.12.2016.
 */
public class Server {

    private static ArrayList<String> bookedFixedDate = new ArrayList<>();
    private static ArrayList<String> notBookedFixedDate = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        System.out.println("Uruchomiono serwer.");
        int connectionNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                connection c = new connection(listener.accept(), connectionNumber++);
                c.start();
            }
        } finally {
            listener.close();
        }
    }

    static class connection extends Thread {
        private Socket socket;
        private int connectionNumber;

        public connection(Socket socket, int connectionNumber) {
            this.socket = socket;
            this.connectionNumber = connectionNumber;
            log("Connection number id: " + connectionNumber);
        }

        private void log(String a) {
            System.out.println(a);
        }

        public void run() {
            String input;

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    while ((input = in.readLine()) != null) {
                        int menuChoice = Integer.parseInt(input);
                        switch (menuChoice) {
                            case 1:
                                pw.println("Dodawanie nowego terminu: ");
                                String newFixedDate = in.readLine();
                                notBookedFixedDate.add(newFixedDate);
                                pw.println("Pomyślnie dodano termin");
                                break;
                            case 2:
                                if (notBookedFixedDate.isEmpty()) {
                                    pw.println("Brak terminow nadających się do usunięcia");
                                } else {
                                    pw.println("Który termin chcesz usunąć?");
                                    pw.println(notBookedFixedDate);
                                    int toDelete = Integer.parseInt(in.readLine());
                                    if (toDelete <= notBookedFixedDate.size() && toDelete > 0) {
                                        String tmp = notBookedFixedDate.remove(toDelete - 1);
                                        pw.println("Pomyślnie usunięto termin");
                                    } else {
                                        pw.println("Nie ma takiego terminu!");
                                    }
                                }
                                break;
                            case 3:
                                if (notBookedFixedDate.isEmpty()) {
                                    pw.println("Brak terminow!");
                                } else {
                                    pw.println("Wolne terminy:" + notBookedFixedDate + "Zajęte terminy:" + bookedFixedDate);
                                }
                                break;
                            case 4:
                                if (bookedFixedDate.isEmpty()) {
                                    pw.println("Brak terminow!");
                                } else {
                                    pw.println("Zajęte terminy:" + bookedFixedDate);
                                }
                                break;
                            case 5:
                                if (notBookedFixedDate.isEmpty()) {
                                    pw.println("Brak terminow!");
                                } else {
                                    pw.println("Wolne terminy:" + notBookedFixedDate);
                                }
                                break;
                            case 6:
                                if (notBookedFixedDate.isEmpty()) {
                                    pw.println("Brak wolnych terminow!");
                                } else {
                                    pw.println("Który termin chcesz zarezerwować?");
                                    pw.println("Wolne terminy:" + notBookedFixedDate);
                                    int toAdd = Integer.parseInt(in.readLine());
                                    if (toAdd <= notBookedFixedDate.size() && toAdd > 0) {
                                        String tmp = notBookedFixedDate.get(toAdd - 1);
                                        bookedFixedDate.add(tmp + " zarezerwowane przez klient " + connectionNumber);
                                        notBookedFixedDate.remove(toAdd - 1);
                                        pw.println("Pomyślnie zarezerwowano termin");
                                    } else {
                                        pw.println("Nie ma takiego terminu!");
                                    }
                                }
                                break;
                            case 7:
                                if (bookedFixedDate.isEmpty()) {
                                    pw.println("Brak terminow!");
                                } else {
                                    pw.println("Który termin chcesz zwolnić?");
                                    pw.println("Zajete terminy:" + bookedFixedDate);
                                    int toFree = Integer.parseInt(in.readLine());
                                    if (toFree <= bookedFixedDate.size() && toFree > 0) {
                                        String tmp = bookedFixedDate.get(toFree - 1);
                                        String replaceTmp = tmp.replace("zarezerwowane przez klient " + connectionNumber, "");
                                        notBookedFixedDate.add(replaceTmp);
                                        bookedFixedDate.remove(toFree - 1);
                                        pw.println("Pomyślnie zwolniono termin");
                                    } else {
                                        pw.println("Nie ma takiego terminu!");
                                    }
                                }
                                break;
                        }
                    }
                }

            } catch (IOException e) {
                log("error number: " + connectionNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Cannot close");
                }
                log("Connection nr: " + connectionNumber + " closed");
            }
        }
    }
}
