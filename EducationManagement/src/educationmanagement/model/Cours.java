package educationmanagement.model;

public class Cours {
    private int id_cours;
    private String libelle;
    private String semestre;

    // Constructeur vide
    public Cours() {
    }

    // Constructeur avec tous les attributs
    public Cours(int id_cours, String libelle, String semestre) {
        this.id_cours = id_cours;
        this.libelle = libelle;
        this.semestre = semestre;
    }

    // Constructeur sans id_cours (pour l'insertion)
    public Cours(String libelle, String semestre) {
        this.libelle = libelle;
        this.semestre = semestre;
    }

    // Getters et Setters

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return "Cours{" +
               "id_cours=" + id_cours +
               ", libelle='" + libelle + '\'' +
               ", semestre='" + semestre + '\'' +
               '}';
    }
}