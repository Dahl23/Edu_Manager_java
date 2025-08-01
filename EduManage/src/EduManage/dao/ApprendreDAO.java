package EduManage.dao;

import EduManage.model.Apprendre;
import EduManage.model.Etudiant;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApprendreDAO {

    private final Connection conn = DBConnection.getInstance();

    public void inscrireEtudiantCours(int idEtudiant, int idCours) throws SQLException {
        String sql = "INSERT INTO apprendre (id_etudiant, id_cours) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEtudiant);
            ps.setInt(2, idCours);
            ps.executeUpdate();
        }
    }

    public List<Integer> getCoursParEtudiant(int idEtudiant) throws SQLException {
        List<Integer> liste = new ArrayList<>();
        String sql = "SELECT id_cours FROM apprendre WHERE id_etudiant = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEtudiant);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(rs.getInt("id_cours"));
            }
        }
        return liste;
    }

    public List<Etudiant> getEtudiantsParCours(int idCours) throws SQLException {
        List<Etudiant> etudiants = new ArrayList<>();

        String sql = "SELECT e.* FROM etudiants e "
                + "JOIN apprendre a ON e.id_etudiant = a.id_etudiant "
                + "WHERE a.id_cours = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCours);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setIdEtudiant(rs.getInt("id_etudiant"));
                e.setNom(rs.getString("nom"));
                e.setFiliere(rs.getString("filiere"));
                e.setNiveau(rs.getString("niveau"));
                etudiants.add(e);
            }
        }

        return etudiants;
    }

}
