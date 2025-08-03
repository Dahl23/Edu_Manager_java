package educationmanagement.dao;

import educationmanagement.model.Enseignant;
import educationmanagement.model.Cours;
import educationmanagement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnseignerDAO {

    /**
     * Affecte un enseignant à un cours.
     * Retourne true si l'affectation est réussie, false sinon.
     */
    public boolean affecterEnseignantACours(int idEnseignant, int idCours) {
        String SQL = "INSERT INTO Enseigner (id_enseignant, id_cours) VALUES (?, ?)";
        boolean inserted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEnseignant);
            pstmt.setInt(2, idCours);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                inserted = true;
                System.out.println("Enseignant ID " + idEnseignant + " affecté au cours ID " + idCours + " avec succès.");
            }
        } catch (SQLException ex) {
            // Gérer le cas où l'enseignant est déjà affecté à ce cours
            if (ex.getErrorCode() == 1062) { // Erreur de duplicata d'entrée pour MySQL
                System.err.println("L'enseignant ID " + idEnseignant + " est déjà affecté au cours ID " + idCours + ".");
            } else {
                System.err.println("Erreur lors de l'affectation de l'enseignant au cours : " + ex.getMessage());
            }
            ex.printStackTrace();
        }
        return inserted;
    }

    /**
     * Désaffecte un enseignant d'un cours.
     * Retourne true si la désaffectation est réussie, false sinon.
     */
    public boolean desaffecterEnseignantDuCours(int idEnseignant, int idCours) {
        String SQL = "DELETE FROM Enseigner WHERE id_enseignant = ? AND id_cours = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEnseignant);
            pstmt.setInt(2, idCours);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Enseignant ID " + idEnseignant + " désaffecté du cours ID " + idCours + " avec succès.");
            } else {
                System.out.println("Aucune affectation trouvée pour l'enseignant ID " + idEnseignant + " et le cours ID " + idCours + ".");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la désaffectation de l'enseignant du cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }

    /**
     * Récupère tous les cours qu'un enseignant enseigne.
     * Retourne une liste d'objets Cours.
     */
    public List<Cours> getCoursByEnseignantId(int idEnseignant) {
        String SQL = "SELECT C.id_cours, C.libelle, C.semestre " +
                     "FROM Cours C INNER JOIN Enseigner EN ON C.id_cours = EN.id_cours " +
                     "WHERE EN.id_enseignant = ?";
        List<Cours> coursList = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEnseignant);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cours cours = new Cours(
                        rs.getInt("id_cours"),
                        rs.getString("libelle"),
                        rs.getString("semestre")
                    );
                    coursList.add(cours);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des cours de l'enseignant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return coursList;
    }

    /**
     * Récupère tous les enseignants affectés à un cours donné.
     * Retourne une liste d'objets Enseignant.
     */
    public List<Enseignant> getEnseignantsByCoursId(int idCours) {
        String SQL = "SELECT EN.id_enseignant, EN.nom, EN.email, EN.specialite, EN.id_utilisateur " +
                     "FROM Enseignants EN INNER JOIN Enseigner E ON EN.id_enseignant = E.id_enseignant " +
                     "WHERE E.id_cours = ?";
        List<Enseignant> enseignants = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idCours);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Enseignant enseignant = new Enseignant(
                        rs.getInt("id_enseignant"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("specialite"),
                        rs.getInt("id_utilisateur")
                    );
                    enseignants.add(enseignant);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des enseignants affectés au cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return enseignants;
    }
}