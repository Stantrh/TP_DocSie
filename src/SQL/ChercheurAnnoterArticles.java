package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChercheurAnnoterArticles extends Base{
    private String requete;

    public ChercheurAnnoterArticles(Connection c){
        super(c);
        this.requete = "SELECT Chercheur.EMAIL, Chercheur.NOMCHERCHEUR, Chercheur.PRENOMCHERCHEUR, COUNT(Annoter.TITRE) AS NombreArticlesAnnotes\n" +
                "FROM Chercheur\n" +
                "LEFT JOIN Annoter ON Chercheur.EMAIL = Annoter.EMAIL\n" +
                "GROUP BY Chercheur.EMAIL, Chercheur.NOMCHERCHEUR, Chercheur.PRENOMCHERCHEUR\n" +
                "HAVING COUNT(Annoter.TITRE) >= ?\n";
    }

    /**
     * Méthode qui permet de trouver les chercheurs qui ont noté au moins nb articles
     * @param nb nombre d'articles minimum
     * @return les chercheurs ayant noté
     */
    public String rechercherChercheurs(int nb) throws SQLException {
        String res = "Aucun chercheur n'a annoté au moins " + nb + " articles...";
        PreparedStatement preparedStatement = this.connection.prepareStatement(this.requete);
        preparedStatement.setInt(1, nb);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            res = "Voici le(s) chercheur(s) ayant annoté au moins " + nb + " articles : \n\n";
            res += "Nom : " + resultSet.getString("nomchercheur") + "\n";
            res += "Prénom : " + resultSet.getString("prenomchercheur") + "\n";
            res += "Email : " + resultSet.getString("email") + "\n";
            res += "Nombre d'articles notés : " + resultSet.getString("NombreArticlesAnnotes") + "\n";

            res += "\n---------------\n\n";

            while(resultSet.next()){
                res += "Nom : " + resultSet.getString("nomchercheur") + "\n";
                res += "Prénom : " + resultSet.getString("prenomchercheur") + "\n";
                res += "Email : " + resultSet.getString("email") + "\n";
                res += "Nombre d'articles notés : " + resultSet.getString("NombreArticlesAnnotes") + "\n";

                res += "\n---------------\n\n";
            }
        }
        return res;
    }
}
