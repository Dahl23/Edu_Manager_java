package EduManage.controller;

import EduManage.dao.UtilisateurDAO;
import EduManage.model.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public class UtilisateurController {
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur seConnecter(String nomUtilisateur, String motDePasse, String role) {
        return utilisateurDAO.authentifier(nomUtilisateur, motDePasse, role);
    }

    public void ajouterUtilisateur(Utilisateur u) throws SQLException {
        utilisateurDAO.ajouterUtilisateur(u);
    }

    // ✅ Nouvelle méthode : Lister tous les utilisateurs
    public List<Utilisateur> listerUtilisateurs() throws SQLException {
        return utilisateurDAO.getTousLesUtilisateurs();
    }

    // ✅ Nouvelle méthode : Modifier un utilisateur
    public void modifierUtilisateur(Utilisateur u) throws SQLException {
        utilisateurDAO.modifierUtilisateur(u);
    }

    // ✅ Nouvelle méthode : Supprimer un utilisateur
    public void supprimerUtilisateur(int idUtilisateur) throws SQLException {
        utilisateurDAO.supprimerUtilisateur(idUtilisateur);
    }
}
