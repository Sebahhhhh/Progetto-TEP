package Hellcat;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente implements Runnable {
    // variabili
    private final String nome;
    // arraylist di casse
    private final List<Cassa> casse;
    private final Semaphore Codaa;
    private final Random random;

    // costruttore
    public Cliente(String nome, List<Cassa> casse, Semaphore codaa) {
        this.nome = nome;
        this.casse = casse;
        this.Codaa = codaa;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            // numero di articoli casuali da 2 a 10
            int articoli = random.nextInt(8) + 2;
            System.out.println("\n╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("  " + nome + " entra nel supermercato con " + articoli + " articoli da prendere. ");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            Thread.sleep(articoli * (random.nextInt(2501) + 500)); // tempo randomico per prendere articoli

            // output
            System.out.println(" ");
            System.out.println("🛒 " + nome + " ha finito di fare la spesa e si dirige alle casse.");

            // cerca la cassa piu corta. E' trhead safe
            Codaa.acquire();
            // get 0 perchè prende il primo elemento della lista (che è la cassa piu corta)
            // se la coda è vuota, prende la prima cassa
            Cassa cassaScelta = casse.get(0);
            // ciclo for-each spiegato nell'altro file
            for (Cassa cassa : casse) {
                if (cassa.getLunghezzaCoda() < cassaScelta.getLunghezzaCoda()) {
                    cassaScelta = cassa;
                }
            }
            // Output che dice che cassa ha scelto e quanta coda ha
            System.out.println("➡️ " + nome + " ha scelto la " + cassaScelta.getNomeEmoji() + " (Coda: " + cassaScelta.getLunghezzaCoda() + " clienti)");
            Codaa.release();

            // si aggiunge alla coda della cassa scelta
            cassaScelta.aggiungiCliente(articoli);

        } catch (InterruptedException e) {
            System.out.println(nome + " errore: " + e.getMessage());
        }
    }
}

