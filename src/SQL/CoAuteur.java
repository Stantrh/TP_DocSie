package SQL;

import java.sql.Connection;

/**
 * Cette classe permet l'affichage de la liste des co-auteurs ayant travaillé avec un chercheur donné
 */
public class CoAuteur extends Base{
    public CoAuteur(Connection c){
        super(c);
    }
}
