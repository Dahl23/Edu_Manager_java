package EduManage.model;

public class Etudiant {
    private int idEtudiant;
    private String nom;
    private String matricule;
    private String email;
    private String niveau;
    private String filiere;
    private int id_utilisateur;

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
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

    public void setIdUtilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }
    
    @Override
    public String toString() {
        return nom; 
    }

}
