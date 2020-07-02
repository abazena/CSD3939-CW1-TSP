package TSPSimulatedAnnealing;
import TSPSimulatedAnnealing.Models.City;
import TSPSimulatedAnnealing.Models.Route;

import java.util.ArrayList;
import java.util.Date;


public class Main {
    private static double startTime;
    private static Route solution = new Route();
    private static int threadsLimit = 80;
    private static int threadStatus = 0;
    public  static ArrayList<City> data_set = new ArrayList<>();

    public static void main(String [] args) {
    //TODO Load data and parse it
        startTime =  getCurrentTimeStamp();
        for (int i = 0; i < threadsLimit ; i++){
            new Thread(new SimulatedAnnealing(data_set)).start();
        }
        while(threadsLimit > threadStatus){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Best Route: " + solution.toString());
        System.out.println("Best Route Distance: " + solution.getTotalDistance());
        double endTimeStamp = getCurrentTimeStamp();
        double diffInSeconds = (endTimeStamp - startTime) / 1000;
        System.out.println("Total Running Time: "+diffInSeconds);
    }
    static synchronized void addSolution(Route route){
        if(solution.getTotalDistance() > route.getTotalDistance()){
            solution = new Route(route.getCities());
            solution.calculateTotalDistance();
        }
        threadStatus++;
    }
    private static long getCurrentTimeStamp(){
        Date date= new Date();
        return date.getTime();
    }
}
