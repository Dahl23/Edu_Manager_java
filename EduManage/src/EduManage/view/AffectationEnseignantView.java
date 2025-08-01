package EduManage.view;

import EduManage.controller.AffectationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AffectationEnseignantView extends JFrame {

    private final JTextField idEnseignantField = new JTextField(20);
    private final JTextField idCoursField = new JTextField(20);
    private final JLabel messageLabel = new JLabel("");

    private final AffectationController controller = new AffectationController();

    public AffectationEnseignantView() {
        setTitle("Affectation Enseignant à un Cours");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Titre
        JLabel titleLabel = new JLabel("Affecter un Enseignant à un Cours", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        titleLabel.setForeground(new Color(33, 37, 41));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel central pour les champs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label et champ ID Enseignant
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID Enseignant :"), gbc);

        gbc.gridx = 1;
        formPanel.add(idEnseignantField, gbc);

        // Label et champ ID Cours
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("ID Cours :"), gbc);

        gbc.gridx = 1;
        formPanel.add(idCoursField, gbc);

        // Message Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        formPanel.add(messageLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel bas avec bouton
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        JButton affecterBtn = new JButton("Affecter");
        affecterBtn.setPreferredSize(new Dimension(120, 35));
        affecterBtn.setFocusPainted(false);
        affecterBtn.setBackground(new Color(0, 123, 255));
        affecterBtn.setForeground(Color.WHITE);
        affecterBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanel.add(affecterBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action du bouton
        affecterBtn.addActionListener((ActionEvent e) -> {
            try {
                int idEnseignant = Integer.parseInt(idEnseignantField.getText().trim());
                int idCours = Integer.parseInt(idCoursField.getText().trim());

                controller.affecterEnseignant(idEnseignant, idCours);

                messageLabel.setText("✅ Affectation réussie !");
                messageLabel.setForeground(new Color(0, 128, 0));

                idEnseignantField.setText("");
                idCoursField.setText("");

            } catch (NumberFormatException ex) {
                messageLabel.setText("❌ Entrées invalides. Veuillez entrer des ID valides.");
                messageLabel.setForeground(Color.RED);
            } catch (SQLException ex) {
                messageLabel.setText("❌ Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }
}
