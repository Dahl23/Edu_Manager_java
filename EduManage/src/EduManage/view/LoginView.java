package EduManage.view;

import EduManage.controller.UtilisateurController;
import EduManage.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "enseignant", "etudiant"});
    private final JLabel messageLabel = new JLabel("");

    private final UtilisateurController utilisateurController = new UtilisateurController();

    public LoginView() {
        setTitle("Connexion - EduManage");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // ==== Header ====
        JLabel titleLabel = new JLabel("🔐 Connexion à EduManage", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        titleLabel.setForeground(new Color(33, 37, 41));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ==== Formulaire ====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nom d'utilisateur :"), gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Mot de passe :"), gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Rôle :"), gbc);

        gbc.gridx = 1;
        formPanel.add(roleComboBox, gbc);

        // Message label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        formPanel.add(messageLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ==== Bouton Connexion ====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        JButton loginButton = new JButton("Se connecter");
        loginButton.setPreferredSize(new Dimension(140, 35));
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(this::loginAction);
        bottomPanel.add(loginButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void loginAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = roleComboBox.getSelectedItem().toString();

        Utilisateur user = utilisateurController.seConnecter(username, password, role);

        if (user != null) {
            dispose();
            switch (user.getRole().toLowerCase()) {
                case "admin":
                    new AdminDashboard().setVisible(true);
                    break;
                case "enseignant":
                    new ChoixCoursView(user).setVisible(true);
                    break;
                case "etudiant":
                    new EtudiantDashboard(user).setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Rôle inconnu : " + user.getRole());
            }
        } else {
            messageLabel.setText("❌ Nom d'utilisateur ou mot de passe incorrect.");
            messageLabel.setForeground(Color.RED);
        }
    }
}
