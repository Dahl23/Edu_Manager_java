package educationmanagement.dao;

import educationmanagement.model.Enseignant;
import educationmanagement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO {

    /**
     * Ajoute un nouvel enseignant dans la base de données.
     * L'id_utilisateur doit déjà exister.
     * Retourne l'ID généré de l'enseignant ou -1 en cas d'échec.
     */
    public int addEnseignant(Enseignant enseignant) {
        String SQL = "INSERT INTO Enseignants (nom, email, specialite, id_utilisateur) VALUES (?, ?, ?, ?)";
        int idGenereted = -1;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getEmail());
            pstmt.setString(3, enseignant.getSpecialite());
            pstmt.setInt(4, enseignant.getId_utilisateur());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenereted = rs.getInt(1);
                        enseignant.setId_enseignant(idGenereted); // Mettre à jour l'objet avec l'ID généré
                        System.out.println("Enseignant ajouté avec succès. ID: " + idGenereted);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de l'enseignant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return idGenereted;
    }

    /**
     * Récupère un enseignant par son ID.
     */
    public Enseignant getEnseignantById(int id) {
        String SQL = "SELECT id_enseignant, nom, email, specialite, id_utilisateur FROM Enseignants WHERE id_enseignant = ?";
        Enseignant enseignant = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    enseignant = new Enseignant(
                        rs.getInt("id_enseignant"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("specialite"),
                        rs.getInt("id_utilisateur")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'enseignant par ID : " + ex.getMessage());
            ex.printStackTrace();
        }
        return enseignant;
    }
    
    /**
     * Récupère un enseignant par son id_utilisateur.
     */
    public Enseignant getEnseignantByIdUtilisateur(int idUtilisateur) {
        String SQL = "SELECT id_enseignant, nom, email, specialite, id_utilisateur FROM Enseignants WHERE id_utilisateur = ?";
        Enseignant enseignant = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idUtilisateur);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    enseignant = new Enseignant(
                        rs.getInt("id_enseignant"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("specialite"),
                        rs.getInt("id_utilisateur")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'enseignant par id_utilisateur : " + ex.getMessage());
            ex.printStackTrace();
        }
        return enseignant;
    }

    /**
     * Récupère tous les enseignants.
     */
    public List<Enseignant> getAllEnseignants() {
        String SQL = "SELECT id_enseignant, nom, email, specialite, id_utilisateur FROM Enseignants";
        List<Enseignant> enseignants = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

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
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de tous les enseignants : " + ex.getMessage());
            ex.printStackTrace();
        }
        return enseignants;
    }

    /**
     * Met à jour un enseignant existant.
     */
    public boolean updateEnseignant(Enseignant enseignant) {
        String SQL = "UPDATE Enseignants SET nom = ?, email = ?, specialite = ?, id_utilisateur = ? WHERE id_enseignant = ?";
        boolean updated = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getEmail());
            pstmt.setString(3, enseignant.getSpecialite());
            pstmt.setInt(4, enseignant.getId_utilisateur());
            pstmt.setInt(5, enseignant.getId_enseignant());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Enseignant ID " + enseignant.getId_enseignant() + " mis à jour avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'enseignant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * Supprime un enseignant par son ID.
     * Note: La suppression en cascade via la clé étrangère sur Utilisateurs
     * supprimera aussi l'entrée correspondante dans la table Utilisateurs
     * si l'option ON DELETE CASCADE est activée sur la FK.
     */
    public boolean deleteEnseignant(int id) {
        String SQL = "DELETE FROM Enseignants WHERE id_enseignant = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Enseignant ID " + id + " supprimé avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'enseignant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }
}