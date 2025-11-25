package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {

    private final List<String> nomsJoueurs = new ArrayList<>();
    private final List<PartieMonoJoueur> parties = new ArrayList<>();

    private int indexJoueurCourant = -1;
    private boolean demarree = false;

    @Override
    public String demarreNouvellePartie(String[] nomsDesJoueurs) {
        if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
            throw new IllegalArgumentException("Il faut au moins un joueur");
        }

        nomsJoueurs.clear();
        parties.clear();

        for (String nom : nomsDesJoueurs) {
            if (nom == null || nom.isBlank()) {
                throw new IllegalArgumentException("Nom de joueur invalide");
            }
            nomsJoueurs.add(nom);
            parties.add(new PartieMonoJoueur());
        }

        indexJoueurCourant = 0;
        demarree = true;

        return messageProchainTir();
    }

    @Override
    public String enregistreLancer(int nombreDeQuillesAbattues) {
        if (!demarree) {
            throw new IllegalStateException("La partie n'a pas été démarrée");
        }

        if (estTerminee()) {
            throw new IllegalStateException("La partie est terminée");
        }

        PartieMonoJoueur partieCourante = parties.get(indexJoueurCourant);

        boolean tourContinue = partieCourante.enregistreLancer(nombreDeQuillesAbattues);

        if (!tourContinue) {
            passerAuJoueurSuivant();
        }

        if (estTerminee()) {
            return "Partie terminée";
        }

        return messageProchainTir();
    }

    @Override
    public int scorePour(String nomDuJoueur) {
        int index = nomsJoueurs.indexOf(nomDuJoueur);
        if (index == -1) {
            throw new IllegalArgumentException("Joueur inconnu");
        }
        return parties.get(index).score();
    }
    public boolean estTerminee() {
        if (!demarree) {
            return false;
        }
        for (PartieMonoJoueur p : parties) {
            if (!p.estTerminee()) {
                return false;
            }
        }
        return true;
    }

    private void passerAuJoueurSuivant() {
        int nbJoueurs = nomsJoueurs.size();
        for (int i = 1; i <= nbJoueurs; i++) {
            int nextIndex = (indexJoueurCourant + i) % nbJoueurs;
            if (!parties.get(nextIndex).estTerminee()) {
                indexJoueurCourant = nextIndex;
                return;
            }
        }
    }

    private String messageProchainTir() {
        PartieMonoJoueur partieCourante = parties.get(indexJoueurCourant);
        String nom = nomsJoueurs.get(indexJoueurCourant);
        int tour = partieCourante.numeroTourCourant();
        int boule = partieCourante.numeroProchainLancer();

        return String.format(
            "Prochain tir : joueur %s, tour n° %d, boule n° %d",
            nom, tour, boule
        );
    }
}
