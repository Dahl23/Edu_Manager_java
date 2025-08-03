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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        idEtudiantField.setBorder(BorderFactory.createTitledBorder("ID Étudiant"));
        idCoursField.setBorder(BorderFactory.createTitledBorder("ID Cours"));

        JButton inscrireBtn = new JButton("Inscrire");
        inscrireBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(idEtudiantField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(idCoursField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(inscrireBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        add(panel);

        inscrireBtn.addActionListener((ActionEvent e) -> {
            try {
                int idEtudiant = Integer.parseInt(idEtudiantField.getText());
                int idCours = Integer.parseInt(idCoursField.getText());
                controller.inscrireEtudiant(idEtudiant, idCours);
                messageLabel.setText("Inscription réussie !");
                messageLabel.setForeground(new Color(0, 128, 0));
                idEtudiantField.setText("");
                idCoursField.setText("");
            } catch (NumberFormatException ex) {
                messageLabel.setText("Entrées invalides.");
                messageLabel.setForeground(Color.RED);
            } catch (SQLException ex) {
                messageLabel.setText("Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }
}
