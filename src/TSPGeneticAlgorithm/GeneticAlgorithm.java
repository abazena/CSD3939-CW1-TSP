package TSPGeneticAlgorithm;
import TSPGeneticAlgorithm.Models.City;
import TSPGeneticAlgorithm.Models.Generation;
import TSPGeneticAlgorithm.Models.Route;
import java.util.*;
import static java.util.Collections.rotate;

class GeneticAlgorithm implements Runnable{
    //the id of the thread handling this instance
    private int threadID;
    //the limit of generations that is thread can handle
    private int generationsLimit;
    //temp var to indicate the number if generations this thread has created
    private int currentGeneration;
    //initial parent one to be used by this instance
    private Route initialParentOne;
    //initial parent two to be used by this instance
    private Route initialParentTwo;

    /**
     * @description GeneticAlgorithm Constructor : this method performs Swap Mutation on a copy of the given param
     * @param threadID:int the id of the thread handling this instance
     * @param generationsLimit:int the limit of generations that this instance can generate
     * @param initialParentOne:Route
     * @param initialParentTwo:Route
     * @return Route: swap mutated route
     */
    GeneticAlgorithm(int threadID , int generationsLimit , Route initialParentOne , Route initialParentTwo){
        this.threadID = threadID;
        this.generationsLimit = generationsLimit;
        this.currentGeneration = 0;
        this.initialParentOne = initialParentOne;
        this.initialParentTwo = initialParentTwo;
    }

