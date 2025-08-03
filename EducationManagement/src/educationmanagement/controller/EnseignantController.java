package educationmanagement.controller;

import educationmanagement.dao.EnseignantDAO;
import educationmanagement.dao.EnseignerDAO;
import educationmanagement.dao.EvaluationDAO;
import educationmanagement.dao.EtudiantDAO; // Peut être nécessaire pour récupérer les détails des étudiants
import educationmanagement.dao.CoursDAO;   // Peut être nécessaire pour récupérer les détails des cours

import educationmanagement.model.Enseignant;
import educationmanagement.model.Cours;
import educationmanagement.model.Evaluation;
import educationmanagement.model.Etudiant;

import java.util.List;

public class EnseignantController {

    private EnseignantDAO enseignantDAO;
    private EnseignerDAO enseignerDAO;
    private EvaluationDAO evaluationDAO;
    private EtudiantDAO etudiantDAO; // Pour récupérer des détails d'étudiants
    private CoursDAO coursDAO;       // Pour récupérer des détails de cours

    public EnseignantController() {
        this.enseignantDAO = new EnseignantDAO();
        this.enseignerDAO = new EnseignerDAO();
        this.evaluationDAO = new EvaluationDAO();
        this.etudiantDAO = new EtudiantDAO();
        this.coursDAO = new CoursDAO();
    }

    /**
     * Récupère l'objet Enseignant complet à partir de son id_utilisateur.
     * Utile après l'authentification.
     * @param idUtilisateur L'ID de l'utilisateur authentifié.
     * @return L'objet Enseignant correspondant.
     */
    public Enseignant getEnseignantByUtilisateurId(int idUtilisateur) {
        return enseignantDAO.getEnseignantByIdUtilisateur(idUtilisateur);
    }

    /**
     * Récupère la liste des cours qu'un enseignant spécifique enseigne.
     * @param idEnseignant L'ID de l'enseignant.
     * @return Une liste d'objets Cours.
     */
    public List<Cours> getAssignedCourses(int idEnseignant) {
        return enseignerDAO.getCoursByEnseignantId(idEnseignant);
    }

    /**
     * Récupère la liste des étudiants inscrits à un cours donné (enseigné par cet enseignant).
     * @param idCours L'ID du cours.
     * @return Une liste d'objets Etudiant.
     */
    public List<Etudiant> getStudentsInCourse(int idCours) {
        // Cette méthode existe dans ApprendreDAO, mais un enseignant pourrait en avoir besoin
        // pour voir la liste de ses étudiants pour un cours donné.
        // Assurez-vous que l'enseignant est bien affecté à ce cours avant d'appeler cette méthode
        // dans la logique de l'interface utilisateur.
        return new educationmanagement.dao.ApprendreDAO().getEtudiantsByCoursId(idCours);
    }

    /**
     * Ajoute une nouvelle évaluation ou met à jour une évaluation existante.
     * @param evaluation L'objet Evaluation à ajouter/modifier.
     * @return true si l'opération est réussie, false sinon.
     */
    public boolean addOrUpdateEvaluation(Evaluation evaluation) {
        // Vérifier si une évaluation du même type existe déjà pour cet étudiant et ce cours
        List<Evaluation> existingEvaluations = evaluationDAO.getEvaluationsByEtudiantAndCours(
            evaluation.getId_etudiant(), evaluation.getId_cours()
        );

        for (Evaluation existingEval : existingEvaluations) {
            if (existingEval.getType_evaluation().equalsIgnoreCase(evaluation.getType_evaluation())) {
                // Si une évaluation du même type existe, la mettre à jour
                evaluation.setId_evaluation(existingEval.getId_evaluation()); // Assigner l'ID existant
                return evaluationDAO.updateEvaluation(evaluation);
            }
        }
        // Si aucune évaluation du même type n'existe, en ajouter une nouvelle
        return evaluationDAO.addEvaluation(evaluation) != -1;
    }

    /**
     * Récupère toutes les évaluations pour un étudiant dans un cours donné.
     * @param idEtudiant L'ID de l'étudiant.
     * @param idCours L'ID du cours.
     * @return Une liste d'objets Evaluation.
     */
    public List<Evaluation> getEvaluationsForStudentInCourse(int idEtudiant, int idCours) {
        return evaluationDAO.getEvaluationsByEtudiantAndCours(idEtudiant, idCours);
    }

    /**
     * Supprime une évaluation.
     * @param idEvaluation L'ID de l'évaluation à supprimer.
     * @return true si la suppression est réussie, false sinon.
     */
    public boolean deleteEvaluation(int idEvaluation) {
        return evaluationDAO.deleteEvaluation(idEvaluation);
    }
}