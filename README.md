# Hellcat Simulation

## Descrizione

Questo programma simula il funzionamento di un supermercato con più casse. I clienti entrano nel supermercato, fanno la spesa e poi si mettono in coda alla cassa con la coda più corta. Ogni cassa elabora i clienti uno alla volta, aggiornando le statistiche sul numero di clienti serviti e il numero totale di articoli elaborati.

## Struttura del Progetto

Il progetto è composto dai seguenti file principali:

- `Main.java`: Il punto di ingresso del programma. Avvia la simulazione con un numero specificato di clienti e casse.
- `Hellcat.java`: Gestisce la creazione delle casse e l'avvio della simulazione.
- `Cassa.java`: Rappresenta una cassa del supermercato. Implementa l'interfaccia `Runnable` per elaborare i clienti in un thread separato.
- `Cliente.java`: Rappresenta un cliente del supermercato. Implementa l'interfaccia `Runnable` per simulare il comportamento del cliente in un thread separato.
- `StatisticheCassa.java`: Tiene traccia delle statistiche per ogni cassa, come il numero di clienti serviti e il numero totale di articoli elaborati.

## Funzionamento

1. **Inizializzazione**: Il programma crea un numero specificato di casse, ciascuna con una velocità di elaborazione diversa.
2. **Avvio della Simulazione**: I clienti vengono creati e avviati in thread separati. Ogni cliente:
   - Entra nel supermercato.
   - Simula il tempo necessario per fare la spesa.
   - Si mette in coda alla cassa con la coda più corta.
3. **Elaborazione dei Clienti**: Ogni cassa elabora i clienti uno alla volta, aggiornando le statistiche e simulando il tempo di elaborazione in base al numero di articoli.
4. **Statistiche Finali**: Al termine della simulazione, vengono mostrate le statistiche di ogni cassa.

## Esecuzione del Programma

Per eseguire il programma, compila ed esegui il file `Main.java`. Puoi specificare il numero di clienti e casse modificando le variabili `numClienti` e `numCasse` nel metodo `main`.

```java
public class Main {
    public static void main(String[] args) {
        int numClienti = 15; // Numero di clienti
        int numCasse = 3;    // Numero di casse

        Hellcat supermercato = new Hellcat(numCasse);
        supermercato.avviaSimulazione(numClienti);
    }
}
