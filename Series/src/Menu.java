import java.util.Scanner;
import java.sql.*;

public class Menu {

    Scanner input = new Scanner(System.in);
    String title, status, rating;
    int choice;

    String userName = "root",
            password = "root",
            URL = "jdbc:mysql://localhost:3306/series";

    public void mainActivity() throws SQLException, InterruptedException {
        System.out.print("");
        try(Connection connection = DriverManager.getConnection(URL, userName, password)) {

            Statement statement = connection.createStatement();

            chooseMenu();

            switch (choice) {
                case 1:
                    ResultSet rs = statement.executeQuery("SELECT * FROM series");
                    while (rs.next()) {
                        System.out.println("Title - " + rs.getString(2));
                        System.out.println("Status - " + rs.getString(3));
                        System.out.println("Rating - " + rs.getString(4));
                        System.out.println("------------\n");
                    }
                    Thread.sleep(1000);
                    mainActivity();
                    break;

                case 2:
                    addSeries();
                    statement.executeUpdate("INSERT INTO series VALUES" +
                            "(NULL, '" + title + "', '" + status + "', '" + rating + "')");
                    mainActivity();
                    break;

                case 3:
                    ResultSet rsptw = statement.executeQuery("SELECT * FROM planToWatch WHERE isWatched = 'free'");
                    System.out.println("1. Series list;");
                    System.out.println("2. Add new series");
                    System.out.println("3. I've watched one\n");
                    System.out.print("Choice: ");
                    String choicePlanToWatchString = input.nextLine();
                    int choicePlanToWatch = Integer.parseInt(choicePlanToWatchString);
                    switch (choicePlanToWatch) {

                        case 1:
                            while(rsptw.next()) {
                                System.out.println("Title - " + rsptw.getString(2));
                                System.out.println("-------------");
                            }
                            System.out.println();
                            mainActivity();
                        break;

                        case 2:
                            addPlanToWatch();
                            statement.executeUpdate("INSERT INTO planToWatch (id, title) VALUES(NULL, '" + title + "')");
                            System.out.println();
                            mainActivity();
                        break;

                        case 3:
                            System.out.println("Which one?\n");
                            ResultSet rsChange = statement.executeQuery("SELECT * FROM planToWatch WHERE isWatched = 'free' ");
                            while(rsChange.next()) {
                                System.out.println("Id - " + rsChange.getInt(1));
                                System.out.println("Title - " + rsChange.getString(2));
                                System.out.println("Status - " + rsChange.getString(3));
                                System.out.println("----------");
                            }
                            System.out.print("Choice: ");
                            String ask =input.nextLine();
                            int askInt = Integer.parseInt(ask);
                            statement.executeUpdate("UPDATE planToWatch SET isWatched = 'watched' WHERE id = " + askInt + "");
                            mainActivity();
                        break;

                        default:
                            System.out.println("Error...");
                            Thread.sleep(1000);
                            mainActivity();
                    }
                        break;

                case 4:
                    ResultSet rsRandom = statement.executeQuery("SELECT id, title FROM planToWatch " +
                            "WHERE isWatched = 'free' ORDER BY RAND() LIMIT 1");
                    while(rsRandom.next()) {
                        System.out.println("Random title - " + rsRandom.getString(2) + "\n");
                    }
                    Thread.sleep(1000);
                    mainActivity();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Error...\n");
                    Thread.sleep(1000);
                    mainActivity();
            }


        }
    }

    public void chooseMenu() {

        System.out.print("");
        System.out.println("1. My list;");
        System.out.println("2. Add new series;");
        System.out.println("3. Plan-to-watch list;");
        System.out.println("4. Random from plan-to-watch list;");
        System.out.println("5. Exit\n");
        System.out.print("Choice: ");
        System.out.println();
        this.choice = Integer.parseInt(input.nextLine());

    }

    public void addSeries() {

        System.out.print("Title: ");
        this.title = input.nextLine();

        System.out.print("dropped or finished?: ");
        this.status = input.nextLine();

        System.out.print("How do you like it? (1 - 10): ");
        this.rating = input.nextLine();
        System.out.println();

    }

    public void addPlanToWatch() {
        System.out.print("Title - ");
        this.title = input.nextLine();
    }

}
