package EduManage.dao;

import EduManage.model.Utilisateur;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    private final Connection conn = DBConnection.getInstance();

    public Utilisateur authentifier(String nomUtilisateur, String motDePasse, String role) {
        String sql = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ? AND role = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomUtilisateur);
            ps.setString(2, motDePasse);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setIdUtilisateur(rs.getInt("id_utilisateur"));
                u.setNomUtilisateur(rs.getString("nom_utilisateur"));
                u.setMotDePasse(rs.getString("mot_de_passe"));
                u.setRole(rs.getString("role"));

                // 🔁 Récupérer ID étudiant ou enseignant
                if ("etudiant".equalsIgnoreCase(role)) {
                    String sqlEtu = "SELECT id_etudiant FROM etudiants WHERE id_utilisateur = ?";
                    try (PreparedStatement psEtu = conn.prepareStatement(sqlEtu)) {
                        psEtu.setInt(1, u.getIdUtilisateur());
                        ResultSet rsEtu = psEtu.executeQuery();
                        if (rsEtu.next()) {
                            u.setIdEtudiant(rsEtu.getInt("id_etudiant"));
                        }
                    }
                } else if ("enseignant".equalsIgnoreCase(role)) {
                    String sqlEns = "SELECT id_enseignant FROM enseignants WHERE id_utilisateur = ?";
                    try (PreparedStatement psEns = conn.prepareStatement(sqlEns)) {
                        psEns.setInt(1, u.getIdUtilisateur());
                        ResultSet rsEns = psEns.executeQuery();
                        if (rsEns.next()) {
                            u.setIdEnseignant(rsEns.getInt("id_enseignant"));
                        }
                    }
                }
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, utilisateur.getNomUtilisateur());
            stmt.setString(2, utilisateur.getMotDePasse());
            stmt.setString(3, utilisateur.getRole());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Échec de la récupération de l'ID utilisateur généré.");
                }
            }
        }
    }

    public List<Utilisateur> getEtudiantsParCours(int idCours) throws SQLException {
        List<Utilisateur> etudiants = new ArrayList<>();
        String sql = "SELECT u.* FROM utilisateurs u "
                   + "JOIN apprendre a ON u.id_utilisateur = a.id_etudiant "
                   + "WHERE a.id_cours = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCours);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setIdUtilisateur(rs.getInt("id_utilisateur"));
                u.setNomUtilisateur(rs.getString("nom_utilisateur"));
                u.setRole(rs.getString("role"));
                etudiants.add(u);
            }
        }
        return etudiants;
    }

    public void deconnecter(Utilisateur utilisateur) {
        utilisateur = null;
        System.out.println("Utilisateur déconnecté.");
    }

    // 🔍 Afficher tous les utilisateurs
    public List<Utilisateur> getTousLesUtilisateurs() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setIdUtilisateur(rs.getInt("id_utilisateur"));
                u.setNomUtilisateur(rs.getString("nom_utilisateur"));
                u.setMotDePasse(rs.getString("mot_de_passe"));
                u.setRole(rs.getString("role"));
                utilisateurs.add(u);
            }
        }
        return utilisateurs;
    }

    // ✏️ Modifier un utilisateur
    public void modifierUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "UPDATE utilisateurs SET nom_utilisateur = ?, mot_de_passe = ?, role = ? WHERE id_utilisateur = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utilisateur.getNomUtilisateur());
            ps.setString(2, utilisateur.getMotDePasse());
            ps.setString(3, utilisateur.getRole());
            ps.setInt(4, utilisateur.getIdUtilisateur());
            ps.executeUpdate();
        }
    }

    // ❌ Supprimer un utilisateur
    public void supprimerUtilisateur(int idUtilisateur) throws SQLException {
        String sql = "DELETE FROM utilisateurs WHERE id_utilisateur = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ps.executeUpdate();
        }
    }
}
