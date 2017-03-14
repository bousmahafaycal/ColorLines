import java.util.*;

public class PlateauColorLines extends Plateau {
	JoueurColorLines j ;
	boolean bonus;
	int lonAlignement = 5;
	int pointsAEnvleverBonus = 25;


	

	public PlateauColorLines(int abs, int ord, boolean damier, JoueurColorLines j,boolean bonus){ 
		super(abs, ord,damier);
		this.j = j;
		this.bonus = bonus;
	}

	public boolean verificationFin() {
		// Fonction vérifiant si la partie est terminée.
		return plateauComplet();
	}

	

	public boolean verificationAlignement(){
		// On vérifie si il y'a des alignements, on ajoute dans ce cas-la des points au joueur selon la grandeur de l'alignement et on supprime les pions
		// Ayant consituté l'alignement.
		ArrayList<Case> array = new  ArrayList<Case>();
		for (int i = 1; i < PionCL.tab.length; i++){ // Ca sert à rien de le faire pour les pions arc en ciel d'ou le i = 1
			verificationAlignementCouleur(i,array);
		}
		int points = array.size()*array.size();
		j.ajouterPoints(points); 
		retirerPion(array);

		return true; // Pour le moment pour la compilation
	}

	public void retirerPion(ArrayList<Case> liste){
		// retirer les pions notammant si il y a un alignement.
		//System.out.println("retirer pion");
		for (int i = 0; i< liste.size(); i++){
			liste.get(i).pion.c = null;
			liste.get(i).pion = null;

		}

	}

	public  int verificationAlignementCouleur(int couleur, ArrayList<Case> casesAlignement){
		// Fonction retournant le nombre d'alignements de 5 pions d'un joueur et remplissant toutes les cases faisant partie d'un alignement dans casesAlignement
		int nbAlignement = 0;
		int compteurLigne = 0, compteurColonne = 0, compteurDiagGauche = 0, compteurDiagDroite = 0 ;
		PionCL pion ;
		for (int i = 0; i < tab.length; i++){
			compteurLigne = 0;
			compteurColonne = 0;
			//System.out.println("nbAlignement : "+nbAlignement);
			for (int i2 = 0; i2 < tab[i].length; i2++){

				// Verification des lignes
				if (tab[i][i2].pion == null){
					compteurLigne = 0;
				}
				else{
					pion = (PionCL) tab[i][i2].pion ;
					
					if (pion.couleur == couleur || pion.couleur == 0)
						compteurLigne++;
					else
						compteurLigne = 0;
				}

				if (compteurLigne >= lonAlignement){
					//System.out.println("Alignement ligne : ["+i+";"+i2+"]");
					for (int i3 = 0; i3 <  compteurLigne; i3++){
						if (! casesAlignement.contains(tab[i][i2-i3]))
							casesAlignement.add(tab[i][i2-i3]);
					}
					nbAlignement++;
				}


				// Verification des colonnes
				if (tab[i2][i].pion == null){
					compteurColonne = 0;
				}
				else{
					pion = (PionCL) tab[i2][i].pion ;
					if (pion.couleur == couleur || pion.couleur == 0)
						compteurColonne++;
					else
						compteurColonne = 0;
				}

				if (compteurColonne >= lonAlignement){
					//System.out.println("Alignement colonne : ["+i2+";"+i+"]");
					for (int i3 = 0; i3 <  compteurColonne; i3++){
						if (! casesAlignement.contains(tab[i2-i3][i]))
							casesAlignement.add(tab[i2-i3][i]);
					}

					nbAlignement++;
				}

				compteurDiagGauche = 0;
				compteurDiagDroite = 0;
				// Verification des diagonales
				for (int i3 = 0; i3 < lonAlignement ; i3++){

					// Verification de la diagonale remontante vers la gauche
					if (i -i3 >= 0 && i2 -i3 >= 0){ // Pour empecher des out of range
						if (tab[i-i3][i2-i3].pion == null){
							compteurDiagGauche = 0;
						}
						else{
							pion = (PionCL) tab[i-i3][i2-i3].pion ;
							if (pion.couleur == couleur || pion.couleur == 0)
								compteurDiagGauche++;
							else
								compteurDiagGauche = 0;
						}

						if (compteurDiagGauche >= lonAlignement){
							//System.out.println("Alignement diagonale gauche : ["+(i-i3)+";"+(i2-i3)+"]");
							for (int i4 = 0; i4 <  compteurDiagGauche; i4++){
								if (! casesAlignement.contains(tab[i-i4][i2-i4]))
									casesAlignement.add(tab[i-i4][i2-i4]);
							}
							nbAlignement++;
						}

					}else {
						compteurDiagGauche = 0;
					}


					// Verification de la diagonale remontante vers la droite
					if (i - i3 >= 0 && i2 + i3 < tab[i].length){ // Pour empecher des out of range
						if (tab[i-i3][i2+i3].pion == null){
							compteurDiagDroite = 0;
						}
						else{
							pion = (PionCL) tab[i-i3][i2+i3].pion ;
							if (pion.couleur == couleur || pion.couleur == 0)
								compteurDiagDroite++;
							else
								compteurDiagDroite = 0;
						}

						if (compteurDiagDroite >= lonAlignement){
							//System.out.println("Alignement diagonale droite : ["+(i-i3)+";"+(i2+i3)+"]");
							for (int i4 = 0; i4 <  compteurDiagDroite; i4++){
								if (! casesAlignement.contains(tab[i-i4][i2+i4]))
									casesAlignement.add(tab[i-i4][i2+i4]);
							}

							nbAlignement++;
						}

					}else {
						compteurDiagDroite = 0;
					}
				}

			}
		} 
		return nbAlignement; 
	}

