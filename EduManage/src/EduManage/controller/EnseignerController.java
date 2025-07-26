package EduManage.controller;

import EduManage.dao.EnseignerDAO;

import java.sql.SQLException;
import java.util.List;

public class EnseignerController {
    private final EnseignerDAO enseignerDAO = new EnseignerDAO();

    public void affecterCoursAEnseignant(int idEnseignant, int idCours) throws SQLException {
        enseignerDAO.affecterCours(idEnseignant, idCours);
    }

    public List<Integer> getCoursParEnseignant(int idEnseignant) throws SQLException {
        return enseignerDAO.getCoursParEnseignant(idEnseignant);
    }
}