    /**
     * @description swapMutation: this method performs Swap Mutation on a copy of the given param
     * @param route:Route instance to swap mutate
     * @return Route: swap mutated route
     */
    private Route swapMutation(Route route){
        //Clone (ArrayList<City> cities) from the given param Route route
        ArrayList<City> cities = new ArrayList<>(route.getCities());
        //get random number in range 0 to the size of the cities in the route - 1
        //select city one
        int randomNumber1 = getRandomNumberInRange(0 , cities.size() -1);
        //get random number in range 0 to the size of the cities in the route - 1
        //select city two
        int randomNumber2 = getRandomNumberInRange(0 , cities.size() -1);
        //ensure that the selected cities are not in the same index
        while (randomNumber1 == randomNumber2)randomNumber2 = getRandomNumberInRange(0 , cities.size() -1);
        //swap selected indexes
        Collections.swap(cities ,randomNumber1 , randomNumber2 );
        //return new route
        return new Route(cities);
    }
    /**
     * @description getRandomNumberInRange: this method generates a random int in the given range
     * @param min:int minimum of the range
     * @param max:int maximum of the range
     * @return int: random number in range from min to max
     */
    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        else{
            return new Random().nextInt((max - min) + 1) + min;
        }
    }
    /**
     * @description randomCrossover: this method performs random cross-over over the given param
     * @param routeOne:Route initial parent one to be used in the cross-over
     * @param routeTwo:Route initial parent Two to be used in the cross-over
     * @return Route
     */
    private Route randomCrossover(Route routeOne, Route routeTwo){
        //clone both lists of cities in the same order from both parents
        ArrayList<City> parentOne = new ArrayList<> (routeOne.getCities());
        ArrayList<City> parentTwo = new ArrayList<> (routeTwo.getCities());
        //temp var to be used later in the operation
        int routeSize = routeOne.getCities().size();

        //generate two random numbers between 0 and routeSize
        int randomNumber1 = getRandomNumberInRange(0 , routeSize -1);
        int randomNumber2 = getRandomNumberInRange(0 , routeSize -1);
        //ensure randomNumber1 is greater than randomNumber2
        while (randomNumber1 <= randomNumber2){
            randomNumber1 = getRandomNumberInRange(0 , routeSize -1);
            randomNumber2 = getRandomNumberInRange(0 , routeSize -1);
        }
        //init two lists to represent children
        ArrayList<City> childOne = new ArrayList<>();
        ArrayList<City> childTwo = new ArrayList<>();


        //copy elements between randomNumber1 and randomNumber2 from parentOne to child
        for (int i  = randomNumber1; i <= randomNumber2; i++){
            childOne.add(parentOne.get(i));
        }
        //add elements to childTwo from parentOne which are not in childOne
        for (int i = 0; i < routeSize; i++){
            //validate that it is not a duplicate
            if(!childOne.contains(parentOne.get(i))){
                childTwo.add(parentOne.get(i));
            }
        }
        //clone parentTwo and rotate the elements after randomNumber2 to bring them to the beginning of the list
        ArrayList<City> parentTwoClone = new ArrayList<>(parentTwo);
        rotate(parentTwoClone , routeSize - randomNumber2 - 1);

        //order elements in childTwo according to their order in parentTwo "Clone of parent two"
        for (int i =0; i<routeSize; i++){
            //validate that it is not a duplicate
            if(!childOne.contains(parentTwoClone.get(i))){
                childOne.add(parentTwoClone.get(i));
            }
        }
        return new Route(childOne);
    }

    /**
     * @description randomCrossover: this method performs split cross-over over the given param
     * first copy the first half of the first parent then copy the second then validate the route
     * doesn't contain duplicates and if it does fix the them
     * @param parentOneRoute:Route initial parent one to be used in the cross-over
     * @param parentTwoRoute:Route initial parent Two to be used in the cross-over
     * @return Route[]
     */
    private Route[] splitCrossOver(Route parentOneRoute , Route parentTwoRoute){
        //clone both lists of cities in the same order from both parents
        ArrayList<City> parentOne = parentOneRoute.getCities();
        ArrayList<City> parentTwo = parentTwoRoute.getCities();
        //temp var to be used later in the operation
        int routeSize = parentOne.size();
        //validate that both parents are of the same length
        if(parentOne.size() != parentTwo.size()){
            throw new IllegalArgumentException("both parents must be of the same size");
        }
        //temp var to be used later in the operation of splitting the parents
        int splitSize = (routeSize/2);
        //init list to be the children
        ArrayList<City> ChildOne = new ArrayList<>();
        ArrayList<City> ChildTwo = new ArrayList<>();
        //copy first half of each parent to children
        //copy from parent one to child one
        //copy from parent two to child two
        for (int i = 0 ; i <splitSize; i++){
            ChildOne.add(parentOne.get(i));
            ChildTwo.add(parentTwo.get(i));
        }
        //copy the second half of each parent to children
        //swap parents and children
        //copy from parent one to child two
        //copy from parent two to child one
        for (int i = splitSize  ; i <routeSize; i++){
            ChildOne.add(parentTwo.get(i));
            ChildTwo.add(parentOne.get(i));
        }
        //check for duplicates by init the lists as HashSet and then checking the length of the lists
        Set<City> ChildOneHashSet = new HashSet<City>(ChildOne);
        //if the size is not the same
        if(ChildOne.size() != ChildOneHashSet.size()){
            //temp ChildOne list to hold the unique values
            ArrayList<City> ChildOneTemp = new ArrayList<>();
            //loop for the size of child one
            for (int i = 0; i < ChildOne.size();i++){
                //if that value in the current index was not added to the temp List
                if(!ChildOneTemp.contains(ChildOne.get(i))){
                    //add the current index to the temp list
                    ChildOneTemp.add(ChildOne.get(i));
                }
                else{
                    //if it was classified as a duplicate
                    //find a replacement from parent one
                    //loop for the size of parent one
                        for (int x = 0; x < parentOne.size(); x++){
                            //check that the current index is not present in the child
                            if(!ChildOne.contains(parentOne.get(x))){
                                //add it to the same index of the duplicate found
                                ChildOne.set(i , parentOne.get(x));
                                break;
                            }
                        }

                }
            }
        }
        //check for duplicates by init the lists as HashSet and then checking the length of the lists
        Set<City> ChildTwoHashSet = new HashSet<City>(ChildTwo);
        //if the size is not the same
        if(ChildTwo.size() != ChildTwoHashSet.size()){
            //temp ChildOne list to hold the unique values
            ArrayList<City> ChildTwoTemp = new ArrayList<>();
            //loop for the size of child two
            for (int i = 0; i < ChildTwo.size();i++){
                //if that value in the current index was not added to the temp List
                if(!ChildTwoTemp.contains(ChildTwo.get(i))){
                    //add the current index to the temp list
                    ChildTwoTemp.add(ChildTwo.get(i));
                }
                else {
                    //if it was classified as a duplicate
                    //find a replacement from parent two
                    //loop for the size of parent two
                        for (int x = 0; x < parentTwo.size(); x++) {
                            //check that the current index is not present in the child
                            if (!ChildTwo.contains(parentTwo.get(x))) {
                                //add it to the same index of the duplicate found
                                ChildTwo.set(i, parentTwo.get(x));
                                break;
                            }
                        }
                }
            }

        }
        //return both children
        return new Route[]{new Route(ChildOne) , new Route(ChildTwo)};
    }
    /**
     * @description GeneticAlgorithmLogic: this method performs all Genetic Algorithms Logic by first making new copies of the parents
     * to ensure that they survive to next generation if no better Routes where generated then performs Genetic Algorithm methods
     * Random Cross-Over, Split Cross-Over and Swap Mutation
     * @param parentOne:Route initial parent one to be used in the creation of the generation
     * @param parentTwo:Route initial parent Two to be used in the creation of the generation
     * @return void
     */
    private void GeneticAlgorithmLogic(Route parentOne , Route parentTwo){

        Generation generation = new Generation(Main.getGenerationID() , this.threadID);
        //add initial parents to the generation
        generation.getPopulation().add(parentOne);
        generation.getPopulation().add(parentTwo);
        //implement swapMutation on both initial parents and add them to the generation
        Route parentOneSwapMutation = this.swapMutation(parentOne);
        Route parentTwoSwapMutation = this.swapMutation(parentTwo);
        generation.getPopulation().add(parentOneSwapMutation);
        generation.getPopulation().add(parentTwoSwapMutation);
        //implement randomCrossover on both swapMutated parents and add them to the generation
        generation.getPopulation().add(this.randomCrossover(
                parentOneSwapMutation ,
                parentTwoSwapMutation
        ));
        //implement splitCrossOver on initial parents
        Route[] splitCrossOverParents = splitCrossOver(parentOne , parentTwo);
        generation.getPopulation().add(splitCrossOverParents[0]);
        generation.getPopulation().add(splitCrossOverParents[1]);
        //implement randomCrossover on both initial parents and add them to the generation
        Route initialParentsRandomCrossover = this.randomCrossover(parentOne , parentTwo);
        generation.getPopulation().add(initialParentsRandomCrossover);
        //implement swapMutation on the child of the initial parents and add it to the generation
        generation.getPopulation().add(this.swapMutation(initialParentsRandomCrossover ));
        //sort generation by fitness
        //generation.sortByFitness();
        Generation finalGeneration = new Generation(generation.getId() , generation.getHandlerThreadID() , generation.getPopulation());
        //add generation to the generations list in the main class
        new Main().addGeneration(finalGeneration);
        //notifyAll();
        //recursive method call: pass the best two in the generation after checking that the thread hasn't exceeded the generation limit
        if (this.currentGeneration <= generationsLimit){
            //increment currentGeneration var
            this.currentGeneration++;
            //update the threadStatus var in the main thread
            new Main().setThreadSataus();
            try{
                //recall GeneticAlgorithmLogic method and pass the best two routes from the previous generation
                GeneticAlgorithmLogic(
                        finalGeneration.getPopulation().get(0),
                        finalGeneration.getPopulation().get(1) );
            }catch(Exception ex){}

        }

    }

    //call GeneticAlgorithmLogic method upon thread start
    @Override
    public void run(){
        this.GeneticAlgorithmLogic(this.initialParentOne , this.initialParentTwo);
    }
}
