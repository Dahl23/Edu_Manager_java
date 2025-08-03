package educationmanagement.view.dialogs;

import educationmanagement.model.Cours;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseDialog extends JDialog {

    private Cours currentCours; // Cours à modifier (null si ajout)

    private JTextField libelleField;
    private JTextField semestreField;

    private boolean confirmed = false;

    // Constructeur pour AJOUTER un nouveau cours
    public CourseDialog(JFrame parent) {
        super(parent, "Ajouter un nouveau cours", true); // true pour modal
        setSize(400, 250);
        setLocationRelativeTo(parent);
        initComponents();
    }

    // Constructeur pour MODIFIER un cours existant
    public CourseDialog(JFrame parent, Cours cours) {
        super(parent, "Modifier le cours : " + cours.getLibelle(), true); // true pour modal
        this.currentCours = cours;
        setSize(400, 250);
        setLocationRelativeTo(parent);
        initComponents();
        populateFields(); // Pré-remplir les champs avec les données existantes
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Libellé:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        libelleField = new JTextField(20);
        formPanel.add(libelleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Semestre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        semestreField = new JTextField(20);
        formPanel.add(semestreField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Boutons Confirmer/Annuler
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton confirmButton = new JButton("Confirmer");
        JButton cancelButton = new JButton("Annuler");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> {
            if (validateInputs()) {
                confirmed = true;
                dispose();
            }
        });
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
    }

    private void populateFields() {
        if (currentCours != null) {
            libelleField.setText(currentCours.getLibelle());
            semestreField.setText(currentCours.getSemestre());
        }
    }

    private boolean validateInputs() {
        if (libelleField.getText().isEmpty() || semestreField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Libellé et Semestre sont requis.", "Erreur de validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Cours getCoursData() {
        // Pour la modification, on réutilise l'ID du cours existant
        int id = (currentCours != null) ? currentCours.getId_cours() : 0;
        return new Cours(id, libelleField.getText(), semestreField.getText());
    }
}