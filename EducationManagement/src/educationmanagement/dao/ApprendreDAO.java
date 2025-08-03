package educationmanagement.dao;

import educationmanagement.model.Apprendre;
import educationmanagement.model.Etudiant;
import educationmanagement.model.Cours;
import educationmanagement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApprendreDAO {

    /**
     * Inscrit un étudiant à un cours.
     * Retourne true si l'inscription est réussie, false sinon.
     */
    public boolean inscrireEtudiantACours(int idEtudiant, int idCours) {
        String SQL = "INSERT INTO Apprendre (id_etudiant, id_cours) VALUES (?, ?)";
        boolean inserted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEtudiant);
            pstmt.setInt(2, idCours);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                inserted = true;
                System.out.println("Etudiant ID " + idEtudiant + " inscrit au cours ID " + idCours + " avec succès.");
            }
        } catch (SQLException ex) {
            // Gérer le cas où l'étudiant est déjà inscrit à ce cours (clé primaire dupliquée)
            if (ex.getErrorCode() == 1062) { // Erreur de duplicata d'entrée pour MySQL
                System.err.println("L'étudiant ID " + idEtudiant + " est déjà inscrit au cours ID " + idCours + ".");
            } else {
                System.err.println("Erreur lors de l'inscription de l'étudiant au cours : " + ex.getMessage());
            }
            ex.printStackTrace();
        }
        return inserted;
    }

    /**
     * Désinscrit un étudiant d'un cours.
     * Retourne true si la désinscription est réussie, false sinon.
     */
    public boolean desinscrireEtudiantDuCours(int idEtudiant, int idCours) {
        String SQL = "DELETE FROM Apprendre WHERE id_etudiant = ? AND id_cours = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEtudiant);
            pstmt.setInt(2, idCours);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Etudiant ID " + idEtudiant + " désinscrit du cours ID " + idCours + " avec succès.");
            } else {
                System.out.println("Aucune inscription trouvée pour l'étudiant ID " + idEtudiant + " et le cours ID " + idCours + ".");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la désinscription de l'étudiant du cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }

    /**
     * Récupère tous les cours auxquels un étudiant est inscrit.
     * Retourne une liste d'objets Cours.
     */
    public List<Cours> getCoursByEtudiantId(int idEtudiant) {
        String SQL = "SELECT C.id_cours, C.libelle, C.semestre " +
                     "FROM Cours C INNER JOIN Apprendre A ON C.id_cours = A.id_cours " +
                     "WHERE A.id_etudiant = ?";
        List<Cours> coursList = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEtudiant);
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
            System.err.println("Erreur lors de la récupération des cours de l'étudiant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return coursList;
    }

    /**
     * Récupère tous les étudiants inscrits à un cours donné.
     * Retourne une liste d'objets Etudiant.
     */
    public List<Etudiant> getEtudiantsByCoursId(int idCours) {
        String SQL = "SELECT E.id_etudiant, E.nom, E.matricule, E.email, E.niveau, E.filiere, E.id_utilisateur " +
                     "FROM Etudiants E INNER JOIN Apprendre A ON E.id_etudiant = A.id_etudiant " +
                     "WHERE A.id_cours = ?";
        List<Etudiant> etudiants = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idCours);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Etudiant etudiant = new Etudiant(
                        rs.getInt("id_etudiant"),
                        rs.getString("nom"),
                        rs.getString("matricule"),
                        rs.getString("email"),
                        rs.getString("niveau"),
                        rs.getString("filiere"),
                        rs.getInt("id_utilisateur")
                    );
                    etudiants.add(etudiant);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des étudiants inscrits au cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return etudiants;
    }
}