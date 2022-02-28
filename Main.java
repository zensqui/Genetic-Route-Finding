//import java.util.Scanner;//Scanner import for 'enter' acceptance
//import java.time.Duration;
import java.util.ArrayList;//ArrayList import to keep track of Organisms
import java.util.Arrays;
import java.util.Collections;

class Main { 
    // initializing cities in two dimensional array as a literal 
                            // X0   A1   B2   C3   D4   E5   F6   G7   H8   I9   J10  K11  L12  M13  N14  O15  P16  Q17  R18  S19  T20
    static int[][] cities = { {0,   94,  76,  141, 91,  60,  120, 145, 91,  74,  90,  55,  145, 108, 41,  49,  33,  151, 69,  111, 24},//  X0
                              {94,  0,   156, 231, 64,  93,  108, 68,  37,  150, 130, 57,  233, 26,  62,  140, 61,  229, 120, 57,  109},// A1
                              {76,  156, 0,   80,  167, 133, 124, 216, 137, 114, 154, 100, 141, 161, 116, 37,  100, 169, 49,  185, 84},//  B2
                              {141, 231, 80,  0,   229, 185, 201, 286, 216, 139, 192, 178, 113, 239, 182, 92,  171, 155, 128, 251, 137},// C3
                              {91,  64,  167, 229, 0,   49,  163, 65,  96,  114, 76,  93,  200, 91,  51,  139, 72,  185, 148, 26,  92},//  D4
                              {60,  93,  133, 185, 49,  0,   165, 115, 112, 65,  39,  91,  151, 117, 39,  99,  61,  139, 128, 75,  49},//  E5
                              {120, 108, 124, 201, 163, 165, 0,   173, 71,  194, 203, 74,  254, 90,  127, 136, 104, 269, 75,  163, 144},// F6
                              {145, 68,  216, 286, 65,  115, 173, 0,   103, 179, 139, 123, 265, 83,  104, 194, 116, 250, 186, 39,  152},// G7
                              {91,  37,  137, 216, 96,  112, 71,  103, 0,   160, 151, 39,  236, 25,  75,  130, 61,  239, 95,  93,  112},// H8
                              {74,  150, 114, 139, 114, 65,  194, 179, 160, 0,   54,  127, 86,  171, 89,  77,  99,  80,  134, 140, 50},//  I9
                              {90,  130, 154, 192, 76,  39,  203, 139, 151, 54,  0,   129, 133, 155, 78,  117, 99,  111, 159, 101, 71},//  J10
                              {55,  57,  100, 178, 93,  91,  74,  123, 39,  127, 129, 0,   199, 61,  53,  91,  30,  206, 63,  101, 78},//  K11
                              {145, 233, 141, 113, 200, 151, 254, 265, 236, 86,  133, 199, 0,   251, 171, 118, 176, 46,  182, 226, 125},// L12
                              {108, 26,  161, 239, 91,  117, 90,  83,  25,  171, 155, 61,  251, 0,   83,  151, 75,  251, 119, 81,  127},// M13
                              {41,  62,  116, 182, 51,  39,  127, 104, 75,  89,  78,  53,  171, 83,  0,   90,  24,  168, 99,  69,  49},//  N14
                              {49,  140, 37,  92,  139, 99,  136, 194, 130, 77,  117, 91,  118, 151, 90,  0,   80,  139, 65,  159, 50},//  O15
                              {33,  61,  100, 171, 72,  61,  104, 116, 61,  99,  99,  30,  176, 75,  24,  80,  0,   179, 76,  86,  52},//  P16
                              {151, 229, 169, 155, 185, 139, 269, 250, 239, 80,  111, 206, 46,  251, 168, 139, 179, 0,   202, 211, 128},// Q17
                              {69,  120, 49,  128, 148, 128, 75,  186, 95,  134, 159, 63,  182, 119, 99,  65,  76,  202, 0,   161, 90},//  R18
                              {111, 57,  185, 251, 26,  75,  163, 39,  93,  140, 101, 101, 226, 81,  69,  159, 86,  211, 161, 0,   115},// S19
                              {24,  109, 84,  137, 92,  49,  144, 152, 112, 50,  71,  78,  125, 127, 49,  50,  52,  128, 90,  115, 0}//    T20
                            };
    static int Length = cities.length;//# of cities
    // n = 7 => 7! = 5040/2 = 2520 possibilities. Solution: 3 2 6 1 7 4 5 => 141 80 124 108 68 65 49 60 (695)
    /*LEADERBOARD:
        1060 14 16 11 18 6 8 13 1 7 19 4 5 10 9 17 12 3 2 15 20
        1099 20 15 2 3 12 17 9 10 5 14 16 4 19 7 1 13 8 11 6 18
        1112 16 8 13 1 7 19 4 5 10 9 17 12 3 15 2 18 6 11 14 20
    */
    //CANCEL DUPLICATE BACKWARD ROUTES -
//-----------------------------------------------------------------------------------------------//
    //static String seed = "32r723rFGYs";//Random # gen. seed
    static int popSize = 150; // (200) size of initial generation; max is summation of mating pool assuming entirely unique pairings for new gen.
    static int select = 75;  // (0.5popSize) size of mating pool
    static int eliteNum = 1;   // (1) # of elites (best fitness) transferred to next generation
    static double crossoverFraction = 0.5; // (0.5) fraction of crossover children as opposed to mutation children of remaining population
    //static int sublength = 4;//sub-sequence length for order crossover (or min/max)
        static int subMax = 1;// (1) Maximum sub-sequence length for order crossover (0~(x-1))
        static int subMin = 1;// (1) Minimum sub-sequence length for order crossover (y~x+y)
        //or.. //int crossovers  //# of points in crossover (1, 2 ... k-point crossover). Must be less than # of cities
    //static int mutations = 3;//# of swap mutations for mutants... OR, implement %mutation per city
            static double mutationChance = 0.1;// (0.1) Chance for mutations calulated per city to occur
            static double weight = 1.15;// (1.15) factor by which fitness impacts probability of selection
// ---- Termination Conditions ---- //
    static int period = 75;// (500)# of generations before a stagnancy check
        static int difference = 1;//fitness must have changed more than this from last generation to not terminate loop
//static boolean linearStop = true;
        //static int stopGeneration = 120000;//# of gens. before stop
        //static int stopMinFit = 1;//Fitness found to terminate
static int cycles = 100;
//static int cluster = 500;//range for cluster termination (added to least possible fitsum)
//-----------------------------------------------------------------------------------------------//
static ArrayList<int[]> generation = new ArrayList<int[]>();//current generation
static ArrayList<int[]> elites = new ArrayList<int[]>();//elites to add to new generation
static ArrayList<int[]> matingPool = new ArrayList<int[]>();//selected generation
static ArrayList<Integer> pastElites = new ArrayList<Integer>();//selected generation
static ArrayList<int[]> pastElitesRoute = new ArrayList<int[]>();//selected generation
static int[] allFit = new int[popSize];//respective fitness of generation
static float fitSum = 0;//Sum of all fitness
static int min = 0;//minimum fit value



