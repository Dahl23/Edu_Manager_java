package EduManage.view;

import EduManage.dao.CoursDAO;
import EduManage.dao.EnseignerDAO;
import EduManage.model.Cours;
import EduManage.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ChoixCoursView extends JFrame {
    private JComboBox<Cours> coursComboBox;

    public ChoixCoursView(Utilisateur utilisateur) {
        setTitle("Choix du cours à évaluer");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---- EN-TÊTE ----
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel headerLabel = new JLabel("Sélection d’un cours");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // ---- CONTENU PRINCIPAL ----
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Choisissez un cours :");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(label, gbc);

        coursComboBox = new JComboBox<>();
        coursComboBox.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        centerPanel.add(coursComboBox, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // ---- BOUTON DE VALIDATION ----
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton validerButton = new JButton("Valider");
        validerButton.setFont(new Font("Arial", Font.BOLD, 14));
        validerButton.setBackground(new Color(50, 120, 200));
        validerButton.setForeground(Color.WHITE);
        validerButton.setFocusPainted(false);
        validerButton.setPreferredSize(new Dimension(120, 35));
        validerButton.addActionListener(this::validerChoix);
        buttonPanel.add(validerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Chargement des cours
        chargerCours(utilisateur);
    }

    private void chargerCours(Utilisateur utilisateur) {
        try {
            if (utilisateur.getIdEnseignant() == null) {
                throw new IllegalArgumentException("ID enseignant non défini.");
            }

            int idEnseignant = utilisateur.getIdEnseignant().intValue();
            EnseignerDAO enseignerDAO = new EnseignerDAO();
            List<Integer> idsCours = enseignerDAO.getCoursParEnseignant(idEnseignant);

            CoursDAO coursDAO = new CoursDAO();
            List<Cours> coursList = new ArrayList<>();
            for (int idCours : idsCours) {
                Cours cours = coursDAO.getCoursParId(idCours);
                if (cours != null) {
                    coursList.add(cours);
                }
            }

            for (Cours c : coursList) {
                coursComboBox.addItem(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des cours.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validerChoix(ActionEvent e) {
        Cours coursSelectionne = (Cours) coursComboBox.getSelectedItem();
        if (coursSelectionne != null) {
            new AjoutEvaluationView(coursSelectionne).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez choisir un cours.", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }
}
