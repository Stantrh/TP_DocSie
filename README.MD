# TP Final DocSie[/]


Concernant l'architecture de mon projet entièrement en JAVA, j'ai décidé de : 
- faire un menu interactif permettant de naviguer entre chaque fonctionnalité 
- faire de chaque fonctionnalité une classe Java pour faciliter la compréhension du code et surtout le main de la classe principale (qui est déjà très volumineux)


Je l'ai réalisé sous IntelliJ Idea ULTIMATE
L'entièreté du projet est disponible, mais pour le lancer je recommande d'ouvrir un invité de commande dans src/
Puis d'exécuter cet enchaînement de commandes :
`javac main.java && java -cp ../ojdbc11.jar;. Main`


Car si on lance le main depuis le projet intelliJ, le rafraichissement de la console ne fonctionne pas, et c'est moins pratique de savoir à quelle étape on en est.

Au lancement du programme on obtient : 
[image menuConnexion]

On renseigne ses identifiants :
[imageIdentifiants]

On arrive sur le menu proposant les différentes fonctionnalités : 
[image menuFonctionnalites]





Packages utilisés :
J'ai utilisé la classe [Scanner](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html) du package [java.util](https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html) pour pouvoir rendre le menu interactif et permettre à l'utilisateur de renseigner des champs (comme les emails des chercheurs, l'option associée à la fonctionnalité choisie)  
