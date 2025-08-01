package EduManage.controller;

import EduManage.dao.CoursDAO;
import EduManage.model.Cours;

import java.sql.SQLException;
import java.util.List;

public class CoursController {
    private final CoursDAO coursDAO = new CoursDAO();

    public void ajouterCours(Cours c) throws SQLException {
        coursDAO.ajouter(c);
    }

    public List<Cours> getTousLesCours() throws SQLException {
        return coursDAO.lister();
    }

    public void modifierCours(Cours c) throws SQLException {
        coursDAO.modifier(c);
    }

    public void supprimerCours(int idCours) throws SQLException {
        coursDAO.supprimer(idCours);
    }

    public Cours getCoursParId(int idCours) throws SQLException {
        return coursDAO.getCoursParId(idCours);
    }
}
