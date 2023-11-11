import SQL.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {

    /**
     * Permet d'effacer le terminal (équivalent d'un CTRL + L)
     */
    public static void effacerConsole(){
        // Effacer la console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean connecte = false;

        while (!connecte) {
            Main.effacerConsole();
            System.out.println("Menu de connexion :");
            System.out.print("1. Se connecter\n2. Quitter\nChoisissez une option : ");

            if (scanner.hasNextInt()) {
                int choixConnexion = scanner.nextInt();
                scanner.nextLine(); // Consomme la fin de la ligne

                // Effacer la console
                Main.effacerConsole();

                if (choixConnexion == 2) {
                    System.out.print("Merci d'avoir utilisé DocSie... A bientôt ! Fermeture dans 3 secondes ...");
                    try {
                        Thread.sleep(1000);
                        System.out.print(" 2 secondes ...");
                        Thread.sleep(1000);
                        System.out.print(" 1 seconde ...");
                        Thread.sleep(1000);
                        Main.effacerConsole();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                if (choixConnexion == 1) {
                    System.out.print("Nom d'utilisateur Oracle : ");
                    String utilisateur = scanner.next();
                    System.out.print("Mot de passe Oracle : ");
                    String motDePasse = scanner.next();
                    scanner.nextLine(); // Pour consommer la fin de ligne
                    System.out.print("URL de connexion (laisser vide par défaut : jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb) : ");
                    String url = scanner.nextLine();
                    if (url.isEmpty()) {
                        url = "jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb";
                    }


                    try {
                        Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
                        System.out.println("\nConnexion réussie à la base de données Oracle!");

                        Thread.sleep(1000);

                        connecte = true;
                        while (connecte) {
                            Main.effacerConsole();
                            System.out.println("Menu :");
                            System.out.print(
                                    "1. Liste des articles écrits par un auteur \n" +
                                            "2. Co-auteurs ayant travaillé avec un chercheur donné\n" +
                                            "3. Affichage des laboratoires de chaque chercheur\n" +
                                            "4. Chercheurs ayant annoté au moins «x» articles\n" +
                                            "5. Moyenne des notes données pour un chercheur\n" +
                                            "6. Affichage pour chaque chercheur d'un laboratoire donné (nb articles publiés, nb et avg notes obtenues) par ordre décroissant (selon nb articles publiés)\n" +
                                            "7. Vérification que la note maximale d'un article donné n'est pas attribué par un chercheur du même labo\n" +
                                            "8. Création et destruction de triggers (deux scripts, faire cette option pour plus d'informations)\n" +
                                            "404. Retour au menu de connexion\n" +
                                            "Choisissez une option : ");
                            int choixMenu = scanner.nextInt();
                            switch (choixMenu) {
                                case 1:
                                    boolean question1 = true;
                                    while (question1) {
                                        Main.effacerConsole();
                                        System.out.println("Rechercher les articles écrits par un auteur");
                                        System.out.println("Voulez-vous rechercher les articles écrits par un auteur selon : ");
                                        System.out.println("1. Son nom\n2. Son email\n3. Retourner au menu principal");
                                        int choixRecherche = scanner.nextInt();
                                        scanner.nextLine(); // pour "consommer" la fin de ligne

                                        if (choixRecherche == 3) {
                                            question1 = false; // Quitter et retourner au menu principal
                                        } else if (choixRecherche == 1 || choixRecherche == 2) {
                                            // On prépare notre objet ListeArticles
                                            ListeArticles l = new ListeArticles(connection);
                                            // Correspondra soit à l'email soit au nom de l'auteur
                                            String auteur = "";

                                            switch (choixRecherche) {
                                                case 1:
                                                    // On demande à l'utilisateur le nom du chercheur
                                                    System.out.print("Entrez le nom du chercheur : ");
                                                    auteur = scanner.nextLine();
                                                    break;
                                                case 2:
                                                    // Et ici son email (au chercheur)
                                                    System.out.print("Entrez l'email du chercheur : ");
                                                    auteur = scanner.nextLine();
                                                    break;
                                                default:
                                                    System.out.println("Option invalide pour la recherche. Veuillez réessayer.");
                                            }
                                            Main.effacerConsole();

                                            String res = l.rechercherArticlesParAuteur(choixRecherche, auteur);
                                            System.out.println(res);

                                            // Demander à l'utilisateur s'il souhaite effectuer une nouvelle recherche
                                            System.out.println("Voulez-vous rechercher un autre auteur ? (1. Oui / 2. Non)");
                                            int choixNouvelleRecherche = scanner.nextInt();
                                            if (choixNouvelleRecherche == 2) {
                                                question1 = false; // Quitter et retourner au menu principal
                                            }
                                        } else {
                                            System.out.println("Option invalide. Veuillez réessayer.");
                                        }
                                    }
                                    break;
                                case 2:
                                    boolean question2 = true;
                                    while(question2){
                                        Main.effacerConsole();
                                        System.out.println("Rechercher les co-auteurs d'un chercheur : ");
                                        System.out.println("Voulez-vous rechercher (pour le chercheur) par rapport à : ");
                                        System.out.println("1. Son nom\n2. Son email\n3. Retourner au menu principal");
                                        int choixRecherche = scanner.nextInt();
                                        scanner.nextLine(); // pour "consommer" la fin de ligne

                                        if (choixRecherche == 3) {
                                            question2 = false; // Quitter et retourner au menu principal
                                        } else if (choixRecherche == 1 || choixRecherche == 2) {
                                            // On prépare notre objet CoAuteur
                                            CoAuteur c = new CoAuteur(connection);
                                            // Correspondra soit à l'email soit au nom de l'auteur
                                            String chercheur = "";
                                            boolean nom = false;
                                            switch (choixRecherche) {
                                                case 1:
                                                    // On demande à l'utilisateur le nom du chercheur
                                                    System.out.print("Entrez le nom du chercheur : ");
                                                    chercheur = scanner.nextLine();
                                                    nom = true;
                                                    break;
                                                case 2:
                                                    // Et ici son email (au chercheur)
                                                    System.out.print("Entrez l'email du chercheur : ");
                                                    chercheur = scanner.nextLine();
                                                    break;
                                                default:
                                                    System.out.println("Option invalide pour la recherche. Veuillez réessayer.");
                                            }
                                            Main.effacerConsole();

                                            String res = c.rechercherCoAuteurs(chercheur, nom);
                                            System.out.println(res);

                                            // Demander à l'utilisateur s'il souhaite effectuer une nouvelle recherche
                                            System.out.println("Voulez-vous réitérer pour un autre chercheur ? (1. Oui / 2. Non)");
                                            int choixNouvelleQ2 = scanner.nextInt();
                                            if (choixNouvelleQ2 == 2) {
                                                question2 = false; // Quitter et retourner au menu principal
                                            }
                                        }else {
                                            System.out.println("Option invalide. Veuillez réessayer.");
                                        }
                                    }
                                    break;
                                case 3:
                                    boolean question3 = true;
                                    while (question3) {
                                        Main.effacerConsole();
                                        LabosChercheurs l = new LabosChercheurs(connection);
                                        String res = l.rechercherLabosParChercheurs();
                                        System.out.println(res);

                                        System.out.println("Pour revenir au menu faites Entrée");
                                        // On consumme la ligne actuelle
                                        scanner.nextLine();
                                        String entree = scanner.nextLine();
                                        if (entree.isEmpty()) {
                                            question3 = false; // Quitter et retourner au menu principal
                                        }
                                    }
                                    break;
                                case 4:
                                    boolean question4 = true;
                                    while (question4) {
                                        Main.effacerConsole();
                                        System.out.println("Chercheurs ayant annoté au moins «x» articles");
                                        System.out.print("Quelle valeur souhaitez-vous donner à x ? ");
                                        int x = scanner.nextInt();

                                        ChercheurAnnoterArticles c = new ChercheurAnnoterArticles(connection);
                                        String res = c.rechercherChercheurs(x);

                                        Main.effacerConsole();

                                        System.out.println(res);


                                        // Demander à l'utilisateur s'il souhaite effectuer une nouvelle recherche
                                        System.out.println("Voulez-vous réitérer pour un autre nombre d'articles ? (1. Oui / 2. Non)");
                                        int choixNouvelleRecherche = scanner.nextInt();
                                        if (choixNouvelleRecherche == 2) {
                                            question4 = false; // Quitter et retourner au menu principal
                                        }
                                    }
                                    break;
                                case 5:
                                    boolean question5 = true;
                                    while (question5) {
                                        Main.effacerConsole();
                                        System.out.println("Moyenne des notes données pour un chercheur");
                                        System.out.println("1. Moyenne uniquement\n2. Version détaillée (notes)\n3. Retourner au menu principal");
                                        int choixRecherche = scanner.nextInt();
                                        scanner.nextLine(); // pour "consommer" la fin de ligne

                                        if (choixRecherche == 3) {
                                            question5 = false;
                                            break;// Quitter et retourner au menu principal
                                        } else if (choixRecherche == 1 || choixRecherche == 2) {
                                            boolean detaille = choixRecherche == 2; // Simplifié par IntelliJ
                                            Main.effacerConsole();
                                            // On demande d'abord de quelle façon rechercher le chercheur
                                            System.out.println("Souhaitez-vous rechercher le chercheur selon : ");
                                            System.out.println("1. Son nom\n2. Son email\n3. Retourner au menu");
                                            int choixChercheur = scanner.nextInt();
                                            scanner.nextLine(); // pour "consommer" la fin de ligne

                                            if (choixChercheur == 3) {
                                                question5 = false;
                                            }else if(choixChercheur == 1 || choixChercheur == 2){

                                            String chercheur = "";
                                            boolean nom = false;
                                            switch (choixChercheur) {
                                                case 1:
                                                    // On demande à l'utilisateur le nom du chercheur
                                                    System.out.print("Entrez le nom du chercheur : ");
                                                    chercheur = scanner.nextLine();
                                                    nom = true;
                                                    break;
                                                case 2:
                                                    // Et ici son email (au chercheur)
                                                    System.out.print("Entrez l'email du chercheur : ");
                                                    chercheur = scanner.nextLine();
                                                    break;
                                                default:
                                                    System.out.println("Option invalide pour la recherche. Veuillez réessayer.");
                                            }
                                            Main.effacerConsole();

                                            // On peut maintenant afficher le résultat du calcul de moyenne
                                            CalculMoyenne c = new CalculMoyenne(connection);
                                            String res = c.calculerMoyenne(detaille, chercheur, nom);
                                            System.out.println(res);

                                            // Demander à l'utilisateur s'il souhaite effectuer une nouvelle recherche
                                            System.out.println("Voulez-vous réitérer pour un autre chercheur ? (1. Oui / 2. Non)");
                                            int choixNouvelleRecherche = scanner.nextInt();
                                            if (choixNouvelleRecherche == 2) {
                                                question5 = false; // Quitter et retourner au menu principal
                                            }
                                            }

                                        } else {
                                            System.out.println("Option invalide. Veuillez réessayer.");
                                        }
                                    }
                                    break;
                                case 6:
                                    boolean question6 = true;
                                    // Consommer le caractère de fin de ligne laissé par la saisie précédente
                                    scanner.nextLine();
                                    while (question6) {
                                        Main.effacerConsole();

                                        System.out.println("Affichage pour chaque chercheur d'un laboratoire donné de :\n" +
                                                "- nombre d'articles publiés\n" +
                                                "- nombre de notes obtenues\n" +
                                                "- moyenne des notes obtenues\n" +
                                                "par ordre décroissant du nombre d'articles publiés");

                                        System.out.print("Entrez le nom du laboratoire : ");
                                        String nomLabo = scanner.nextLine(); // Lire le nom du laboratoire

                                        Main.effacerConsole();

                                        AffChercheurLabDonne a = new AffChercheurLabDonne(connection);
                                        String res = a.afficherChercheursTries(nomLabo);
                                        System.out.println(res);

                                        // Demander à l'utilisateur s'il souhaite effectuer une nouvelle recherche
                                        System.out.println("Voulez-vous réitérer pour un autre laboratoire ? (1. Oui / 2. Non)");
                                        int choix = scanner.nextInt();
                                        scanner.nextLine(); // Consommer la nouvelle ligne restante
                                        if (choix == 2) {
                                            question6 = false; // Quitter et retourner au menu principal
                                        }
                                    }
                                    break;
                                case 7:
                                    boolean question7 = true;
                                    // Consommer le caractère de fin de ligne laissé par la saisie précédente
                                    scanner.nextLine();
                                    while(question7){
                                        Main.effacerConsole();

                                        System.out.println("Vérification que la note maximale attribuée à un article n'a pas été attribuée par un chercheur appartenant au même laboratoire que l'un des auteurs de cet article");
                                        System.out.print("Entrez le nom de l'article : ");
                                        String nomArticle = scanner.nextLine(); // Lire le nom de l'article

                                        Main.effacerConsole();

                                        VerifierNoteMax v = new VerifierNoteMax(connection);
                                        String res = v.verifierAttributionNoteMaximale(nomArticle);
                                        System.out.println(res);

                                        // Demander à l'utilisateur s'il souhaite effectuer une nouvelle recherche
                                        System.out.println("Voulez-vous réitérer pour un autre article ? (1. Oui / 2. Non)");
                                        int choi = scanner.nextInt();
                                        scanner.nextLine(); // Consommer la nouvelle ligne restante
                                        if (choi == 2) {
                                            question7 = false; // Quitter et retourner au menu principal
                                        }
                                    }
                                    break;
                                case 404:
                                    connecte = false;
                                    break;
                                default:
                                    System.out.println("Option invalide. Veuillez réessayer.");
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Échec de la connexion à la base de données Oracle. Veuillez réessayer.");
                        e.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }else{
                // L'entrée n'est pas un nombre
                System.out.println("Option invalide. Veuillez entrer un nombre.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                scanner.nextLine(); // Consomme l'entrée incorrecte
            }

        }
        scanner.close();
    }
}