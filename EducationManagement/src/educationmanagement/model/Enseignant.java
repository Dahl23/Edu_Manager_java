package educationmanagement.model;

public class Enseignant {
    private int id_enseignant;
    private String nom;
    private String email;
    private String specialite;
    private int id_utilisateur; // Clé étrangère vers Utilisateur

    // Constructeur vide
    public Enseignant() {
    }

    // Constructeur avec tous les attributs
    public Enseignant(int id_enseignant, String nom, String email, String specialite, int id_utilisateur) {
        this.id_enseignant = id_enseignant;
        this.nom = nom;
        this.email = email;
        this.specialite = specialite;
        this.id_utilisateur = id_utilisateur;
    }

    // Constructeur sans id_enseignant (pour l'insertion)
    public Enseignant(String nom, String email, String specialite, int id_utilisateur) {
        this.nom = nom;
        this.email = email;
        this.specialite = specialite;
        this.id_utilisateur = id_utilisateur;
    }

    // Getters et Setters

    public int getId_enseignant() {
        return id_enseignant;
    }

    public void setId_enseignant(int id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    @Override
    public String toString() {
        return "Enseignant{" +
               "id_enseignant=" + id_enseignant +
               ", nom='" + nom + '\'' +
               ", email='" + email + '\'' +
               ", specialite='" + specialite + '\'' +
               ", id_utilisateur=" + id_utilisateur +
               '}';
    }
}