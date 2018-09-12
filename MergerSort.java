import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Ascending implements Comparator<Comparable> { //Компараторы для определения режима сортировки
    @Override
    public int compare(Comparable o1, Comparable o2) {
        return o1.compareTo(o2);
    }
}

class Descending implements Comparator<Comparable> {
    @Override
    public int compare(Comparable    o1, Comparable o2) {
        return -1 * o1.compareTo(o2);
    }
}

public class MergerSort {
    private static final String INTEGER_DATA_KEY = "-i";
    private static final String STRING_DATA_KEY = "-s";
    private static final String DESCENDING_SORT_KEY = "-d";
    private static final String ASCENDING_SORT_KEY = "-a";


    private static <T> List<T> readFile(String filePath, Class<T> dataType) { //Чтение из файла в список
        List<T> myList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Object value = line;
                if (dataType.equals(Integer.class)) {
                    value = Integer.valueOf(line);
                }
                myList.add((T) value);
            }
        } catch (IOException e) {
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("File has null line");
        }
        return myList;
    }

    private static <T> List<T> writeFile(List<T> list, String filePath) throws RuntimeException { //запись отсортированного
        try (BufferedWriter writer = new BufferedWriter(                                         //массива в выходной файл
                new OutputStreamWriter(
                        new FileOutputStream(filePath), "UTF-8"))) {
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i).toString());
                writer.write("\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static <T> List<T> merge(List<T> list1, List<T> list2, Comparator comparator) { //Слияние 2-ух частей разбитого массива
        int len_1 = list1.size(), len_2 = list2.size();
        int count_1 = 0, count_2 = 0;
        int len = len_1 + len_2;
        List<T> result = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if (count_2 < len_2 && count_1 < len_1) {
                if (comparator.compare(list1.get(count_1), list2.get(count_2)) > 0) result.add(list2.get(count_2++));
                else result.add(list1.get(count_1++));

            } else if (count_2 < len_2) {
                result.add(list2.get(count_2++));
            } else {
                result.add(list1.get(count_1++));
            }
        }
        return result;
    }

    private static <T> List<T> mergeFiles(String[] filesPath, Class<T> dataType) {
        int numberOfFiles = filesPath.length - 3;
        List<T> mainList;
        List<T> iterList;
        mainList = readFile(filesPath[3], dataType);
        if (filesPath.length == 4) return mainList;
        for (int i = 0; i < numberOfFiles - 1; i++) {
            iterList = readFile(filesPath[i + 4], dataType);
            mainList.addAll(iterList);
        }
            return mainList;

    }

    private static <T> List<T> mergeSort(List<T> list, Comparator comporator, Class<T> dataType ) { //Рекурсивная функция слияния
        int len = list.size();
        if (len < 2) return list;
        int mid = len / 2;
        return merge(mergeSort(list.subList(0, mid), comporator, dataType),
                mergeSort(list.subList(mid, len), comporator, dataType), comporator);

    }


    public static void main(String[] args) {
        String sortType = args[0];
        Comparator sortTypeObject;
        switch (sortType) {
            case DESCENDING_SORT_KEY:
                sortTypeObject = new Ascending();
                break;
            case ASCENDING_SORT_KEY:
                sortTypeObject = new Descending();
                break;
            default:
                sortTypeObject = new Ascending();
                break;

        }

        String dataType = args[1];
        Class dataTypeClass;
        switch (dataType) {
            case INTEGER_DATA_KEY:
                dataTypeClass = Integer.class;
                break;
            case STRING_DATA_KEY:
                dataTypeClass = String.class;
                break;
            default:
                System.out.println("Enter dataType");
                return;
        }
        System.out.println((mergeSort(mergeFiles(args, dataTypeClass), sortTypeObject, dataTypeClass)));
        writeFile((mergeSort(mergeFiles(args, dataTypeClass), sortTypeObject, dataTypeClass)), args[2]);



    }
}
