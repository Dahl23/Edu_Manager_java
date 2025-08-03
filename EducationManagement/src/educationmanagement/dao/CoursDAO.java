package educationmanagement.dao;

import educationmanagement.model.Cours;
import educationmanagement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoursDAO {

    /**
     * Ajoute un nouveau cours dans la base de données.
     * Retourne l'ID généré du cours ou -1 en cas d'échec.
     */
    public int addCours(Cours cours) {
        String SQL = "INSERT INTO Cours (libelle, semestre) VALUES (?, ?)";
        int idGenereted = -1;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cours.getLibelle());
            pstmt.setString(2, cours.getSemestre());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenereted = rs.getInt(1);
                        cours.setId_cours(idGenereted); // Mettre à jour l'objet avec l'ID généré
                        System.out.println("Cours ajouté avec succès. ID: " + idGenereted);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout du cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return idGenereted;
    }

    /**
     * Récupère un cours par son ID.
     */
    public Cours getCoursById(int id) {
        String SQL = "SELECT id_cours, libelle, semestre FROM Cours WHERE id_cours = ?";
        Cours cours = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cours = new Cours(
                        rs.getInt("id_cours"),
                        rs.getString("libelle"),
                        rs.getString("semestre")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du cours par ID : " + ex.getMessage());
            ex.printStackTrace();
        }
        return cours;
    }

    /**
     * Récupère tous les cours.
     */
    public List<Cours> getAllCours() {
        String SQL = "SELECT id_cours, libelle, semestre FROM Cours";
        List<Cours> coursList = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Cours cours = new Cours(
                    rs.getInt("id_cours"),
                    rs.getString("libelle"),
                    rs.getString("semestre")
                );
                coursList.add(cours);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de tous les cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return coursList;
    }

    /**
     * Met à jour un cours existant.
     */
    public boolean updateCours(Cours cours) {
        String SQL = "UPDATE Cours SET libelle = ?, semestre = ? WHERE id_cours = ?";
        boolean updated = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, cours.getLibelle());
            pstmt.setString(2, cours.getSemestre());
            pstmt.setInt(3, cours.getId_cours());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Cours ID " + cours.getId_cours() + " mis à jour avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * Supprime un cours par son ID.
     */
    public boolean deleteCours(int id) {
        String SQL = "DELETE FROM Cours WHERE id_cours = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Cours ID " + id + " supprimé avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }
}