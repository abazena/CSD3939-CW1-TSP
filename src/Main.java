import utils.Parser;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws IOException {
            System.out.println("1. Run GA" +
                    "\n2. Run SA");
            String input = new Scanner(System.in).nextLine();
            if (input.equals("1")) {

                System.out.println("Please enter path to data-set");
                input = new Scanner(System.in).nextLine();
                ArrayList<TSPGeneticAlgorithm.Models.City> data_set = new ArrayList<>(loadGADataset(input));
                if (data_set.get(0) != null)
                    TSPGeneticAlgorithm.Main.data_set.addAll(data_set);
                TSPGeneticAlgorithm.Main.main(null);
            } else if (input.equals("2")) {
                System.out.println("Please enter path to data-set");
                input = new Scanner(System.in).nextLine();
                ArrayList<TSPSimulatedAnnealing.Models.City> data_set = new ArrayList<>(loadSADataset(input));
                if (data_set.get(0) != null)
                    TSPSimulatedAnnealing.Main.data_set.addAll(loadSADataset(input));
                TSPSimulatedAnnealing.Main.main(null);
            }

            else {
                System.err.println("Invalid Option");
            }





    }
    private static ArrayList<TSPGeneticAlgorithm.Models.City>  loadGADataset(String path){
        Parser parser = new Parser();
        String  data = null;
        try {
            data = parser.loadTxt(path , UTF_8);
        } catch (IOException e) {
            return null;
        }
        String[][] dataset = parser.parseArray2D(data , " ");
        ArrayList<TSPGeneticAlgorithm.Models.City> cities = new ArrayList<>();
        for (String[] anArray : dataset) {
            double x = Double.parseDouble(anArray[1]);
            double y = Double.parseDouble(anArray[2]);
            cities.add(new TSPGeneticAlgorithm.Models.City(x , y , anArray[0]));
        }
        return cities;
    }
    private static ArrayList<TSPSimulatedAnnealing.Models.City>  loadSADataset(String path){
        Parser parser = new Parser();
        String  data = null;
        try {
            data = parser.loadTxt(path , UTF_8);
        } catch (IOException e) {
            return null;
        }
        String[][] dataset = parser.parseArray2D(data , " ");
        ArrayList<TSPSimulatedAnnealing.Models.City> cities = new ArrayList<>();
        for (String[] anArray : dataset) {
            double x = Double.parseDouble(anArray[1]);
            double y = Double.parseDouble(anArray[2]);
            cities.add(new TSPSimulatedAnnealing.Models.City(x , y , anArray[0]));
        }
        return cities;
    }

}
