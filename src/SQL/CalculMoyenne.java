package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe qui permet de calculer la moyenne des notes attribuées par un chercheur donné
 */
public class CalculMoyenne extends Base{
    /**
     * Requete pour avoir la moyenne des notes d'attribuées par un chercheur
     */
    private String requete;

    /**
     * Requete pour avoir l'ensemble des notes attribuées par un chercheur
     */
    private String listeNotes;

    /**
     * Constructeur qui crée un objet CalculMoyenne
     * @param c Connexion à la base de données oracle
     */
    public CalculMoyenne(Connection c){
        super(c);
        this.requete = "SELECT avg(note) as MoyenneNotes FROM Noter";
        this.listeNotes = "SELECT * from noter";
    }

    /**
     * Permet de retourner la moyenne (détaillée ou non) des notes attribuées par un chercheur (désigné par son nom ou email selon la valeur du booléen nom)
     * @param detaille
     * @param chercheur
     * @param nom
     * @return
     */
    public String calculerMoyenne(boolean detaille, String chercheur, boolean nom){
        // On vérifie d'abord selon quoi on fait la recherche
        if(nom){
            this.requete += " inner join CHERCHEUR on noter.EMAIL = CHERCHEUR.EMAIL where chercheur.NOMCHERCHEUR = ?";
            this.listeNotes += " inner join CHERCHEUR on noter.EMAIL = CHERCHEUR.EMAIL where chercheur.NOMCHERCHEUR = ?";
        }else{
            this.requete += " WHERE email = ?";
            this.listeNotes += " where email = ?";
        }

        String res = chercheur + " n'a noté aucun article...\n";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(this.requete);
            preparedStatement.setString(1, chercheur);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                res = "La moyenne des notes attribuées par " + chercheur + " est de : " + resultSet.getString("MoyenneNotes") + "/5\n";

                if(detaille){
                    // Alors on va afficher également toutes les notes qui constituent cette moyenne
                    preparedStatement = this.connection.prepareStatement(this.listeNotes);
                    preparedStatement.setString(1, chercheur);
                    resultSet = preparedStatement.executeQuery();
                    res += "Concernant les détails, voici la liste des notes attribuées à chaque article : \n\n";

                    while(resultSet.next()){
                        res += resultSet.getString("titre") + " : " + resultSet.getString("note") + "/5\n";
                        res += "-------------------\n";
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
