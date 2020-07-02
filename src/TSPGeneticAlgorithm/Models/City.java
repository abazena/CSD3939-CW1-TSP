package TSPGeneticAlgorithm.Models;

public class City {
    /**
     * @description X coordinate of the city
     * */
    private double x;
    /**
     * @description Y coordinate of the city
     * */
    private double y;
    /**
     * @description Name of the city
     * */
    private String name;

    /**
     * @description Name of the city
     * */
    public City(double x, double y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * @description X getter
     * */

    private double getX(){
        return this.x;
    }
    /**
     * @description Y getter
     * */
    private double getY(){
        return this.y;
    }
    /**
     * @description City name getter
     * */
    public String getName(){
        return this.name;
    }
    /**
     * @description calculate the distance from this city to the city passed as a param
     * @param city: City the TO city
     * @return the distance from this city to the passed city in a double format
     *
     * */
    double measureDistance(City city) {

        return Math.sqrt(Math.pow((this.getX() - city.getX()), 2) + Math.pow((this.getY() - city.getY()), 2));

    }
}
