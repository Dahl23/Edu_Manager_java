package educationmanagement.controller;

import educationmanagement.dao.UtilisateurDAO;
import educationmanagement.dao.EtudiantDAO;
import educationmanagement.dao.EnseignantDAO;
import educationmanagement.dao.CoursDAO;
import educationmanagement.dao.ApprendreDAO;
import educationmanagement.dao.EnseignerDAO;

import educationmanagement.model.Utilisateur;
import educationmanagement.model.Etudiant;
import educationmanagement.model.Enseignant;
import educationmanagement.model.Cours;

import java.util.List;

public class AdminController {

    private UtilisateurDAO utilisateurDAO;
    private EtudiantDAO etudiantDAO;
    private EnseignantDAO enseignantDAO;
    private CoursDAO coursDAO;
    private ApprendreDAO apprendreDAO;
    private EnseignerDAO enseignerDAO;

    public AdminController() {
        this.utilisateurDAO = new UtilisateurDAO();
        this.etudiantDAO = new EtudiantDAO();
        this.enseignantDAO = new EnseignantDAO();
        this.coursDAO = new CoursDAO();
        this.apprendreDAO = new ApprendreDAO();
        this.enseignerDAO = new EnseignerDAO();
    }

    // --- Gestion des Utilisateurs (profils de connexion) ---

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.getAllUtilisateurs();
    }
    
    public int addUtilisateur(Utilisateur utilisateur) {
        return utilisateurDAO.addUtilisateur(utilisateur);
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurDAO.getUtilisateurById(id);
    }

    public boolean updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurDAO.updateUtilisateur(utilisateur);
    }

    public boolean deleteUtilisateur(int id) {
        // La suppression d'un utilisateur devrait idéalement aussi supprimer l'étudiant/enseignant associé
        // grâce à ON DELETE CASCADE sur la base de données.
        // Sinon, il faudrait récupérer l'id_etudiant/id_enseignant et les supprimer manuellement d'abord.
        return utilisateurDAO.deleteUtilisateur(id);
    }

    // --- Gestion des Étudiants ---

    /**
     * Ajoute un nouvel étudiant. Crée d'abord un profil utilisateur, puis l'étudiant.
     * @param etudiant L'objet Etudiant avec les infos (sans id_etudiant, sans id_utilisateur).
     * @param nomUtilisateur Le nom d'utilisateur pour la connexion.
     * @param motDePasse Le mot de passe pour la connexion.
     * @return true si l'étudiant et son profil utilisateur sont ajoutés avec succès, false sinon.
     */
    public boolean addEtudiant(Etudiant etudiant, String nomUtilisateur, String motDePasse) {
        // 1. Créer le profil Utilisateur
        Utilisateur user = new Utilisateur(nomUtilisateur, motDePasse, "Etudiant");
        int idUtilisateur = utilisateurDAO.addUtilisateur(user);

        if (idUtilisateur != -1) {
            // 2. Associer l'id_utilisateur à l'objet Etudiant et ajouter l'étudiant
            etudiant.setId_utilisateur(idUtilisateur);
            int idEtudiant = etudiantDAO.addEtudiant(etudiant);
            return idEtudiant != -1;
        }
        System.err.println("Échec de la création du profil utilisateur pour l'étudiant.");
        return false;
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantDAO.getAllEtudiants();
    }

    public Etudiant getEtudiantById(int id) {
        return etudiantDAO.getEtudiantById(id);
    }

    /**
     * Met à jour les informations d'un étudiant et potentiellement son profil utilisateur.
     * @param etudiant L'objet Etudiant avec les infos mises à jour.
     * @param nomUtilisateur Le nouveau nom d'utilisateur (si modifié).
     * @param motDePasse Le nouveau mot de passe (si modifié).
     * @return true si la mise à jour est réussie, false sinon.
     */
    public boolean updateEtudiant(Etudiant etudiant, String nomUtilisateur, String motDePasse) {
        // 1. Mettre à jour l'utilisateur associé si nom d'utilisateur ou mot de passe change
        Utilisateur user = utilisateurDAO.getUtilisateurById(etudiant.getId_utilisateur());
        if (user != null) {
            user.setNom_utilisateur(nomUtilisateur);
            user.setMot_de_passe(motDePasse);
            if (!utilisateurDAO.updateUtilisateur(user)) {
                System.err.println("Échec de la mise à jour du profil utilisateur pour l'étudiant.");
                return false;
            }
        } else {
             System.err.println("Profil utilisateur introuvable pour l'étudiant ID " + etudiant.getId_etudiant());
             return false; // Ou gérer cette erreur différemment si l'ID utilisateur est nul
        }
        // 2. Mettre à jour l'étudiant
        return etudiantDAO.updateEtudiant(etudiant);
    }

    public boolean deleteEtudiant(int id) {
        Etudiant etudiant = etudiantDAO.getEtudiantById(id);
        if (etudiant != null) {
            // Supprimer l'étudiant (ce qui devrait CASCADE sur Utilisateur grâce à la BD)
            return etudiantDAO.deleteEtudiant(id);
        }
        return false;
    }

    // --- Gestion des Enseignants ---

    /**
     * Ajoute un nouvel enseignant. Crée d'abord un profil utilisateur, puis l'enseignant.
     * @param enseignant L'objet Enseignant avec les infos (sans id_enseignant, sans id_utilisateur).
     * @param nomUtilisateur Le nom d'utilisateur pour la connexion.
     * @param motDePasse Le mot de passe pour la connexion.
     * @return true si l'enseignant et son profil utilisateur sont ajoutés avec succès, false sinon.
     */
    public boolean addEnseignant(Enseignant enseignant, String nomUtilisateur, String motDePasse) {
        // 1. Créer le profil Utilisateur
        Utilisateur user = new Utilisateur(nomUtilisateur, motDePasse, "Enseignant");
        int idUtilisateur = utilisateurDAO.addUtilisateur(user);

        if (idUtilisateur != -1) {
            // 2. Associer l'id_utilisateur à l'objet Enseignant et ajouter l'enseignant
            enseignant.setId_utilisateur(idUtilisateur);
            int idEnseignant = enseignantDAO.addEnseignant(enseignant);
            return idEnseignant != -1;
        }
        System.err.println("Échec de la création du profil utilisateur pour l'enseignant.");
        return false;
    }

    public List<Enseignant> getAllEnseignants() {
        return enseignantDAO.getAllEnseignants();
    }

    public Enseignant getEnseignantById(int id) {
        return enseignantDAO.getEnseignantById(id);
    }

    /**
     * Met à jour les informations d'un enseignant et potentiellement son profil utilisateur.
     * @param enseignant L'objet Enseignant avec les infos mises à jour.
     * @param nomUtilisateur Le nouveau nom d'utilisateur (si modifié).
     * @param motDePasse Le nouveau mot de passe (si modifié).
     * @return true si la mise à jour est réussie, false sinon.
     */
    public boolean updateEnseignant(Enseignant enseignant, String nomUtilisateur, String motDePasse) {
        // 1. Mettre à jour l'utilisateur associé si nom d'utilisateur ou mot de passe change
        Utilisateur user = utilisateurDAO.getUtilisateurById(enseignant.getId_utilisateur());
        if (user != null) {
            user.setNom_utilisateur(nomUtilisateur);
            user.setMot_de_passe(motDePasse);
            if (!utilisateurDAO.updateUtilisateur(user)) {
                System.err.println("Échec de la mise à jour du profil utilisateur pour l'enseignant.");
                return false;
            }
        } else {
             System.err.println("Profil utilisateur introuvable pour l'enseignant ID " + enseignant.getId_enseignant());
             return false;
        }
        // 2. Mettre à jour l'enseignant
        return enseignantDAO.updateEnseignant(enseignant);
    }

    public boolean deleteEnseignant(int id) {
        Enseignant enseignant = enseignantDAO.getEnseignantById(id);
        if (enseignant != null) {
            // Supprimer l'enseignant (ce qui devrait CASCADE sur Utilisateur grâce à la BD)
            return enseignantDAO.deleteEnseignant(id);
        }
        return false;
    }

    // --- Gestion des Cours (seul l'admin ajoute/modifie/supprime des cours) ---

    public int addCours(Cours cours) {
        return coursDAO.addCours(cours);
    }

    public List<Cours> getAllCours() {
        return coursDAO.getAllCours();
    }

    public Cours getCoursById(int id) {
        return coursDAO.getCoursById(id);
    }

    public boolean updateCours(Cours cours) {
        return coursDAO.updateCours(cours);
    }

    public boolean deleteCours(int id) {
        return coursDAO.deleteCours(id);
    }

    // --- Gestion des Affectations (enseignants aux cours) ---

    public boolean affecterEnseignantACours(int idEnseignant, int idCours) {
        return enseignerDAO.affecterEnseignantACours(idEnseignant, idCours);
    }

    public boolean desaffecterEnseignantDuCours(int idEnseignant, int idCours) {
        return enseignerDAO.desaffecterEnseignantDuCours(idEnseignant, idCours);
    }

    public List<Cours> getCoursByEnseignantId(int idEnseignant) {
        return enseignerDAO.getCoursByEnseignantId(idEnseignant);
    }
    
    public List<Enseignant> getEnseignantsByCoursId(int idCours) {
        return enseignerDAO.getEnseignantsByCoursId(idCours);
    }

    // --- Gestion des Inscriptions (étudiants aux cours) ---

    public boolean inscrireEtudiantACours(int idEtudiant, int idCours) {
        return apprendreDAO.inscrireEtudiantACours(idEtudiant, idCours);
    }

    public boolean desinscrireEtudiantDuCours(int idEtudiant, int idCours) {
        return apprendreDAO.desinscrireEtudiantDuCours(idEtudiant, idCours);
    }

    public List<Cours> getCoursByEtudiantId(int idEtudiant) {
        return apprendreDAO.getCoursByEtudiantId(idEtudiant);
    }
    
    public List<Etudiant> getEtudiantsByCoursId(int idCours) {
        return apprendreDAO.getEtudiantsByCoursId(idCours);
    }
}