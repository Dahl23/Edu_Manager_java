package EduManage.view;

import EduManage.dao.EvaluationDAO;
import EduManage.dao.UtilisateurDAO;
import EduManage.model.Cours;
import EduManage.model.Evaluation;
import EduManage.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AjoutEvaluationView extends JFrame {

    private final Cours cours;
    private final JComboBox<Utilisateur> etudiantComboBox;
    private final JComboBox<String> typeComboBox;
    private final JTextField noteField;
    private final DefaultListModel<String> evaluationListModel;
    private final JLabel messageLabel = new JLabel("");

    public AjoutEvaluationView(Cours cours) {
        this.cours = cours;

        setTitle("Ajouter une Évaluation - " + cours.getLibelle());
        setSize(550, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // === Header avec Titre + Déconnexion ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Ajouter une Évaluation");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JButton deconnexionBtn = new JButton("Déconnexion");
        deconnexionBtn.setFocusPainted(false);
        deconnexionBtn.setBackground(new Color(231, 76, 60));
        deconnexionBtn.setForeground(Color.WHITE);
        deconnexionBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        deconnexionBtn.setPreferredSize(new Dimension(130, 35));

        deconnexionBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Êtes-vous sûr de vouloir vous déconnecter ?",
                    "Confirmation de déconnexion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView().setVisible(true);
            }
        });

        // Remplace si besoin
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(deconnexionBtn, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // === Liste des évaluations existantes ===
        evaluationListModel = new DefaultListModel<>();
        JList<String> evaluationList = new JList<>(evaluationListModel);
        evaluationList.setBorder(BorderFactory.createTitledBorder("Évaluations existantes"));
        evaluationList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(evaluationList);
        scrollPane.setPreferredSize(new Dimension(500, 120));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // === Formulaire d'ajout ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Nouvelle évaluation"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        etudiantComboBox = new JComboBox<>();
        chargerEtudiants();
        addFormRow(formPanel, gbc, 0, "Étudiant :", etudiantComboBox);

        typeComboBox = new JComboBox<>(new String[]{"TP", "Examen", "Projet"});
        addFormRow(formPanel, gbc, 1, "Type d'évaluation :", typeComboBox);

        noteField = new JTextField();
        addFormRow(formPanel, gbc, 2, "Note :", noteField);

        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);

        // === Bouton enregistrer ===
        JButton enregistrerBtn = new JButton("Enregistrer");
        enregistrerBtn.setPreferredSize(new Dimension(120, 35));
        enregistrerBtn.setBackground(new Color(0, 123, 255));
        enregistrerBtn.setForeground(Color.WHITE);
        enregistrerBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        enregistrerBtn.setFocusPainted(false);
        enregistrerBtn.addActionListener(this::enregistrerEvaluation);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(enregistrerBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        chargerEvaluations();
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int y, String label, JComponent input) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(input, gbc);
    }

    private void chargerEtudiants() {
        try {
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            List<Utilisateur> etudiants = utilisateurDAO.getEtudiantsParCours(cours.getIdCours());
            etudiantComboBox.removeAllItems();
            for (Utilisateur etu : etudiants) {
                etudiantComboBox.addItem(etu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des étudiants : " + e.getMessage());
        }
    }

    private void chargerEvaluations() {
        try {
            EvaluationDAO evaluationDAO = new EvaluationDAO();
            List<Evaluation> evaluations = evaluationDAO.getEvaluationsParCours(cours.getIdCours());
            evaluationListModel.clear();
            for (Evaluation ev : evaluations) {
                String ligne = ev.getEtudiant().getNom() + " - " + ev.getTypeEvaluation() + " : " + ev.getNote();
                evaluationListModel.addElement(ligne);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des évaluations.");
        }
    }

    private void enregistrerEvaluation(ActionEvent e) {
        try {
            Utilisateur etudiant = (Utilisateur) etudiantComboBox.getSelectedItem();
            String type = (String) typeComboBox.getSelectedItem();
            float note = Float.parseFloat(noteField.getText());

            Evaluation evaluation = new Evaluation();
            evaluation.setCours(cours);
            evaluation.setEtudiant(etudiant);
            evaluation.setTypeEvaluation(type);
            evaluation.setNote(note);

            EvaluationDAO evaluationDAO = new EvaluationDAO();
            evaluationDAO.ajouterEvaluation(evaluation);

            messageLabel.setText("✅ Évaluation enregistrée !");
            messageLabel.setForeground(new Color(0, 128, 0));

            noteField.setText("");
            chargerEvaluations();

        } catch (NumberFormatException ex) {
            messageLabel.setText("❌ Note invalide (entrez un nombre)");
            messageLabel.setForeground(Color.RED);
        } catch (Exception ex) {
            messageLabel.setText("❌ Erreur lors de l'enregistrement");
            messageLabel.setForeground(Color.RED);
        }
    }
}
