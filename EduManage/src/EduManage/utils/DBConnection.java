package EduManage.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection instance;

    private static final String URL = "jdbc:mysql://localhost:3306/ems";
    private static final String USER = "root";         
    private static final String PASSWORD = "";         

    private DBConnection() {
        // Constructeur privé pour empêcher l’instanciation directe
    }

    public static Connection getInstance() {
        if (instance == null) {
            try {
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion à la base de données réussie !");
            } catch (SQLException e) {
                System.err.println("❌ Échec de la connexion à la base de données : " + e.getMessage());
            }
        }
        return instance;
    }
}
