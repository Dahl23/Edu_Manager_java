package educationmanagement.dao;

import educationmanagement.model.Etudiant;
import educationmanagement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {

    /**
     * Ajoute un nouvel étudiant dans la base de données.
     * L'id_utilisateur doit déjà exister.
     * Retourne l'ID généré de l'étudiant ou -1 en cas d'échec.
     */
    public int addEtudiant(Etudiant etudiant) {
        String SQL = "INSERT INTO Etudiants (nom, matricule, email, niveau, filiere, id_utilisateur) VALUES (?, ?, ?, ?, ?, ?)";
        int idGenereted = -1;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, etudiant.getNom());
            pstmt.setString(2, etudiant.getMatricule());
            pstmt.setString(3, etudiant.getEmail());
            pstmt.setString(4, etudiant.getNiveau());
            pstmt.setString(5, etudiant.getFiliere());
            pstmt.setInt(6, etudiant.getId_utilisateur());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenereted = rs.getInt(1);
                        etudiant.setId_etudiant(idGenereted); // Mettre à jour l'objet avec l'ID généré
                        System.out.println("Etudiant ajouté avec succès. ID: " + idGenereted);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de l'étudiant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return idGenereted;
    }

    /**
     * Récupère un étudiant par son ID.
     */
    public Etudiant getEtudiantById(int id) {
        String SQL = "SELECT id_etudiant, nom, matricule, email, niveau, filiere, id_utilisateur FROM Etudiants WHERE id_etudiant = ?";
        Etudiant etudiant = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    etudiant = new Etudiant(
                        rs.getInt("id_etudiant"),
                        rs.getString("nom"),
                        rs.getString("matricule"),
                        rs.getString("email"),
                        rs.getString("niveau"),
                        rs.getString("filiere"),
                        rs.getInt("id_utilisateur")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'étudiant par ID : " + ex.getMessage());
            ex.printStackTrace();
        }
        return etudiant;
    }
    
    /**
     * Récupère un étudiant par son id_utilisateur.
     */
    public Etudiant getEtudiantByIdUtilisateur(int idUtilisateur) {
        String SQL = "SELECT id_etudiant, nom, matricule, email, niveau, filiere, id_utilisateur FROM Etudiants WHERE id_utilisateur = ?";
        Etudiant etudiant = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, idUtilisateur);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    etudiant = new Etudiant(
                        rs.getInt("id_etudiant"),
                        rs.getString("nom"),
                        rs.getString("matricule"),
                        rs.getString("email"),
                        rs.getString("niveau"),
                        rs.getString("filiere"),
                        rs.getInt("id_utilisateur")
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'étudiant par id_utilisateur : " + ex.getMessage());
            ex.printStackTrace();
        }
        return etudiant;
    }

    /**
     * Récupère tous les étudiants.
     */
    public List<Etudiant> getAllEtudiants() {
        String SQL = "SELECT id_etudiant, nom, matricule, email, niveau, filiere, id_utilisateur FROM Etudiants";
        List<Etudiant> etudiants = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

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
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de tous les étudiants : " + ex.getMessage());
            ex.printStackTrace();
        }
        return etudiants;
    }

    /**
     * Met à jour un étudiant existant.
     */
    public boolean updateEtudiant(Etudiant etudiant) {
        String SQL = "UPDATE Etudiants SET nom = ?, matricule = ?, email = ?, niveau = ?, filiere = ?, id_utilisateur = ? WHERE id_etudiant = ?";
        boolean updated = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, etudiant.getNom());
            pstmt.setString(2, etudiant.getMatricule());
            pstmt.setString(3, etudiant.getEmail());
            pstmt.setString(4, etudiant.getNiveau());
            pstmt.setString(5, etudiant.getFiliere());
            pstmt.setInt(6, etudiant.getId_utilisateur());
            pstmt.setInt(7, etudiant.getId_etudiant());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Etudiant ID " + etudiant.getId_etudiant() + " mis à jour avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'étudiant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

    /**
     * Supprime un étudiant par son ID.
     * Note: La suppression en cascade via la clé étrangère sur Utilisateurs
     * supprimera aussi l'entrée correspondante dans la table Utilisateurs
     * si l'option ON DELETE CASCADE est activée sur la FK.
     */
    public boolean deleteEtudiant(int id) {
        String SQL = "DELETE FROM Etudiants WHERE id_etudiant = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Etudiant ID " + id + " supprimé avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'étudiant : " + ex.getMessage());
            ex.printStackTrace();
        }
        return deleted;
    }
}