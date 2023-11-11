package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe qui permet pour chaque chercheur d’un laboratoire donné, afficher le nombre d’articles
 * publiés, le nombre et la moyenne des notes obtenues. Les
 * chercheurs sont triés par ordre décroissant du nombre d’articles publiés.
 */
public class AffChercheurLabDonne extends Base{
    /**
     * Unique requête qu'on utilisera pour l'affichage
     */
    private String requete;

    /**
     * Construit un objet AffChercheurLabDonne à partir d'une connection à une base de données et instancie la requête qu'on utilisera
     * @param c
     */
    public AffChercheurLabDonne(Connection c){
        super(c);
        // Pas le choix de tout faire en une seule requête, car pour avoir le tri il faut déjà avoir le nombre d'articles publiés
        // Left join pour avoir le droit à des lignes null si certaines n'ont rien noté
        this.requete = "SELECT\n" +
                "    C.NOMCHERCHEUR, C.PRENOMCHERCHEUR, C.EMAIL,\n" +
                "    COUNT(DISTINCT A.TITRE) AS NOMBRE_ARTICLES_PUBLIES,\n" +
                "    COUNT(NOTE) AS NOMBRE_NOTES,\n" +
                "    AVG(NOTE) AS MOYENNE_NOTES\n" +
                "FROM\n" +
                "    CHERCHEUR C\n" +
                "JOIN\n" +
                "    TRAVAILLER T ON C.EMAIL = T.EMAIL\n" +
                "JOIN\n" +
                "    LABORATOIRE L ON T.NOMLABO = L.NOMLABO\n" +
                "LEFT JOIN\n" +
                "    ECRIRE E ON C.EMAIL = E.EMAIL\n" +
                "LEFT JOIN\n" +
                "    ARTICLE A ON E.TITRE = A.TITRE\n" +
                "LEFT JOIN\n" +
                "    NOTER N ON A.TITRE = N.TITRE\n" +
                "WHERE\n" +
                "    L.NOMLABO = ?" +
                "GROUP BY\n" +
                "    C.NOMCHERCHEUR, C.PRENOMCHERCHEUR, C.EMAIL\n" +
                "ORDER BY\n" +
                "    NOMBRE_ARTICLES_PUBLIES DESC";
    }

    /**
     * Retourne une chaine de caractères qui affiche pour chaque chercheur appartenant à un laboratoire donnée,
     * leur nombre d'articles publiés, leur nombre de notes obtenues, et la moyenne de ces notes obtenues
     * les chercheurs sont ordonnés par ordre décroissant selon leur nombre d'articles publiés
     * @param labo
     * @return
     */
    public String afficherChercheursTries(String labo){
        String res = "Voici le classement par ordre décroissant du nombre d'articles publiés des chercheurs du laboratoire "
                + labo + " : \n\n";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(this.requete);
            preparedStatement.setString(1, labo);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                res += resultSet.getString("nomchercheur") + " " +
                        resultSet.getString("prenomchercheur") + " " +
                        resultSet.getString("email") + " : \n";
                res += "\t. [" + resultSet.getInt("nombre_articles_publies") + "] article(s) publié(s)\n";
                if(resultSet.getInt("nombre_notes") == 0)
                    res += "\tn'a obtenu encore aucune note...";
                else{
                    res += "\t" + resultSet.getInt("nombre_notes") + " notes reçues au total pour une moyenne de : " +
                    resultSet.getInt("moyenne_notes") + "/5\n";
                }
                res += "\n-----------\n\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return res;
    }

}
