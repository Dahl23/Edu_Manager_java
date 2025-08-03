package educationmanagement.model;

public class Utilisateur {
    private int id_utilisateur;
    private String nom_utilisateur;
    private String mot_de_passe;
    private String role; // Ex: "Administrateur", "Enseignant", "Etudiant"

    // Constructeur vide (par défaut)
    public Utilisateur() {
    }

    // Constructeur avec tous les attributs (utile pour créer un objet complet)
    public Utilisateur(int id_utilisateur, String nom_utilisateur, String mot_de_passe, String role) {
        this.id_utilisateur = id_utilisateur;
        this.nom_utilisateur = nom_utilisateur;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
    }

    // Constructeur sans id_utilisateur (utile pour l'insertion où l'ID est auto-incrémenté)
    public Utilisateur(String nom_utilisateur, String mot_de_passe, String role) {
        this.nom_utilisateur = nom_utilisateur;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
    }

    // Getters et Setters pour chaque attribut

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
               "id_utilisateur=" + id_utilisateur +
               ", nom_utilisateur='" + nom_utilisateur + '\'' +
               ", mot_de_passe='[PROTECTED]'" + // Ne pas afficher le mot de passe pour des raisons de sécurité
               ", role='" + role + '\'' +
               '}';
    }
}