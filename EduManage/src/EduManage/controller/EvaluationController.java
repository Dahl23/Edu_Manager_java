package EduManage.controller;

import EduManage.dao.EvaluationDAO;
import EduManage.model.Cours;
import EduManage.model.Etudiant;
import EduManage.model.Evaluation;

import java.sql.SQLException;
import java.util.List;

public class EvaluationController {
    private final EvaluationDAO evaluationDAO = new EvaluationDAO();

//    public void ajouterEvaluation(Evaluation e) throws SQLException {
//        evaluationDAO.ajouter(e);
//    }

    public List<Evaluation> getToutesLesEvaluations() throws SQLException {
        return evaluationDAO.lister();
    }
    
    public void ajouterEvaluation(int idEtudiant, int idCours, String type, float note) throws SQLException {
    Evaluation evaluation = new Evaluation();
    evaluation.setIdEtudiant(idEtudiant);
    evaluation.setIdCours(idCours);
    evaluation.setTypeEvaluation(type);
    evaluation.setNote(note);
    evaluationDAO.ajouterEvaluation(evaluation);
}
    
    public List<Cours> getCoursDisponibles() throws SQLException {
        return evaluationDAO.getCoursDisponibles();
    }

   
    public List<Etudiant> getEtudiantsDisponibles() throws SQLException {
        return evaluationDAO.getEtudiantsDisponibles();
    }

    public List<Evaluation> getEvaluationsByEtudiant(int idEtudiant) {
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        return evaluationDAO.getEvaluationsByEtudiant(idEtudiant);
    }


}
