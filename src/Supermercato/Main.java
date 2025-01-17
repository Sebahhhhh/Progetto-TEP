package Supermercato;

public class Main {
    public static void main(String[] args) {

        // clienti totali
        int numClienti = 15;
        // casse totali
        int numCasse = 3;

        // avvia il programma
        Supermercato supermercato = new Supermercato(numCasse);
        supermercato.avviaSimulazione(numClienti);
    }
}

