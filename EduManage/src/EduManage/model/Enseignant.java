package EduManage.model;

public class Enseignant {
    private int idEnseignant;
    private String nom;
    private String email;
    private String specialite;
    private int id_utilisateur;

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
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
}
