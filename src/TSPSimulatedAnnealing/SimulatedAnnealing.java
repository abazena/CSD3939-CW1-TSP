package TSPSimulatedAnnealing;

import TSPSimulatedAnnealing.Models.City;
import TSPSimulatedAnnealing.Models.Route;
import utils.HttpLoader;
import utils.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SimulatedAnnealing implements Runnable{
    private static double temperature = 1000000;
    private static double coolingRate = 0.0003;
    private ArrayList<City> data_set;

    public SimulatedAnnealing(ArrayList<City> data_set){
        this.data_set = new ArrayList<>(data_set);
    }
    private void SimulatedAnnealingLogic () throws IOException{
        ArrayList<City> cities = new ArrayList<>(this.data_set);
        //Collections.shuffle(cities , new Random(100));
        Route currentRoute = new Route(cities);
        currentRoute.generateRandomRoute();

        Route optimalRoute = new Route(currentRoute.getCities());
        while (temperature > 1){
            Route newOptimalRoute = new Route(currentRoute.getCities());
            int cityOneIndex = randomIntInRange(0 , newOptimalRoute.getCities().size() -1);
            int cityTwoIndex = randomIntInRange(0 , newOptimalRoute.getCities().size() -1);
            while (cityOneIndex == cityTwoIndex)cityTwoIndex = randomIntInRange(0 , newOptimalRoute.getCities().size() -1);

            City cityOne = newOptimalRoute.getCities().get(cityOneIndex);
            City cityTwo = newOptimalRoute.getCities().get(cityTwoIndex);

            newOptimalRoute.getCities().set(cityTwoIndex , cityOne);
            newOptimalRoute.getCities().set(cityOneIndex , cityTwo);

            newOptimalRoute.calculateTotalDistance();

            double initRouteDistance = currentRoute.getTotalDistance();
            double newRouteDistance = newOptimalRoute.getTotalDistance();
            double randomDouble = randomDoubleInRange();
            if(calculateAcceptanceProbability(initRouteDistance, newRouteDistance , temperature) > randomDouble){
                currentRoute = new Route(newOptimalRoute.getCities());
            }
            if(newOptimalRoute.getTotalDistance() < optimalRoute.getTotalDistance()){
                optimalRoute = new Route(newOptimalRoute.getCities());
            }
            temperature *= 1 - coolingRate;
        }
        Main.addSolution(optimalRoute);

    }
    private static double calculateAcceptanceProbability(double currentDistance, double newDistance , double temperature){
        if(newDistance < currentDistance)
            return 1.0;
        return Math.exp((currentDistance - newDistance) / temperature);
    }
    private static double randomDoubleInRange(){
        return new Random().nextInt(1000 )/ 1000.0;
    }
    private static int randomIntInRange(int min , int max){
        if(min > max){
            throw new IllegalArgumentException("max must be greater than min");
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    @Override
    public void run() {
        try {
            this.SimulatedAnnealingLogic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