    public static void main(String[] args) {//Main

    //Initialization - genrate random select of x cities; brute solve to act as stems for first generation. - do random (1/1) for now, //hungry ants algorithim
    long startTime = System.currentTimeMillis();
        
        int generations = 0;
            int cycleTracker = 0;
        int pastfit = 0;
        int change = 0;
        boolean repeat = true;
        //double oldCrossOver = crossoverFraction;
        //double oldMutationChance = mutationChance;
        //int oldEliteNum = eliteNum;
        boolean restart = true;
        do {//Start of loop
            if (restart) {
                pastfit = 0;
                change = 0;
                generation = new ArrayList<int[]>();
                for (int i = 0; i<popSize; i++) {
                    int[] route =  generateRoute();//0, 0, 0, new ArrayList<Integer>(), new int[Length]);
                    /*
                    for (int x = 0; x<route.length; x++) {
                        System.out.print(" " + route[x]);
                    }
                    System.out.println(); */
                    generation.add(route);
                }
                restart = false;
            }
        //Evaluation - determining 'fitness' of each route
        //System.out.println("Generation: " + generations + " ; size: " + generation.size());
            evaluate();
            //Determining elites
            int[] eliteFit = allFit.clone();
            Arrays.sort(eliteFit);
            min = eliteFit[0];

            /*for (int i = 0; i < eliteFit.length; i++) {
               System.out.println(eliteFit[i] + " " + allFit[i]);
            }*/
            for (int i = 0; i < eliteNum; i++) {
                int index = getArrayIndex(allFit, eliteFit[i]);
                //System.out.println("Index: " + index);
                //System.out.println("Elite Fit: " + eliteFit[i]);
                elites.add(generation.get(index));
                    /*for (int x = 0; x < elites.get(i).length; x++) {
                        System.out.print(" " + elites.get(i)[x]);
                    }
                    System.out.println();*/
            }
                //System.out.println("                        Minimum distance is : " + min + " ; Fit sum: " + fitSum);
                //System.out.println("Route: " + Arrays.asList(allFit).indexOf(min));
                

        //Termination conditions
        if (generations%period == 0) {//every x generations, check if fitness changed
                    change = pastfit-min;
                        if (Math.abs(change) < difference) {
                            //Start new cycle
                                pastElites.add(min);
                                pastElitesRoute.add(elites.get(0).clone());
                                restart = true;
                                cycleTracker++;
                        } 
                    pastfit = min;
        }
        /*if ((min == stopMinFit || generations >= stopGeneration) && linearStop) {
            repeat=false;
        }*/
        if (generations%5000 == 0) {
            System.out.println("/");
        }
        if (cycleTracker >= cycles) {
            repeat = false;
        }
        //Selection - determining mating pool (roulette wheel for now) - PREVENT Duplicated selections, invoke eliteism? inpmlement SUS
            //generate roulette wheel selection from allFit as weights, subtract min?
            select();
        
            //Killing old generation
                generation = new ArrayList<int[]>();
                for (int i = 0; i < elites.size(); i++) {
                        generation.add(elites.get(i));

                    }
                    elites = new ArrayList<int[]>();
                    /*System.out.println("Elites in next gen: ");
                for (int i = 0; i < generation.size(); i++) {
                    for (int x = 0; x < generation.get(i).length; x++) {
                        System.out.print(generation.get(i)[x] + " ");
                    }
                    System.out.println(eliteFit[i]);
                }*/
        //Crossover/reproduction - new gen. made of unique pairings? mutations of initial children? //ENSURE each chromosome in mating pool used at least one
            crossover();//handles mutations aswell
        
            //Killing mating pool
                matingPool = new ArrayList<int[]>();
            //Killing fitness pool
                allFit = new int[popSize];
                fitSum = 0;
        generations++;
                //if (min == 1060) 
                //break;
        } while (repeat); //termination condition


        double pastEliteSum = 0;
        for (int i = 0; i< pastElites.size(); i++) {
            System.out.print(" " + pastElites.get(i));
            pastEliteSum+=pastElites.get(i);
        }
        System.out.println();
        System.out.println("Elite sum: " + pastEliteSum);
        double elites = pastElites.size();
        System.out.println("Elites: " + elites);
        pastEliteSum = (pastEliteSum/elites);
        ArrayList<Integer> eliteFit = new ArrayList<Integer>(); 
        for (int i =0; i<pastElites.size(); i++) {
            eliteFit.add(pastElites.get(i));
        }
        Collections.sort(pastElites);
        min = pastElites.get(0);
        int index = eliteFit.indexOf(min);
        long duration = System.currentTimeMillis() - startTime;
        double effeciency = 100000000.0/(((double)duration*pastEliteSum));
        System.out.println();
        System.out.println("Time elapsed: " + duration + " ; generations: " + generations + " ; effeciency: " + effeciency + " ; Avg. Elite: " + pastEliteSum + " ; minFitFound: " + min);
        System.out.print("minRoute: ");
                for (int x = 0; x<Length-1; x++) {
                    System.out.print(" " + pastElitesRoute.get(index)[x]);
                }
        //695
            /*
                        int fit = 0;
            for (int x = 0; x<route.length; x++) {
                fit+=route[x];
            }
        do {
                int rand = (int)(Math.random() * (colDest.size()));
                col = colDest.get(rand);
                    route[destinations] = cities[row][col];
                    destinations ++;
                    colDest.remove(Integer.valueOf(col));
                    row = col;
        } while (colDest.size() > 0);
            route[destinations] = cities[row][0];
                for (int i = 0; i<route.length; i++) {
                    System.out.println(route[i]);
                }
        */
            
    }

