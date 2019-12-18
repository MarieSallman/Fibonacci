import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class FibRecurDP
{

    static ThreadMXBean bean = ManagementFactory.getThreadMXBean( );

    static int MAXINPUTSIZE  = 46;

    static int MININPUTSIZE  =  1;
    static long MAXVALUE = 2000;
    static long MINVALUE = -2000;
    static long numberOfTrials = 1000000;

    static String ResultsFolderPath = "/home/marie/Results/"; // pathname to results folder

    static FileWriter resultsFile;

    static PrintWriter resultsWriter;

    static int fib(int n)
    {
        /* Declare an array to store Fibonacci numbers. */
        int f[] = new int[n+2]; // 1 extra to handle case, n = 0
        int i;

        /* 0th and 1st number of the series are 0 and 1*/
        f[0] = 0;
        f[1] = 1;

        for (i = 2; i <= n; i++)
        {
       /* Add the previous 2 numbers in the series 
         and store it */
            f[i] = f[i-1] + f[i-2];
        }

        return f[n];
    }

    public static void main (String args[])
    {
        int n = 9;
        System.out.println("Using dynamic recursion to calculate the " + n + "th number of the fibonacci sequence: " + fib(n));
        n = 20;
        System.out.println("Using dynamic recursion to calculate the " + n + "th number of the fibonacci sequence: " + fib(n));
        n = 42;
        System.out.println("Using dynamic recursion to calculate the " + n + "th number of the fibonacci sequence: " + fib(n));

        //runFullExperiment("FibRecurDP-Exp1-ThrowAway.txt");

        //runFullExperiment("FibRecurDP-Exp2.txt");

        //runFullExperiment("FibRecurDP-Exp3.txt");
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

                fib(n);

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