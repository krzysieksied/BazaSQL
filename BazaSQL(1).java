package pracazespołowaniestacjonarne;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

public class BazaSQL {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/osoby";
        String login = "root";
        String password = "";

        Connection conn = DriverManager.getConnection(url, login, password);

        Statement st = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
        
        
        DatabaseMetaData daneBazy = conn.getMetaData();

       
        Scanner scanner= new Scanner(System.in);
        String koniec = null;
        
        
        while (!"k".equals(koniec)) {

            ResultSet nazwyTabel = daneBazy.getTables(null, null, null, new String[]{"TABLE"});
            while (nazwyTabel.next()) {
                System.out.println("tabela: " + nazwyTabel.getString(3));
            }

            System.out.println("Którą tabele wyświetlić");
            String zmienna = scanner.nextLine();

            ResultSet rs = st.executeQuery("SELECT * FROM " + zmienna);
            ResultSetMetaData rsmd = rs.getMetaData();

            int liczbaKolumn = rsmd.getColumnCount();

            for (int i = 1; i <= liczbaKolumn; i++) {
                System.out.print(rsmd.getColumnName(i) + " ");
            }

            System.out.println("");

            while (rs.next()) {
                for (int i = 1; i <= liczbaKolumn; i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println("");
            }
            System.out.println("Chcesz coś zmienic?(tak)");
            String czyTak = scanner.nextLine();
            if ("tak".equals(czyTak)) {

                System.out.println("Podaj ktory rekord do zmiany ");
                int rekord = scanner.nextInt();
                scanner.nextLine();
                System.out.println("co chcesz zmienic(nazwa kolumny) ");

                String kolumna = scanner.nextLine();

                System.out.println("podaj nowa wartosc ");
                String wartosc2 = scanner.nextLine();
                rs.absolute(rekord);
                rs.updateString(kolumna, wartosc2);
                rs.updateRow();
            } else {
                System.out.println("Konczymy to wpisz k");
                koniec = scanner.nextLine();
            }

        }
    }

}
