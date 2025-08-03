package Projects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class psychoQuiz {
    public static void main(String[] args) {

        String[] pytania = {"Czy człowiek może być uzależniony od własnych myśli?",
                "Czy każda emocja niesie za sobą jakąś informacje?",
                "Czy ignorowanie problemów emocjonalnych sprawia, że one znikają?",
                "Czy poczucie własnej wartości zależy wyłącznnie od opinii innych?",
                "Czy świadomość ciała pomaga zrozumieć własne emocje?",
                "Czy wypieranie traumatycznych doświadczeń jest skuteczną formą leczenia?",
                "Czy można nauczyć się rozpoznawać swoje mechanizmy obronne?",
                "Czy wszyscy ludzie doświadczają wewnętrznego krytyka?",
                "Czy życie w trybie 'autopilota' wpływa negatywnie na zdrowie psychiczne?",
                "Czy praktykowanie uważności 'mindfulness' może zmniejszyć stres?"
        };

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("questions.txt"))) {
            for (String p : pytania) {
                writer.write(p);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String[] pyt = new String[pytania.length];


        try (BufferedReader reader = new BufferedReader(new FileReader("questions.txt"))) {
            for (int i = 0; i < pyt.length; i++) {
                pyt[i] = reader.readLine();
                if (pyt[i] == null) {
                    pyt[i] = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String[] odpowiedzi = {"tak", "tak", "nie", "nie", "tak",
                "nie", "tak", "tak", "tak", "tak"};


        int[] indeksy = new int[pytania.length];
        for (int i = 0; i < indeksy.length; i++) {
            indeksy[i] = i;
        }

        Random rand = new Random();

        for (int i = indeksy.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = indeksy[i];
            indeksy[i] = indeksy[j];
            indeksy[j] = temp;
        }

        String imie;
        while (true) {
            imie = JOptionPane.showInputDialog("Podaj swoje imię");
            if (imie == null) {  // kliknięto Cancel albo zamknięto okno
                JOptionPane.showMessageDialog(null, "Test przerwany, spróbuj ponownie później!");
                return;  // natychmiast wyjdź z maina, zakończ program
            }
            imie = imie.trim();
            if (imie.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Proszę wpisać poprawnie imię");
                continue;  // pytaj ponownie
            }
            break;  // poprawne imię, wychodzimy z pętli
        }


        int licznik = 0;
        boolean przerwany = false;

        for (int i = 0; i < pytania.length; i++) {
            int index = indeksy[i];
            String odp = JOptionPane.showInputDialog(pytania[index]);

            if (odp == null) {
                JOptionPane.showMessageDialog(null, "Test przerwany, spróbuj ponownie pózniej!");
                przerwany = true;
                break;
            }

            odp = odp.trim().toLowerCase();

            if (!odp.equals("tak") && !odp.equals("nie")) {
                JOptionPane.showMessageDialog(null, "Odpowiedz musi brzmieć 'tak' lub 'nie'");
                i--; // powtórz to samo pytanie
                continue;
            }
            if (odp.equals(odpowiedzi[index])) {
                licznik++;
            }
        }

        if (!przerwany && licznik >= 0 && licznik <= 2) {
            JOptionPane.showMessageDialog(null, licznik + "/10pkt - brak zdolności do świadomego myślenia");
        } else if (!przerwany && licznik >= 3 && licznik <= 5) {
            JOptionPane.showMessageDialog(null, licznik + "/10pkt - niski poziom samoświadomości");
        } else if (!przerwany && licznik >= 6 && licznik <= 8) {
            JOptionPane.showMessageDialog(null, +licznik + "/10pkt - średni poziom samoświadomości");
        } else if (!przerwany && licznik >= 9 && licznik <= 10) {
            JOptionPane.showMessageDialog(null, +licznik + "/10pkt - wysoki poziom samoświadomości.");
        }

        LocalDate dzisiaj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String data = dzisiaj.format(formatter);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true))) {
            String linia = imie + " | " + licznik + "/10pkt | " + data;
            writer.write(linia);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}






