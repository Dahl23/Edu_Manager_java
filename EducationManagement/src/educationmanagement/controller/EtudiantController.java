package educationmanagement.controller;

import educationmanagement.dao.EtudiantDAO;
import educationmanagement.dao.ApprendreDAO;
import educationmanagement.dao.EvaluationDAO;

import educationmanagement.model.Etudiant;
import educationmanagement.model.Cours;
import educationmanagement.model.Evaluation;

import java.util.List;

public class EtudiantController {

    private EtudiantDAO etudiantDAO;
    private ApprendreDAO apprendreDAO;
    private EvaluationDAO evaluationDAO;

    public EtudiantController() {
        this.etudiantDAO = new EtudiantDAO();
        this.apprendreDAO = new ApprendreDAO();
        this.evaluationDAO = new EvaluationDAO();
    }

    /**
     * Récupère l'objet Etudiant complet à partir de son id_utilisateur.
     * Utile après l'authentification.
     * @param idUtilisateur L'ID de l'utilisateur authentifié.
     * @return L'objet Etudiant correspondant.
     */
    public Etudiant getEtudiantByUtilisateurId(int idUtilisateur) {
        return etudiantDAO.getEtudiantByIdUtilisateur(idUtilisateur);
    }

    /**
     * Récupère la liste des cours auxquels un étudiant est inscrit.
     * @param idEtudiant L'ID de l'étudiant.
     * @return Une liste d'objets Cours.
     */
    public List<Cours> getEnrolledCourses(int idEtudiant) {
        return apprendreDAO.getCoursByEtudiantId(idEtudiant);
    }

    /**
     * Récupère toutes les évaluations (notes) d'un étudiant.
     * @param idEtudiant L'ID de l'étudiant.
     * @return Une liste d'objets Evaluation.
     */
    public List<Evaluation> getMyGrades(int idEtudiant) {
        return evaluationDAO.getEvaluationsByEtudiantId(idEtudiant);
    }
    
    /**
     * Récupère les évaluations spécifiques d'un étudiant pour un cours donné.
     * @param idEtudiant L'ID de l'étudiant.
     * @param idCours L'ID du cours.
     * @return Une liste d'objets Evaluation.
     */
    public List<Evaluation> getMyGradesForCourse(int idEtudiant, int idCours) {
        return evaluationDAO.getEvaluationsByEtudiantAndCours(idEtudiant, idCours);
    }
} 