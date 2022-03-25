import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List; 

public class FileCsv {
    public static void checkFileExist(String filename){
        try{
            File file = new File(filename);

            if(file.createNewFile()){
                if(filename.equals("CustomerLog.csv")){
                    preloadCustomer(filename);
                }else if(filename.equals("ShopLog.csv")){
                    preloadShop(filename);
                }
 
                System.out.println("File is missing, new file(" + filename + ") is created"); 
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void preloadCustomer(String filename) throws IOException{
        List<String> lines = Arrays.asList(
            "1,0121112222,Ali,1,123",
            "2,0123456789,Abu,1,123",
            "3,0123456781,Banjamin,1,123",
            "4,0173649586,Jason,1,123",
            "5,0163789231,Cookiez,1,123",
            "6,0174659384,Test,1,123"    
            );
        Files.write(Paths.get(filename), lines);
    }

    private static void preloadShop(String filename)throws IOException{
        List<String> lines = Arrays.asList(
            "1,0388889999,Tesco,Siti,false",
            "2,0345678909,Giant,Ahmad,false",
            "3,0346583272,Sunshine,Muhammad,false",
            "4,0309128943,Family Mart,Alex,false"
            );
        Files.write(Paths.get(filename), lines);
    }

    public static void resetCustomerRecord(){
        try{
            preloadCustomer("CustomerLog.csv");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void resetShopRecord(){
        try{
            preloadShop("ShopLog.csv");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteAllFileContent(String filename){
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(filename);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        writer.print("");
        writer.close();
    }

    public static boolean isFileFound(String filename){
        File temp = new File(filename);
        boolean exist = temp.exists();

        return exist;
    }

    public static List<String> readFileCsv(String filename) throws IOException{
        List<String> result = Files.readAllLines(Paths.get(filename));
        return result;
    }

    public static void writeFileCsv(String filename, Object obj) throws IOException{
        List<String> previousData = readFileCsv(filename);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < previousData.size(); ++i){
            sb.append(previousData.get(i) + '\n');
        }
        sb.append(obj.toString());

        Files.write(Paths.get(filename), sb.toString().getBytes());
        //System.out.println("Data inserted"); //test
    }   

    public static void changeFileCsv(String filename, Object obj, int line) throws IOException{
        List<String> allData = readFileCsv(filename);
        allData.set(line - 1, obj.toString()); //change the value
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < allData.size(); ++i){
            sb.append(allData.get(i) + '\n');
        }

        Files.write(Paths.get(filename), sb.toString().getBytes());
    }

    public static String[] searchCsvResult(String filename, int column, String searchKeyword) throws IOException{
        String[] result = null;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while((line = reader.readLine()) != null){
            String[] values = line.split(",");
            if(values[column].equals(searchKeyword)){
                result = values;
            }
        }

        reader.close();
        return result;
    }

    //search for if the same data exist or not
    public static boolean searchCsvDataExist(String filename, int column, String searchKeyword) throws IOException{
        boolean result = false;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while((line = reader.readLine()) != null){
            String [] values = line.split(",");
            //System.out.println("Values = " + values[column]); //test
            if(values[column].equals(searchKeyword)){
                result = true;
                break;
            }
        }

        reader.close();
        return result;
    }

    public static int getRowNumber(String filename) throws IOException{
        int row = 0;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        while(reader.readLine() != null)
            ++row;
        
        reader.close();
        return row;
    }
}
