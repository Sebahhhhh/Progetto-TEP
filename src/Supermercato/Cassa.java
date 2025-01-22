package Supermercato;

import java.util.ArrayList;
import java.util.List;

public class Cassa implements Runnable {

    private final String nome;
    private final int tempoPerArticolo;
    private final String emoji;
    private final List<Integer> codaClienti;
    private final StatisticheCassa statistiche;

    // costruttore
    public Cassa(String nome, int tempoPerArticolo, String emoji) {
        this.nome = nome;
        this.tempoPerArticolo = tempoPerArticolo;
        this.emoji = emoji;
        this.codaClienti = new ArrayList<>();
        this.statistiche = new StatisticheCassa(nome, emoji);
    }

    // get e set
    public String getNome() {
        return nome;
    }

    public String getNomeEmoji() {
        return emoji + " " + nome;
    }

    public int getTempoPerArticolo() {
        return tempoPerArticolo;
    }

    public int getLunghezzaCoda() {
        // evita la concorezza sulla lunghezza della coda
        synchronized (codaClienti) {
            return codaClienti.size();
        }
    }

    public StatisticheCassa getStatistiche() {
        return statistiche;
    }

    public void aggiungiCliente(int articoli) {
        synchronized (codaClienti) {
            codaClienti.add(articoli);
            // notify per i nuovi clienti
            codaClienti.notify();
        }
    }

    // starta
    public void run() {
        while (true) {
            try {
                int articoli;
                synchronized (codaClienti) {
                    while (codaClienti.isEmpty()) {
                        // wait che attende nuovi clienti per l'elaborazione
                        codaClienti.wait();
                    }
                    articoli = codaClienti.remove(0);
                }
                System.out.println(" ");
                System.out.println(emoji + " " + nome + " sta elaborando " + articoli + " articoli... (" + tempoPerArticolo + "ms per articolo)");
                // simula tempo di elabirazione
                Thread.sleep(articoli * tempoPerArticolo);


                // aggiorna tutte le statistiche
                // output fine cliente
                statistiche.aggiungiCliente(articoli);
                System.out.println(" ");
                System.out.println("✅ " + nome + " ha terminato il cliente con " + articoli + " articoli.");

            } catch (InterruptedException e) {
                System.out.println(nome + " ha riscontrato un errore: " + e.getMessage());
            }
        }
    }
}
