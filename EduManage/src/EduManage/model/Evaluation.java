package EduManage.model;

public class Evaluation {
    private int idEvaluation;
    private String typeEvaluation;
    private float note;
    private Etudiant etudiant; // Remplacer idEtudiant par un objet Etudiant
    private Cours cours;       // Remplacer idCours par un objet Cours
    private String libelle;
    private String nom;

    public int getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(int idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public String getTypeEvaluation() {
        return typeEvaluation;
    }

    public void setTypeEvaluation(String typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) { // Changement du type de Utilisateur à Etudiant
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Optionnel : Gardez idEtudiant et idCours si nécessaire pour la base de données
    public int getIdEtudiant() {
        return etudiant != null ? etudiant.getIdEtudiant() : 0; // Protection contre null
    }

    public void setIdEtudiant(int idEtudiant) {
        if (this.etudiant == null) {
            this.etudiant = new Etudiant();
        }
        this.etudiant.setIdEtudiant(idEtudiant);
    }

    public int getIdCours() {
        return cours != null ? cours.getIdCours() : 0; // Protection contre null
    }

    public void setIdCours(int idCours) {
        if (this.cours == null) {
            this.cours = new Cours();
        }
        this.cours.setIdCours(idCours);
    }

    public void setEtudiant(Utilisateur etudiant) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}