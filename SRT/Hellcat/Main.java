package Hellcat;

public class Main {
    public static void main(String[] args) {

        // clienti totali
        int numClienti = 15;
        // casse totali
        int numCasse = 3;

        // avvia il programma
        // lo avvia in base a quanti clienti e quante casse noi decidiamo di mettere
        // si potrebbe anche fare in modo che questi dati vengano chiesti all'utente a programma gia avviato.

        // import java.util.Scanner;
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Inserisci il numero di clienti:");
        // numClienti = scanner.nextInt();
        // System.out.println("Inserisci il numero di casse:");
        // numCasse = scanner.nextInt();


        Supermercato supermercato = new Supermercato(numCasse);
        supermercato.avviaSimulazione(numClienti);
    }
}

