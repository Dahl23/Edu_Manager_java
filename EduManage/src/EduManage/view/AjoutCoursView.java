package EduManage.view;

import EduManage.controller.CoursController;
import EduManage.model.Cours;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class AjoutCoursView extends JFrame {
    private final JTextField libelleField = new JTextField();
    private final JTextField semestreField = new JTextField();
    private final JLabel messageLabel = new JLabel("");
    private final JTextArea listeCoursArea = new JTextArea();

    private final CoursController controller = new CoursController();

    public AjoutCoursView() {
        setTitle("Ajout d'un Cours");
        setSize(500, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        libelleField.setBorder(BorderFactory.createTitledBorder("Libellé du cours"));
        semestreField.setBorder(BorderFactory.createTitledBorder("Semestre"));

        JButton ajouterBtn = new JButton("Ajouter le cours");
        ajouterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(libelleField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(semestreField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(ajouterBtn);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(messageLabel);

        listeCoursArea.setEditable(false);
        listeCoursArea.setBorder(BorderFactory.createTitledBorder("Liste des cours"));
        JScrollPane scrollPane = new JScrollPane(listeCoursArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // === Bouton retour vers AdminDashboard ===
        JButton retourBtn = new JButton("   <- Retour à la page d'Admin   ");
        retourBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourBtn.addActionListener(e -> {
            this.dispose(); // fermer la fenêtre actuelle
            new AdminDashboard().setVisible(true); // ouvrir la fenêtre AdminDashboard
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel du bas pour le bouton retour
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottomPanel.add(retourBtn);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        afficherCours();

        ajouterBtn.addActionListener((ActionEvent e) -> {
            String libelle = libelleField.getText();
            String semestre = semestreField.getText();

            if (libelle.isEmpty() || semestre.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs");
                messageLabel.setForeground(Color.RED);
                return;
            }

            Cours cours = new Cours();
            cours.setLibelle(libelle);
            cours.setSemestre(semestre);

            try {
                controller.ajouterCours(cours);
                messageLabel.setText("Cours ajouté avec succès");
                messageLabel.setForeground(new Color(0, 128, 0));
                libelleField.setText("");
                semestreField.setText("");
                afficherCours();
            } catch (SQLException ex) {
                messageLabel.setText("Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });
    }

    private void afficherCours() {
        try {
            List<Cours> coursList = controller.getTousLesCours();
            StringBuilder sb = new StringBuilder();
            for (Cours c : coursList) {
                sb.append("- ").append(c.getLibelle()).append(" | Semestre: ").append(c.getSemestre()).append("\n");
            }
            listeCoursArea.setText(sb.toString());
        } catch (SQLException ex) {
            listeCoursArea.setText("Erreur lors du chargement des cours.");
        }
    }
}
