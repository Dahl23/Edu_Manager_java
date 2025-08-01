package EduManage.view;

import EduManage.controller.UtilisateurController;
import EduManage.model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JLabel messageLabel;

    private final UtilisateurController utilisateurController = new UtilisateurController();

    // Palette cohérente
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color DARK_COLOR = new Color(44, 62, 80);
    private static final Color TEXT_COLOR = new Color(52, 73, 94);
    private static final Color LIGHT_COLOR = new Color(245, 245, 245);
    private static final Color WHITE = Color.WHITE;

    public LoginView() {
        setTitle("Connexion - EduManage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(LIGHT_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(DARK_COLOR);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("🔐 Connexion EduManage");
        titleLabel.setForeground(WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(titleLabel);
        return header;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));
        panel.setBackground(LIGHT_COLOR);

        usernameField = createInputField("Nom d'utilisateur");
        passwordField = createPasswordField("Mot de passe");
        roleComboBox = createComboBox(new String[]{"admin", "enseignant", "etudiant"}, "Rôle");

        loginButton = createStyledButton("Se connecter");
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(roleComboBox);
        panel.add(Box.createVerticalStrut(25));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(messageLabel);

        loginButton.addActionListener(this::loginAction);

        return panel;
    }

    private JTextField createInputField(String title) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }

    private JPasswordField createPasswordField(String title) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }

    private JComboBox<String> createComboBox(String[] options, String title) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createTitledBorder(title));
        return comboBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    private void loginAction(ActionEvent e) {
        String username = usernameField.getText();
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
            messageLabel.setText("Nom d'utilisateur, mot de passe ou rôle incorrect");
        }
    }
}
