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
        clientiTotali = 0;

        // creazione delle casse con un tempo scelto (la cassiera alpha è piu veloce e la cassiera gamma è la piu lenta)
        casse.add(new Cassa("ALPHA", 1000, "🟢"));
        casse.add(new Cassa("BETA", 2200, "🔵"));
        casse.add(new Cassa("GAMMA", 3500, "🟣"));
    }
     // si usa nel main per avviare il programma (apre il negozio)
    public void avviaSimulazione(int numClienti) {
        clientiTotali = numClienti;

        System.out.println("\n================================ Supermercato ================================");
        System.out.println("Velocità delle casse (ms per articolo):");
        for (Cassa cassa : casse) {
            System.out.println("  " + cassa.getNomeEmoji() + ": " + cassa.getTempoPerArticolo() + " millisecondi per articolo");
        }
        System.out.println("===============================================================================\n");

        // avvio i trheads delle casse
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
        try {
            Thread.sleep((numClienti * 4000) + 5000); // stima del tempo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mostraStatistiche();
    }

    // per visualizzare ogni cassa quanti clienti e articoli ha fatto
    private void mostraStatistiche() {
        System.out.println("\n======================== Statistiche di fine giornata ========================");
        for (Cassa cassa : casse) {
            System.out.println(cassa.getStatistiche());
        }
        System.out.println("==============================================================================");
    }
}

