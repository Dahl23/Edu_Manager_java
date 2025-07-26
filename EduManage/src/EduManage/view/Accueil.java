package EduManage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Accueil extends JFrame {

    public Accueil() {
        setTitle("Bienvenue sur EduManage");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#fdfdfd"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);

        // Logo ou titre
        JLabel titre = new JLabel("Bienvenue 👋");
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        titre.setForeground(new Color(33, 33, 33));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titre, gbc);

        // Sous-titre
        JLabel sousTitre = new JLabel("Education Manager - Application de gestion de l'enseignement des etudiants");
        sousTitre.setFont(new Font("Arial", Font.PLAIN, 14));
        sousTitre.setForeground(new Color(100, 100, 100));
        gbc.gridy++;
        panel.add(sousTitre, gbc);

//        // Image/logo optionnel (si tu as un logo.jpg)
//        ImageIcon logo = new ImageIcon("src/tms/assets/logo.png"); // à adapter selon ton chemin
//        if (logo.getImageLoadStatus() == MediaTracker.COMPLETE) {
//            JLabel img = new JLabel(logo);
//            gbc.gridy++;
//            panel.add(img, gbc);
//        }

        // Bouton Connexion
        gbc.gridy++;
        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.setBackground(new Color(66, 135, 245));
        btnConnexion.setForeground(Color.WHITE);
        btnConnexion.setFont(new Font("Arial", Font.BOLD, 16));
        btnConnexion.setPreferredSize(new Dimension(200, 40));
        panel.add(btnConnexion, gbc);

        add(panel, BorderLayout.CENTER);

        // Actions
        btnConnexion.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }


}

