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
        setTitle("Inscription d’un étudiant à un cours");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // === Main Panel ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // === Header ===
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(500, 60));

        JLabel headerLabel = new JLabel("Inscription Étudiant à un Cours");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // === Formulaire ===
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Champ ID Étudiant
        idEtudiantField.setBorder(BorderFactory.createTitledBorder("ID Étudiant"));
        idEtudiantField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        idEtudiantField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Champ ID Cours
        idCoursField.setBorder(BorderFactory.createTitledBorder("ID Cours"));
        idCoursField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        idCoursField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Bouton Inscrire
        JButton inscrireBtn = new JButton("Inscrire");
        inscrireBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        inscrireBtn.setBackground(new Color(33, 150, 243));
        inscrireBtn.setForeground(Color.WHITE);
        inscrireBtn.setFocusPainted(false);
        inscrireBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        inscrireBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Label message
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        messageLabel.setForeground(Color.RED);

        // Ajout des composants
        formPanel.add(idEtudiantField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(idCoursField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(inscrireBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(messageLabel);

        // Ajout aux panels principaux
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Action du bouton
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
                messageLabel.setText("Veuillez saisir des nombres valides.");
                messageLabel.setForeground(Color.RED);
            } catch (SQLException ex) {
                messageLabel.setText("Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }
}
