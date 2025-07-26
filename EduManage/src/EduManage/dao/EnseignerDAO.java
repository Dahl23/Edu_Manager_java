package EduManage.dao;

import EduManage.model.Enseigner;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignerDAO {
    private final Connection conn = DBConnection.getInstance();

    public void affecterCours(int idEnseignant, int idCours) throws SQLException {
        String sql = "INSERT INTO enseigner (id_enseignant, id_cours) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEnseignant);
            ps.setInt(2, idCours);
            ps.executeUpdate();
        }
    }

    public List<Integer> getCoursParEnseignant(int idEnseignant) throws SQLException {
        List<Integer> liste = new ArrayList<>();
        String sql = "SELECT id_cours FROM enseigner WHERE id_enseignant = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEnseignant);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(rs.getInt("id_cours"));
            }
        }
        return liste;
    }
}
