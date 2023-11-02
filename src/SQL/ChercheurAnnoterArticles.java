package SQL;

import java.sql.Connection;

public class ChercheurAnnoterArticles extends Base{
    private String requete;

    public ChercheurAnnoterArticles(Connection c){
        super(c);
        this.requete = "SELECT Chercheur.NOMCHERCHEUR, COUNT(Annoter.TITRE) AS NombreArticlesAnnotes\n" +
                "FROM Chercheur\n" +
                "LEFT JOIN Annoter ON Chercheur.EMAIL = Annoter.EMAIL\n" +
                "GROUP BY Chercheur.NOMCHERCHEUR\n" +
                "HAVING COUNT(Annoter.TITRE) >= ?";
    }

    /**
     *
     * @param nb
     * @return
     */
    public String rechercherChercheurs(int nb){

    }
}