    public static int[] generateRoute() {//int row, int col, int destinations, ArrayList<Integer> colDest, int[] route) {//Method to generate route
        ArrayList<Integer> destinations = new ArrayList<Integer>();
        int[] route = new int[Length-1];
            for (int i = 1; i< Length; i++) {//Populating route with random valid destinations
                destinations.add(i);//adding # of cities
            }
        int city;
        int visit = 0;
                    //route[0] = 0;
                do {
                    int rand = (int)(Math.random() * (destinations.size()));
                    city = destinations.get(rand);
                    route[visit] = city;
                        visit ++;
                        destinations.remove(Integer.valueOf(city));
                } while (destinations.size() > 0);
                    //route[visit] = 0;
                    return route;
    }
    
    public static void evaluate() {
        for (int i = 0; i<popSize; i++) {
            int fit = 0;
            int row = 0;
            int col = 0;
            for (int x = 0; x<Length; x++) {
                if (x!=Length-1) {
                    col = generation.get(i)[x];
                    //System.out.print(" " + generation.get(i)[x]);
                } else {
                    col = 0;
                }
                fit+=cities[row][col];
                row = col;
            }
            fitSum+=fit;
            allFit[i]=fit;
            //System.out.println(" " + allFit[i]);
        }
    //int min = Collections.min(List.allFit);
    }

