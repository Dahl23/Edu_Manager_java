package EduManage.view;

import EduManage.controller.AffectationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AffectationEnseignantView extends JFrame {
    private final JTextField idEnseignantField = new JTextField();
    private final JTextField idCoursField = new JTextField();
    private final JLabel messageLabel = new JLabel("");

    private final AffectationController controller = new AffectationController();

    public AffectationEnseignantView() {
        setTitle("Affectation Enseignant à un Cours");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        idEnseignantField.setBorder(BorderFactory.createTitledBorder("ID Enseignant"));
        idCoursField.setBorder(BorderFactory.createTitledBorder("ID Cours"));

        JButton affecterBtn = new JButton("Affecter");
        affecterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(idEnseignantField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(idCoursField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(affecterBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        add(panel);

        affecterBtn.addActionListener((ActionEvent e) -> {
            try {
                int idEnseignant = Integer.parseInt(idEnseignantField.getText());
                int idCours = Integer.parseInt(idCoursField.getText());
                controller.affecterEnseignant(idEnseignant, idCours);
                messageLabel.setText("Affectation réussie !");
                messageLabel.setForeground(new Color(0, 128, 0));
                idEnseignantField.setText("");
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
