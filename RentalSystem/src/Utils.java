// Liew Jiann Shen (1191100556)

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Utils {
    
    private Utils() {} // Avoid creating instances of this class

    // Rewrite csv file with new array UserType(1->Agent 2->Owner 3->Tenant)
    public static void writeCsvFile(String path, User user, char userType) throws IOException{

        List<String> data = Files.readAllLines(Paths.get(path));
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < data.size(); i++) {
            sb.append(data.get(i) + '\n');
        }

        sb.append(user.toString() + ',' + userType);
        Files.write(Paths.get(path), sb.toString().getBytes());
    } 

    // Add a new row to an csv file for User
    public static void writeCsvFile(String path, User user) throws IOException{

        List<String> data = Files.readAllLines(Paths.get(path));
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < data.size(); i++) {
            sb.append(data.get(i) + '\n');
        }

        sb.append(user.toString());
        Files.write(Paths.get(path), sb.toString().getBytes());
    } 

    // Add a new row to an csv file for Property
    public static void writeCsvFile(String path, String propertyData) throws IOException{

        List<String> data = Files.readAllLines(Paths.get(path));
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < data.size(); i++) {
            sb.append(data.get(i) + '\n');
        }
        sb.append(propertyData);
        Files.write(Paths.get(path), sb.toString().getBytes());
    }

    // Rewrite whole csv file from a list of data
    public static void writeCSvFile(String path, List list) throws IOException {
    
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + '\n');
        }

        Files.write(Paths.get(path), sb.toString().getBytes());
    }

        // Rewrite whole csv file for a list of pending property data
        public static void writeCsvPendingPropertyFile(String path, LinkedList<Property> propertyData) throws IOException {

            StringBuilder sb = new StringBuilder();
    
            for(int i = 0; i < propertyData.size(); i++) {
                sb.append(propertyData.get(i).getPendingProperty() + '\n');
            }
    
            Files.write(Paths.get(path), sb.toString().getBytes());
        }

    // Perform search for a specific column with custion delimeter
    public static String searchCsvFile(String path, int column, String search, String delimeter) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(delimeter);
            if(values[column].equals(search)) {
                reader.close();
                return values[column];
            }
        }
        reader.close();
        return null;
    }

    // Perform search for a specific column
    public static String searchCsvFile(String path, int column, String search) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if(values[column].equals(search)) {
                reader.close();
                return values[column];
            }
        }
        reader.close();
        return null;
    }

    // Perform search for two specific column
    public static String searchCsvFile(String path, int column, int column2, String search, String seach2) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if(values[column].equals(search) && values[column2].equals(seach2)) {
                reader.close();
                return values[column];
            }
        }
        reader.close();
        return null;
    }

}
