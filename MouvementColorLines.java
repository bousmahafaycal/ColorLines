public class MouvementColorLines {
	Case depart, arrivee;

	public  MouvementColorLines (Case depart, Case arrivee) {
		this.depart = depart;
		this.arrivee = arrivee;
	}
 
	public boolean  isValid(PlateauColorLines p){
		// Verifie que le mouvement  est valide (le pion sur la case de départ existe, la case d'arrivée est de la meme couleur, appel à depart.pion.isValid(p,arrivee))
		if (! arrivee.blanc == depart.blanc)
			return false;

		if (depart.pion == null)
			return false;

		return depart.pion.isValid(p,arrivee);
	}

	public boolean faire(PlateauColorLines p){
		if (! isValid(p)){
			//System.out.println("MouvementColorLines REFUSE");
			return false;
		}

		
		arrivee.fill(depart.getPion());
		depart.removePion();
		return true;
	}

}