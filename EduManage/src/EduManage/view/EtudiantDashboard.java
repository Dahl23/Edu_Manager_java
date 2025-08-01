package EduManage.view;

import EduManage.controller.EvaluationController;
import EduManage.model.Evaluation;
import EduManage.model.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EtudiantDashboard extends JFrame {

    private JTable evaluationTable;
    private Utilisateur etudiantConnecte;

    public EtudiantDashboard(Utilisateur etudiantConnecte) {
        this.etudiantConnecte = etudiantConnecte;
        setTitle("Tableau de bord de l'étudiant");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // === Panel principal ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Couleur de fond claire

        // === Barre supérieure ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219)); // Bleu clair moderne
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Label à gauche
        JLabel headerLabel = new JLabel("Mes cours et mes notes");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Bouton déconnexion à droite
        JButton deconnexionBtn = new JButton("Déconnexion");
        deconnexionBtn.setFocusPainted(false);
        deconnexionBtn.setBackground(new Color(231, 76, 60)); // Rouge vif
        deconnexionBtn.setForeground(Color.WHITE);
        deconnexionBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        deconnexionBtn.setPreferredSize(new Dimension(130, 35));
        deconnexionBtn.addActionListener(this::deconnexionAction);

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(deconnexionBtn, BorderLayout.EAST);

        // === Tableau des évaluations ===
        String[] columnNames = {"Cours", "Type d'évaluation", "Note"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        evaluationTable = new JTable(model);
        evaluationTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        evaluationTable.setRowHeight(24);
        evaluationTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        evaluationTable.getTableHeader().setBackground(new Color(52, 152, 219));
        evaluationTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(evaluationTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === Ajout des composants ===
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        chargerEvaluations();
        setVisible(true);
    }

    private void chargerEvaluations() {
        EvaluationController evaluationController = new EvaluationController();
        List<Evaluation> evaluations = evaluationController.getEvaluationsByEtudiant(etudiantConnecte.getIdEtudiant());

        DefaultTableModel model = (DefaultTableModel) evaluationTable.getModel();
        model.setRowCount(0); // Vider le tableau

        for (Evaluation e : evaluations) {
            model.addRow(new Object[]{
                e.getLibelle(),
                e.getTypeEvaluation(),
                e.getNote()
            });
        }
    }

    private void deconnexionAction(ActionEvent e) {

        // Déconnexion avec confirmation
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
    }

}

