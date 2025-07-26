package EduManage;

import javax.swing.SwingUtilities;
import EduManage.view.Accueil;

public class EduManage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Accueil().setVisible(true);
        });
    }
    
    
}
