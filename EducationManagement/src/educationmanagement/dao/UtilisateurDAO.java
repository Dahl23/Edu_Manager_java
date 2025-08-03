package educationmanagement.dao;

import educationmanagement.model.Utilisateur;
import educationmanagement.util.DBConnection; // Import de notre classe de connexion
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Pour récupérer l'ID généré automatiquement
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    /**
     * Ajoute un nouvel utilisateur dans la base de données.
     * Retourne l'ID généré de l'utilisateur ou -1 en cas d'échec.
     */
    public int addUtilisateur(Utilisateur utilisateur) {
        String SQL = "INSERT INTO Utilisateurs (nom_utilisateur, mot_de_passe, role) VALUES (?, ?, ?)";
        int idGenereted = -1;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, utilisateur.getNom_utilisateur());
            pstmt.setString(2, utilisateur.getMot_de_passe());
            pstmt.setString(3, utilisateur.getRole());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenereted = rs.getInt(1);
                        utilisateur.setId_utilisateur(idGenereted); // Mettre à jour l'objet avec l'ID généré
                        System.out.println("Utilisateur ajouté avec succès. ID: " + idGenereted);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + ex.getMessage());
            ex.printStackTrace();
        }
        return idGenereted;
    }

    /**
     * Récupère un utilisateur par son ID.
     */
    public Utilisateur getUtilisateurById(int id) {
        String SQL = "SELECT id_utilisateur, nom_utilisateur, mot_de_passe, role FROM Utilisateurs WHERE id_utilisateur = ?";
        Utilisateur utilisateur = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'utilisateur par ID : " + ex.getMessage());
            ex.printStackTrace();
        }
        return utilisateur;
    }
    
    /**
     * Récupère un utilisateur par son nom d'utilisateur (utile pour la connexion).
     */
    public Utilisateur getUtilisateurByUsername(String username) {
        String SQL = "SELECT id_utilisateur, nom_utilisateur, mot_de_passe, role FROM Utilisateurs WHERE nom_utilisateur = ?";
        Utilisateur utilisateur = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'utilisateur par nom d'utilisateur : " + ex.getMessage());
            ex.printStackTrace();
        }
        return utilisateur;
    }

    /**
     * Récupère tous les utilisateurs.
     */
    public List<Utilisateur> getAllUtilisateurs() {
        String SQL = "SELECT id_utilisateur, nom_utilisateur, mot_de_passe, role FROM Utilisateurs";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                    rs.getInt("id_utilisateur"),
                    rs.getString("nom_utilisateur"),
                    rs.getString("mot_de_passe"),
                    rs.getString("role")
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de tous les utilisateurs : " + ex.getMessage());
            ex.printStackTrace();
        }
        return utilisateurs;
    }

    /**
     * Met à jour un utilisateur existant.
     * Le mot de passe peut être mis à jour ou non selon la logique métier.
     */
    public boolean updateUtilisateur(Utilisateur utilisateur) {
        String SQL = "UPDATE Utilisateurs SET nom_utilisateur = ?, mot_de_passe = ?, role = ? WHERE id_utilisateur = ?";
        boolean updated = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, utilisateur.getNom_utilisateur());
            pstmt.setString(2, utilisateur.getMot_de_passe());
            pstmt.setString(3, utilisateur.getRole());
            pstmt.setInt(4, utilisateur.getId_utilisateur());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Utilisateur ID " + utilisateur.getId_utilisateur() + " mis à jour avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * Supprime un utilisateur par son ID.
     */
    public boolean deleteUtilisateur(int id) {
        String SQL = "DELETE FROM Utilisateurs WHERE id_utilisateur = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Utilisateur ID " + id + " supprimé avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }
}