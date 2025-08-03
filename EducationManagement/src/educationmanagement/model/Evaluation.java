package educationmanagement.model;

public class Evaluation {
    private int id_evaluation;
    private String type_evaluation;
    private double note; // Utiliser double pour la note pour plus de précision
    private int id_etudiant; // Clé étrangère vers Etudiant
    private int id_cours;    // Clé étrangère vers Cours

    // Constructeur vide
    public Evaluation() {
    }

    // Constructeur avec tous les attributs
    public Evaluation(int id_evaluation, String type_evaluation, double note, int id_etudiant, int id_cours) {
        this.id_evaluation = id_evaluation;
        this.type_evaluation = type_evaluation;
        this.note = note;
        this.id_etudiant = id_etudiant;
        this.id_cours = id_cours;
    }

    // Constructeur sans id_evaluation (pour l'insertion)
    public Evaluation(String type_evaluation, double note, int id_etudiant, int id_cours) {
        this.type_evaluation = type_evaluation;
        this.note = note;
        this.id_etudiant = id_etudiant;
        this.id_cours = id_cours;
    }

    // Getters et Setters

    public int getId_evaluation() {
        return id_evaluation;
    }

    public void setId_evaluation(int id_evaluation) {
        this.id_evaluation = id_evaluation;
    }

    public String getType_evaluation() {
        return type_evaluation;
    }

    public void setType_evaluation(String type_evaluation) {
        this.type_evaluation = type_evaluation;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public int getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
               "id_evaluation=" + id_evaluation +
               ", type_evaluation='" + type_evaluation + '\'' +
               ", note=" + note +
               ", id_etudiant=" + id_etudiant +
               ", id_cours=" + id_cours +
               '}';
    }
}