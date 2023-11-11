package SQL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VerifierNoteMax extends Base {

    /**
     * Permet d'obtenir les emails des chercheurs ayant mis la note maximale à un article
     */
    private String requeteEmailNoteMax;

    /**
     * Permet d'obtenir les laboratoires dans lesquels les personnes qui ont écrit l'article travaillent
     */
    private String requeteLaboArticle;

    /**
     * Permet de vérifier si un chercheur travaille dans un laboratoire
     */
    private String requeteChercheurLabo;

    /**
     * Constructeur qui prend la connexion en attribut et instancie les requêtes qui seront utilisées plus tard
     * @param c
     */

    public VerifierNoteMax(Connection c) {
        super(c);
        this.requeteEmailNoteMax = "\n" +
                "SELECT N.EMAIL, MAX(NOTE) AS Note_Max\n" +
                "FROM NOTER N\n" +
                "WHERE N.NOTE = (\n" +
                "    SELECT MAX(NOTE)\n" +
                "    FROM NOTER\n" +
                "    WHERE TITRE = ?\n" +
                ")\n" +
                "AND N.TITRE = ?\n" +
                "GROUP BY N.EMAIL\n";
        this.requeteLaboArticle = "SELECT DISTINCT T.NOMLABO\n" +
                "FROM Travailler T\n" +
                "JOIN Chercheur C ON T.EMAIL = C.EMAIL\n" +
                "WHERE C.EMAIL IN (\n" +
                "    SELECT DISTINCT E.EMAIL\n" +
                "    FROM Ecrire E\n" +
                "    WHERE E.TITRE = ?\n" +
                ")";
        this.requeteChercheurLabo = "SELECT COUNT(*) AS COUNT_TRAVAILLER\n" +
                "FROM Travailler\n" +
                "WHERE EMAIL = ? AND NOMLABO = ?";

    }

    /**
     * Permet de vérifier pour un chercheur (email) donné et le nom d'un laboratoire, si ce chercheur travaille dans ce laboratoire.
     * Renvoie vrai si oui, faux s'il n'y travaille pas
     * @param email
     * @param nomLabo
     * @return
     * @throws SQLException
     */
    public boolean travailleDansLabo(String email, String nomLabo) throws SQLException {
        // On prépare la connection
        PreparedStatement preparedStatement = this.connection.prepareStatement(this.requeteChercheurLabo);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, nomLabo);


        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("count_travailler");

        return count == 1;
    }

    /**
     * Permet de vérifier si la note maximale attribuée à un article a été attribuée par un chercheur appartenant au même
     * laboratoire qu'au moins un des auteurs de ce même article
     * @param nomArticle
     * @return
     * @throws SQLException
     */
    public String verifierAttributionNoteMaximale(String nomArticle) throws SQLException {
        // On doit préparer toutes nos requêtes et les exécuter pour avoir les résultats
        PreparedStatement preparedStatement = this.connection.prepareStatement(this.requeteEmailNoteMax);
        preparedStatement.setString(1, nomArticle);
        preparedStatement.setString(2, nomArticle);
        ResultSet listeEmails = preparedStatement.executeQuery();

        // Pour avoir la liste des laboratoires
        preparedStatement = this.connection.prepareStatement(this.requeteLaboArticle);
        preparedStatement.setString(1, nomArticle);
        ResultSet listeLabos = preparedStatement.executeQuery();

        // On prépare le string à retourner
        String res = "";


        List<String> emails = new ArrayList<>();
        if(listeEmails.next()){
            emails.add(listeEmails.getString("EMAIL"));
            int noteMax = listeEmails.getInt("Note_Max");
            while (listeEmails.next()) {
                emails.add(listeEmails.getString("EMAIL"));
            }



            // Créez une liste pour stocker les noms de laboratoires du deuxième ResultSet.
            List<String> labos = new ArrayList<>();
            while (listeLabos.next()) {
                labos.add(listeLabos.getString("NOMLABO"));
            }


            // Maintenant, vous pouvez parcourir les listes et vérifier s'il y a correspondance.
            boolean aucuneCorrespondance = true;
            int i = 0; // indice liste mails
            int j = 0; // indice liste labos
            String email = "";
            String labo = "";
            while(aucuneCorrespondance && i < emails.size()){
                // On récupère le mail
                email = emails.get(i);
                j = 0;

                while(aucuneCorrespondance && j < labos.size()){
                    labo = labos.get(j);
                    if(travailleDansLabo(email, labo))
                        aucuneCorrespondance = false;
                    j++;
                }
                i++;
            }
            // Déjà vérifier s'il y a des notes

            // Si aucun des chercheurs ne travaillent dans aucun des labos, alors cela veut dire que la note maximale est
            // "legit"
            res = "La note maximale de l'article " + nomArticle
                    + "(" + noteMax + "/5)" +
                    " a été donné par : " + email +
                    "qui travaille bel et bien dans le laboratoire " + labo + "\n";
            if(aucuneCorrespondance){
                res = "La note maximale de l'article " + nomArticle + "( " + noteMax + "/5)" + " a été donné par quelqu'un qui ne travaille dans aucun de ces laboratoires (laboratoires dans lesquels travaillent l(es) auteur(s) de l'article): \n\n";
                for(String labora : labos){
                    res += "\t" + labora + "\n";
                    res += "\t-------\n";
                }
            }
        }else{
            res = "Aucune note n'a été attribuée à l'article : " + nomArticle + "...\n";
        }

        return res;
    }
}
