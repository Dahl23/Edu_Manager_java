package EduManage.view;

import EduManage.controller.EvaluationController;
import EduManage.model.Cours;
import EduManage.model.Etudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AjoutEvaluationView extends JFrame {
    private JComboBox<String> typeEvaluationComboBox;
    private JComboBox<Cours> coursComboBox;
    private JTable etudiantsTable;
    private JButton enregistrerButton;
    private JButton btnDeconnexion;

    private EvaluationController evaluationController = new EvaluationController();

    public AjoutEvaluationView() throws SQLException {
        setTitle("Ajouter Évaluation");
        setSize(800, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Haut : type d'évaluation et cours
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Informations d'évaluation"));

        topPanel.add(new JLabel("Type d'évaluation :"));
        typeEvaluationComboBox = new JComboBox<>(new String[]{"TP", "Examen"});
        topPanel.add(typeEvaluationComboBox);

        topPanel.add(new JLabel("Cours :"));
        coursComboBox = new JComboBox<>();
        for (Cours cours : evaluationController.getCoursDisponibles()) {
            coursComboBox.addItem(cours);
        }
        topPanel.add(coursComboBox);

        panel.add(topPanel, BorderLayout.NORTH);

        // Centre : table des étudiants
        List<Etudiant> etudiants = evaluationController.getEtudiantsDisponibles();
        String[] colonnes = {"ID", "Nom", "Matricule", "Filière", "Niveau", "Note"};
        Object[][] donnees = new Object[etudiants.size()][6];
        for (int i = 0; i < etudiants.size(); i++) {
            donnees[i][0] = etudiants.get(i).getIdEtudiant();
            donnees[i][1] = etudiants.get(i).getNom();
            donnees[i][2] = etudiants.get(i).getMatricule();
            donnees[i][3] = etudiants.get(i).getFiliere();
            donnees[i][4] = etudiants.get(i).getNiveau();
            donnees[i][5] = 0.0; // note initiale
        }

        etudiantsTable = new JTable(donnees, colonnes);
        JScrollPane scrollPane = new JScrollPane(etudiantsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Notes des étudiants"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bas : boutons enregistrer et déconnexion dans un sous-panel
        enregistrerButton = new JButton("Enregistrer les évaluations");
        btnDeconnexion = new JButton("Se Déconnecter");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(enregistrerButton);
        bottomPanel.add(btnDeconnexion);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // Action bouton enregistrer
        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) typeEvaluationComboBox.getSelectedItem();
                Cours cours = (Cours) coursComboBox.getSelectedItem();
                double maxNote = type.equals("TP") ? 8.0 : 12.0;

                for (int i = 0; i < etudiantsTable.getRowCount(); i++) {
                    int idEtudiant = (int) etudiantsTable.getValueAt(i, 0);
                    Object valeur = etudiantsTable.getValueAt(i, 5);
                    float note;

                    try {
                        note = (float) Double.parseDouble(valeur.toString());
                        if (note < 0 || note > maxNote) {
                            JOptionPane.showMessageDialog(null, "Note invalide pour l'étudiant ID " + idEtudiant +
                                    " : doit être entre 0 et " + maxNote);
                            return;
                        }

                        evaluationController.ajouterEvaluation(idEtudiant, cours.getIdCours(), type, note);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Note invalide pour l'étudiant ID " + idEtudiant);
                        return;
                    } catch (SQLException ex) {
                        Logger.getLogger(AjoutEvaluationView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                JOptionPane.showMessageDialog(null, "Évaluations enregistrées avec succès !");
                dispose();
            }
        });

        // Action bouton déconnexion
        btnDeconnexion.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }
}




//package EduManage.view;
//
//import EduManage.controller.EvaluationController;
//import EduManage.dao.EvaluationDAO;
//import EduManage.model.Cours;
//import EduManage.model.Etudiant;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.SQLException;
//import java.util.List;
//
//public class AjoutEvaluationView extends JFrame {
//    private JComboBox<String> typeEvaluationComboBox;
//    private JComboBox<Cours> coursComboBox;
//    private JComboBox<Etudiant> etudiantComboBox;
//    private JTextField noteField;
//    private JButton enregistrerButton;
//
//    private EvaluationController evaluationController;
//    private EvaluationDAO evaluationDAO;
//
//    private int idEnseignant;
//
//    public AjoutEvaluationView() {
//        this.idEnseignant = idEnseignant;
//        this.evaluationController = new EvaluationController();
//        this.evaluationDAO = new EvaluationDAO();
//
//        setTitle("Ajout d'une évaluation");
//        setSize(450, 300);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        initUI();
//        chargerCours();
//        chargerEtudiants();
//
//        setVisible(true);
//    }
//
//    private void initUI() {
//        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
//
//        panel.add(new JLabel("Type d'évaluation :"));
//        typeEvaluationComboBox = new JComboBox<>(new String[]{"TP", "Examen"});
//        panel.add(typeEvaluationComboBox);
//
//        panel.add(new JLabel("Cours :"));
//        coursComboBox = new JComboBox<>();
//        panel.add(coursComboBox);
//
//
//////   topPanel.add(new JLabel("Cours :"));
////        panel.add(new JLabel("Cours :"));
////        coursComboBox = new JComboBox<>();
////        for (Cours c : evaluationController.getCoursDisponibles(int idEnseignant)) {
////            coursComboBox.addItem(c);
////        }
////        panel.add(coursComboBox);
//
//
//        panel.add(new JLabel("Étudiant :"));
//        etudiantComboBox = new JComboBox<>();
//        panel.add(etudiantComboBox);
//
//        panel.add(new JLabel("Note :"));
//        noteField = new JTextField();
//        panel.add(noteField);
//
//        enregistrerButton = new JButton("Enregistrer");
//        enregistrerButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                enregistrerEvaluation();
//            }
//        });
//
//        panel.add(new JLabel()); // vide
//        panel.add(enregistrerButton);
//
//        add(panel);
//    }
//
//    private void chargerCours() {
//        try {
//            List<Cours> coursList = evaluationDAO.getCoursDisponibles(idEnseignant);
//            for (Cours c : coursList) {
//                coursComboBox.addItem(c);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des cours.");
//        }
//    }
//
//    private void chargerEtudiants() {
//        try {
//            List<Etudiant> etudiants = evaluationDAO.getEtudiantsDisponibles(idEnseignant);
//            for (Etudiant e : etudiants) {
//                etudiantComboBox.addItem(e);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des étudiants.");
//        }
//    }
//
//    private void enregistrerEvaluation() {
//        String type = (String) typeEvaluationComboBox.getSelectedItem();
//        String noteStr = noteField.getText();
//
//        Cours cours = (Cours) coursComboBox.getSelectedItem();
//        Etudiant etudiant = (Etudiant) etudiantComboBox.getSelectedItem();
//
//        if (cours == null || etudiant == null || type == null || noteStr.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
//            return;
//        }
//
//        try {
//            float note = Float.parseFloat(noteStr);
//            evaluationController.ajouterEvaluation(etudiant.getIdEtudiant(), cours.getIdCours(), type, note);
//            JOptionPane.showMessageDialog(this, "Évaluation ajoutée avec succès.");
//            dispose(); // fermer la fenêtre
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(this, "La note doit être un nombre.");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'évaluation.");
//        }
//    }
//}