    public static void select() {//Method to select individuals for mating pool (Roulette)
                float curFit = 0;//Expectation value (probabilty)
                float totProb = 0;//Sum of all expectation values
                float prevProb = 0;//Incremental probabilty for each chromosome
                ArrayList<Float> roulette = new ArrayList<Float>();

                for (int x = 0; x<popSize; x++) {//Calculating sum of expectation values
                    curFit = (float) (allFit[x]-(min/weight) + 1);//Expectation val (route - (smallest route / range to reduce pressure) + 1 incase 0)
                    totProb+= (fitSum / curFit);//^
                }
                for (int x = 0; x<popSize; x++) {
                    curFit = (float) (allFit[x]-(min/weight)) + 1;//redundant
                    //System.out.print(" fit: " + allFit[x] + " ; exp: " + curFit);
                    prevProb += ((fitSum / curFit) / totProb);//incremental expectations
                    //System.out.print(" ; prob: %" + prevProb * 100);
                    //System.out.println();
                    roulette.add(prevProb);//adding to wheel
                }
                //System.out.println("totalProb: " + totProb);
                //System.out.println("Mating pool: ");
                for (int i = 0; i<select; i++) {//for # of selections for mating pool (mating pool size)...
                    double rand = Math.random();
                    for (int y = 0; y<roulette.size(); y++) {//looping through roulette (index corres. to main population)
                        float min;
                        if (y == 0) {//setting min to 0 to not exceed array
                            min = 0;
                        } else {//setting min to next probability
                            min = roulette.get(y-1);
                        }
                        if (rand < roulette.get(y) && rand > min) {//if less than next and greater than current probabilty range...
                                matingPool.add(generation.get(y));//adding to generation
                        }
                    }
                    /*for (int z = 0; z<Length-1; z++) {
                        System.out.print(" " + matingPool.get(i)[z]);
                    }
                    System.out.println();*/
                }
    }

