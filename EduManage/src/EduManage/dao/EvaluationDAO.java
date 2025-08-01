package EduManage.dao;

import EduManage.model.Evaluation;
import EduManage.model.Cours;
import EduManage.model.Etudiant;
import EduManage.model.Utilisateur;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDAO {

    private final Connection conn = DBConnection.getInstance();

//    public void ajouterEvaluation(Evaluation evaluation) throws SQLException {
//        String sql = "INSERT INTO evaluations (type_evaluation, note, id_etudiant, id_cours) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, evaluation.getTypeEvaluation());
//            ps.setFloat(2, evaluation.getNote());
//            ps.setInt(3, evaluation.getIdEtudiant());
//            ps.setInt(4, evaluation.getIdCours());
//            ps.executeUpdate();
//        }
//    }
    public void ajouterEvaluation(Evaluation e) throws SQLException {
        String sql = "INSERT INTO evaluations (id_etudiant, id_cours, type_evaluation, note) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getEtudiant().getIdEtudiant());
            stmt.setInt(2, e.getCours().getIdCours());
            stmt.setString(3, e.getTypeEvaluation());
            stmt.setFloat(4, e.getNote());
            stmt.executeUpdate();
        }
    }

    public List<Evaluation> getEvaluationsParCours(int idCours) throws SQLException {
        List<Evaluation> liste = new ArrayList<>();
        String sql = "SELECT e.*, u.nom FROM evaluations e "
                + "JOIN utilisateurs u ON u.id_utilisateur = e.id_etudiant "
                + "WHERE e.id_cours = ?";
        try (Connection conn = DBConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCours);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur etudiant = new Utilisateur();
                etudiant.setIdUtilisateur(rs.getInt("id_etudiant"));
                etudiant.setNom(rs.getString("nom"));

                Evaluation ev = new Evaluation();
                ev.setEtudiant(etudiant);
                ev.setTypeEvaluation(rs.getString("type_evaluation"));
                ev.setNote(rs.getFloat("note"));

                liste.add(ev);
            }
        }
        return liste;
    }

    public List<Evaluation> lister() throws SQLException {
        List<Evaluation> liste = new ArrayList<>();
        String sql = "SELECT * FROM evaluations";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Evaluation e = new Evaluation();
                e.setIdEvaluation(rs.getInt("id_evaluation"));
                e.setTypeEvaluation(rs.getString("type_evaluation"));
                e.setNote(rs.getFloat("note"));
                e.setIdEtudiant(rs.getInt("id_etudiant"));
                e.setIdCours(rs.getInt("id_cours"));
                liste.add(e);
            }
        }
        return liste;
    }

    //Méthode pour récupérer la liste des cours disponibles
    public List<Cours> getCoursDisponibles() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String sql = "SELECT id_cours, libelle FROM cours";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cours c = new Cours();
                c.setIdCours(rs.getInt("id_cours"));
                c.setLibelle(rs.getString("libelle"));
                coursList.add(c);
            }
        }
        return coursList;
    }

    public List<Etudiant> getEtudiantsDisponibles() throws SQLException {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiants";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setIdEtudiant(rs.getInt("id_etudiant"));
                e.setNom(rs.getString("nom"));
                e.setMatricule(rs.getString("matricule"));
                e.setEmail(rs.getString("email"));
                e.setNiveau(rs.getString("niveau"));
                e.setFiliere(rs.getString("filiere"));
                etudiants.add(e);
            }
        }
        return etudiants;
    }

    public List<Evaluation> getEvaluationsByEtudiant(int idEtudiant) {
        List<Evaluation> evaluations = new ArrayList<>();
        String sql = "SELECT e.ID_EVALUATION, e.TYPE_EVALUATION, e.NOTE, "
                + "e.ID_ETUDIANT, e.ID_COURS, c.libelle AS libelle "
                + "FROM evaluations e "
                + "JOIN cours c ON e.ID_COURS = c.ID_COURS "
                + "WHERE e.ID_ETUDIANT = ?";

        try (Connection conn = DBConnection.getInstance(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEtudiant);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Evaluation e = new Evaluation();
                e.setIdEvaluation(rs.getInt("id_evaluation"));
                e.setTypeEvaluation(rs.getString("type_evaluation"));
                e.setNote(rs.getFloat("note"));
                e.setIdEtudiant(rs.getInt("id_etudiant"));
                e.setIdCours(rs.getInt("id_cours"));
                e.setLibelle(rs.getString("libelle")); // Nom du cours

                evaluations.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return evaluations;
    }

}
