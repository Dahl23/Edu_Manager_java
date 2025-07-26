package EduManage.controller;

import EduManage.dao.EnseignerDAO;

import java.sql.SQLException;

public class AffectationController {
    private final EnseignerDAO enseignerDAO = new EnseignerDAO();

    public void affecterEnseignant(int idEnseignant, int idCours) throws SQLException {
        enseignerDAO.affecterCours(idEnseignant, idCours);
    }
}
