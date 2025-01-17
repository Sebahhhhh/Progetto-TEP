package Supermercato;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente implements Runnable {

    private final String nome;
    private final List<Cassa> casse;
    private final Semaphore mutexCoda;
    private final Random random;

    public Cliente(String nome, List<Cassa> casse, Semaphore mutexCoda) {
        this.nome = nome;
        this.casse = casse;
        this.mutexCoda = mutexCoda;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            // numero di articoli casuali da 2 a 10
            int articoli = random.nextInt(9) + 2;
            System.out.println("\n╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("  " + nome + " entra nel supermercato con " + articoli + " articoli da prendere. ");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            Thread.sleep(articoli * (random.nextInt(2501) + 500)); // tempo randomico per prendere articoli

            // output
            System.out.println(" ");
            System.out.println("🛒 " + nome + " ha finito di fare la spesa e si dirige alle casse.");

            // cerca la cassa piu corta, è thread-safe
            mutexCoda.acquire();
            Cassa cassaScelta = casse.get(0);
            for (Cassa cassa : casse) {
                if (cassa.getLunghezzaCoda() < cassaScelta.getLunghezzaCoda()) {
                    cassaScelta = cassa;
                }
            }
            System.out.println("➡️ " + nome + " ha scelto la " + cassaScelta.getNomeEmoji() + " (Coda: " + cassaScelta.getLunghezzaCoda() + " clienti)");
            mutexCoda.release();

            // viene aggiunto alla casa
            cassaScelta.aggiungiCliente(articoli);

        } catch (InterruptedException e) {
            System.out.println(nome + " ha riscontrato un errore: " + e.getMessage());
        }
    }
}

