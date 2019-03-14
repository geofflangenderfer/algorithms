import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private final int trials;
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] probs;
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n or T must be > 0");
        }

   
        this.trials = trials;
        this.probs = new double[trials];
        // Monte Carlo
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                // create array of randomized indices of sites in Percolation obj.
                int[] sitesIndices = new int[n*n+2];
                for (int j = 0; j < sitesIndices.length; j++) { 
                    sitesIndices[j] = j;
                }
                StdRandom.shuffle(sitesIndices);

                    // open random site
                for (int index : sitesIndices) {
                
                    int row = ((index - 1) / n) + 1;
                    int col = ((index - 1) % n) + 1;

                    if (perc.isOpen(row, col)) { 
                        continue;
                    }
                    else {
                        perc.open(row, col);
                        break;
                    }
                }
            }

            // add P(percolation), p, to probs
            int totOpen = perc.numberOfOpenSites();
            double p = (double) totOpen / (n*n);
            this.probs[i] = p;

        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        double x = StdStats.mean(this.probs);
        return x;
        
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        double s = StdStats.stddev(this.probs); 
        return s;
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double low = this.mean() - (PercolationStats.CONFIDENCE_95*this.stddev()) / Math.sqrt(this.trials);
        return low;
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double high = this.mean() + (PercolationStats.CONFIDENCE_95*this.stddev()) / Math.sqrt(this.trials);
        return high;
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, trials);
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println("95% confidence interval = [" 
                        + p.confidenceLo() + ", "
                        + p.confidenceHi() + "]"); 
        System.out.println();
    }
}
