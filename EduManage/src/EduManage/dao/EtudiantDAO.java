package EduManage.dao;

import EduManage.model.Etudiant;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    private final Connection conn = DBConnection.getInstance();

    public void ajouter(Etudiant e) throws SQLException {
        String sql = "INSERT INTO etudiants (nom, matricule, email, niveau) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getMatricule());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getNiveau());
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
                liste.add(e);
            }
        }
        return liste;
    }

    public void ajouter(Etudiant etudiant, int idUtilisateur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
