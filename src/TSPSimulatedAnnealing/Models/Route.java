package TSPSimulatedAnnealing.Models;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Route {

    private ArrayList<City> cities = new ArrayList<>();
    private double totalDistance;
    public Route(ArrayList<City> cities){
        //Collections.shuffle(cities);
        this.cities.addAll(cities);
        calculateTotalDistance();
        this.totalDistance = getTotalDistance();
    }
    public Route(){
        this.totalDistance = 1000000000;
    }
    public void generateRandomRoute(){
        Collections.shuffle(this.cities , new Random(100));
        calculateTotalDistance();
    }
    public void calculateTotalDistance(){
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
    public double getTotalDistance(){return this.totalDistance;}
    public ArrayList<City> getCities(){
        return this.cities;
    }
    public String toString(){
        String str = "";
        for (int i =0; i < this.cities.size(); ++i){
            str = str + this.cities.get(i).getName() +"=>";
        }
        str = str + this.cities.get(0).getName();
        return str;
    }

}
