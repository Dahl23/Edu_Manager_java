package EduManage.view;

import EduManage.controller.EnseignantController;
import EduManage.model.Enseignant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AjoutEnseignantView extends JFrame {
    private final JTextField idUtilisateurField = new JTextField(20);
    private final JTextField nomField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField specialiteField = new JTextField(20);
    private final JLabel messageLabel = new JLabel("");

    private final EnseignantController controller = new EnseignantController();

    public AjoutEnseignantView() {
        setTitle("Ajout d'un Enseignant");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Titre
        JLabel titleLabel = new JLabel("Ajouter un Enseignant", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champs
        addLabeledField(formPanel, gbc, 0, "ID Utilisateur :", idUtilisateurField);
        addLabeledField(formPanel, gbc, 1, "Nom :", nomField);
        addLabeledField(formPanel, gbc, 2, "Email :", emailField);
        addLabeledField(formPanel, gbc, 3, "Spécialité :", specialiteField);

        // Message
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        formPanel.add(messageLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Bouton
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton ajouterBtn = new JButton("Ajouter");
        ajouterBtn.setPreferredSize(new Dimension(140, 40));
        ajouterBtn.setFocusPainted(false);
        ajouterBtn.setBackground(new Color(0, 123, 255));
        ajouterBtn.setForeground(Color.WHITE);
        ajouterBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        buttonPanel.add(ajouterBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action du bouton
        ajouterBtn.addActionListener((ActionEvent e) -> {
            try {
                int idUtilisateur = Integer.parseInt(idUtilisateurField.getText().trim());
                String nom = nomField.getText().trim();
                String email = emailField.getText().trim();
                String specialite = specialiteField.getText().trim();

                if (nom.isEmpty() || email.isEmpty() || specialite.isEmpty()) {
                    messageLabel.setText("Veuillez remplir tous les champs.");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                Enseignant enseignant = new Enseignant();
                enseignant.setId_utilisateur(idUtilisateur);
                enseignant.setNom(nom);
                enseignant.setEmail(email);
                enseignant.setSpecialite(specialite);

                controller.ajouterEnseignant(enseignant);

                messageLabel.setText("✅ Enseignant ajouté avec succès !");
                messageLabel.setForeground(new Color(0, 128, 0));

                // Reset
                idUtilisateurField.setText("");
                nomField.setText("");
                emailField.setText("");
                specialiteField.setText("");

            } catch (NumberFormatException ex) {
                messageLabel.setText("❌ Veuillez remplir tous les champs");
                messageLabel.setForeground(Color.RED);
            } catch (SQLException ex) {
                messageLabel.setText("❌ Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }

    private void addLabeledField(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(200, 30));
        panel.add(field, gbc);
    }
}
