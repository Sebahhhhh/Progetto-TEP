package Supermercato;

public class StatisticheCassa {

    private final String nomeCassa;
    private final String emoji;
    private int clientiServiti;
    private int articoliTotali;

    // costruttore
    public StatisticheCassa(String nomeCassa, String emoji) {
        this.nomeCassa = nomeCassa;
        this.emoji = emoji;
        // fa partire le statistiche da ovviamente 0
        this.clientiServiti = 0;
        this.articoliTotali = 0;
    }
    // synchronized (che evita la concorrenza di questa parte)
    public synchronized void aggiungiCliente(int articoli) {
        clientiServiti++;
        articoliTotali += articoli;
    }

    // get e set
    public String getNomeCassa() {
        return nomeCassa;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getClientiServiti() {
        return clientiServiti;
    }

    public int getArticoliTotali() {
        return articoliTotali;
    }

    // to string
    public String toString() {
        return emoji + " " + nomeCassa + ": " +
                clientiServiti + " clienti serviti, " +
                articoliTotali + " articoli totali elaborati";
    }
}

