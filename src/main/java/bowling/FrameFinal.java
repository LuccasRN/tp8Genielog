package bowling;

public class FrameFinal extends Frame {

    public FrameFinal() {
        super(10);
    }

    @Override
    public int maxLancers() {
        return 3;
    }

    @Override
    public boolean estComplet() {
        if (lancers.size() < 2) {
            return false;
        }
        if (lancers.size() == 2) {
            // Si strike ou spare avec les deux premières boules -> droit à une 3ème
            if (estStrike() || estSpare()) {
                return false;
            } else {
                return true; // ni strike ni spare : terminé après 2 lancers
            }
        }
        // 3 lancers -> forcément terminé
        return true;
    }
}

