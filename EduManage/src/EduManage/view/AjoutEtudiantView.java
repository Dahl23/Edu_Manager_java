package EduManage.view;

import EduManage.controller.EtudiantController;
import EduManage.model.Etudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AjoutEtudiantView extends JFrame {
    private final JTextField nomField = new JTextField();
    private final JTextField matriculeField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField niveauField = new JTextField();
    private final JLabel messageLabel = new JLabel("");

    private final EtudiantController controller = new EtudiantController();

    public AjoutEtudiantView() {
        setTitle("Ajout d'un Étudiant");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        nomField.setBorder(BorderFactory.createTitledBorder("Nom"));
        matriculeField.setBorder(BorderFactory.createTitledBorder("Matricule"));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        niveauField.setBorder(BorderFactory.createTitledBorder("Niveau"));

        JButton ajouterBtn = new JButton("Ajouter l'étudiant");
        ajouterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(nomField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(matriculeField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(niveauField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(ajouterBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        add(panel);

        ajouterBtn.addActionListener((ActionEvent e) -> {
            String nom = nomField.getText();
            String matricule = matriculeField.getText();
            String email = emailField.getText();
            String niveau = niveauField.getText();

            if (nom.isEmpty() || matricule.isEmpty() || email.isEmpty() || niveau.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs");
                messageLabel.setForeground(Color.RED);
                return;
            }

            Etudiant etudiant = new Etudiant();
            etudiant.setNom(nom);
            etudiant.setMatricule(matricule);
            etudiant.setEmail(email);
            etudiant.setNiveau(niveau);

            try {
                controller.ajouterEtudiant(etudiant);
                messageLabel.setText("Étudiant ajouté avec succès");
                messageLabel.setForeground(new Color(0, 128, 0));
                nomField.setText(""); matriculeField.setText(""); emailField.setText(""); niveauField.setText("");
            } catch (SQLException ex) {
                messageLabel.setText("Erreur lors de l'ajout : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }
}
