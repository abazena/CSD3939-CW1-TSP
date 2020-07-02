package TSPGeneticAlgorithm;
//import dev.filesystem.txt.TextLoader;
import TSPGeneticAlgorithm.Models.City;
import TSPGeneticAlgorithm.Models.Generation;
import TSPGeneticAlgorithm.Models.Route;
import utils.HttpLoader;
import utils.Parser;

import java.io.IOException;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main{

    private static double startTime;
    private static int getGenerationID = 0;
    private static ArrayList<Generation> generations = new ArrayList<>();
    private static int threadStatus= 0;
    public  static ArrayList<City>  data_set = new ArrayList<>();

    public static void main(String []args) throws IOException {

        ArrayList<City> cities = new ArrayList<>(data_set);


        startTime =  getCurrentTimeStamp();

        ArrayList<Route> initialPopulation = new ArrayList<>();

        for (int i = 0; i < 500; i++){
            Collections.shuffle(cities , new Random(i*200));
            initialPopulation.add(new Route(cities));
        }
        Generation initialGeneration = new Generation(initialPopulation);
        int x = 0;
        int generationLimit = 100;
        int threadsLimit = 80;
        for(int i = 0; i <= threadsLimit; i++ ){
           new Thread(
                   new GeneticAlgorithm(i , generationLimit , initialGeneration.getPopulation().get(x), initialGeneration.getPopulation().get(x+1))
           ).start();

            x = x +2;
        }

        while(threadStatus <= (generationLimit * threadsLimit)){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            try{
                generations.sort(Comparator.comparingDouble(Generation::getId));
            }catch(Exception ex){

            }

        for (Generation generation : generations) {
            try{ generation.printGeneration(); }catch(Exception ex){}
        }
        printBestRoute(generations);

        double endTimeStamp = getCurrentTimeStamp();
        double diffInSeconds = (endTimeStamp - startTime) / 1000;
        System.out.println("Total Running Time: "+diffInSeconds);




    }
    private static void printBestRoute(ArrayList<Generation> finalGenerations){
        ArrayList<Route> bestRoutes = new ArrayList<>();
        try{
            for (int i =0 ; i < finalGenerations.size(); i++) {
                Route tempRoute = new Route(
                        finalGenerations.get(i).getPopulation().get(0).getCities(),
                        finalGenerations.get(i).getPopulation().get(0).getFitness(),
                        finalGenerations.get(i).getPopulation().get(0).getTotalDistance(),
                        finalGenerations.get(i).getId(),
                        finalGenerations.get(i).getHandlerThreadID()
                );
                bestRoutes.add(tempRoute);
            }
            bestRoutes.sort((lhs, rhs) -> Double.compare(rhs.getFitness(), lhs.getFitness()));

            System.out.println();
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Best route: " +bestRoutes.get(0).toString() +" total distance: " +bestRoutes.get(0).getTotalDistance() + " route fitness: " + bestRoutes.get(0).getFitness() + " Generation ID: " + bestRoutes.get(0).getGenerationID() + " thread ID: " +bestRoutes.get(0).getThreadId() );
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
        }catch(Exception ex){
            System.err.println("Error Printing The Best Route Please Check Manually ");
        }
    }
    static synchronized int getGenerationID(){
        getGenerationID += 1;
        return getGenerationID;
    }
    synchronized void setThreadSataus(){
        threadStatus++;
        notifyAll();
    }
    synchronized void addGeneration(Generation generation){
        generations.add(generation);
        notifyAll();
    }
    private static long getCurrentTimeStamp(){
        Date date= new Date();
        return date.getTime();
    }

}
/*
private static String getStrInput(){
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        return url;
    }
    private static int getInput(){
        Scanner scanner = new Scanner(System.in);
        try{
            int userOption = Integer.parseInt(scanner.nextLine());
            return userOption;
        }catch(Exception ex){
            System.err.println(ex);
            return -1;
        }
    }
    public static void print(Route route){
        route.toString();
        System.out.println(route.getTotalDistance());
        System.out.println(route.getFitness());
    }
GeneticAlgorithm ga = new GeneticAlgorithm();
        Collections.shuffle(cities ,  new Random(ga.getRandomNumberInRange(100 , 1000000)));
        Route routeOne = new Route(cities);
        Collections.shuffle(cities , new Random(ga.getRandomNumberInRange(1000000 , 1001000000)));
        Route routeTwo = new Route(cities);
        Route swapMutationRouteOne = ga.swapMutation(routeOne);
        Route swapMutationRouteTwo = ga.swapMutation(routeTwo);
        Route randomCrossoverRouteOne = ga.randomCrossover(routeOne , routeTwo);
        Route randomCrossoverRouteTwo = ga.randomCrossover(swapMutationRouteOne , swapMutationRouteTwo);
        Route swapMutationRouteOneChild = ga.swapMutation(randomCrossoverRouteOne);
        Route swapMutationRouteTwoChild = ga.swapMutation(randomCrossoverRouteTwo);
        Route randomCrossoverChildernRoutes = ga.randomCrossover(randomCrossoverRouteOne , randomCrossoverRouteTwo);


        ArrayList<Route> population = new ArrayList<>();
        population.add(routeOne);
        population.add(routeTwo);
        population.add(swapMutationRouteOne);
        population.add(swapMutationRouteTwo);
        population.add(randomCrossoverRouteOne);
        population.add(randomCrossoverRouteTwo);
        population.add(swapMutationRouteOneChild);
        population.add(swapMutationRouteTwoChild);
        population.add(randomCrossoverChildernRoutes);
        Generation temp = new Generation(0 , population);
        Generation temp1 = new Generation(1 , population);
        Generation temp2 = new Generation(2 , population);

        temp.printGeneration();
        temp1.printGeneration();
        temp2.printGeneration();
        ArrayList<Route> population1 = new ArrayList<>();
        for (int i = 0; i < 20000; i++){
            Collections.shuffle(cities , new Random(i*20));
            population1.add(new Route(cities));
            Route tempRoute = ga.randomCrossover(population1.get(i) , new Route(cities));
            tempRoute.setChildt(true);
            population1.add(tempRoute);
            System.out.println(population1.get(i+1).getTotalDistance());
        }
        Generation generation= new Generation(3 , population1);
        generation.printGeneration();
        System.out.println("--------------------------------------");
        System.out.println(generation.getPopulation().get(0).getTotalDistance());
        System.out.println(generation.getPopulation().get(0).getChildt());
        System.out.println(generation.getPopulation().get(1).getTotalDistance());
        System.out.println(generation.getPopulation().get(1).getChildt());
        System.out.println(generation.getPopulation().get(2).getTotalDistance());
        System.out.println(generation.getPopulation().get(2).getChildt());

 private static ArrayList<City> loadData()throws IOException{
        HttpLoader httpRequest = new HttpLoader();
        String url = "http://www.cwa.mdx.ac.uk/csd3939/coursework/test2atsp.txt"; //getStrInput();
        // String data = httpRequest.makeRequest(url , "UTF-8");
        Parser parser = new Parser();
        String data =  parser.loadTxt("datasets/dataset3.txt" , UTF_8);
        String[][] dataset = parser.parseArray2D(data , " ");
        ArrayList<City> cities = new ArrayList<>();
        for (String[] anArray : dataset) {
            double x = Double.parseDouble(anArray[1]);
            double y = Double.parseDouble(anArray[2]);
            cities.add(new City(x , y , anArray[0]));
        }
        return cities;
    }
    private static ArrayList<City> loadDataTxtFile() throws IOException {
        Parser parser = new Parser();
        String  data = parser.loadTxt("datasets/dataset2.txt" , UTF_8);
        String[][] dataset = parser.parseArray2D(data , " ");
        ArrayList<City> cities = new ArrayList<>();
        for (String[] anArray : dataset) {
            double x = Double.parseDouble(anArray[1]);
            double y = Double.parseDouble(anArray[2]);
            cities.add(new City(x , y , anArray[0]));
        }
        return cities;

    }
















System.out.println(
                "CSD3939 CW1 TSP GA By Abdu-arahman Bazena \n" +
                        "Please select: \n" +
                        "1- Use Test Data-set 1.\n" +
                        "2- Use Test Data-set 2.\n" +
                        "3- Use Test Data-set 3.\n" +
                        "4- Choose From File System.\n" +
                        "5- Load File Using URL\n" +
                        "6- Exit.");
        int userOption = getInput();
        switch (userOption) {
            case 1:  //monthString = "January";
                break;
            case 2:  //monthString = "February";
                break;
            case 3: // monthString = "March";
                break;
            case 4:
                System.out.println("Lunching File Chooser.");
                FileChooser fileChooser = new FileChooser();
                fileChooser.grabFocus();
                String filePath = fileChooser.selectFile("Select Data-Set" , "~/" , FileChooser.FILES_ONLY);
                System.out.println(filePath);
                break;
            case 5:
                System.out.println("Please enter url of data-set");
                HttpLoader httpRequest = new HttpLoader();
                String url = getStrInput();
                String data = httpRequest.makeRequest(url , "UTF-8");
                Parser parser = new Parser();
                String[][] dataset = parser.parseArray2D(data , " ");
                ArrayList<City> cities = new ArrayList<>();
                for (String[] anArray : dataset) {
                    double x = Double.parseDouble(anArray[1]);
                    double y = Double.parseDouble(anArray[2]);
                    cities.add(new City(x , y , anArray[0]));
                }
                GeneticAlgorithm ga = new GeneticAlgorithm();
                Collections.shuffle(cities ,  new Random(ga.getRandomNumberInRange(100 , 1000000)));
                Route routeOne = new Route(cities);
                System.out.println("Initial routeOne");
                print(routeOne);

                Collections.shuffle(cities , new Random(ga.getRandomNumberInRange(1000000 , 1001000000)));
                Route routeTwo = new Route(cities);
                System.out.println("Initial routeTwo");
                print(routeTwo);
                Route swapMutationRouteOne = ga.swapMutation(routeOne);

                System.out.println("swapMutationRouteOne");
                print(swapMutationRouteOne);

                Route swapMutationRouteTwo = ga.swapMutation(routeTwo);

                System.out.println("swapMutationRouteTwo");
                print(swapMutationRouteTwo);


                Route randomCrossoverRouteOne = ga.randomCrossover(routeOne , routeTwo);

                System.out.println("randomCrossover For routeOne and routeTwo Child #1");
                print(randomCrossoverRouteOne);

                Route randomCrossoverRouteTwo = ga.randomCrossover(swapMutationRouteOne , swapMutationRouteTwo);

                System.out.println("randomCrossover For swapMutationRouteOne and swapMutationRouteTwo Child #2");
                print(randomCrossoverRouteTwo);


                Route swapMutationRouteOneChild = ga.swapMutation(randomCrossoverRouteOne);

                System.out.println(" swapMutationRouteOneChild for Child #1");
                print(swapMutationRouteOneChild);


                Route swapMutationRouteTwoChild = ga.swapMutation(randomCrossoverRouteTwo);

                System.out.println(" swapMutationRouteTwoChild for Child #2");
                print(swapMutationRouteTwoChild);


                Route randomCrossoverChildernRoutes = ga.randomCrossover(randomCrossoverRouteOne , randomCrossoverRouteTwo);

                System.out.println("randomCrossoverChildernRoutes randomCrossover for both children");
                print(randomCrossoverChildernRoutes);



                break;
            case 6:
                System.out.println("Exiting");
                System.exit(0);
                break;
            default:
                System.err.println("Invalid Option.");
                break;
        }*/
