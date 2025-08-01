package EduManage.model;

public class Cours {

    private int idCours;
    private String libelle;
    private String semestre;

    public Cours() {
        // Constructeur vide nécessaire pour l’utilisation dans CoursDAO.lister()
    }

    public Cours(int idCours, String libelle, String semestre) {
        this.idCours = idCours;
        this.libelle = libelle;
        this.semestre = semestre;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
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

    // 👇 Cette méthode permet d'afficher libelle dans la JComboBox
    @Override
    public String toString() {
        return libelle;
    }
}
