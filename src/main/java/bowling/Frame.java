package bowling;

import java.util.ArrayList;
import java.util.List;

public abstract class Frame {

    protected final int numero;
    protected final List<Integer> lancers = new ArrayList<>();

    public Frame(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void ajouterLancer(int quilles) {
        if (estComplet()) {
            throw new IllegalStateException("Le frame " + numero + " est déjà complet");
        }
        lancers.add(quilles);
    }

    public boolean estStrike() {
        return !lancers.isEmpty() && lancers.get(0) == 10;
    }

    public boolean estSpare() {
        return lancers.size() >= 2 && lancers.get(0) + lancers.get(1) == 10 && !estStrike();
    }

    public int scoreDeBase() {
        int s = 0;
        for (int q : lancers) {
            s += q;
        }
        return s;
    }

    public int nombreDeLancers() {
        return lancers.size();
    }

    public int getLancer(int index) {
        return index < lancers.size() ? lancers.get(index) : 0;
    }

    /** Nombre maximum de lancers possibles dans ce frame. */
    public abstract int maxLancers();

    /** Vrai si ce frame est terminé (plus de lancer possible). */
    public abstract boolean estComplet();
}

