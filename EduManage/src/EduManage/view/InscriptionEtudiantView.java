package EduManage.view;

import EduManage.controller.InscriptionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class InscriptionEtudiantView extends JFrame {
    private final JTextField idEtudiantField = new JTextField();
    private final JTextField idCoursField = new JTextField();
    private final JLabel messageLabel = new JLabel("");

    private final InscriptionController controller = new InscriptionController();

    public InscriptionEtudiantView() {
        setTitle("Inscription Étudiant à un Cours");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Champ ID Étudiant
        idEtudiantField.setBorder(BorderFactory.createTitledBorder("ID Étudiant"));
        idEtudiantField.setAlignmentX(Component.LEFT_ALIGNMENT);
        idEtudiantField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        idEtudiantField.setMinimumSize(new Dimension(200, 35));

        // Champ ID Cours
        idCoursField.setBorder(BorderFactory.createTitledBorder("ID Cours"));
        idCoursField.setAlignmentX(Component.LEFT_ALIGNMENT);
        idCoursField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        idCoursField.setMinimumSize(new Dimension(200, 35));

        JButton inscrireBtn = new JButton("Inscrire");
        inscrireBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        inscrireBtn.setBackground(new Color(33, 150, 243));
        inscrireBtn.setForeground(Color.WHITE);
        inscrireBtn.setFocusPainted(false);
        inscrireBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        mainPanel.add(idEtudiantField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(idCoursField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(inscrireBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(messageLabel);

        add(mainPanel);

        inscrireBtn.addActionListener((ActionEvent e) -> {
            try {
                int idEtudiant = Integer.parseInt(idEtudiantField.getText().trim());
                int idCours = Integer.parseInt(idCoursField.getText().trim());
                controller.inscrireEtudiant(idEtudiant, idCours);
                messageLabel.setText("Inscription réussie !");
                messageLabel.setForeground(new Color(0, 128, 0));
                idEtudiantField.setText("");
                idCoursField.setText("");
            } catch (NumberFormatException ex) {
                messageLabel.setText("Entrées invalides. Veuillez saisir des nombres.");
                messageLabel.setForeground(Color.RED);
            } catch (SQLException ex) {
                messageLabel.setText("Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }
}
