public class JoueurColorLines extends Joueur {

	// Cette classe a été crée pour les bonus, mais sera completée ulterieurment si le temps nous le permet.

	int points = 0;
	

	public JoueurColorLines (String pseudo){
		super(pseudo);
	}

	public void ajouterPoints(int  points){
		this.points += points;

	}

	public void enleverPoints( int points) {
		// On lui enleve des points notamment si il utilise un bonus.
		this.points -= points;
	}

	public void ouvrir(String chaine){
		super.ouvrir(chaine);
	}

	public String sauvegarder (){
		String chaine = super.sauvegarder();
		chaine = Outils.constitueBalise("JoueurColorLines",chaine);
		return chaine;
	}
}