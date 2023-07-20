package Jdbc1;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Europe/Kiev";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "password";

    static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            try {
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                initDB();

                while (true) {
                    System.out.println("1: add flat");
                    System.out.println("2: delete flat");
                    System.out.println("3: change flat");
                    System.out.println("4: view flats");
                    System.out.println("5: view flats by price");
                    System.out.println("6: view flats by rooms");
                    System.out.println("7: view flats by square");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addFlat(sc);
                            break;
                        case "2":
                            deleteFlat(sc);
                            break;
                        case "3":
                            changeFlatPrice(sc);
                            break;
                        case "4":
                            viewFlats();
                            break;
                        case "5":
                            viewFlatsByPrice();
                            break;
                        case "6":
                            viewFlatsByRooms();
                            break;
                        case "7":
                            viewFlatsBySquare();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void initDB() throws SQLException {
        Statement st = conn.createStatement();
        try {
            st.execute("CREATE TABLE IF NOT EXISTS Flats (id INT NOT NULL " +
                    "AUTO_INCREMENT PRIMARY KEY, city VARCHAR(20) NOT NULL, " +
                    "district VARCHAR(20) NOT NULL, square DOUBLE NOT NULL, " +
                    "rooms INT NOT NULL , address VARCHAR(30) NOT NULL, price int NOT NULL )");
        } finally {
            st.close();
        }
    }

    private static void addFlat(Scanner sc) throws SQLException {
        System.out.print("Enter city name: ");
        String city = sc.nextLine();
        System.out.print("Enter district name: ");
        String district = sc.nextLine();
        System.out.print("Enter square of flat: ");
        String sSquare = sc.nextLine();
        double square = Double.parseDouble(sSquare);
        System.out.print("Enter number of rooms: ");
        String sRooms = sc.nextLine();
        int rooms = Integer.parseInt(sRooms);
        System.out.print("Enter adress of flat: ");
        String address = sc.nextLine();
        System.out.print("Enter flat price: ");
        String sflatPrice = sc.nextLine();
        int price = Integer.parseInt(sflatPrice);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Flats (city, district, address, square, rooms, price) VALUES(?, ?, ?, ?, ?, ?)");
        try {
            ps.setString(1, city);
            ps.setString(2, district);
            ps.setString(3, address);
            ps.setDouble(4, square);
            ps.setInt(5, rooms);
            ps.setInt(6, price);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    private static void deleteFlat(Scanner sc) throws SQLException {
        System.out.print("Enter flat's address: ");
        String address = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("DELETE FROM Flats WHERE address = ?");
        try {
            ps.setString(1, address);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }


    private static void changeFlatPrice(Scanner sc) throws SQLException {
        System.out.print("Enter address of flat: ");
        String address = sc.nextLine();
        System.out.print("Enter flat price: ");
        String sFlatPrice = sc.nextLine();
        int price = Integer.parseInt(sFlatPrice);

        PreparedStatement ps = conn.prepareStatement("UPDATE Flats SET price = ? WHERE address = ?");
        try {
            ps.setInt(1, price);
            ps.setString(2, address);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }


    private static void viewFlatsByPrice() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Flats ORDER BY price");
        try {
            ResultSet rs = ps.executeQuery();

            try {
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.printf("%-20s", md.getColumnName(i));
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.printf("%-20s", rs.getString(i));
                    }
                    System.out.println();
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

    private static void viewFlatsByRooms() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Flats ORDER BY rooms");
        try {
            ResultSet rs = ps.executeQuery();

            try {
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.printf("%-20s", md.getColumnName(i));
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.printf("%-20s", rs.getString(i));
                    }
                    System.out.println();
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

    private static void viewFlatsBySquare() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Flats ORDER BY square");
        try {
            ResultSet rs = ps.executeQuery();

            try {
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.printf("%-20s", md.getColumnName(i));
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.printf("%-20s", rs.getString(i));
                    }
                    System.out.println();
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

    private static void viewFlats() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Flats");
        try {
            ResultSet rs = ps.executeQuery();

            try {
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.printf("%-20s", md.getColumnName(i));
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.printf("%-20s", rs.getString(i));
                    }
                    System.out.println();
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }
}