	public void remplirAuto(PartieColorLines p){
		// Fonction qui ajoutera automatiquement les 3 pions de couleur aléatoire à des positions aléatoires. Utilisera la focntion ajouterPion.
		ArrayList<Case> array = caseDispo();
		int a, b;
		PionCL pion;
		Integer [] tab2 = new Integer[9];

		for (int i = 0; i < tab2.length; i++)
			tab2[i] = -1;


		for (int i = 0; i < 3; i++ ){
			if (array.size() == 0){
				p.ajouterCoupAuto(tab2[0], tab2[1], tab2[2], tab2[3], tab2[4], tab2[5], tab2[6], tab2[7], tab2[8]);
				return ;
			}
			a = (int)( Math.random()*array.size());
			b =  (int)( Math.random()*PionCL.tab.length);
			pion = new PionCL(array.get(a),b);
			array.get(a).pion = pion;
			tab2[3*i] = array.get(a).x;
			tab2[3*i+1] = array.get(a).y;
			tab2[3*i+2] =  b;
			array.remove(a);
		}

		p.ajouterCoupAuto(tab2[0], tab2[1], tab2[2], tab2[3], tab2[4], tab2[5], tab2[6], tab2[7], tab2[8]);      
	
	}

	

	public boolean remplirCouleur(int couleur, PartieColorLines p, int tour){
		// Fonction qui ajoute trois pions d'une meme couleur à des endroits aléatoires. Utilisera la focntion ajouterPion.
		if (couleur < 0 || couleur >= PionCL.tab.length)
			return false;

		Integer [] tab2 = new Integer[9];

		ArrayList<Case> array = caseDispo();
		int a;
		PionCL pion;
		for (int i = 0; i < 3; i++ ){
			if (array.size() == 0)
				return true;
			a = (int)( Math.random()*array.size());
			pion = new PionCL(array.get(a),couleur);
			array.get(a).pion = pion;
			tab2[3*i] = array.get(a).x;
			tab2[3*i+1] = array.get(a).y;
			tab2[3*i+2] =  couleur;
			array.remove(a);
		}

		p.ajouterCoupAuto(tab2[0], tab2[1], tab2[2], tab2[3], tab2[4], tab2[5], tab2[6], tab2[7], tab2[8]); 
		p.ajouterBonus(tour);

		j.enleverPoints(pointsAEnvleverBonus);
		return true;
	}

	public boolean remplirPositions(int []abs, int []ord, PartieColorLines p, int tour){
		// Fonction qui mets trois pions de couleurs aléatoire à des endroits choisis. Utilisera la focntion ajouterPion.
		PionCL pion;
		int b;
		for (int i = 0; i < abs.length; i++){
			if (abs[i] < 0 || ord[i] < 0 || abs[i] >= tab[0].length || ord[i] >= tab.length)
				return false;
			if (tab[ord[i]][abs[i]].pion != null)
				return false;
			if (i!= 0 && abs[i-1] == abs[i] && ord[i-1] == ord[i])
				return false;
		}

		Integer [] tab2 = new Integer[9];

		for (int i = 0; i < 3 ;  i++){
			b =  (int)( Math.random()*PionCL.tab.length);
			pion = new PionCL(tab[ord[i]][abs[i]],b);
			tab[ord[i]][abs[i]].pion = pion;
			tab2[3*i] = abs[i];
			tab2[3*i+1] = ord[i];
			tab2[3*i+2] =  b;
			
		}

		p.ajouterCoupAuto(tab2[0], tab2[1], tab2[2], tab2[3], tab2[4], tab2[5], tab2[6], tab2[7], tab2[8]);
		p.ajouterBonus(tour);

		j.enleverPoints(pointsAEnvleverBonus);

		return true;
	}


}