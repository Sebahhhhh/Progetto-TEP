package Hellcat;

import java.util.ArrayList;
import java.util.List;

public class Cassa implements Runnable {

    private final String nome;
    private final int tempoPerArticolo;
    private final String emoji;
    private final List<Integer> codaClienti;
    private final StatisticheCassa statistiche;
    // modifica fatta per aggiungere la persona che sta pagando alla cassa
    // variabile per tracciare se un cliente è in elaborazione
    private boolean clienteInElaborazione;

    // costruttore
    public Cassa(String nome, int tempoPerArticolo, String emoji) {
        this.nome = nome;
        this.tempoPerArticolo = tempoPerArticolo;
        this.emoji = emoji;
        this.codaClienti = new ArrayList<>();
        this.statistiche = new StatisticheCassa(nome, emoji);
        // questa nuova variabile è inizializzata a false
        this.clienteInElaborazione = false;
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

    // metodo per ottenere la lunghezza della coda, includendo il cliente in elaborazione
    public int getLunghezzaCoda() {
        synchronized (codaClienti) {
            // aggiunge 1 alla dimensione della coda se un cliente è in elaborazione
            int size = codaClienti.size();
            // if ClienteInElaborazione è true, aggiungi 1 alla dimensione della coda
            if (clienteInElaborazione) {
                size += 1;
            }
            return size;
        }
    }
    // get
    public StatisticheCassa getStatistiche() {
        return statistiche;
    }

    // metodo per aggiungere un cliente alla coda
    public void aggiungiCliente(int articoli) {
        // synchronized (codaClienti) per evitare la concorrenza
        synchronized (codaClienti) {
            // aggiunge il cliente alla coda
            codaClienti.add(articoli);
            // notifica i thread in attesa che un nuovo cliente è stato aggiunto
            codaClienti.notify();
        }
    }

    // metodo run avviato dal main
    public void run() {
        // ciclo while per elaborare i clienti
        while (true) {
            try {
                int articoli;
                synchronized (codaClienti) {
                    while (codaClienti.isEmpty()) {
                        // attende nuovi clienti se la coda è vuota
                        codaClienti.wait();
                    }
                    // rimuove il primo cliente dalla coda e imposta clienteInElaborazione a true
                    // serve a evitare che la coda risulti vuota, e quindi evita che una persona si metta in coda (che risulta vuota) ma in realtà c'è una persona in elaborazione
                    articoli = codaClienti.remove(0);
                    clienteInElaborazione = true;
                }
                System.out.println(" ");
                System.out.println(emoji + " " + nome + " sta elaborando " + articoli + " articoli... (" + tempoPerArticolo + "ms per articolo)");
                // simula il tempo di elaborazione (tempo cassa per il numero di articoli)
                Thread.sleep(articoli * tempoPerArticolo);

                // aggiorna le statistiche dopo aver elaborato il cliente
                statistiche.aggiungiCliente(articoli);
                // output
                System.out.println(" ");
                System.out.println("✅ " + nome + " ha terminato il cliente con " + articoli + " articoli.");

                synchronized (codaClienti) {
                    // imposta clienteInElaborazione a false dopo che l'elaborazione è completata
                    // così che poi, praticamente la coda scala e qualcunaltro si può mettere in elaborazione (in parole povere reinizia il ciclo)
                    clienteInElaborazione = false;
                }

            } catch (InterruptedException e) {
                System.out.println(nome + "errore: " + e.getMessage());
            }
        }
    }
}