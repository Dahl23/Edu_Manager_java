package educationmanagement.dao;

import educationmanagement.model.Evaluation;
import educationmanagement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDAO {

    /**
     * Ajoute une nouvelle évaluation dans la base de données.
     * Retourne l'ID généré de l'évaluation ou -1 en cas d'échec.
     */
    public int addEvaluation(Evaluation evaluation) {
        String SQL = "INSERT INTO Evaluations (type_evaluation, note, id_etudiant, id_cours) VALUES (?, ?, ?, ?)";
        int idGenereted = -1;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, evaluation.getType_evaluation());
            pstmt.setDouble(2, evaluation.getNote());
            pstmt.setInt(3, evaluation.getId_etudiant());
            pstmt.setInt(4, evaluation.getId_cours());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenereted = rs.getInt(1);
                        evaluation.setId_evaluation(idGenereted); // Mettre à jour l'objet avec l'ID généré
                        System.out.println("Evaluation ajoutée avec succès. ID: " + idGenereted);
                    }
                }
            }
        } catch (SQLException ex) {
            // Gérer le cas où une évaluation du même type existe déjà pour cet étudiant et ce cours
            if (ex.getErrorCode() == 1062) { // Erreur de duplicata d'entrée pour MySQL
                System.err.println("Une évaluation de type '" + evaluation.getType_evaluation() + 
                                   "' existe déjà pour l'étudiant ID " + evaluation.getId_etudiant() + 
                                   " et le cours ID " + evaluation.getId_cours() + ".");
            } else {
                System.err.println("Erreur lors de l'ajout de l'évaluation : " + ex.getMessage());
            }
            ex.printStackTrace();
        }
        return idGenereted;
    }

    /**
     * Récupère une évaluation par son ID.
     */
    public Evaluation getEvaluationById(int id) {
        String SQL = "SELECT id_evaluation, type_evaluation, note, id_etudiant, id_cours FROM Evaluations WHERE id_evaluation = ?";
        Evaluation evaluation = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    evaluation = new Evaluation(
                        rs.getInt("id_evaluation"),
                        rs.getString("type_evaluation"),
                        rs.getDouble("note"),
                        rs.getInt("id_etudiant"),
                        rs.getInt("id_cours")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'évaluation par ID : " + ex.getMessage());
            ex.printStackTrace();
        }
        return evaluation;
    }

    /**
     * Récupère toutes les évaluations pour un étudiant donné et un cours donné.
     */
    public List<Evaluation> getEvaluationsByEtudiantAndCours(int idEtudiant, int idCours) {
        String SQL = "SELECT id_evaluation, type_evaluation, note, id_etudiant, id_cours FROM Evaluations WHERE id_etudiant = ? AND id_cours = ?";
        List<Evaluation> evaluations = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEtudiant);
            pstmt.setInt(2, idCours);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Evaluation evaluation = new Evaluation(
                        rs.getInt("id_evaluation"),
                        rs.getString("type_evaluation"),
                        rs.getDouble("note"),
                        rs.getInt("id_etudiant"),
                        rs.getInt("id_cours")
                    );
                    evaluations.add(evaluation);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des évaluations par étudiant et cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        return evaluations;
    }
    
    /**
     * Récupère toutes les évaluations d'un étudiant.
     */
    public List<Evaluation> getEvaluationsByEtudiantId(int idEtudiant) {
        String SQL = "SELECT id_evaluation, type_evaluation, note, id_etudiant, id_cours FROM Evaluations WHERE id_etudiant = ?";
        List<Evaluation> evaluations = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idEtudiant);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Evaluation evaluation = new Evaluation(
                        rs.getInt("id_evaluation"),
                        rs.getString("type_evaluation"),
                        rs.getDouble("note"),
                        rs.getInt("id_etudiant"),
                        rs.getInt("id_cours")
                    );
                    evaluations.add(evaluation);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des évaluations par étudiant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return evaluations;
    }

    /**
     * Met à jour une évaluation existante.
     */
    public boolean updateEvaluation(Evaluation evaluation) {
        String SQL = "UPDATE Evaluations SET type_evaluation = ?, note = ?, id_etudiant = ?, id_cours = ? WHERE id_evaluation = ?";
        boolean updated = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, evaluation.getType_evaluation());
            pstmt.setDouble(2, evaluation.getNote());
            pstmt.setInt(3, evaluation.getId_etudiant());
            pstmt.setInt(4, evaluation.getId_cours());
            pstmt.setInt(5, evaluation.getId_evaluation());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Evaluation ID " + evaluation.getId_evaluation() + " mise à jour avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'évaluation : " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * Supprime une évaluation par son ID.
     */
    public boolean deleteEvaluation(int id) {
        String SQL = "DELETE FROM Evaluations WHERE id_evaluation = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Evaluation ID " + id + " supprimée avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'évaluation : " + ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }
}