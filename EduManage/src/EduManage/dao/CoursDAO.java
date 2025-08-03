package EduManage.dao;

import EduManage.model.Cours;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAO {
    private final Connection conn = DBConnection.getInstance();

    public void ajouter(Cours c) throws SQLException {
        String sql = "INSERT INTO cours (libelle, semestre) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getLibelle());
            ps.setString(2, c.getSemestre());
            ps.executeUpdate();
        }
    }

    public List<Cours> lister() throws SQLException {
        List<Cours> liste = new ArrayList<>();
        String sql = "SELECT * FROM cours";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cours c = new Cours();
                c.setIdCours(rs.getInt("id_cours"));
                c.setLibelle(rs.getString("libelle"));
                c.setSemestre(rs.getString("semestre"));
                liste.add(c);
            }
        }
        return liste;
    }
}
