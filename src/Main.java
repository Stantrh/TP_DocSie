import SQL.CalculMoyenne;
import SQL.ChercheurAnnoterArticles;
import SQL.LabosChercheurs;
import SQL.ListeArticles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {

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
                                            "6. Affichage pour chaque chercheur (nb articles publiés, nb et avg notes obtenues) par ordre décroissant (selon nb articles publiés)\n" +
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
                                case 2:
                                    // Traitement pour l'option 2
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
                                                    System.out.println("KKKKKOption invalide pour la recherche. Veuillez réessayer.");
                                            }

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