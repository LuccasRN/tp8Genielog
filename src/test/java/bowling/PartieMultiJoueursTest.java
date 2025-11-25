package bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartieMultiJoueursTest {

    private IPartieMultiJoueurs partie;

    @BeforeEach
    void setUp() {
        partie = new PartieMultiJoueurs();
    }

    @Test
    void scenarioExempleEnonce() {
        String[] players = { "Pierre", "Paul" };

        // Démarrage de la partie
        String msg = partie.demarreNouvellePartie(players);
        assertEquals("Prochain tir : joueur Pierre, tour n° 1, boule n° 1", msg);

        // Pierre : 5
        msg = partie.enregistreLancer(5);
        assertEquals("Prochain tir : joueur Pierre, tour n° 1, boule n° 2", msg);

        // Pierre : 3  -> fin du tour de Pierre, on passe à Paul
        msg = partie.enregistreLancer(3);
        assertEquals("Prochain tir : joueur Paul, tour n° 1, boule n° 1", msg);

        // Paul : 10 (strike) -> fin de son tour, on revient à Pierre, tour 2 boule 1
        msg = partie.enregistreLancer(10);
        assertEquals("Prochain tir : joueur Pierre, tour n° 2, boule n° 1", msg);

        // Pierre tour 2 : 7
        msg = partie.enregistreLancer(7);
        assertEquals("Prochain tir : joueur Pierre, tour n° 2, boule n° 2", msg);

        // Pierre tour 2 : 3 -> fin du tour de Pierre, on passe à Paul tour 2 boule 1
        msg = partie.enregistreLancer(3);
        assertEquals("Prochain tir : joueur Paul, tour n° 2, boule n° 1", msg);

        // Vérification des scores
        int scorePierre = partie.scorePour("Pierre");
        int scorePaul   = partie.scorePour("Paul");

        assertEquals(18, scorePierre); // (5+3) + (7+3)
        assertEquals(10, scorePaul);   // strike seul pour l'instant (10 + 0 + 0)
    }

    @Test
    void joueurInconnuDeclencheException() {
        String[] players = { "Pierre", "Paul" };
        partie.demarreNouvellePartie(players);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> partie.scorePour("Jacques")
        );
        assertEquals("Joueur inconnu", ex.getMessage());
    }

    @Test
    void demarreNouvellePartieAvecAucunJoueurDeclencheException() {
        String[] players = {};
        assertThrows(IllegalArgumentException.class,
            () -> partie.demarreNouvellePartie(players));
    }

    @Test
    void enregistreLancerAvantDemarrageDeclencheException() {
        assertThrows(IllegalStateException.class,
            () -> partie.enregistreLancer(5));
    }

    @Test
    void scorePourAvantDemarrageDeclencheException() {
        assertThrows(IllegalArgumentException.class,
            () -> partie.scorePour("Pierre"));
    }
}

