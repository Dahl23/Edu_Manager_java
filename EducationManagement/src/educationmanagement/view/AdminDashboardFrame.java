package educationmanagement.view;

import educationmanagement.model.Utilisateur;
import educationmanagement.controller.NavigationController;
import educationmanagement.controller.AdminController; // Nous aurons besoin de ce contrôleur pour les sous-panels
import educationmanagement.view.pannels.CourseManagementPanel;
import educationmanagement.view.pannels.StudentEnrollmentPanel;
import educationmanagement.view.pannels.TeacherAssignmentPanel;
import educationmanagement.view.pannels.UserManagementPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardFrame extends JFrame {

    private Utilisateur currentUser; // Pour stocker l'utilisateur connecté (Admin)
    private AdminController adminController; // L'instance du contrôleur pour l'admin

    public AdminDashboardFrame(Utilisateur user) {
        this.currentUser = user;
        this.adminController = new AdminController(); // Initialiser le contrôleur
        
        setTitle("Système de Gestion de l'Enseignement - Tableau de Bord Administrateur - Connecté en tant que " + user.getNom_utilisateur());
        setSize(1200, 800); // Taille plus grande pour un tableau de bord
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        initComponents();
        addLogoutMenuItem(); // Ajouter l'option de déconnexion
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // 1. Panel de gestion des Utilisateurs (Étudiants et Enseignants)
        // Nous allons créer un JPanel dédié pour ça
        UserManagementPanel userManagementPanel = new UserManagementPanel(adminController);
        tabbedPane.addTab("Gestion des Utilisateurs", userManagementPanel);
        tabbedPane.setMnemonicAt(0, 'U'); // Raccourci clavier Alt+U

        // 2. Panel de gestion des Cours
        // Nous allons créer un JPanel dédié pour ça
        CourseManagementPanel courseManagementPanel = new CourseManagementPanel(adminController);
        tabbedPane.addTab("Gestion des Cours", courseManagementPanel);
        tabbedPane.setMnemonicAt(1, 'C'); // Raccourci clavier Alt+C

        // 3. Panel d'Affectation Enseignants aux Cours
        TeacherAssignmentPanel teacherAssignmentPanel = new TeacherAssignmentPanel(adminController);
        tabbedPane.addTab("Affectation Enseignants", teacherAssignmentPanel);
        tabbedPane.setMnemonicAt(2, 'A'); // Raccourci clavier Alt+A

        // 4. Panel d'Inscription Étudiants aux Cours
        StudentEnrollmentPanel studentEnrollmentPanel = new StudentEnrollmentPanel(adminController);
        tabbedPane.addTab("Inscription Étudiants", studentEnrollmentPanel);
        tabbedPane.setMnemonicAt(3, 'I'); // Raccourci clavier Alt+I

        add(tabbedPane, BorderLayout.CENTER);
    }
    
    // Méthode pour ajouter le menu de déconnexion
    private void addLogoutMenuItem() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem logoutItem = new JMenuItem("Déconnexion");
        logoutItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationController.logout(AdminDashboardFrame.this); // Appel de la méthode de déconnexion
            }
        });
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
}