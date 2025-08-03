package EduManage.view;

import EduManage.controller.UtilisateurController;
import EduManage.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JLabel messageLabel;

    private final UtilisateurController utilisateurController = new UtilisateurController();

    public LoginView() {
        setTitle("Connexion - EduManage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("EduManage - Connexion");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(33, 37, 41));

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setBorder(BorderFactory.createTitledBorder("Nom d'utilisateur"));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBorder(BorderFactory.createTitledBorder("Mot de passe"));

        roleComboBox = new JComboBox<>(new String[]{"admin", "enseignant", "etudiant"});
        roleComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        roleComboBox.setBorder(BorderFactory.createTitledBorder("Rôle"));

        loginButton = new JButton("Se connecter");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 40));

        messageLabel = new JLabel("");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.RED);

        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(roleComboBox);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String role = roleComboBox.getSelectedItem().toString();

                Utilisateur user = utilisateurController.seConnecter(username, password, role);

                if (user != null) {
                    dispose();
                    switch (user.getRole()) {
                        case "admin":
                            new AdminDashboard().setVisible(true);
                            break;
                        case "etudiant":
//                          new VueEtudiantCoursNotes(user.getIdEtudiant()).setVisible(true);
                            break;
                        case "enseignant":
                            new AjoutEvaluationView().setVisible(true);
                            break;
                       default:
                            JOptionPane.showMessageDialog(null, "Rôle inconnu : " + user.getRole());
                    }
                } else {
                    messageLabel.setText("Nom d'utilisateur, mot de passe ou rôle incorrect");
                }
            }
        });
    }
}
