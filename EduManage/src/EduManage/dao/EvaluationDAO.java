package EduManage.dao;

import EduManage.model.Evaluation;
import EduManage.model.Cours;
import EduManage.model.Etudiant;
import EduManage.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDAO {
    private final Connection conn = DBConnection.getInstance();

    public void ajouterEvaluation(Evaluation evaluation) throws SQLException {
        String sql = "INSERT INTO evaluations (type_evaluation, note, id_etudiant, id_cours) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, evaluation.getTypeEvaluation());
            ps.setFloat(2, evaluation.getNote());
            ps.setInt(3, evaluation.getIdEtudiant());
            ps.setInt(4, evaluation.getIdCours());
            ps.executeUpdate();
        }
    }

    public List<Evaluation> lister() throws SQLException {
        List<Evaluation> liste = new ArrayList<>();
        String sql = "SELECT * FROM evaluations";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
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

    // Nouvelle méthode pour récupérer la liste des cours disponibles
    public List<Cours> getCoursDisponibles() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String sql = "SELECT id_cours, libelle FROM cours";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
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
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
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
  
  
  
//        public List<Cours> getCoursDisponibles() throws SQLException {
//        List<Cours> liste = new ArrayList<>();
//
//        String sql = """
//            SELECT DISTINCT c.id_cours, c.libelle
//            FROM cours c
//            JOIN enseigner e ON c.id_cours = e.id_cours
//            WHERE e.id_enseignant = ?
//            """;
//
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, idEnseignant);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Cours cours = new Cours();
//                    cours.setIdCours(rs.getInt("id_cours"));
//                    cours.setLibelle(rs.getString("libelle"));
//                    liste.add(cours);
//                }
//            }
//        }
//
//        return liste;
}

    
    
//        public List<Etudiant> getEtudiantsDisponibles(int idEnseignant) throws SQLException {
//            List<Etudiant> liste = new ArrayList<>();
//
//            String sql = """
//                SELECT DISTINCT u.id_utilisateur, u.nom_utilisateur
//                FROM utilisateurs u
//                JOIN apprendre a ON u.id_utilisateur = a.id_etudiant
//                JOIN cours c ON a.id_cours = c.id_cours
//                JOIN enseigner e ON c.id_cours = e.id_cours
//                WHERE e.id_enseignant = ?
//                AND u.role = 'etudiant'
//                """;
//
//            try (PreparedStatement ps = conn.prepareStatement(sql)) {
//                ps.setInt(1, idEnseignant);
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        Etudiant etudiant = new Etudiant();
//                        etudiant.setIdEtudiant(rs.getInt("id_utilisateur"));
//                        etudiant.setNom(rs.getString("nom"));
//                        liste.add(etudiant);
//                    }
//                }
//            }
//
//            return liste;
//        }

    }


