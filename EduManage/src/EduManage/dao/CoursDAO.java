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
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
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

    public Cours getCoursParId(int idCours) throws SQLException {
        String sql = "SELECT * FROM cours WHERE id_cours = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCours);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cours(rs.getInt("id_cours"), rs.getString("libelle"), rs.getString("semestre"));
            }
        }
        return null;
    }

    // Nouvelle méthode : modifier un cours
    public void modifier(Cours c) throws SQLException {
        String sql = "UPDATE cours SET libelle = ?, semestre = ? WHERE id_cours = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getLibelle());
            ps.setString(2, c.getSemestre());
            ps.setInt(3, c.getIdCours());
            ps.executeUpdate();
        }
    }

    // Nouvelle méthode : supprimer un cours
    public void supprimer(int idCours) throws SQLException {
        String sql = "DELETE FROM cours WHERE id_cours = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCours);
            ps.executeUpdate();
        }
    }
}
