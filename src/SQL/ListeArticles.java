package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cette classe permet l'affichage de la liste des articles écrits par un auteur donné
 */
public class ListeArticles extends Base{

    private String requete;

    public ListeArticles(Connection c){
        super(c);

        // Cette requête sera variable uniquement sur sa condition where, qu'on rajoutera selon
        this.requete = "select ARTICLE.TITRE, TYPEARTICLE from article " +
                "inner join ecrire on article.TITRE = ecrire.TITRE " +
                "inner join chercheur on ecrire.EMAIL = chercheur.email ";
    }

    /**
     * Méthode qui reçoit un choix désignant si la variable auteur contient l'email ou le nom de l'auteur puis retourne
     * les articles qu'il a écrits
     * @param choix 1 ou 2 (nom ou email)
     * @param auteur (nom ou email)
     * @return liste des articles écrits par l'auteur passé en paramètres
     */
    public String rechercherArticlesParAuteur(int choix, String auteur){
        // On recherche ici par nom
        if(choix == 1){
            this.requete += "where CHERCHEUR.nomchercheur = ?";
        }else if(choix == 2){ // Ici par email
            this.requete += "where CHERCHEUR.email = ?";
        }
        String res = "L'auteur mentionné n'a écrit aucun livre";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(this.requete);
            preparedStatement.setString(1, auteur);

            ResultSet resultSet = preparedStatement.executeQuery();

            // On vérifie s'il a écrit au moins un article
            if (resultSet.next()) {
                // S'il y a au moins un résultat alors il a écrit au moins 1 article
                res = "L'auteur a écrit les articles suivants :\n";
                String titreArticle = resultSet.getString("titre");
                String typearticle = resultSet.getString("typearticle");
                res += "Titre : \"" + titreArticle + "\" Type : " + typearticle +"\n";

                while (resultSet.next()) {
                    titreArticle = resultSet.getString("titre");
                    typearticle = resultSet.getString("typearticle");
                    res += "Titre : " + titreArticle + " Type : " + typearticle +"\n";
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
