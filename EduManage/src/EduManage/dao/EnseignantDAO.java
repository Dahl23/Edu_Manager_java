package EduManage.dao;

import EduManage.model.Enseignant;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO {
    private final Connection conn = DBConnection.getInstance();

    public void ajouter(Enseignant e) throws SQLException {
        String sql = "INSERT INTO enseignants (id_utilisateur, nom, email, specialite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getId_utilisateur());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getSpecialite());
            ps.executeUpdate();
        }
    }

    public List<Enseignant> lister() throws SQLException {
        List<Enseignant> liste = new ArrayList<>();
        String sql = "SELECT * FROM enseignants";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Enseignant e = new Enseignant();
                e.setIdEnseignant(rs.getInt("id_enseignant"));
                e.setNom(rs.getString("nom"));
                e.setEmail(rs.getString("email"));
                e.setSpecialite(rs.getString("specialite"));
                liste.add(e);
            }
        }
        return liste;
    }
}
