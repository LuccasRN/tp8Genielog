package bowling;

public interface IPartieMultiJoueurs {

    /**
     * Démarre une nouvelle partie multi-joueurs.
     * @param nomsDesJoueurs tableau des noms (>=1)
     * @return message du type :
     *   "Prochain tir : joueur Pierre, tour n° 1, boule n° 1"
     */
    String demarreNouvellePartie(String[] nomsDesJoueurs);

    /**
     * Enregistre un lancer pour le joueur courant.
     * @param nombreDeQuillesAbattues quilles abattues (0..10)
     * @return message du type :
     *   "Prochain tir : joueur Paul, tour n° X, boule n° Y"
     * ou "Partie terminée"
     * @throws IllegalStateException si la partie n'a pas été démarrée ou est terminée
     */
    String enregistreLancer(int nombreDeQuillesAbattues);

    /**
     * Donne le score du joueur demandé.
     * @param nomDuJoueur nom du joueur
     * @return score actuel
     * @throws IllegalArgumentException si le joueur est inconnu
     */
    int scorePour(String nomDuJoueur);

    /**
     * @return true si la partie multi-joueurs est terminée
     */
    boolean estTerminee();
}

