package SQL;

import java.sql.*;

/**
 * Cette classe permet d'obtenir la liste des laboratoires dans lequel un chercheur travaille pour chaque chercheur
 */
public class LabosChercheurs extends Base {

    /**
     * requête qui sera utilisée pour obtenir la liste des chercheurs de la base de données
     */
    private String listeChercheurs;

    /**
     * requête qui sera utilisée pour avoir la liste des laboratoires d'un chercheur
     */
    private String laboratoires;

    /**
     * Construit un objet LabosChercheurs à partir d'une connection et construit deux requêtes qui devront être préparées
     * @param c
     */
    public LabosChercheurs(Connection c){
        super(c);
        this.listeChercheurs = "SELECT nomchercheur, prenomchercheur, email FROM Chercheur ORDER BY nomchercheur ASC";
        this.laboratoires = "select LABORATOIRE.NOMLABO, LABORATOIRE.SIGLELABO, LABORATOIRE.ADRESSELABO from LABORATOIRE " +
                "inner join TRAVAILLER on LABORATOIRE.NOMLABO = TRAVAILLER.NOMLABO " +
                "where TRAVAILLER.EMAIL = ?";
    }

    /**
     *
     * @return
     */
    public String rechercherLabosParChercheurs(){
        // La première requête à effectuer est pour avoir la liste des auteurs
        // Et pour chaque auteur on va effectuer une requête pour avoir les labos dans lesquels il travaille
        String res = "Voici pour chaque chercheur la liste des laboratoires dans lesquels il travaille : \n\n";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet chercheurs = statement.executeQuery(this.listeChercheurs);

            while (chercheurs.next()) {
                // Pour chaque chercheur
                res += "Nom : " + chercheurs.getString("nomchercheur") + "\n";
                res += "Prénom : " + chercheurs.getString("prenomchercheur") + "\n";
                res += "Email : " + chercheurs.getString("email") + "\n";

                // On s'occupe de la requête permettant d'avoir la liste des labos
                PreparedStatement preparedStatement = this.connection.prepareStatement(this.laboratoires);
                preparedStatement.setString(1, chercheurs.getString("email"));
                ResultSet listeLabos = preparedStatement.executeQuery();

                // Pour chaque laboratoire du chercheur
                while (listeLabos.next()) {
                    res += "\t------------------------\n";
                    res += "\tLaboratoire : " + listeLabos.getString("nomlabo") + "\n";
                    res += "\tSigle : " + listeLabos.getString("siglelabo") + "\n";
                    res += "\tAdresse : " + listeLabos.getString("adresselabo") + "\n";
                    res += "\t------------------------\n";
                }

                res += "\n================================\n\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
