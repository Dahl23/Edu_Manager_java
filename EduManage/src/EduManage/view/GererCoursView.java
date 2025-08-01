package EduManage.view;

import EduManage.controller.CoursController;
import EduManage.model.Cours;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class GererCoursView extends JFrame {
    private final JTextField libelleField = new JTextField();
    private final JTextField semestreField = new JTextField();
    private final JLabel messageLabel = new JLabel("");

    private final DefaultListModel<Cours> coursListModel = new DefaultListModel<>();
    private final JList<Cours> coursJList = new JList<>(coursListModel);

    private final CoursController controller = new CoursController();

    public GererCoursView() {
        setTitle("Gestion des Cours");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal avec padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // --- Panel formulaire ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(245, 245, 245));

        libelleField.setBorder(BorderFactory.createTitledBorder("Libellé du cours"));
        libelleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        semestreField.setBorder(BorderFactory.createTitledBorder("Semestre"));
        semestreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JButton ajouterBtn = new JButton("Ajouter le cours");
        JButton modifierBtn = new JButton("Modifier le cours sélectionné");
        JButton supprimerBtn = new JButton("Supprimer le cours sélectionné");

        // Style boutons
        Color btnColor = new Color(33, 150, 243);
        ajouterBtn.setBackground(btnColor);
        ajouterBtn.setForeground(Color.WHITE);
        ajouterBtn.setFocusPainted(false);
        ajouterBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        ajouterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        modifierBtn.setBackground(btnColor);
        modifierBtn.setForeground(Color.WHITE);
        modifierBtn.setFocusPainted(false);
        modifierBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        modifierBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        supprimerBtn.setBackground(new Color(220, 53, 69)); // Rouge Bootstrap
        supprimerBtn.setForeground(Color.WHITE);
        supprimerBtn.setFocusPainted(false);
        supprimerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        supprimerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(libelleField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(semestreField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(ajouterBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(modifierBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(supprimerBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(messageLabel);

        // --- Liste des cours ---
        coursJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(coursJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des cours"));
        scrollPane.setPreferredSize(new Dimension(440, 250));

        // --- Bouton retour ---
        JButton retourBtn = new JButton("← Retour à la page d'Admin");
        retourBtn.setBackground(new Color(96, 125, 139));
        retourBtn.setForeground(Color.WHITE);
        retourBtn.setFocusPainted(false);
        retourBtn.setPreferredSize(new Dimension(200, 40));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.add(retourBtn);

        // Ajout composants au main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Chargement initial
        afficherCours();

        // Listeners boutons

        ajouterBtn.addActionListener((ActionEvent e) -> {
            String libelle = libelleField.getText().trim();
            String semestre = semestreField.getText().trim();

            if (libelle.isEmpty() || semestre.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            Cours cours = new Cours();
            cours.setLibelle(libelle);
            cours.setSemestre(semestre);

            try {
                controller.ajouterCours(cours);
                messageLabel.setText("Cours ajouté avec succès !");
                messageLabel.setForeground(new Color(0, 128, 0));
                libelleField.setText("");
                semestreField.setText("");
                afficherCours();
            } catch (SQLException ex) {
                messageLabel.setText("Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });

        modifierBtn.addActionListener(e -> {
            Cours selected = coursJList.getSelectedValue();
            if (selected == null) {
                messageLabel.setText("Veuillez sélectionner un cours à modifier.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            String nouveauLibelle = libelleField.getText().trim();
            String nouveauSemestre = semestreField.getText().trim();

            if (nouveauLibelle.isEmpty() || nouveauSemestre.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs pour modifier.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            selected.setLibelle(nouveauLibelle);
            selected.setSemestre(nouveauSemestre);

            try {
                controller.modifierCours(selected);
                messageLabel.setText("Cours modifié avec succès !");
                messageLabel.setForeground(new Color(0, 128, 0));
                afficherCours();
            } catch (SQLException ex) {
                messageLabel.setText("Erreur : " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        });

        supprimerBtn.addActionListener(e -> {
            Cours selected = coursJList.getSelectedValue();
            if (selected == null) {
                messageLabel.setText("Veuillez sélectionner un cours à supprimer.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Confirmez-vous la suppression du cours \"" + selected.getLibelle() + "\" ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.supprimerCours(selected.getIdCours());
                    messageLabel.setText("Cours supprimé avec succès !");
                    messageLabel.setForeground(new Color(0, 128, 0));
                    afficherCours();
                } catch (SQLException ex) {
                    messageLabel.setText("Erreur : " + ex.getMessage());
                    messageLabel.setForeground(Color.RED);
                }
            }
        });

        coursJList.addListSelectionListener(e -> {
            Cours selected = coursJList.getSelectedValue();
            if (selected != null) {
                libelleField.setText(selected.getLibelle());
                semestreField.setText(selected.getSemestre());
            }
        });

        retourBtn.addActionListener(e -> {
            this.dispose();
            new AdminDashboard().setVisible(true);
        });
    }

    private void afficherCours() {
        try {
            coursListModel.clear();
            List<Cours> coursList = controller.getTousLesCours();
            for (Cours c : coursList) {
                coursListModel.addElement(c);
            }
        } catch (SQLException ex) {
            messageLabel.setText("Erreur lors du chargement des cours.");
            messageLabel.setForeground(Color.RED);
        }
    }
}
