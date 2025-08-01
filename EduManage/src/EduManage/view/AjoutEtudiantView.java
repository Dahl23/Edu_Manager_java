package EduManage.view;

import EduManage.controller.EtudiantController;
import EduManage.model.Etudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AjoutEtudiantView extends JFrame {
    private final JTextField idUtilisateurField = new JTextField(20);
    private final JTextField nomField = new JTextField(20);
    private final JTextField matriculeField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField niveauField = new JTextField(20);
    private final JTextField filiereField = new JTextField(20);
    private final JLabel messageLabel = new JLabel("");

    private final EtudiantController controller = new EtudiantController();

    public AjoutEtudiantView() {
        setTitle("Ajout d'un Étudiant");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Titre
        JLabel titleLabel = new JLabel("Ajouter un Étudiant", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Important pour l'élargissement horizontal
        gbc.weightx = 0;                           // Par défaut 0 pour label

        addLabeledField(formPanel, gbc, 0, "ID Utilisateur :", idUtilisateurField);
        addLabeledField(formPanel, gbc, 1, "Nom :", nomField);
        addLabeledField(formPanel, gbc, 2, "Matricule :", matriculeField);
        addLabeledField(formPanel, gbc, 3, "Email :", emailField);
        addLabeledField(formPanel, gbc, 4, "Niveau :", niveauField);
        addLabeledField(formPanel, gbc, 5, "Filière :", filiereField);

        // Message
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1; // Pour que le label prenne tout l'espace horizontal disponible
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

        // Action
        ajouterBtn.addActionListener((ActionEvent e) -> {
            try {
                int idUtilisateur = Integer.parseInt(idUtilisateurField.getText().trim());
                String nom = nomField.getText().trim();
                String matricule = matriculeField.getText().trim();
                String email = emailField.getText().trim();
                String niveau = niveauField.getText().trim();
                String filiere = filiereField.getText().trim();

                if (nom.isEmpty() || matricule.isEmpty() || email.isEmpty() || niveau.isEmpty() || filiere.isEmpty()) {
                    messageLabel.setText("Veuillez remplir tous les champs.");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                Etudiant etudiant = new Etudiant();
                etudiant.setIdUtilisateur(idUtilisateur);
                etudiant.setNom(nom);
                etudiant.setMatricule(matricule);
                etudiant.setEmail(email);
                etudiant.setNiveau(niveau);
                etudiant.setFiliere(filiere);

                controller.ajouterEtudiant(etudiant);

                messageLabel.setText("✅ Étudiant ajouté avec succès !");
                messageLabel.setForeground(new Color(0, 128, 0));

                idUtilisateurField.setText("");
                nomField.setText("");
                matriculeField.setText("");
                emailField.setText("");
                niveauField.setText("");
                filiereField.setText("");

            } catch (NumberFormatException ex) {
                messageLabel.setText("❌ Veuillez remplir tous les champs");
                messageLabel.setForeground(Color.RED);
            } catch (SQLException ex) {
                messageLabel.setText("❌ Erreur SQL : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }

    private void addLabeledField(JPanel panel, GridBagConstraints gbc, int y, String labelText, JTextField field) {
        // Label
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0; // Le label ne s'étire pas
        JLabel label = new JLabel(labelText);
        panel.add(label, gbc);

        // Champ de texte
        gbc.gridx = 1;
        gbc.weightx = 1; // Le champ s'étire horizontalement
        field.setPreferredSize(new Dimension(200, 30)); // Taille préférée minimale
        panel.add(field, gbc);
    }
}
