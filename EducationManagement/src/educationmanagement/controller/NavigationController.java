package educationmanagement.controller;

import educationmanagement.model.Utilisateur;
import educationmanagement.view.AdminDashboardFrame;
import educationmanagement.view.EnseignantDashboardFrame;
import educationmanagement.view.EtudiantDashboardFrame;
import educationmanagement.view.AuthFrame;

import javax.swing.JFrame; // Pour fermer la fenêtre actuelle si nécessaire

public class NavigationController {

    public static void redirectToDashboard(JFrame currentFrame, Utilisateur utilisateur) {
        if (utilisateur == null) {
            System.err.println("Redirection échouée : Utilisateur est null.");
            return;
        }

        JFrame nextFrame = null;
        switch (utilisateur.getRole()) {
            case "Admin":
                nextFrame = new AdminDashboardFrame(utilisateur); // Passer l'utilisateur au tableau de bord
                break;
            case "Enseignant":
//                nextFrame = new EnseignantDashboardFrame(utilisateur);
                break;
            case "Etudiant":
//                nextFrame = new EtudiantDashboardFrame(utilisateur);
                break;
            default:
                System.err.println("Rôle inconnu pour la redirection : " + utilisateur.getRole());
                return; // Ne rien faire ou montrer une erreur
        }

        if (nextFrame != null) {
            nextFrame.setVisible(true);
            if (currentFrame != null) {
                currentFrame.dispose(); // Ferme la fenêtre actuelle (ex: AuthFrame)
            }
        }
    }

    public static void logout(JFrame currentFrame) {
        AuthFrame authFrame = new AuthFrame();
        authFrame.setVisible(true);
        if (currentFrame != null) {
            currentFrame.dispose(); // Ferme la fenêtre du tableau de bord
        }
        System.out.println("Déconnexion réussie.");
    }
}