package TSPGeneticAlgorithm.Models;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * @description data representation of a chromosome
 * */

public class Route implements Comparator<Route> {


    /**
     * @description Content of the chromosome
     * */
    private ArrayList<City> cities = new ArrayList<>();
    /**
     * @description Total distance of the chromosome
     * */
    private double totalDistance;
    /**
     * @description Fitness of the chromosome
     * */
    private double fitness;
    /**
     * @description Generation Id of the chromosome
     * */
    private int generationId;
    /**
     * @description Thread handler Id of the chromosome
     * */
    private int threadId;




    /**
     * @description Constructor of chromosome
     * @param cities : content of the chromosome inorder
     * */
    public Route(ArrayList<City> cities){
        //add cities
        this.cities.addAll(cities);
        //Calculate Total Distance
        calculateTotalDistance();
        //Calculate Fitness
        calculateFitness();
        //set total distance and fitness
        this.totalDistance = getTotalDistance();
        this.fitness = getFitness();
    }
    /**
     * @description Constructor of chromosome
     * @param cities : content of the chromosome inorder
     * @param fitness : fitness of the chromosome
     * @param totalDistance : total distance of the chromosome
     * @param threadId : thread ID
     * @param generationId : Generation ID
     * */
    public Route(ArrayList<City> cities, double fitness , double totalDistance , int generationId , int threadId){
        //add cities
        this.cities.addAll(cities);
        //set total distance
        this.totalDistance = totalDistance;
        //set fitness
        this.fitness = fitness;
        //set generation id
        this.generationId = generationId;
        //set thread id
        this.threadId = threadId;
    }
    /**
     * @description calculate total distance of the route including the distance back to the starting point
     * */
    private void calculateTotalDistance(){
        double totalDistance = 0.0;
        for (int i =0; i<this.cities.size();++i){
            if(i != cities.size() - 1 ){
                totalDistance += this.cities.get(i).measureDistance(this.cities.get(i+1));
            }
            else{
                totalDistance += this.cities.get(i).measureDistance(this.cities.get(0));
                this.totalDistance = totalDistance;
            }
        }
        this.totalDistance = totalDistance;
    }
    /**
     * @description calculate fitness of the route
     * */
    private void calculateFitness(){
        this.fitness = 1 / this.totalDistance * 100;
    }
    /**
     * @description total distance getter
     * */
    public double getTotalDistance(){return this.totalDistance;}
    /**
     * @description fitness getter
     * */
    public double getFitness(){return this.fitness;}
    /**
     * @description cities getter
     * */
    public ArrayList<City> getCities(){
        return this.cities;
    }
    /**
     * @description print route inorder
     * */
    public String toString(){
        String str = "";
        for (int i =0; i < this.cities.size(); ++i){
            str = str + this.cities.get(i).getName() +"=>";
        }
        str = str + this.cities.get(0).getName();
        return str;
    }

    /**
     * @description Override compare function from Comparator interface
     **/
    @Override
    public int compare(Route o1, Route o2) {
        return 0;
    }
    /**
     * @description generation id getter
     * */
    public int getGenerationID() {
        return this.generationId;
    }
    /**
     * @description thread id getter
     * */
    public int getThreadId(){
        return this.threadId;
    }
}
