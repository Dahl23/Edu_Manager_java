package EduManage.controller;

import EduManage.dao.UtilisateurDAO;
import EduManage.model.Utilisateur;

import java.sql.SQLException;

public class UtilisateurController {
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur seConnecter(String nomUtilisateur, String motDePasse, String role) {
        return utilisateurDAO.authentifier(nomUtilisateur, motDePasse, role);
    }

    public void ajouterUtilisateur(Utilisateur u) throws SQLException {
        utilisateurDAO.ajouterUtilisateur(u);
    }
    
}
