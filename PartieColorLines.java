import java.util.ArrayList;

public class PartieColorLines {
	// Classe qui permet de tenir à jour l'historique des coups d'une partie.

	ArrayList<PlateauColorLines> liste = new ArrayList<PlateauColorLines> () ;
	ArrayList<Integer[]>listeCoup = new ArrayList<Integer[]>();
	ArrayList<Integer[]> listeCoupAuto = new ArrayList<Integer[]>();
	ArrayList<Integer> listeBonus = new ArrayList<Integer>();
	JoueurColorLines j1;
	boolean damier, bonus ;
	int taille;

	public PartieColorLines (Sauvegarde sauv, int a ){



		if (a >= 0 && a < sauv.nom.size())
			lire(sauv,sauv.nom.get(a));

		
	}

	

	public PartieColorLines (JoueurColorLines j1, int taille, boolean damier,boolean bonus){
		this.j1 = j1;
		this.taille = taille;
		this.damier = damier;
		this.bonus = bonus;

	}


	public String sauvegarder(Sauvegarde sauv){
		// Sauvegarde j1 et j2, 
		String chaine = Outils.constitueBalise("Taille",String.valueOf(taille))+"\n";
		chaine += Outils.constitueBalise("Bonus",String.valueOf(bonus))+"\n";
		chaine += Outils.constitueBalise("Damier",String.valueOf(damier))+"\n";
		chaine += j1.sauvegarder()+"\n";
		String chaine2;

		for (int i = 0; i < listeCoup.size(); i++){
			chaine2 = "";
			chaine2 += Outils.constitueBalise("Abs",String.valueOf(listeCoup.get(i)[0]));
			chaine2 += Outils.constitueBalise("Ord",String.valueOf(listeCoup.get(i)[1]));
			chaine2 += Outils.constitueBalise("Abs2",String.valueOf(listeCoup.get(i)[2]));
			chaine2 += Outils.constitueBalise("Ord2",String.valueOf(listeCoup.get(i)[3]));
			//chaine2 += Outils.constitueBalise("Points",String.valueOf(listeCoup.get(i)[4]));
			chaine2 = Outils.constitueBalise("Coup",chaine2);
			chaine += chaine2 + "\n";
		}
		chaine +="\n";

		for (int i = 0; i < listeCoupAuto.size(); i++){
			chaine2 = "";
			chaine2 += Outils.constitueBalise("Abs",String.valueOf(listeCoupAuto.get(i)[0]));
			chaine2 += Outils.constitueBalise("Ord",String.valueOf(listeCoupAuto.get(i)[1]));
			chaine2 += Outils.constitueBalise("Coul",String.valueOf(listeCoupAuto.get(i)[2]));
			chaine2 += Outils.constitueBalise("Abs2",String.valueOf(listeCoupAuto.get(i)[3]));
			chaine2 += Outils.constitueBalise("Ord2",String.valueOf(listeCoupAuto.get(i)[4]));
			chaine2 += Outils.constitueBalise("Coul2",String.valueOf(listeCoupAuto.get(i)[5]));
			chaine2 += Outils.constitueBalise("Abs3",String.valueOf(listeCoupAuto.get(i)[6]));
			chaine2 += Outils.constitueBalise("Ord3",String.valueOf(listeCoupAuto.get(i)[7]));
			chaine2 += Outils.constitueBalise("Coul3",String.valueOf(listeCoupAuto.get(i)[8]));
			//chaine2 += Outils.constitueBalise("Points",String.valueOf(listeCoup.get(i)[4]));
			chaine2 = Outils.constitueBalise("Auto",chaine2);
			chaine += chaine2 + "\n";
		}
		chaine +="\n";

		for (int i = 0; i < listeBonus.size(); i++){
			chaine2 = Outils.constitueBalise("BonusUtilise",String.valueOf(listeBonus.get(i)));
			chaine += chaine2+"\n";
		}


		String nom = Outils.getDate()+ " " +j1.pseudo;
		boolean continuer = true;
		int i = 0;
		String nom2 = nom;
		while (continuer) {
			if (i == 0)
				continuer = ! sauv.ajouter(nom);
			else {
				nom2 = nom + " ("+i+")";
				continuer = ! sauv.ajouter(nom2);
			}

			i++;
			
		}
		
		Outils.ecrireFichier(sauv.getAdresseDossier()+nom2, chaine);
		return nom;
	}

