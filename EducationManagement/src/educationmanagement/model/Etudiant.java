package educationmanagement.model;

public class Etudiant {
    private int id_etudiant;
    private String nom;
    private String matricule;
    private String email;
    private String niveau;
    private String filiere;
    private int id_utilisateur; // Clé étrangère vers Utilisateur

    // Constructeur vide
    public Etudiant() {
    }

    // Constructeur avec tous les attributs
    public Etudiant(int id_etudiant, String nom, String matricule, String email, String niveau, String filiere, int id_utilisateur) {
        this.id_etudiant = id_etudiant;
        this.nom = nom;
        this.matricule = matricule;
        this.email = email;
        this.niveau = niveau;
        this.filiere = filiere;
        this.id_utilisateur = id_utilisateur;
    }

    // Constructeur sans id_etudiant (pour l'insertion)
    public Etudiant(String nom, String matricule, String email, String niveau, String filiere, int id_utilisateur) {
        this.nom = nom;
        this.matricule = matricule;
        this.email = email;
        this.niveau = niveau;
        this.filiere = filiere;
        this.id_utilisateur = id_utilisateur;
    }

    // Getters et Setters

    public int getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
               "id_etudiant=" + id_etudiant +
               ", nom='" + nom + '\'' +
               ", matricule='" + matricule + '\'' +
               ", email='" + email + '\'' +
               ", niveau='" + niveau + '\'' +
               ", filiere='" + filiere + '\'' +
               ", id_utilisateur=" + id_utilisateur +
               '}';
    }
}