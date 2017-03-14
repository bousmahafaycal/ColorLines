public class PionCL  extends Pion {
	static String []tab = {"arc-en-ciel","bleu","rouge","jaune","vert","orange","gris"};
	int couleur = 0;

	public PionCL(Case c, int couleur){
		super(c);
		if (couleur >= 0 &&  couleur < tab.length)
			this.couleur = couleur;

	}

	public boolean isValid(Plateau p, Case  arrivee){
		// Verifier que la case d'arrivée est vide et qu'un chemin est disponible
		if (! verifieAriveeVide(arrivee))
			return false;
		return true;
	}
}