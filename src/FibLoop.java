import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class FibLoop {

    static ThreadMXBean bean = ManagementFactory.getThreadMXBean( );

    static int MAXINPUTSIZE  = 46;

    static int MININPUTSIZE  =  1;
    static long MAXVALUE = 2000;
    static long MINVALUE = -2000;
    static long numberOfTrials = 1000000;

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        int count = 7, num1 = 0, num2 = 1;
        //System.out.print("Fibonacci Series of "+count+" numbers:");

        for (int i = 1; i <= count; ++i)
        {
            //System.out.print(num1+" ");

            /* On each iteration, we are assigning second number
             * to the first number and assigning the sum of last two
             * numbers to the second number
             */
            int sumOfPrevTwo = num1 + num2;
            num1 = num2;
            num2 = sumOfPrevTwo;
        }
        int n = 9;
        System.out.println("Using a loop to calculate the " + (n + 1) + "th number of the fibonacci sequence: " + fibLoop(n));
        n = 20;
        System.out.println("Using a loop to calculate the " + (n + 1) + "th number of the fibonacci sequence: " + fibLoop(n));
        n = 42;
        System.out.println("Using a loop to calculate the " + (n + 1) + "th number of the fibonacci sequence: " + fibLoop(n));
        //System.out.println(fibLoop(8));

        //runFullExperiment("FibLoop-Exp1-ThrowAway.txt");

        //runFullExperiment("FibLoop-Exp2.txt");

        //runFullExperiment("FibLoop-Exp3.txt");
    }

    public static int fibLoop(int count){
        int fib = count;
        int num1 = 0;
        int num2 = 1;

        for (int i = 1; i <= fib; ++i) {

            int sumOfPrevTwo = num1 + num2;
            num1 = num2;
            num2 = sumOfPrevTwo;
        }
        return num2;
    }

    static void runFullExperiment(String resultsFileName){

        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);

            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);

            return; // not very foolproof... but we do expect to be able to create/open the file...

        }



        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial



        resultsWriter.println("#InputSize    AverageTime"); // # marks a comment in gnuplot data

        resultsWriter.flush();

        for(int inputSize=MININPUTSIZE;inputSize<=MAXINPUTSIZE; inputSize++) {

            // progress message...

            System.out.println("Running test for input size "+inputSize+" ... ");



            /* repeat for desired number of trials (for a specific size of input)... */

            long batchElapsedTime = 0;

            // generate a list of randomly spaced integers in ascending sorted order to use as test input

            // In this case we're generating one list to use for the entire set of trials (of a given input size)

            // but we will randomly generate the search key for each trial






            // instead of timing each individual trial, we will time the entire set of trials (for a given input size)

            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the

            // stopwatch methods themselves

            //BatchStopwatch.start(); // comment this line if timing trials individually



            // run the tirals

            for (long trial = 0; trial < numberOfTrials; trial++) {

                int n = inputSize;

                TrialStopwatch.start(); // *** uncomment this line if timing trials individually

                fibLoop(n);

                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually

            }

            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch



            /* print data for this size of input */

            resultsWriter.printf("%12d  %15.2f \n",inputSize, averageTimePerTrialInBatch); // might as well make the columns look nice

            resultsWriter.flush();

            System.out.println(" ....done.");

        }

    }
}