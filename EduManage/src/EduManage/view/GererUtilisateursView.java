package EduManage.view;

import EduManage.controller.UtilisateurController;
import EduManage.model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class GererUtilisateursView extends JFrame {

    // Palette cohérente
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_COLOR = new Color(236, 240, 241);
    private static final Color DARK_COLOR = new Color(44, 62, 80);
    private static final Color WHITE_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(52, 73, 94);

    private JTable table;
    private DefaultTableModel model;
    private UtilisateurController utilisateurController = new UtilisateurController();

    public GererUtilisateursView() {
        setTitle("Gestion des Utilisateurs - EduManage");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(LIGHT_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        chargerUtilisateurs();
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK_COLOR);
        header.setBorder(new EmptyBorder(15, 30, 15, 30));

        JLabel title = new JLabel("👥 Gestion des Utilisateurs");
        title.setForeground(WHITE_COLOR);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(title, BorderLayout.CENTER);
        return header;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_COLOR);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        model = new DefaultTableModel(new Object[]{"ID", "Nom Utilisateur", "Mot de Passe", "Rôle"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(createButtonsPanel(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(LIGHT_COLOR);

        JButton btnAjouter = createStyledButton("➕ Ajouter", SECONDARY_COLOR);
        JButton btnModifier = createStyledButton("✏️ Modifier", PRIMARY_COLOR);
        JButton btnSupprimer = createStyledButton("🗑️ Supprimer", DANGER_COLOR);
        JButton retourBtn = createStyledButton("⬅️ Retour", DARK_COLOR);

        btnAjouter.addActionListener(e -> ajouterUtilisateur());
        btnModifier.addActionListener(e -> modifierUtilisateur());
        btnSupprimer.addActionListener(e -> supprimerUtilisateur());
        retourBtn.addActionListener(e -> {
            dispose();
            new AdminDashboard().setVisible(true);
        });

        panel.add(btnAjouter);
        panel.add(btnModifier);
        panel.add(btnSupprimer);
        panel.add(retourBtn);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK_COLOR);
        footer.setBorder(new EmptyBorder(10, 30, 10, 30));

        JLabel status = new JLabel("📌 Liste des utilisateurs actuellement dans le système");
        status.setForeground(LIGHT_COLOR);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel date = new JLabel("📅 " + java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        ));
        date.setForeground(LIGHT_COLOR);
        date.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footer.add(status, BorderLayout.WEST);
        footer.add(date, BorderLayout.EAST);

        return footer;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(WHITE_COLOR);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brighter(color, 0.15f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(darker(color, 0.15f));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(brighter(color, 0.15f));
            }
        });

        return button;
    }

    private Color brighter(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() * (1 + factor)));
        int g = Math.min(255, (int)(color.getGreen() * (1 + factor)));
        int b = Math.min(255, (int)(color.getBlue() * (1 + factor)));
        return new Color(r, g, b);
    }

    private Color darker(Color color, float factor) {
        int r = Math.max(0, (int)(color.getRed() * (1 - factor)));
        int g = Math.max(0, (int)(color.getGreen() * (1 - factor)));
        int b = Math.max(0, (int)(color.getBlue() * (1 - factor)));
        return new Color(r, g, b);
    }

    private void chargerUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = utilisateurController.listerUtilisateurs();
            model.setRowCount(0);
            for (Utilisateur u : utilisateurs) {
                model.addRow(new Object[]{
                        u.getIdUtilisateur(),
                        u.getNomUtilisateur(),
                        u.getMotDePasse(),
                        u.getRole()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement : " + e.getMessage());
        }
    }

    private void ajouterUtilisateur() {
        JTextField nomField = new JTextField();
        JTextField mdpField = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Etudiant", "Enseignant"});

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom utilisateur :"));
        panel.add(nomField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(mdpField);
        panel.add(new JLabel("Rôle :"));
        panel.add(roleBox);

        int res = JOptionPane.showConfirmDialog(this, panel, "Ajouter un utilisateur", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            Utilisateur u = new Utilisateur();
            u.setNomUtilisateur(nomField.getText());
            u.setMotDePasse(mdpField.getText());
            u.setRole(roleBox.getSelectedItem().toString());

            try {
                utilisateurController.ajouterUtilisateur(u);
                chargerUtilisateurs();
                JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès !");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur d'ajout : " + e.getMessage());
            }
        }
    }

    private void modifierUtilisateur() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        JTextField nomField = new JTextField((String) model.getValueAt(row, 1));
        JTextField mdpField = new JTextField((String) model.getValueAt(row, 2));
        JTextField roleField = new JTextField((String) model.getValueAt(row, 3));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom utilisateur :"));
        panel.add(nomField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(mdpField);
        panel.add(new JLabel("Rôle :"));
        panel.add(roleField);

        int res = JOptionPane.showConfirmDialog(this, panel, "Modifier utilisateur", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(id);
            u.setNomUtilisateur(nomField.getText());
            u.setMotDePasse(mdpField.getText());
            u.setRole(roleField.getText());

            try {
                utilisateurController.modifierUtilisateur(u);
                chargerUtilisateurs();
                JOptionPane.showMessageDialog(this, "Modification réussie !");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur de modification : " + e.getMessage());
            }
        }
    }

    private void supprimerUtilisateur() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                utilisateurController.supprimerUtilisateur(id);
                chargerUtilisateurs();
                JOptionPane.showMessageDialog(this, "Utilisateur supprimé.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur de suppression : " + e.getMessage());
            }
        }
    }
}
