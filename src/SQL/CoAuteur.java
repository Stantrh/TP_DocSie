package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cette classe permet l'affichage de la liste des co-auteurs ayant travaillé avec un chercheur donné
 */
public class CoAuteur extends Base{

    /**
     * Requete qui permet d'obtenir la liste des articles sur lesquels un chercheur a travaillé
     */
    private String requeteTitres;

    /**
     * Requete qui a partir d'un titre determine les co auteurs du chercheur l'ayant écrit.
     */
    private String requeteCoAuteurs;
    public CoAuteur(Connection c){
        super(c);
        this.requeteTitres = "select titre from ECRIRE";
        this.requeteCoAuteurs = "select * from ecrire";
    }

    /**
     * Methode qui permet d'obtenir les co auteurs d'un chercheur selon son nom ou son email (si le booléen nom vaut faux, c'est par l'email que se fait la recherche)
     * @param chercheur
     * @param nom
     * @return
     */
    public String rechercherCoAuteurs(String chercheur, boolean nom) throws SQLException {
        // On adapte les requêtes selon si on cherche par le nom ou pas l'email
        if(nom){
            this.requeteTitres += " inner join chercheur on ecrire.email = chercheur.email where chercheur.nomchercheur = ?";
            this.requeteCoAuteurs += " inner join chercheur on ecrire.email = chercheur.email where chercheur.nomchercheur != ? and titre = ?";
        }
        else {
            this.requeteTitres += " where email = ?";
            this.requeteCoAuteurs += " inner join chercheur on ecrire.email = chercheur.email where chercheur.email  != ? and titre = ?";
        }

        // Puis on peut commencer à préparer la première
        PreparedStatement preparedStatement = this.connection.prepareStatement(this.requeteTitres);
        preparedStatement.setString(1, chercheur);
        ResultSet resultSet = preparedStatement.executeQuery();

        String res = "Voici la liste des co-auteurs ayant travaillé avec " + chercheur + " : \n\n";

        // On a maintenant notre liste d'articles écrits par le chercheur en question
        // On va maintenant pour chaque article effectuer notre deuxième requête pour avoir la liste des auteurs qu'on affichera directement
        while(resultSet.next()){
            preparedStatement = this.connection.prepareStatement(this.requeteCoAuteurs);
            preparedStatement.setString(1, chercheur);
            preparedStatement.setString(2, resultSet.getString("titre"));
            // Pour chaque article on a dans ce resultSet la liste des personnes ayant travaillé sur le livre autres que le chercheur en question
            ResultSet coAuteurs = preparedStatement.executeQuery();
            res += "Pour l'article \"" + resultSet.getString("titre") + "\" --> \n";

            // On vérifie s'il y a des co-auteurs, s'il n'y en a pas, on le signifie
            if(coAuteurs.next()){
                res += "\t . " + coAuteurs.getString("nomchercheur") + " " + coAuteurs.getString("prenomchercheur")
                        + " " + coAuteurs.getString("email") + "\n";
                while(coAuteurs.next()){
                    res += "\t . " + coAuteurs.getString("nomchercheur") + " " + coAuteurs.getString("prenomchercheur")
                            + " " + coAuteurs.getString("email") + "\n";
                }
            }else{
                res += "\t . Aucun autre chercheur n'a contribué à cet article...\n";
            }
            res += "\n----------\n";
        }


        return res;
    }



}
