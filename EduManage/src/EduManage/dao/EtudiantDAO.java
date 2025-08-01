package EduManage.dao;

import EduManage.model.Etudiant;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    private final Connection conn = DBConnection.getInstance();

    public void ajouter(Etudiant etudiant, int idUtilisateur) throws SQLException {
        String sql = "INSERT INTO etudiants (id_utilisateur, nom, matricule, email, niveau, filiere) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, etudiant.getId_utilisateur());
            ps.setString(2, etudiant.getNom());
            ps.setString(3, etudiant.getMatricule());
            ps.setString(4, etudiant.getEmail());
            ps.setString(5, etudiant.getNiveau());
            ps.setString(6, etudiant.getFiliere());
            ps.executeUpdate();
        }
    }

    public List<Etudiant> lister() throws SQLException {
        List<Etudiant> liste = new ArrayList<>();
        String sql = "SELECT * FROM etudiants";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setIdEtudiant(rs.getInt("id_etudiant"));
                e.setNom(rs.getString("nom"));
                e.setMatricule(rs.getString("matricule"));
                e.setEmail(rs.getString("email"));
                e.setNiveau(rs.getString("niveau"));
                e.setFiliere(rs.getString("Filiere"));
                liste.add(e);
            }
        }
        return liste;
    }

//    public void ajouter(Etudiant etudiant, int idUtilisateur) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}
