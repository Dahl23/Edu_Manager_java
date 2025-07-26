package EduManage.controller;

import EduManage.dao.ApprendreDAO;

import java.sql.SQLException;

public class InscriptionController {
    private final ApprendreDAO apprendreDAO = new ApprendreDAO();

    public void inscrireEtudiant(int idEtudiant, int idCours) throws SQLException {
        apprendreDAO.inscrireEtudiantCours(idEtudiant, idCours);
    }
}
