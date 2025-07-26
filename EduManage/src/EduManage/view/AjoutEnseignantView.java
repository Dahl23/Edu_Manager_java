package EduManage.view;

import EduManage.controller.EnseignantController;
import EduManage.model.Enseignant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AjoutEnseignantView extends JFrame {
    private final JTextField nomField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField specialiteField = new JTextField();
    private final JLabel messageLabel = new JLabel("");

    private final EnseignantController controller = new EnseignantController();

    public AjoutEnseignantView() {
        setTitle("Ajout d'un Enseignant");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        nomField.setBorder(BorderFactory.createTitledBorder("Nom"));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        specialiteField.setBorder(BorderFactory.createTitledBorder("Spécialité"));

        JButton ajouterBtn = new JButton("Ajouter l'enseignant");
        ajouterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(nomField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(specialiteField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(ajouterBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        add(panel);

        ajouterBtn.addActionListener((ActionEvent e) -> {
            String nom = nomField.getText();
            String email = emailField.getText();
            String specialite = specialiteField.getText();

            if (nom.isEmpty() || email.isEmpty() || specialite.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs");
                messageLabel.setForeground(Color.RED);
                return;
            }

            Enseignant enseignant = new Enseignant();
            enseignant.setNom(nom);
            enseignant.setEmail(email);
            enseignant.setSpecialite(specialite);

            try {
                controller.ajouterEnseignant(enseignant);
                messageLabel.setText("Enseignant ajouté avec succès");
                messageLabel.setForeground(new Color(0, 128, 0));
                nomField.setText(""); emailField.setText(""); specialiteField.setText("");
            } catch (SQLException ex) {
                messageLabel.setText("Erreur lors de l'ajout : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }
}
