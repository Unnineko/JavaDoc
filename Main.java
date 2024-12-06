import java.io.*;
import java.util.*;

/**
 * Главный класс приложения, который отвечает за чтение мест из файла, * сортировку мест и поиск подходящих мест.
 */
public class Main {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        List<int[]> places = readPlacesFromFile("C:\\Users\\umine\\text.txt");
        if (places.isEmpty()) {
            System.out.println("Номеров мест не найдено.");
            return;
        }
        sortPlaces(places);
        findAndPrintSuitableSeats(places);
    }

    /**
     * Читает места из указанного файла.
     *
     * Файл должен содержать количество строк в первой строке, а затем
     * строки с двумя целыми числами, представляющими номер ряда и номер места.
     *
     * @param filename путь к файлу для чтения
     * @return список мест, представленный массивами целых чисел
     */
    private static List<int[]> readPlacesFromFile(String filename) {
        List<int[]> places = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(filename))) {
            String firstLine = bf.readLine();
            if (firstLine == null || firstLine.trim().isEmpty()) {
                System.out.println("Файл пуст или первая строка отсутствует.");
                return places;
            }

            int countRow = Integer.parseInt(firstLine.trim());
            for (int i = 0; i < countRow; i++) {
                String line = bf.readLine();
                if (line == null || line.trim().isEmpty()) {
                    System.out.println("Строка " + (i + 1) + " пуста.");
                    continue; // Пропускаем пустые строки
                }

                String[] temp = line.trim().split(" ");
                if (temp.length < 2) {
                    System.out.println("Недостаточно данных в строке " + (i + 1));
                    continue; // Пропускаем строки с недостаточными данными
                }

                try {
                    places.add(new int[]{Integer.parseInt(temp[0]), Integer.parseInt(temp[1])});
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка формата в строке " + (i + 1) + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        return places;
    }

    /**
     * Сортирует список мест по номерам рядов и местам.
     *
     * Места сортируются по возрастанию номера ряда и по убыванию номера места.
     *
     * @param places список мест для сортировки
     */
    private static void sortPlaces(List<int[]> places) {
        places.sort(Comparator.comparingInt((int[] a) -> a[0])
                .thenComparing((int[] a) -> a[1], Comparator.reverseOrder()));
    }

    /**
     * Находит и выводит подходящие места.
     *
     * Подходящие места определяются как места, которые идут подряд с пропуском
     * (например, если место 1 и место 4 в одном ряду).
     *
     * @param places список мест для поиска подходящих мест
     */
    private static void findAndPrintSuitableSeats(List<int[]> places) {
        List<String> suitableSeats = new ArrayList<>();
        for (int i = 1; i < places.size(); i++) {
            // Проверяем, если места идут подряд с пропуском
            if (places.get(i)[0] == places.get(i - 1)[0] && places.get(i)[1] - places.get(i - 1)[1] == -3) {
                int maxRow = places.get(i)[0];
                int minPlace = places.get(i)[1] + 1; // Следующее место
                suitableSeats.add(maxRow + " " + minPlace);
            }
        }

        // Выводим подходящие места
        if (suitableSeats.isEmpty()) {
            System.out.println("Подходящих мест не найдено.");
        } else {
            System.out.println("Подходящие места:");
            for (String seat : suitableSeats) {
                System.out.println(seat);
            }
        }
    }
}