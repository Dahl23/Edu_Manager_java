package educationmanagement.view;

import educationmanagement.controller.AuthController;
import educationmanagement.controller.NavigationController;
import educationmanagement.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AuthFrame() {
        setTitle("Système de Gestion de l'Enseignement - Connexion");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        initComponents();
        addListeners();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges internes pour les composants

        // Titre
        JLabel titleLabel = new JLabel("Connexion au Système");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // Nom d'utilisateur
        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);

        // Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        // Bouton de connexion
        loginButton = new JButton("Se Connecter");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 130, 180)); // Bleu acier
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Enlève le cadre de focus
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        add(panel);
    }

    private void addListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(AuthFrame.this, "Veuillez entrer le nom d'utilisateur et le mot de passe.", "Champ(s) vide(s)", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                AuthController authController = new AuthController();
                Utilisateur authenticatedUser = authController.authenticate(username, password);

                if (authenticatedUser != null) {
                    JOptionPane.showMessageDialog(AuthFrame.this, "Connexion réussie en tant que " + authenticatedUser.getRole() + " !", "Connexion réussie", JOptionPane.INFORMATION_MESSAGE);
                    // Utilise le NavigationController pour la redirection et la fermeture de cette fenêtre
                    NavigationController.redirectToDashboard(AuthFrame.this, authenticatedUser);
                } else {
                    JOptionPane.showMessageDialog(AuthFrame.this, "Nom d'utilisateur ou mot de passe incorrect.", "Erreur de Connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Méthode main pour lancer l'application
    public static void main(String[] args) {
        // Définir le Look and Feel du système pour une meilleure apparence
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new AuthFrame().setVisible(true);
        });
    }
}