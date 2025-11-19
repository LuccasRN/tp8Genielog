package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMonoJoueur {

    private final List<Frame> frames = new ArrayList<>();
    private int currentFrameIndex = 0;   // 0..9
    private boolean terminee = false;

    public PartieMonoJoueur() {
        // 9 frames normales + 1 frame final
        for (int i = 1; i <= 9; i++) {
            frames.add(new FrameNormal(i));
        }
        frames.add(new FrameFinal()); // numéro = 10
    }

    public boolean enregistreLancer(int nombreDeQuillesAbattues) {
        if (terminee) {
            throw new IllegalStateException("La partie est déjà terminée");
        }
        if (nombreDeQuillesAbattues < 0 || nombreDeQuillesAbattues > 10) {
            throw new IllegalArgumentException("Nombre de quilles invalide : " + nombreDeQuillesAbattues);
        }

        Frame frameCourant = frames.get(currentFrameIndex);
        frameCourant.ajouterLancer(nombreDeQuillesAbattues);

        boolean frameComplet = frameCourant.estComplet();

        if (currentFrameIndex == 9 && frameComplet) { // 10e frame terminé
            terminerPartie();
            return false;
        }

        if (frameComplet) {
            currentFrameIndex++;
            return false; // le tour est fini
        } else {
            return true;  // le tour continue
        }
    }

    public int score() {
        // Aplano todos los lanzamientos en una lista
        List<Integer> tousLesLancers = new ArrayList<>();
        for (Frame f : frames) {
            for (int i = 0; i < f.nombreDeLancers(); i++) {
                tousLesLancers.add(f.getLancer(i));
            }
        }

        int score = 0;
        int indexLancer = 0;

        for (int frame = 1; frame <= 10; frame++) {
            int premier = getLancer(tousLesLancers, indexLancer);

            // Strike
            if (premier == 10) {
                int bonus1 = getLancer(tousLesLancers, indexLancer + 1);
                int bonus2 = getLancer(tousLesLancers, indexLancer + 2);
                score += 10 + bonus1 + bonus2;
                indexLancer += 1;
            } else {
                int second = getLancer(tousLesLancers, indexLancer + 1);
                int total = premier + second;

                if (total == 10) { // spare
                    int bonus = getLancer(tousLesLancers, indexLancer + 2);
                    score += 10 + bonus;
                } else {
                    score += total;
                }
                indexLancer += 2;
            }
        }

        return score;
    }

    public boolean estTerminee() {
        return terminee;
    }

    public int numeroTourCourant() {
        if (terminee) {
            return 0;
        }
        return frames.get(currentFrameIndex).getNumero();
    }

    public int numeroProchainLancer() {
        if (terminee) {
            return 0;
        }
        Frame f = frames.get(currentFrameIndex);
        return f.nombreDeLancers() + 1; // 1, 2 ou 3
    }

    /* ----------- utilitaires internes ----------- */

    private int getLancer(List<Integer> lancers, int index) {
        return index < lancers.size() ? lancers.get(index) : 0;
    }

    private void terminerPartie() {
        terminee = true;
    }
}

