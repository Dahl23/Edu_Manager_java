package educationmanagement.model;

// Cette classe représente la table d'association 'apprendre'
// qui contient les clés primaires composites (id_etudiant, id_cours)
public class Apprendre {
    private int id_etudiant;
    private int id_cours;

    // Constructeur vide
    public Apprendre() {
    }

    // Constructeur avec tous les attributs
    public Apprendre(int id_etudiant, int id_cours) {
        this.id_etudiant = id_etudiant;
        this.id_cours = id_cours;
    }

    // Getters et Setters

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
        return "Apprendre{" +
               "id_etudiant=" + id_etudiant +
               ", id_cours=" + id_cours +
               '}';
    }
}