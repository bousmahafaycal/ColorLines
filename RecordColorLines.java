import java.util.ArrayList;
public class RecordColorLines{
	// Classe qui permet de garder les record du jeu color lines.

	ArrayList<Integer> points = new ArrayList<Integer>();
	ArrayList<String> pseudo = new ArrayList<String>();
	ArrayList<Boolean> damier = new ArrayList<Boolean>();
	ArrayList<Boolean> bonus = new ArrayList<Boolean>();
	String chemin = "recordcolorlines.f";

	public RecordColorLines(){
		if (Outils.testPresence(chemin))
			lire();
		else
			sauvegarder();
	}


	public void sauvegarder(){
		// Sauvegarde ces records dans un fichier.
		String chaine = "";
		String chaine2 = "";
		for (int i = 0; i < points.size(); i++){
			chaine2 = "";
			chaine2 += Outils.constitueBalise("Points",String.valueOf(points.get(i)));
			chaine2 += Outils.constitueBalise("Pseudo",pseudo.get(i));
			chaine2 += Outils.constitueBalise("damier",String.valueOf(damier.get(i)));
			chaine2 += Outils.constitueBalise("Bonus",String.valueOf(bonus.get(i)));
			chaine2 = Outils.constitueBalise("Record",chaine2);
			chaine  += chaine2+"\n";


		}

		Outils.ecrireFichier(chemin,chaine);
	}

	public void lire(){
		// Fonction qui lit dans un fichier tous les records déja existants.
		String chaine = Outils.lireFichier(chemin);
		int a = Outils.compter(chaine, "<Record");
		String chaine2, pseudoS, pointsS,damierS, bonusS ;
		for (int i = 0; i < a ; i++){
			chaine2 = Outils.recupereBaliseAuto(chaine,"Record",(i+1),"Record",false);
			pseudoS = Outils.recupereBaliseAuto(chaine2,"Pseudo",1,"Pseudo",false);
			pointsS = Outils.recupereBaliseAuto(chaine2,"Points",1,"Points",false);
			damierS = Outils.recupereBaliseAuto(chaine2,"damier",1,"damier",false);
			bonusS = Outils.recupereBaliseAuto(chaine2,"Bonus",1,"Bonus",false);
			ajouterSansVerification(Integer.parseInt(pointsS),pseudoS, Boolean.parseBoolean(damierS), Boolean.parseBoolean(bonusS) );
		}
			
	}

	public boolean ajouter(int points, String pseudo, boolean damier, boolean bonus){
		// Fonction qui ajoute le record si c'est un record et return false sinon .
		int a = getRecord(damier,bonus) ;
		if (a == -1){
			ajouterSansVerification(points,pseudo,damier,bonus);
			return true;
		}else {
			return modifieRecord(points,pseudo,a);
		}
	}

	public int getRecord ( boolean damier, boolean bonus){
		// Verifie qu'un type de record existe, renvoie -1 si il n'existe pas sinon renvoie l'indice du record
		for (int i = 0; i < this.damier.size() ; i++){
			if (this.damier.get(i).equals(damier) && this.bonus.get(i).equals(bonus))
				return i;
		}
		return -1;
	}

	public int getPoints(int id){
		return points.get(id);
	}

	public String getPseudo (int id){
		return pseudo.get(id);
	}

	private void ajouterSansVerification (int points, String pseudo, boolean damier, boolean bonus){
		// Ajoute un nouveau record sans verification qu'il y'en a un avant, cette fonction est donc private.
		this.pseudo.add(pseudo);
		this.points.add(points);
		this.bonus.add(bonus);
		this.damier.add(damier);
		sauvegarder();
	}

	private boolean modifieRecord(int points, String pseudo, int id){
		// Modifie le record si et seulement si il a été battu.
		boolean d, b;
		if (points > this.points.get(id)){
			b = bonus.get(id);
			d = damier.get(id);
			
			this.pseudo.remove(id);
			this.points.remove(id);
			bonus.remove(id);
			damier.remove(id);

			ajouterSansVerification(points, pseudo, d, b);

			return true;
		}

		return false;

	}

	


}