package EduManage.controller;

import EduManage.dao.EnseignantDAO;
import EduManage.model.Enseignant;

import java.sql.SQLException;
import java.util.List;

public class EnseignantController {
    private final EnseignantDAO enseignantDAO = new EnseignantDAO();

    public void ajouterEnseignant(Enseignant e) throws SQLException {
        enseignantDAO.ajouter(e);
    }

    public List<Enseignant> getTousLesEnseignants() throws SQLException {
        return enseignantDAO.lister();
    }
}
