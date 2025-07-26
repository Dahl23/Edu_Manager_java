package EduManage.controller;

import EduManage.dao.ApprendreDAO;

import java.sql.SQLException;
import java.util.List;

public class ApprendreController {
    private final ApprendreDAO apprendreDAO = new ApprendreDAO();

    public void inscrireEtudiantACours(int idEtudiant, int idCours) throws SQLException {
        apprendreDAO.inscrireEtudiantCours(idEtudiant, idCours);
    }

    public List<Integer> getCoursParEtudiant(int idEtudiant) throws SQLException {
        return apprendreDAO.getCoursParEtudiant(idEtudiant);
    }
}
