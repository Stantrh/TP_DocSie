package SQL;

import java.sql.Connection;

/**
 * Classe mère de toutes les autres classes pour factoriser la connection qu'elle prend en attribut
 */
public class Base {
    Connection connection;

    /**
     * Constructeur qui prend en paramètres une connection
     * @param c
     */
    public Base(Connection c){
        this.connection = c;
    }
}
