package educationmanagement.model;

// Cette classe représente la table d'association 'enseigner'
// qui contient les clés primaires composites (id_enseignant, id_cours)
public class Enseigner {
    private int id_enseignant;
    private int id_cours;

    // Constructeur vide
    public Enseigner() {
    }

    // Constructeur avec tous les attributs
    public Enseigner(int id_enseignant, int id_cours) {
        this.id_enseignant = id_enseignant;
        this.id_cours = id_cours;
    }

    // Getters et Setters

    public int getId_enseignant() {
        return id_enseignant;
    }

    public void setId_enseignant(int id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    @Override
    public String toString() {
        return "Enseigner{" +
               "id_enseignant=" + id_enseignant +
               ", id_cours=" + id_cours +
               '}';
    }
}