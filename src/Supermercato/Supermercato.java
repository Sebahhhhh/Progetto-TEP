package Supermercato;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Supermercato {

    private final List<Cassa> casse;
    private final Semaphore coda;
    private int clientiTotali;

    public Supermercato(int numCasse) {
        casse = new ArrayList<>();
        // permette che un solo cliente alla volta entra nella cassa, quindi protegge
        coda = new Semaphore(1);
        // contatore dei clienti
        clientiTotali = 0;

        // creazione delle casse con un tempo scelto (la cassiera alpha è piu veloce e la cassiera gamma è la piu lenta)
        // il codice della emoji sono i simboli per essere piu chiari nella console, sono codici che danno emoji (presi da google)
        casse.add(new Cassa("ALPHA", 900, "\uD83D\uDD34"));
        casse.add(new Cassa("BETA", 2000, "\uD83D\uDD35"));
        casse.add(new Cassa("GAMMA", 3200, "\uD83D\uDD36"));
    }
     // si usa nel main per avviare il programma (apre il negozio)
    public void avviaSimulazione(int numClienti) {
        clientiTotali = numClienti;

        System.out.println("\n================================ Supermercato ================================");
        System.out.println("Velocità delle casse (ms per articolo):");

        // il ciclo for-each lo ho cercato perchè mai utilizzato prima d'ora. Si usa per cicli iterati (che si ripetono)
        // il ciclo inizia iterando il primo elemento, e lo assegna alla variabile cassa
        // il codice poi viene eseguito, usando il valore di "cassa" corrente
        // il ciclo si ripete per tutti gli elementi di "casse" (quindi per tutte e 3 le casse)
        for (Cassa cassa : casse) {
            System.out.println("  " + cassa.getNomeEmoji() + ": " + cassa.getTempoPerArticolo() + " millisecondi per articolo");
        }
        System.out.println("===============================================================================\n");

        // avvio i trheads delle casse
        // ciclo for-each uguale a quello sopra
        for (Cassa cassa : casse) {
            new Thread(cassa).start();
        }

        // fa entrare i clienti
        for (int i = 1; i <= numClienti; i++) {
            Cliente cliente = new Cliente("Cliente-" + i, casse, coda);
            new Thread(cliente).start();
            try {
                Thread.sleep(700); // attesa per farli entrare, fra uno e l'altro
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // si accerta, aspettand un po', che tutte le casse abbiano terminato
        // **
        try {
            Thread.sleep((numClienti * 4000) + 4500); // stima del tempo (non ho idea di come fare per fare in modo che si esegua solo  al termine definitivo delle casse)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mostraStatistiche();
    }

    // per visualizzare ogni cassa quanti clienti e articoli ha fatto
    // **
    private void mostraStatistiche() {
        System.out.println("\n======================== Statistiche di fine giornata ========================");
        for (Cassa cassa : casse) {
            System.out.println(cassa.getStatistiche());
        }
        System.out.println("==============================================================================");
    }
}

