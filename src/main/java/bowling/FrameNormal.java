package bowling;

public class FrameNormal extends Frame {

    public FrameNormal(int numero) {
        super(numero);
    }

    @Override
    public int maxLancers() {
        return 2;
    }

    @Override
    public boolean estComplet() {
        // Strike direct ou bien deux lancers effectués
        return estStrike() || lancers.size() == 2;
    }
}