	public boolean lire (Sauvegarde sauv, String nom){
		// Lire et ouvrir une partie.
		if (! Outils.testPresence(sauv.getAdresseDossier()+nom))
			return false;
		String chaine = Outils.lireFichier(sauv.getAdresseDossier()+nom);
		bonus = Boolean.parseBoolean(Outils.recupereBaliseAuto(chaine,"Bonus",1,"Bonus",false));
		taille = Integer.parseInt(Outils.recupereBaliseAuto(chaine,"Taille",1,"Taille",false));
		damier = Boolean.parseBoolean(Outils.recupereBaliseAuto(chaine,"Damier",1,"Damier",false));

		String joueur = Outils.recupereBaliseAuto(chaine,"JoueurColorLines",1,"JoueurColorLines",false);
		j1 = new JoueurColorLines("toto");
		j1.ouvrir(joueur);


		int a = Outils.compter(chaine,"<Coup");
		Integer coo[] ;
		String chaine2;

		for (int i  = 0 ; i< a ; i++){
			chaine2 = Outils.recupereBaliseAuto(chaine,"Coup",(i+1),"Coup",false);
			coo = new Integer[4];
			coo[0] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Abs",1,"Abs",false));
			coo[1] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Ord",1,"Ord",false));
			coo[2] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Abs2",1,"Abs2",false));
			coo[3] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Ord2",1,"Ord2",false));
			//coo[4] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Points",1,"Points",false));

			listeCoup.add(coo);

		}

		a = Outils.compter(chaine,"<Auto");
		for (int i  = 0 ; i< a ; i++){
			chaine2 = Outils.recupereBaliseAuto(chaine,"Auto",(i+1),"Auto",false);
			coo = new Integer[9];
			coo[0] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Abs",1,"Abs",false));
			coo[1] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Ord",1,"Ord",false));
			coo[2] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Coul",1,"Coul",false));
			coo[3] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Abs2",1,"Abs2",false));
			coo[4] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Ord2",1,"Ord2",false));
			coo[5] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Coul2",1,"Coul2",false));
			coo[6] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Abs3",1,"Abs3",false));
			coo[7] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Ord3",1,"Ord3",false));
			coo[8] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Coul3",1,"Coul3",false));
			//coo[4] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Points",1,"Points",false));

			listeCoupAuto.add(coo);

		}

		if (bonus){
			a = Outils.compter(chaine,"<BonusUtilise");
			for (int i = 0; i < a; i++){
				listeBonus.add(Integer.valueOf(Outils.recupereBaliseAuto(chaine,"BonusUtilise",(i+1),"BonusUtilise",false)));
			}
		}

		// Lire les coups.
		construirePlateaux();

		return true;
	}

	public void ajouterCoup(int abs, int ord,int abs2, int ord2){
		// Ajoute et crée le plateau correspondant. // Les points sont donnés après 
		//int tab[] = {abs,ord};
		Integer [] tab2 = {abs,ord,abs2,ord2};
		listeCoup.add(tab2);
		//construirePlateau(listeCoup.size()-1);

	}

	public void ajouterCoupAuto(int abs, int ord, int coul, int abs2, int ord2, int coul2, int abs3, int ord3, int coul3){
		// Ajoute et crée le plateau correspondant. // Les points sont donnés après 
		//int tab[] = {abs,ord};
		Integer [] tab2 = {abs,ord,coul,abs2,ord2,coul2,abs3,ord3,coul3};
		listeCoupAuto.add(tab2);
		construirePlateau(listeCoup.size()-1); // La construction se fait après l'ajout des coups automatiques. 

	}

	public  void ajouterBonus(int b){
		listeBonus.add(b);
	}

