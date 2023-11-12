package SQL;

import java.sql.*;

public class Triggers extends Base{
    /**
     * Requete permettant de créer la table de logs associée au trigger
     */
    private String creerTableLogs;

    /**
     * Requete créant le trigger permettant de vérifier si un chercheur essaye de noter son propre article
     */
    private String verifierNoteChercheur;

    /**
     * Requete detruisant le trigger qui permet de vérifier si un chercheur essaye de noter son propre article
     */
    private String detruireVerifNote;


    /**
     * Requete créant le trigger permettant de stocker dans une table de logs les opérations effectuées dans la table annotation
     */
    private String loggerDAnnotations;

    /**
     * Requete detruisant le trigger qui stocke dans une table de logs les opérations effectuées dans la table annotation
     */
    private String detruireLogs;
    /**
     * Constructeur qui prend en paramètres une connection
     *
     * @param c
     */
    public Triggers(Connection c) {
        super(c);

        this.verifierNoteChercheur = "CREATE OR REPLACE TRIGGER verifierNoteChercheur\n" +
                "BEFORE INSERT ON Noter\n" +
                "FOR EACH ROW\n" +
                "declare\n" +
                "    v_coAuteur NUMBER;\n" +
                "begin\n" +
                "    -- On vérifie le nombre de co-auteurs pour l'article\n" +
                "    select COUNT(email) INTO v_coAuteur\n" +
                "    from Ecrire\n" +
                "    where titre = :NEW.titre\n" +
                "      and email = :NEW.email;\n" +
                "\n" +
                "    -- Si le count vaut + de 0, ça veut dire qu'il est co auteur\n" +
                "    if v_coAuteur > 0 then\n" +
                "        RAISE_APPLICATION_ERROR(-20023, 'Insertion imossible : Un chercheur auteur/co-auteur d''un article ne peut pas le noter.');\n" +
                "    end if;\n" +
                "END";

        this.loggerDAnnotations = "CREATE OR REPLACE TRIGGER loggerDAnnotation\n" +
                "AFTER INSERT OR UPDATE ON annoter\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "    -- On enregistre l'action dans la table log_chercheurs\n" +
                "    IF INSERTING THEN\n" +
                "        INSERT INTO log_chercheurs (utilisateur, date_action, type_action)\n" +
                "        VALUES (USER, SYSDATE, 'Insertion dans la table annoter');\n" +
                "    ELSIF UPDATING THEN\n" +
                "        INSERT INTO log_chercheurs (utilisateur, date_action, type_action)\n" +
                "        VALUES (USER, SYSDATE, 'Mise à jour dans la table annoter');\n" +
                "    END IF;\n" +
                "END";
        this.detruireVerifNote = "DROP TRIGGER verifierNoteChercheur";

        this.detruireLogs = "DROP TRIGGER loggerDAnnotations";

        this.creerTableLogs = "CREATE TABLE log_chercheurs (\n" +
                "    utilisateur VARCHAR2(500) NOT NULL,\n" +
                "    date_action DATE NOT NULL,\n" +
                "    type_action VARCHAR2(500) NOT NULL\n" +
                ")";


    }

    public String triggerCreer(boolean creer, String nomTrigger) throws SQLException {
        String res = "";
        // On vérifie d'abord si le trigger existe déjà
//        String requete = "SELECT * FROM ALL_TRIGGERS WHERE TRIGGER_NAME = ?";
//        PreparedStatement preparedStatement = this.connection.prepareStatement(requete);
//        preparedStatement.setString(1, nomTrigger);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        boolean existe = false;
//        if(resultSet.next()) {
//            // Alors le trigger existe déjà
//            existe = true;
//        }

        Statement statement = this.connection.createStatement();
        // Si creer vaut vrai, alors on crée le trigger, sinon on le drop
        if(creer){
            // On vérifie d'abord si la table de logs existe, sinon on la crée
            ResultSet resTable = statement.executeQuery("SELECT table_name FROM user_tables WHERE table_name = 'LOG_CHERCHEURS'");
            if(!resTable.next())
                // Si la table n'existe pas, alors on la crée
                statement.executeQuery(this.creerTableLogs);
            // Puis s'il existe déjà (le trigger), on renvoie qu'il existe déjà
//            if(existe)
//                res = "Le trigger " + nomTrigger + " existe déjà...";
//            else{ // Sinon on le crée
                if(nomTrigger.equals("verifierNoteChercheur")){
                    statement.executeUpdate(this.verifierNoteChercheur);
                }else if(nomTrigger.equals("loggerDAnnotations")){
                    statement.executeUpdate(this.loggerDAnnotations);
                }
                res = "Le trigger " + nomTrigger + " a bien été créé !";
//            }
        }else{
            // S'il existe pas, et qu'on souhaite le supprimer, alors on le renvoie
//            if(!existe){
//                res = "Le trigger " + nomTrigger + " n'existe pas...";
//            }else{
                if(nomTrigger.equals("verifierNoteChercheur")){
                    statement.executeUpdate(this.detruireVerifNote);
                }else if(nomTrigger.equals("loggerDAnnotations")){
                    statement.executeUpdate(this.detruireLogs);
                    statement.executeUpdate("DROP TABLE LOG_CHERCHEURS");
                }
                res = "Le trigger " + nomTrigger + " a bien été supprimé";
//            }
        }
        return res;
    }
}