    public static void crossover() {
        int crosses = (int)Math.round(crossoverFraction * (popSize-eliteNum));//# of crossover children
        int mutants = (popSize-eliteNum)-crosses;                             //# of mutant children
        //System.out.println("cross: " + crosses);
        //System.out.println("mutants: " + mutants);                             
        //int randomSpace = Length-sublength;
            for (int i = 0; i < crosses; i++) {
                //int[] mommy;  *Perhaps ensure at least one pairing by all parents within mating pool *before* switching to rand. select?
                //int[] daddy; = matingPool.g
                //if (crosses < matingPool.size()-1) {
                //    mommy= matingPool.get(crosses).clone();
                //}
                int rand2;
                int rand = (int)(Math.random() * (matingPool.size()));
                do {
                    rand2 = (int)(Math.random() * (matingPool.size()));
                } while (rand == rand2);
                int[] mommy = matingPool.get(rand).clone();
                int[] daddy = matingPool.get(rand2).clone();
                /*
                System.out.println("mommy: " );
                for (int x = 0; x<mommy.length; x++) {
                    System.out.print(" " + mommy[x]);                             
                }
                System.out.println(" ");
                System.out.println("daddy: " );
                for (int x = 0; x<daddy.length; x++) {
                    System.out.print(" " + daddy[x]);                             
                }
                System.out.println( );
                */
                    int sublength = (int)(Math.random() * (subMax)) + subMin;
                    int subIndex = (int)(Math.random() * (Length-sublength));
                    int[] mommySub = new int[sublength];
                    //System.out.println("mommy sub: ");
                    ArrayList<Integer> invalid = new ArrayList<Integer>();
                        for (int x = 0; x<sublength; x++) {
                            mommySub[x] = mommy[x+subIndex];
                            invalid.add(mommySub[x]);
                            //System.out.print(" " + mommySub[x]);
                        }
                    //System.out.println( );
                int[] child = new int[mommy.length];
                       ArrayList<Integer> validDaddy = new ArrayList<Integer>();
                        for (int c = 0; c < daddy.length; c++ ) {
                            if (!invalid.contains(daddy[c])) {
                                validDaddy.add(daddy[c]);
                            }
                        }
                        //System.out.println("child: ");
                        int z = 0;
                        for (int y = 0; y < daddy.length; y++) {
                            if (y >= subIndex && y < (sublength + subIndex)) {
                                child[y] = mommySub[y-subIndex];
                            } else {
                                child[y] = validDaddy.get(z);
                                z++;
                            }  
                            //System.out.print(" " + child[y]);
                        }
                        generation.add(child);
   
            }
            //Mutations,         //Swap mutations by chance%?
            for (int i = 0; i < mutants; i++) {
                //System.out.println("mutant: " + i);
                int[] parent = matingPool.get((int)(Math.random() * (matingPool.size()))).clone();
                /*System.out.println("parent: " );
                for (int x = 0; x<parent.length; x++) {
                    System.out.print(" " + parent[x]);                             
                }*/
                //System.out.println();
                    //int[] child;
                    //Could also do linear mutation implement with looping for # of mutations
                    for (int x = 0; x<parent.length; x++) {
                        double mutate = Math.random();
                        if (mutate < mutationChance) {
                            int pos1 = (int)(Math.random() * (parent.length));
                            int pos2; 
                            do {
                                pos2 = (int)(Math.random() * (parent.length));
                            } while (pos2 == pos1);
                            int temp = parent[pos1];
                            parent[pos1] = parent[pos2];
                            parent[pos2] = temp;
                        }
                    }
                    /*System.out.println("clone: " );
                    for (int x = 0; x<parent.length; x++) {
                        System.out.print(" " + parent[x]);                             
                    }*/
                    //System.out.println();
                    generation.add(parent);
                    /*System.out.println("clone in gen.: " );
                    for (int x = 0; x<Length-1; x++) {
                        System.out.print(" " + generation.get(generation.size()-1)[x]);                             
                    }
                    System.out.println();*/
            }
            /*
            for (int i = 0; i<generation.size(); i++) {
                for (int x = 0; x<Length-1; x++) {
                    System.out.print(" " + generation.get(i)[x]);                             
                }
            System.out.println(" idx "+ i);
            }
            System.out.println("generated new generation: " + generation.size());*/
    }

    public static int getArrayIndex(int[] arr, int value) {
        int k=0;
        for (int i = 0; i<arr.length; i++) {
            if (arr[i] == value) {
                k=i;
                break;
            }
        }
        return k;
    }
}