	public PlateauColorLines construirePlateau(int coup){
		
		if (coup < 0 ||  coup >= listeCoupAuto.size()) return null;
		JoueurColorLines joueurActuel  = new JoueurColorLines(j1.pseudo);
		MouvementColorLines mouv ;
		PlateauColorLines p = new PlateauColorLines(taille, taille, damier, joueurActuel, bonus);
		Case deb, arr;
		PionCL pion;

		//System.out.println("Coup :" +coup);

		for (int i = 0; i <= coup; i++){
			//System.out.println("i : "+i+" 0 : "+listeCoup.get(i)[0]+", 1 : "+listeCoup.get(i)[1]);
			//System.out.println("Joueur actuel : "+joueurActuel.pseudo);$

			if (i != 0 && i != listeCoupAuto.size()){
				deb = p.tab[listeCoup.get(i-1)[1]][listeCoup.get(i-1)[0]];
				arr = p.tab[listeCoup.get(i-1)[3]][listeCoup.get(i-1)[2]];
				mouv = new MouvementColorLines(deb,arr);
				mouv.faire(p);

				p.verificationAlignement();


			}

			for (int i2 = 0; i2 < 3; i2++){
				if ( listeCoupAuto.get(i)[i2*3+1] != -1 && listeCoupAuto.get(i)[i2*3] != -1){
					pion = new PionCL(p.tab[listeCoupAuto.get(i)[i2*3+1]][listeCoupAuto.get(i)[i2*3]],listeCoupAuto.get(i)[i2*3+2]);
					p.tab[listeCoupAuto.get(i)[i2*3+1]][listeCoupAuto.get(i)[i2*3]].pion = pion;

				}
					
			}

			for (int i2 = 0; i2 < listeBonus.size(); i2++){
				if (i == listeBonus.get(i2)){
					joueurActuel.enleverPoints(p.pointsAEnvleverBonus);
					break;
				}
			}

			
			p.verificationAlignement(); // On verifie si l'ajout n'a pas fais des alignements
			if(p.verificationFin()){
				System.out.println("verificationFin");
				return p;
			}
					


					
		}
		
		return p;
	}


	public void construirePlateaux(){
		// A partir de la liste des coups, construire la liste de plateau.
		//System.out.println("Liste coup .size "+listeCoup.size());
		PlateauColorLines p = new PlateauColorLines(taille, taille, damier, j1, bonus);
		liste.add(p);
		for (int i =  0;  i < listeCoupAuto.size(); i++){
			liste.add(construirePlateau(i));
			System.out.println("coupAuto "+i+" : "+listeCoupAuto.get(i)[0]+","+listeCoupAuto.get(i)[1]+" ; "+listeCoupAuto.get(i)[3]+","+listeCoupAuto.get(i)[4]+" ; "+listeCoupAuto.get(i)[6]+","+listeCoupAuto.get(i)[7]);
			if (i != listeCoupAuto.size() - 1){
				System.out.println("coup "+i+" : "+listeCoup.get(i)[0]+","+listeCoup.get(i)[1]+" ; "+listeCoup.get(i)[2]+","+listeCoup.get(i)[3]);
			}
			else{
				//InterfaceConsoleCL.affichePlateau(liste.get(i));
				System.out.println();
				InterfaceConsoleCL.affichePlateau(liste.get(i+1));
			}
			System.out.println();


		}
		System.out.println("Plateux nb : "+liste.size());
	}

	public PlateauColorLines getPlateau(int coup){
		// Renvoie le plateau Gomuku tel qu'il est avant le coup n°coup jouer par les blancs ou les noirs.
		//System.out.println("Liste size : "+liste.size()+" coup = "+coup);
		return liste.get(coup);
	}

	public int[] getCoup (int coup){
		//Integer[] tab = listeCoup.get(coup);
		int [] tab = new int[4];
		tab[0] = listeCoup.get(coup)[0] + 1;
		tab[1] = listeCoup.get(coup)[1] +1;
		tab[2] = listeCoup.get(coup)[2] +1;
		tab[3] = listeCoup.get(coup)[3] +1;
		/*tab[0] += 1;
		tab[1] += 1; // Ajouter 2 et 3*/
		return tab;
	}


}