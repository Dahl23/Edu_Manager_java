package EduManage.dao;

import EduManage.model.Utilisateur;
import EduManage.utils.DBConnection;

import java.sql.*;

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
//                u.setIdEtudiant(rs.getInt("id_etudiant"));
//                u.setIdEnseignant(rs.getInt("id_enseignant"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Ajout d’un nouvel utilisateur
    public int ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, role) VALUES (?, ?, ?)";

//        try (Connection conn = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, utilisateur.getNomUtilisateur());
            stmt.setString(2, utilisateur.getMotDePasse());
            stmt.setString(3, utilisateur.getRole());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // retourne l'ID généré
                } else {
                    throw new SQLException("Échec de la récupération de l'ID utilisateur généré.");
                }
            }
        }
    }
    
    //Deconnexion
    public void deconnecter(Utilisateur utilisateur) {
    utilisateur = null;
    System.out.println("Utilisateur déconnecté.");
}

}
