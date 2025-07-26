package EduManage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Dashboard Admin - EduManage");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Espace Administrateur", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnAjoutEtudiant = new JButton("Ajouter Étudiant");
        JButton btnAjoutEnseignant = new JButton("Ajouter Enseignant");
        JButton btnAjoutCours = new JButton("Ajouter Cours");
        JButton btnAssocierEtudiantCours = new JButton("Inscrire Étudiant à un cours");
        JButton btnAssocierEnseignantCours = new JButton("Assigner Enseignant à un cours");
        JButton btnDeconnexion = new JButton("Déconnexion");
        buttonPanel.add(btnAjoutEtudiant);
        buttonPanel.add(btnAjoutEnseignant);
        buttonPanel.add(btnAjoutCours);
        buttonPanel.add(btnAssocierEtudiantCours);
        buttonPanel.add(btnAssocierEnseignantCours);
        buttonPanel.add(btnDeconnexion);
        

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        btnAjoutEtudiant.addActionListener((ActionEvent e) -> new AjoutEtudiantView().setVisible(true));
        btnAjoutEnseignant.addActionListener((ActionEvent e) -> new AjoutEnseignantView().setVisible(true));
        btnAjoutCours.addActionListener((ActionEvent e) -> new AjoutCoursView().setVisible(true));
        btnAssocierEtudiantCours.addActionListener((ActionEvent e) -> new InscriptionEtudiantView().setVisible(true));
        btnAssocierEnseignantCours.addActionListener((ActionEvent e) -> new AffectationEnseignantView().setVisible(true));
        
        // Déconnexion
        btnDeconnexion.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }
}

