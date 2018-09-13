import java.io.*;
import java.util.*;



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
/*    private static final String INTEGER_DATA_KEY = "-i";
    private static final String STRING_DATA_KEY = "-s";
    private static final String DESCENDING_SORT_KEY = "-d";
    private static final String ASCENDING_SORT_KEY = "-a";*/



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

    private static <T> List<T> writeFile(List<T> list, String filePath)  { //запись отсортированного
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

    private static <T> List<T> mergeFiles(List<T> list, Class<T> dataType) { //слияние файлов
        List<T> mainList;
        List<T> iterList;
        mainList = readFile((String)list.get(0), dataType);
        if (list.size() == 2) return mainList;
        for (int i = 1  ; i < list.size() - 1; i++) {
            iterList = readFile((String) list.get(i), dataType);
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

    public Comparator switchSort(Parser parser) { //выбор типа сортировки
        Comparator sortTypeObject;
        if (parser.getOptions().containsKey(Parser.ASCENDING_SORT_KEY) ||
                parser.getOptions().containsKey(Parser.DESCENDING_SORT_KEY) == false) {
            sortTypeObject = new Ascending();
        } else {
            sortTypeObject = new Descending();
        }
        return sortTypeObject;
    }
    public Class switchType(Parser parser) { //выбор типа данных
        Class dataTypeClass;
        if (parser.getOptions().containsKey(Parser.INTEGER_DATA_KEY)) {
            dataTypeClass = Integer.class;
        } else {
            dataTypeClass = String.class;
        }
        return dataTypeClass;
    }
    public static void main(String[] args) {
        Parser parser = new Parser();
        MergerSort mergerSort = new MergerSort();
        parser.parse(args);
        if (parser.confirmOptions() == false) {
            System.out.println("Arguments entered incorrectly");
            return;
        }
/*        mergerSort.switchType(parser);
        mergerSort.switchSort(parser); */

/*        Comparator sortTypeObject;
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

        }*/

/*        String dataType = args[1];
        Class dataTypeClass;
        switch (dataType) {
            case INTEGER_DATA_KEY:
                dataTypeClass = Integer.class;
                break;
            case STRING_DATA_KEY:
                dataTypeClass = String.class;
                break;
            default:
                parser.getOptions();
                System.out.println("Enter dataType");
                return;
        }*/

        writeFile((mergeSort(mergeFiles(parser.getOptions().get(Parser.FILES_KEY),
                mergerSort.switchType(parser)), mergerSort.switchSort(parser), mergerSort.switchType(parser))),
                parser.getOptions().get(Parser.FILES_KEY).get(parser.getOptions().get(Parser.FILES_KEY).size() - 1));
    }
}
