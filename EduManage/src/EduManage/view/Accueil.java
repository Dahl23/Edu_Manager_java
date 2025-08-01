package EduManage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Accueil extends JFrame {

    public Accueil() {
        setTitle("Bienvenue sur EduManage");
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel principal avec fond clair
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#f4f6f9"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridx = 0;

        // Titre principal
        JLabel titre = new JLabel("Bienvenue 👋");
        titre.setFont(new Font("SansSerif", Font.BOLD, 30));
        titre.setForeground(new Color(33, 33, 33));
        gbc.gridy = 0;
        panel.add(titre, gbc);

        // Sous-titre descriptif
        JLabel sousTitre = new JLabel("<html><div style='text-align: center;'>"
                + "EduManage - Plateforme de gestion de l’enseignement <br> pour les étudiants et enseignants"
                + "</div></html>");
        sousTitre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sousTitre.setForeground(new Color(90, 90, 90));
        gbc.gridy++;
        panel.add(sousTitre, gbc);


        // Bouton "Se connecter"
        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.setBackground(new Color(66, 135, 245));
        btnConnexion.setForeground(Color.WHITE);
        btnConnexion.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnConnexion.setFocusPainted(false);
        btnConnexion.setPreferredSize(new Dimension(200, 45));
        gbc.gridy++;
        panel.add(btnConnexion, gbc);

        // Effet de survol
        btnConnexion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConnexion.setBackground(new Color(30, 105, 230));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConnexion.setBackground(new Color(66, 135, 245));
            }
        });

        // Action du bouton
        btnConnexion.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        add(panel, BorderLayout.CENTER);
    }
}
