import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class FibMatrix
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
        int F[][] = new int[][]{{1,1},{1,0}};
        if (n == 0)
            return 0;
        power(F, n-1);

        return F[0][0];
    }

    /* Helper function that multiplies 2 matrices F and M of size 2*2, and
    puts the multiplication result back to F[][] */
    static void multiply(int F[][], int M[][])
    {
        int x =  F[0][0]*M[0][0] + F[0][1]*M[1][0];
        int y =  F[0][0]*M[0][1] + F[0][1]*M[1][1];
        int z =  F[1][0]*M[0][0] + F[1][1]*M[1][0];
        int w =  F[1][0]*M[0][1] + F[1][1]*M[1][1];

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    /* Helper function that calculates F[][] raise to the power n and puts the
    result in F[][]
    Note that this function is designed only for fib() and won't work as general
    power function */
    static void power(int F[][], int n)
    {
        int i;
        int M[][] = new int[][]{{1,1},{1,0}};

        // n - 1 times multiply the matrix to {{1,0},{0,1}}
        for (i = 2; i <= n; i++)
            multiply(F, M);
    }

    /* Driver program to test above function */
    public static void main (String args[])
    {
        int n = 9;
        System.out.println("Using a matrix to calculate the " + n + "th number of the fibonacci sequence: " + fib(n));
        n = 20;
        System.out.println("Using a matrix to calculate the " + n + "th number of the fibonacci sequence: " + fib(n));
        n = 46;
        System.out.println("Using a matrix to calculate the " + n + "th number of the fibonacci sequence: " + fib(n));

        //runFullExperiment("FibMatrix-Exp1-ThrowAway.txt");

        //runFullExperiment("FibMatrix-Exp2.txt");

        //runFullExperiment("FibMatrix-Exp3.txt");
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
