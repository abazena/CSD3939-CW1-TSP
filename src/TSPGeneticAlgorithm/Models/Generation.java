package TSPGeneticAlgorithm.Models;

import java.util.ArrayList;

public class Generation {
    /**
     * @description the id of the generation
     * */

    private int id;
    /**
     * @description the id of the thread that handled this generation
     * */
    private int HandlerThreadID;
    /**
     * the population of the generation / the routes that have been made in this generation
     * */
    private ArrayList<Route> population;
    /**
     * @description Generation constructor that is only used for the initial generation
     * @param population: ArrayList of routes containing the population
     * */
    public Generation( ArrayList<Route> population){
        this.population = new ArrayList<>();
        this.population.addAll(population);
        this.population.sort((lhs, rhs) -> {
            return Double.compare(rhs.getFitness(), lhs.getFitness());
        });
    }
    /**
     * @description Generation constructor
     * @param id: the id if the generation
     * @param HandlerThreadID : the id of the thread handled this generation
     * @param population: ArrayList of routes containing the population
     * */
    public Generation(int id , int HandlerThreadID , ArrayList<Route> population){
        this.id = id;
        this.population = new ArrayList<>();
        this.population.addAll(population);
        this.HandlerThreadID = HandlerThreadID;
        this.population.sort((lhs, rhs) -> {
            return Double.compare(rhs.getFitness(), lhs.getFitness());
        });
    }
    /**
     * @description Generation constructor
     * @param id: generation id
     * @param HandlerThreadID: id of the thread that handled this generation
     * */
    public Generation(int id, int HandlerThreadID){
        this.population = new ArrayList<>();
        this.id = id;
        this.HandlerThreadID = HandlerThreadID;
    }
    /**
     * @description Generation id getter
     * @return id: id of the generation
     * */
    public int getId() { return this.id; }
    /**
     * @description handler thread id getter
     * @return id: id of the generation
     * */
    public int getHandlerThreadID() { return this.HandlerThreadID; }
    /**
     * @description Population getter
     * @return population: population of the generation
     * */
    public ArrayList<Route> getPopulation() { return population; }
    /**
     * @description print content of the generation in a format
     * */
    public void printGeneration(){
        final Object[][] table = new String[this.population.size()][];
        for (int i =0 ; i<table.length; i++){
            table[i] = new String[]{population.get(i).toString() , ""+population.get(i).getFitness() , ""+population.get(i).getTotalDistance()};
        }
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Generation # " +this.id + "  Handled by thread # " + this.HandlerThreadID);
        System.out.println("--------------------------------------------------------------------");
        String[] header = new String[]{"Route" , "Fitness" , "Total Distance"};
        System.out.format("|%15s\t|\t%15s\t\t|\t%15s\t\t|\n", header);
        System.out.println("--------------------------------------------------------------------");


        for (final Object[] row : table) {
            System.out.format("|%15s\t|\t%15s\t|\t%15s\t|\n", row);
        }
    }
}
