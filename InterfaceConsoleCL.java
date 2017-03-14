public class InterfaceConsoleCL {

	static Sauvegarde sauv = new Sauvegarde("Donnees/ColorLines/save.f", "Donnees/ColorLines/Games/");

	public static void menuCL(){
		// Laisse l'utilisateur jouer ou revenir au menuPrincipal ou aller aux parametres
		boolean continuer = true;
		int a;
		String [] tab = {"0 - Quitter", "1 - Jouer de manière classique", "2 - Jouer à une variante","3 - Voir les records","4 - Voir les parties"};

		while (continuer) {
			a = menu("Menu Color Lines : ",tab,true);
			if (a == 0){
				continuer = false;
			}
			else if (a == 1)
				jouer(false);
			else if (a == 2)
				jouer(true);
			else if (a == 3)
				menuRecords();
			else if (a == 4)
				regarder();
			else{
				System.out.println("Merci de bien vouloir choisir un choix possible");
				System.out.println();
			}

		}
	}

	public static void regarder (){
		// Interface console pour choisir une partie .
		boolean continuer = true;
		int a ;
		String [] tab = new String[sauv.nom.size()+1];
		tab[0] = "0 - Revenir au menu précédent.";
		for (int i = 0; i < sauv.nom.size(); i++){
			tab[i+1] = (i+1)+" - "+sauv.nom.get(i);
		}

		while (continuer) {
			a = menu("Menu regarder : ",tab,true);
			if (a == 0)
				continuer = false;
			else if (a > 0 && a < tab.length)
				regarder (a-1);
			else
				System.out.println("Merci de bien vouloir choisir un choix possible.");
		}
	}

	public static void regarder (int nb){
		// interface console pour regarder une partie.
		PartieColorLines partie = new PartieColorLines(sauv,nb);
		boolean continuer = true;
		int i = 0;
		int a = 0;
		PlateauColorLines p;
		JoueurColorLines joueurActuel;
		String [] tab = {"0 - Retour au menu précédent","1 - Revenir au début de la partie", "2 - Revenir au coup précédent","3 - Aller au coup suivant","4 - Aller à la fin de la partie"};
		while (continuer) {
			
			p = partie.getPlateau(i);
			joueurActuel = p.j;

			affichePlateau(p);
			System.out.println();
			if (i > 0)
				System.out.println("Tour n°"+i+" : ");


			if (i > 0 && i < partie.listeCoup.size())
				System.out.println("Le dernier coup qui a été joué est le déplacement de ["+partie.getCoup(i-1)[0]+";"+partie.getCoup(i-1)[1]+"] en ["+
					partie.getCoup(i-1)[2]+";"+partie.getCoup(i-1)[3]+"]");
			
			System.out.println();
			System.out.println(partie.j1.pseudo+" : "+joueurActuel.points+" points");
			System.out.println();


			a = menu("Que souhaitez vous faire ?",tab, false);
			if (a == 0)
				continuer = false;
			else if (a == 1)
				i=0;
			else if (a == 2 && i > 0)
				i--;
			else if (a == 2 && i == 0){
				System.out.println("Erreur : On est déja au début de la partie, il n'y a donc pas de coup précédent.");
				System.out.println();
			}
			else if (a == 3 && i < partie.liste.size()-1)
				i++;
			else if (a == 3 && i == partie.liste.size()-1){
				System.out.println("Erreur : On est déja à la fin de la partie, il n'y a donc pas de coup suivant.");
				System.out.println();
			}
			else if (a == 4)
				i = partie.liste.size()-1;
			else {
				System.out.println("Merci de bien vouloir choisir un choix possible.");
			}
		}
	}

	public static void menuRecords() {
		// Affiche le détenteur du record pour chacune des 4 variantes du jeu
		RecordColorLines r = new RecordColorLines();
		int id;

		System.out.println("--------------------------");
		System.out.println("Records color lines :");
		id = r.getRecord(false, false); // A faire à chque fois qu'on modifie le record.
		if (id == -1 ){
			System.out.println("Classique : Aucun record à battre pour le moment.");
		}else {
			System.out.println("Classique : "+r.getPseudo(id)+" avec "+ r.getPoints(id)+" points.");
		}
		System.out.println("-------------");
		id = r.getRecord(true, false); // A faire à chque fois qu'on modifie le record.
		if (id == -1 ){
			System.out.println("Damier : Aucun record à battre pour le moment.");
		}else {
			System.out.println("Damier : "+r.getPseudo(id)+" avec "+ r.getPoints(id)+" points.");
		}
		System.out.println("-------------");
		id = r.getRecord(false, true); // A faire à chque fois qu'on modifie le record.
		if (id == -1 ){
			System.out.println("Bonus : Aucun record à battre pour le moment.");
		}else {
			System.out.println("Bonus : "+r.getPseudo(id)+" avec "+ r.getPoints(id)+" points.");
		}
		System.out.println("-------------");
		id = r.getRecord(true, true); // A faire à chque fois qu'on modifie le record.
		if (id == -1 ){
			System.out.println("Damier et bonus : Aucun record à battre pour le moment.");
		}else {
			System.out.println("Damier et bonus : "+r.getPseudo(id)+" avec "+ r.getPoints(id)+" points.");
		}

		System.out.println();
		System.out.println("Appuyez sur Entrée pour revenir au menu précédent.");
		Outils.getString();
		System.out.println("--------------------------");
		System.out.println();

	}

	public static void parametres(){
		// Ecris les parametres de la partie (nombre de cases) dans un fichier
	}

	public static PlateauColorLines creePlateau (boolean variante){
		// Fonction qui crée un plateau 
		boolean continuer;

		// On demande si l'on souhaite jouer sur un echiquier à deux couleuurs ou si l'on souhaite jouer avec les bonus.
		boolean damier = false, bonus = false;
		if (variante){
			damier = getdamier();
			System.out.println();
			bonus = getBonus();
			System.out.println();
		}

		// On demande la taille du plateau
		int taille = getTaille();
		System.out.println();

		

		System.out.println("Merci de bien vouloir donner les informations concernant le joueur : ");
		JoueurColorLines j1 = getJoueur();
		System.out.println();
		


		PlateauColorLines plateau = new PlateauColorLines(taille, taille, damier, j1, bonus);

		return plateau;
	}

	public static void jouer (boolean variante){
		// Lis le fichier parametre et le crée si il n'existe pas ou qu'il est illisible.
		// Créer une partie à partir de ces parametres
		// Demander les joueurs avec la fonction getJoueur()

		PlateauColorLines plateau = creePlateau(variante);
		JoueurColorLines j = plateau.j;
		int points = 0;
		int i = 0;
		boolean continuer = true;

		String [] tableau = {"0 - Ne pas utiliser de bonus","1 - Utiliser un bonus couleur pour 25 points","2 - Utiliser un bonus position pour 25 points."};
		System.out.println("--------------------------"); 
		System.out.println("Nous allons jouer à Color Lines");
		System.out.println("Le jeu continue jusqu'à ce que le plateau soit plein.");
		boolean continuer2, continuer3 ;
		String chaine;
		String [] tableau2 = new String [PionCL.tab.length - 1];
		for (int i2 = 1; i2 < PionCL.tab.length; i2++){
			tableau2[i2-1] = i2 + " - "+ PionCL.tab[i2];
		}

		int nb,nb2 = 0;
		int []abs ;
		int [] ord;
		System.out.println();
		affichePlateau(plateau);
		System.out.println();
		RecordColorLines r = new RecordColorLines();
		int id = r.getRecord(plateau.damier, plateau.bonus); // A faire à chque fois qu'on modifie le record.
		if (id == -1 ){
			System.out.println("Aucun record à battre pour le moment.");
		}else {
			System.out.println("Le record a battre est de "+r.getPoints(id)+" points.\nIl est détenu par "+r.getPseudo(id));
		}

		PartieColorLines partie = new PartieColorLines(j, plateau.abs,plateau.damier,plateau.bonus);
		
      
		while (continuer){
			//System.out.println("C'est à "+j.pseudo+" de jouer : ");
			System.out.println("Tour n°"+(i+1)+" :");
			
			if (! plateau.bonus)
				plateau.remplirAuto(partie);
			else  {
				// Si les bonus sont activés.
				continuer2 = true;
				while (continuer2){
					System.out.println();
					affichePlateau(plateau);
					System.out.println();
					nb = menu("Quel bonus souhaitez vous utiliser ?",tableau, false);
					if (nb == 0){ // pas de bonus choisi
						plateau.remplirAuto(partie);
						continuer2 = false;
					}
					else if (nb == 1){ // Utilisation du bonus couleur
						System.out.println();
						continuer3 = true;
						while (continuer3) {
							nb2 = menu("Quelle couleur souhaitez vous ? ",tableau2, false);
							if (nb > tableau2.length || nb < 1){
								System.out.println();
								System.out.println("Merci de bien vouloir entrer une couleur possible.");
								System.out.println("");
							}
							else 
								continuer3 = false;
						}

						plateau.remplirCouleur(nb2,partie,i);
						continuer2 = false;
							
					}
					else if (nb == 2){ // bonus de position utilisé
						abs = new int [3];
						ord = new int [3];
						continuer3 = true;
						while (continuer3){
							System.out.println("Merci de bien vouloir entrer les coordonnées.");
							for (int i2 = 0; i2 < 3  ; i2++){
								System.out.println("Pion n°"+(i2+1));
								abs[i2] = getAbcisse();
								ord[i2] = getOrdonnee();
								System.out.println();
							}
							continuer3 =  ! plateau.remplirPositions(abs,ord,partie,i);
							if (continuer3){
								System.out.println("Veuillez entrer des coordonnées possible s'il vous plait.");
							}
						}
						
						continuer2 = false;

							
					}
					else {
						System.out.println("Merci de bien vouloir faire un choix possible.");
					}
				}
			}

			plateau.verificationAlignement(); // On verifie si l'ajout n'a pas fais des alignements

			points = j.points;
			System.out.println("Le joueur a "+points+" points.");

			affichePlateau(plateau);
			System.out.println();

			i++;
			if(plateau.verificationFin())
				continuer = false;
			else{
				faisCoup(plateau,j,partie);
				plateau.verificationAlignement();
			}
		}
		System.out.println();
		System.out.println("Plateau final : ");
		affichePlateau(plateau);
		System.out.println("--------------------------");
		System.out.println();
		System.out.println();
		System.out.println("--------------------------");
		System.out.println("La partie s'est finie au bout de "+i+" tours.");
		System.out.println("Vous avez fini avec "+j.points+" points.");
		if (r.ajouter(j.points ,j.pseudo, plateau.damier, plateau.bonus))
			System.out.println("Vous avez le nouveau record !");
		else
			System.out.println("Dommage ! Le record reste détenu par "+r.getPseudo(id)+" qui a obtenu "+r.getPoints(id)+" points.");
		System.out.println();
		System.out.println("Souhaitez vous sauvegarder la partie ? (O/n)");
		String save = Outils.getString();
		if (! save.equals("n")){


			System.out.println("La partie a été sauvegardée. Son nom est "+partie.sauvegarder(sauv));

		}else {
			System.out.println("Partie non sauvegardée.");
		}
			
		System.out.println("--------------------------");
		System.out.println();

	}

	

	public static void affichePlateau(PlateauColorLines p){
		PionCL pion ;
		boolean refaire = true;
		int a = p.tab[0].length;

		for (int i = -1; i < p.tab.length; i++){
			//System.out.println("i : "+i);
			if (! refaire){
				if ((i+1)/10 == 0)
					System.out.print((i+1)+" |");
				else 
					System.out.print((i+1)+"|");
				a = p.tab[i].length;
			} else 
				System.out.print("   ");
				
			

			for (int i2 = 0 ; i2 < a; i2++){


				if (refaire){
					if ((i2+1)/10 == 0)
						System.out.print((i2+1)+" ");
					else 
						System.out.print((i2+1));
					
				}
				else {
					if (p.tab[i][i2].pion == null){
						if (p.tab[i][i2].blanc)
							System.out.print("\u25FC ");
						else
							System.out.print("\u25FB ");
					}

						
					else{
						pion = (PionCL) p.tab[i][i2].pion;
						if (pion.couleur == 0)
							System.out.print("A ");
						else if (pion.couleur == 1)
							System.out.print("B ");
						else if (pion.couleur == 2)
							System.out.print("R ");
						else if (pion.couleur == 3)
							System.out.print("J ");
						else if (pion.couleur == 4)
							System.out.print("V ");
						else if (pion.couleur == 5)
							System.out.print("O ");
						else if (pion.couleur == 6)
							System.out.print("G ");
					}
				}
					
				

			}

			if (refaire)
				refaire = false;
			System.out.println("");
		}
	}


	// -------------------------------------
	// --------------------------------------
	// Fonctions de soutient
	public static int menu (String phrase, String [] choix, boolean barre){
		// Fonction affichant un menu de manière structurée et renvoyant le choix de l'utilisateur.
		if (barre)
			System.out.println("--------------------------");

		System.out.println(phrase);
		for (int i = 0; i < choix.length; i++){
			System.out.println(choix[i]);
		}
		System.out.print("Votre choix : ");
		int a = Outils.getInt();
		if (barre)
			System.out.println("--------------------------");
		System.out.println();
		return a;
	}

	public static JoueurColorLines getJoueur(){
		// Fonction qui permet de renvoyer un JoueurColorLines en ayant déterminer si le joueur est une IA ou un humain.
		
		String pseudo = getPseudo();
		System.out.println();
		JoueurColorLines j = new JoueurColorLines(pseudo);
		return j;
	}

	public static String getPseudo(){
		// Fonction demandant et renvoyant le pseudo du joueur
		System.out.println("Donnez le pseudo du joueur : ");
		String question = Outils.getString();
		return question;
	}

	public static int getOrdonnee(){
		// Fonction demandant et retournant l'ordonnee.
		int ordonnee = 0;
		boolean continuer = true;
		while (continuer){
			System.out.println("Donnez l'ordonnee (commençant à 1) de la case que vous souhaitez séléctionner : ");
			ordonnee = Outils.getInt();
			if (ordonnee >= 0)
				continuer=false;
			else {
				System.out.println("Merci de donner une ordonnee possible.");
			}
		}
		return ordonnee - 1;
	}

	public static int getAbcisse(){
		// Fonction demandant et retournant l'abcisse.
		int abcisse = 0;
		boolean continuer = true;
		while (continuer){
			System.out.println("Donnez l'abcisse (commençant à 1) de la case que vous souhaitez séléctionner : ");
			abcisse = Outils.getInt();
			if (abcisse >= 0)
				continuer=false;
			else {       
				System.out.println("Merci de donner une abcisse possible.");
			}
		}
		return abcisse - 1;
	}

	public static boolean getdamier(){
		// Fonction qui renvoie un booleen pour savoir si le plateau va être un dammmier ou moonochrome.
		boolean o = true;
		boolean continuer = true;
		while (continuer){
			String [] tab = {"1 - Le plateau sera disposé en damier (des cases blanches et des cases noires).","2 - Le plateau n'aura que des cases blanches."};
			int ordre = menu("Choissiez si le plateau doit être disposé en damier ou pas : ",tab, false);
			
			if (ordre == 1){
				continuer=false;
				o = true;
			}
			else if (ordre == 2){
				continuer = false;
				o = false;
			}
			else {
				System.out.println("Merci de choisir un choix possible.");
			}
		}
		return o;
	}

	public static boolean getBonus(){
		// Fonction qui renvoie un booleen pour savoir si les bonus sont acceptés ou pas.
		boolean o = true;
		boolean continuer = true;
		while (continuer){
			String [] tab = {"1 - Les bonus sont autorisés.","2 - Les bonus ne sont pas autorisés."};
			int ordre = menu("Choissiez si les bonus sont autorisés ou pas : ",tab, false);
			
			if (ordre == 1){
				continuer=false;
				o = true;
			}
			else if (ordre == 2){
				continuer = false;
				o = false;
			}
			else {
				System.out.println("Merci de choisir un choix possible.");
			}
		}
		return o;
	}

	public static void faisCoup(PlateauColorLines p ,JoueurColorLines j, PartieColorLines partie){
		// Fonction qui va demander à l'utilisateur un coup, vérifier qu'il est possible, si ce n'est pas le cas demander à l'utilisateur un autre coup.
		// Si le joueur est automatique, alors on appelle la fonction permettant de réaliser un coup automatique.
		

		boolean continuer = true;
		int abs = -1, ord = -1, abs2 = -1, ord2 = -1;
		while (continuer) {
			System.out.println("Choisissez le pion que vous souhaitez déplacer.");
			abs = getAbcisse();
			ord = getOrdonnee();
			System.out.println();
			System.out.println("Choisissez l'endroit ou vous souhaitez la mettre.");
			abs2 = getAbcisse();
			ord2 = getOrdonnee();
			System.out.println();

			if (abs >= p.tab[0].length || abs< 0 || ord >= p.tab.length || ord< 0 || abs2 >= p.tab[0].length || abs2< 0 || ord2 >= p.tab.length || ord2 < 0 ){
				System.out.println("Mouvement imposssible, merci de bien vouloir donner des coordonnées possibles");
				System.out.println();
			}
			else {
				MouvementColorLines mouv = new MouvementColorLines(p.tab[ord][abs],p.tab[ord2][abs2]);

				if (mouv.faire(p)){
					continuer = false;
				}
				else {
					System.out.println("Ce mouvement n'est pas possible, veuillez réessayer.");
					System.out.println();
				}
				
			}
				

		}
		partie.ajouterCoup(abs,ord,abs2,ord2);
	}

	public static int getTaille(){
		// Fonction demandant et retournant la taille du plateau.
		int taille = 0;
		boolean continuer = true;
		while (continuer){
			System.out.println("Donnez la taille du plateau sur lequel vous souhaitez jouer : ");
			taille = Outils.getInt();
			if (taille >= 0)
				continuer=false;
			else {
				System.out.println("Merci de donner une taille positive.");
			}
		}
		return taille;
	}

}