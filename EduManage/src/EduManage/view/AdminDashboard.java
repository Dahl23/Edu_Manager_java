package EduManage.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JFrame {
    
    // Palette de couleurs moderne
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Bleu principal
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);    // Bleu clair
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);       // Vert
    private static final Color DANGER_COLOR = new Color(231, 76, 60);        // Rouge
    private static final Color DARK_COLOR = new Color(44, 62, 80);           // Gris foncé
    private static final Color LIGHT_COLOR = new Color(236, 240, 241);       // Gris clair
    private static final Color WHITE_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(52, 73, 94);
    
    public AdminDashboard() {
        initializeFrame();
        createComponents();
    }
    
    private void initializeFrame() {
        setTitle("Dashboard Admin - EduManage");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Couleur de fond
        getContentPane().setBackground(LIGHT_COLOR);
        
    }
    
    private void createComponents() {
        // Header avec titre et informations
        JPanel headerPanel = createHeaderPanel();
        
        // Panel principal avec les boutons
        JPanel mainPanel = createMainPanel();
        
        // Footer avec informations système
        JPanel footerPanel = createFooterPanel();
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        // Titre principal
        JLabel titleLabel = new JLabel("🎓 Espace Administrateur");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(WHITE_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Sous-titre
        JLabel subtitleLabel = new JLabel("Système de Gestion Éducative - EduManage");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(LIGHT_COLOR);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(DARK_COLOR);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        // Panel principal avec disposition verticale
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(LIGHT_COLOR);
        
        // Section 1: Gestion des utilisateurs (grande carte centrale)
        JPanel userManagementSection = createSectionPanel("Gestion des Utilisateurs", "👥");
        JPanel userButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        userButtonsPanel.setBackground(WHITE_COLOR);
        
        JButton btnGererUtilisateurs = createStyledCardButton("👥 Gérer Utilisateurs", "Modifier, supprimer des comptes", PRIMARY_COLOR, new Dimension(280, 80));
        JButton btnAjoutEtudiant = createStyledCardButton("🎓 Ajouter Étudiant", "Nouveau compte étudiant", SECONDARY_COLOR, new Dimension(200, 80));
        JButton btnAjoutEnseignant = createStyledCardButton("👨‍🏫 Ajouter Enseignant", "Nouveau compte enseignant", SECONDARY_COLOR, new Dimension(200, 80));
        
        userButtonsPanel.add(btnGererUtilisateurs);
        userButtonsPanel.add(btnAjoutEtudiant);
        userButtonsPanel.add(btnAjoutEnseignant);
        userManagementSection.add(userButtonsPanel);
        
        // Section 2: Gestion des cours
        JPanel courseManagementSection = createSectionPanel("Gestion des Cours", "📚");
        JPanel courseButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        courseButtonsPanel.setBackground(WHITE_COLOR);
        
        JButton btnGererCours = createStyledCardButton("📚 Gérer Cours", "Créer, modifier les cours", ACCENT_COLOR, new Dimension(220, 80));
        JButton btnAssocierEtudiantCours = createStyledCardButton("📝 Inscrire Étudiant", "Associer étudiant à un cours", new Color(155, 89, 182), new Dimension(220, 80));
        JButton btnAssocierEnseignantCours = createStyledCardButton("👨‍🏫 Assigner Enseignant", "Associer enseignant à un cours", new Color(230, 126, 34), new Dimension(220, 80));
        
        courseButtonsPanel.add(btnGererCours);
        courseButtonsPanel.add(btnAssocierEtudiantCours);
        courseButtonsPanel.add(btnAssocierEnseignantCours);
        courseManagementSection.add(courseButtonsPanel);
        
        // Section 3: Actions système
        JPanel systemSection = createSectionPanel("Actions Système", "⚙️");
        JPanel systemButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        systemButtonsPanel.setBackground(WHITE_COLOR);
        
        JButton btnDeconnexion = createStyledCardButton("🚪 Déconnexion", "Quitter le système", DANGER_COLOR, new Dimension(200, 60));
        
        systemButtonsPanel.add(btnDeconnexion);
        systemSection.add(systemButtonsPanel);
        
        // Ajout des sections au panel principal
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(userManagementSection);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(courseManagementSection);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(systemSection);
        contentPanel.add(Box.createVerticalGlue());
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Action listeners
        btnAjoutEtudiant.addActionListener((ActionEvent e) -> new AjoutEtudiantView().setVisible(true));
        btnAjoutEnseignant.addActionListener((ActionEvent e) -> new AjoutEnseignantView().setVisible(true));
        btnGererCours.addActionListener((ActionEvent e) -> new GererCoursView().setVisible(true));
        btnAssocierEtudiantCours.addActionListener((ActionEvent e) -> new InscriptionEtudiantView().setVisible(true));
        btnAssocierEnseignantCours.addActionListener((ActionEvent e) -> new AffectationEnseignantView().setVisible(true));
        btnGererUtilisateurs.addActionListener((ActionEvent e) -> new GererUtilisateursView().setVisible(true));
        
        // Déconnexion avec confirmation
        btnDeconnexion.addActionListener(e -> {
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
        
        return mainPanel;
    }
    
    private JPanel createSectionPanel(String title, String icon) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(WHITE_COLOR);
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        // Titre de la section
        JLabel sectionTitle = new JLabel(icon + "  " + title);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(TEXT_COLOR);
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        sectionPanel.add(sectionTitle);
        
        return sectionPanel;
    }
    
    private JButton createStyledCardButton(String mainText, String subText, Color backgroundColor, Dimension size) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        
        // Panel pour le contenu du bouton
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Texte principal
        JLabel mainLabel = new JLabel(mainText);
        mainLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainLabel.setForeground(WHITE_COLOR);
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Texte secondaire (description)
        JLabel subLabel = new JLabel(subText);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLabel.setForeground(new Color(255, 255, 255, 180));
        subLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        contentPanel.add(mainLabel, BorderLayout.CENTER);
        contentPanel.add(subLabel, BorderLayout.SOUTH);
        
        button.add(contentPanel);
        
        // Style de base
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(backgroundColor.darker(), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effets hover avec animation plus subtile
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color hoverColor = brighter(backgroundColor, 0.2f);
                button.setBackground(hoverColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(hoverColor.darker(), 2),
                    BorderFactory.createEmptyBorder(4, 4, 4, 4)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(backgroundColor.darker(), 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                Color pressedColor = darker(backgroundColor, 0.2f);
                button.setBackground(pressedColor);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                Color hoverColor = brighter(backgroundColor, 0.2f);
                button.setBackground(hoverColor);
            }
        });
        
        return button;
    }
    
    // Méthodes utilitaires pour les couleurs
    private Color brighter(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() * (1 + factor)));
        int g = Math.min(255, (int)(color.getGreen() * (1 + factor)));
        int b = Math.min(255, (int)(color.getBlue() * (1 + factor)));
        return new Color(r, g, b);
    }
    
    private Color darker(Color color, float factor) {
        int r = Math.max(0, (int)(color.getRed() * (1 - factor)));
        int g = Math.max(0, (int)(color.getGreen() * (1 - factor)));
        int b = Math.max(0, (int)(color.getBlue() * (1 - factor)));
        return new Color(r, g, b);
    }
    

    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(DARK_COLOR);
        footerPanel.setBorder(new EmptyBorder(15, 30, 15, 30));
        
        // Informations système
        JLabel systemInfo = new JLabel("🖥️ Système EduManage v1.0 - Connecté en tant qu'Administrateur");
        systemInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        systemInfo.setForeground(LIGHT_COLOR);
        
        // Date et heure
        JLabel dateTime = new JLabel("📅 " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        ));
        dateTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateTime.setForeground(LIGHT_COLOR);
        
        footerPanel.add(systemInfo, BorderLayout.WEST);
        footerPanel.add(dateTime, BorderLayout.EAST);
        
        return footerPanel;
    }
    
}