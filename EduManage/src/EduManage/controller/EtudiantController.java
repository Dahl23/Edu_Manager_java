package EduManage.controller;

import EduManage.model.Etudiant;
import EduManage.model.Utilisateur;
import EduManage.dao.EtudiantDAO;
import EduManage.dao.UtilisateurDAO;

import java.sql.SQLException;
import java.util.List;

public class EtudiantController {
    private final EtudiantDAO etudiantDAO = new EtudiantDAO();
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public void ajouterEtudiant(Etudiant etudiant) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(etudiant.getEmail());
        utilisateur.setMotDePasse("1234"); // mot de passe par défaut
        utilisateur.setRole("etudiant");

        int idUtilisateur = utilisateurDAO.ajouterUtilisateur(utilisateur);
        etudiantDAO.ajouter(etudiant, idUtilisateur);
    }

    public List<Etudiant> getTousLesEtudiants() throws SQLException {
        return etudiantDAO.lister();
    }
}
