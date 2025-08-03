package educationmanagement.controller;

import educationmanagement.dao.UtilisateurDAO;
import educationmanagement.model.Utilisateur;

public class AuthController {

    private UtilisateurDAO utilisateurDAO;

    public AuthController() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    /**
     * Tente d'authentifier un utilisateur.
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @return L'objet Utilisateur si l'authentification réussit, null sinon.
     */
    public Utilisateur authenticate(String username, String password) {
        Utilisateur utilisateur = utilisateurDAO.getUtilisateurByUsername(username);

        if (utilisateur != null && utilisateur.getMot_de_passe().equals(password)) {
            System.out.println("Authentification réussie pour : " + utilisateur.getNom_utilisateur() + " (" + utilisateur.getRole() + ")");
            return utilisateur;
        } else {
            System.out.println("Échec de l'authentification pour : " + username);
            return null;
        }
    }
}