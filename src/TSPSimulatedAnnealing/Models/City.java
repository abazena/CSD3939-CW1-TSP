package TSPSimulatedAnnealing.Models;

public class City {
    private double x;
    private double y;
    private String name;


    public City(double x, double y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }
    private double getX(){
        return this.x;
    }
    private double getY(){
        return this.y;
    }
    public String getName(){
        return this.name;
    }
    double measureDistance(City city) {

        return Math.sqrt(Math.pow((this.getX() - city.getX()), 2) + Math.pow((this.getY() - city.getY()), 2));

    }
}
