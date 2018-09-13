import java.util.*;

public class Parser {

    public static final String FILES_KEY = "files";
    public static final String INTEGER_DATA_KEY = "-i";
    public static final String STRING_DATA_KEY = "-s";
    public static final String DESCENDING_SORT_KEY = "-d";
    public static final String ASCENDING_SORT_KEY = "-a";
    private Map<String, List<String>> options;

    public Parser() {
        options = new TreeMap<>();
        options.put(FILES_KEY, new ArrayList<>());
    }

    public void parse(String[] args) {
        for (String argument : args) {
            if (argument.startsWith("-")) {
                options.put(argument, null);
            } else {
                options.get(FILES_KEY).add(argument);
            }

        }
    }
    public Comparator switchSort() {
        Comparator sortTypeObject;
        if (options.containsKey(ASCENDING_SORT_KEY) || options.containsKey(DESCENDING_SORT_KEY) == false) {
            sortTypeObject = new Ascending();
        } else {
            sortTypeObject = new Descending();
        }
        return sortTypeObject;
    }
    public Class switchType() {
        Class dataTypeClass;
        if (options.containsKey(INTEGER_DATA_KEY)) {
            dataTypeClass = Integer.class;
        } else {
            dataTypeClass = String.class;
        }
        return dataTypeClass;
    }
    public boolean confirmOptions() {
        if ((options.containsKey("-d") & options.containsKey("-a")) ||
                (options.containsKey("-i") & options.containsKey("-s")) ||
                (options.containsKey("-s") || options.containsKey("-i")) == false) {
            return false;
        } else return true;
    }

    public Map<String, List<String >> getOptions(){
        return options;
    }
}
