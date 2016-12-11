import java.io.*;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Piotrek on 4.12.2016.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9898);
        String menuChoice;
        while (true)
        {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));


            showMenu();
            while ((menuChoice = userInput.readLine()) != null) {
                int menu= Integer.parseInt(menuChoice);
                switch (menu) {
                    case 1:
                        output.println("1");
                        System.out.println(serverInput.readLine());

                        String newFixedDate = userInput.readLine();
                        output.println(newFixedDate);

                        System.out.println(serverInput.readLine());
                        System.out.println("");
                        break;
                    case 2:
                        output.println("2");
                        if (Objects.equals(serverInput.readLine(), "Brak terminow nadających się do usunięcia")) {
                            System.out.println("Brak terminów");
                            System.out.println("");
                            break;
                        } else {
                            System.out.println("Który termin chcesz usunąć?");
                            System.out.println(serverInput.readLine());
                            int deleteFixedDate = Integer.parseInt(userInput.readLine());
                            System.out.println(deleteFixedDate);

                            if (Objects.equals(serverInput.readLine(), "Nie ma takiego terminu!")) {
                                System.out.println("Nie ma takiego terminu!");
                                System.out.println("");
                                break;
                            } else {
                                System.out.println("Udało się usunąć termin");
                                System.out.println("");
                                break;
                            }
                        }
                    case 3:
                        output.println("3");
                        System.out.println(serverInput.readLine());
                        System.out.println("");
                        break;
                    case 4:
                        output.println("4");
                        System.out.println(serverInput.readLine());
                        System.out.println("");
                        break;
                    case 5:
                        output.println("5");
                        System.out.println(serverInput.readLine());
                        System.out.println("");
                        break;
                    case 6:
                        output.println("6");
                        if (Objects.equals(serverInput.readLine(), "Brak wolnych terminow!")) {
                            System.out.println("Brak wolnych terminow!");
                            System.out.println("");
                            break;
                        } else {
                            System.out.println("Który termin chcesz zarezerwować?");
                            System.out.println(serverInput.readLine());
                            int bookFixedDate = Integer.parseInt(userInput.readLine());
                            output.println(bookFixedDate);

                            if (Objects.equals(serverInput.readLine(), "Nie ma takiego terminu!")) {
                                System.out.println("Nie ma takiego terminu!");
                                System.out.println("");
                                break;
                            } else {
                                System.out.println("Pomyślnie zarezerwowano termin");
                                System.out.println("");
                                break;
                            }
                        }
                    case 7:
                        output.println("7");
                        if (Objects.equals(serverInput.readLine(), "Brak terminow!")) {
                            System.out.println("Brak terminow!");
                            System.out.println("");
                            break;
                        } else {
                            System.out.println("Który termin chcesz zwolnić?");
                            System.out.println(serverInput.readLine());
                            int bookFixedDate = Integer.parseInt(userInput.readLine());
                            output.println(bookFixedDate);

                            if (Objects.equals(serverInput.readLine(), "Nie ma takiego terminu!")) {
                                System.out.println("Nie ma takiego terminu!");
                                System.out.println("");
                                break;
                            } else {
                                System.out.println("Zwolniono pomyślnie");
                                System.out.println("");
                                break;
                            }
                        }
                    case 8:
                        System.exit(8);
                        break;
                }
                showMenu();
            }


            try{
                Thread.sleep(2000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void showMenu(){
        System.out.println("MENU:");
        System.out.println("1. Dodaj termin");
        System.out.println("2. Usuń termin");
        System.out.println("3. Pokaż wszytskie terminy");
        System.out.println("4. Pokaż zarezerwowane terminy");
        System.out.println("5. Pokaż wolne terminy");
        System.out.println("6. Zarezerwuj termin");
        System.out.println("7. Zwolnij termin");
        System.out.println("8. Wyjście");
    }

}
