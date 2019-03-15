import edu.princeton.cs.algs4.WeightedQuickUnionUF; 
import edu.princeton.cs.algs4.StdRandom; 

public class Percolation {
    private final int n;
    private boolean[] sites;
    private final WeightedQuickUnionUF ds;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        this.n = n;
        if (n <= 0) { 
            throw new java.lang.IllegalArgumentException("n must be > 0");
        }

        // sites keeps tracks whether each site is open/closed
        ds = new WeightedQuickUnionUF(n*n + 2);
        this.sites = new boolean[n*n+2]; 
        for (int i = 0; i < n*n+2; i++) { this.sites[i] = false; }
        this.sites[0] = true; 
        this.sites[n*n+1] = true; 

        // connect top/bottom virtual site to top/bottom row in ds
        for (int i = 1; i < n+1; i++) { ds.union(0, i); }
        for (int i = n*n; i > n*(n-1); i--) { ds.union(n*n+1, i); }

    }
    private int toIndex(int row, int col) {
        return this.n*(row - 1) + (col - 1) + 1;
    } 
    private boolean onGrid(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            return false;
        }
        return true;
    }

    public    void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            String message = row + ", " + col + " invalid for n = " + this.n;
            throw new java.lang.IllegalArgumentException(message);
        }
        // open original
        int index = this.toIndex(row, col);
        this.sites[index] = true;
        
        // connect to neighbors in ds
        // up
        if (this.onGrid(row-1, col)) {
            if (this.isOpen(row-1, col)) { 
                int n1 = this.toIndex(row-1, col);
                this.ds.union(index, n1); 
            }
        }
        // down
        if (this.onGrid(row+1, col)) {
            if (this.isOpen(row+1, col)) { 
                int n2 = this.toIndex(row+1, col);
                this.ds.union(index, n2); 
            }
        }
        // right
        if (this.onGrid(row, col+1)) {
            if (this.isOpen(row, col+1)) { 
                int n3 = this.toIndex(row, col+1);
                this.ds.union(index, n3); 
            }
        }
        // left
        if (this.onGrid(row, col-1)) {
            if (this.isOpen(row, col-1)) { 
                int n4 = this.toIndex(row, col-1);
                this.ds.union(index, n4); 
            }
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            String message = row + ", " + col + " invalid for n = " + this.n;
            throw new java.lang.IllegalArgumentException(message);
        }
        int index = this.toIndex(row, col);
        return this.sites[index];  
        
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {

        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            String message = row + ", " + col + " invalid for n = " + this.n;
            throw new java.lang.IllegalArgumentException(message);
                                                         
        }
        // full if it's connected to top virtual site located at 0
        if (this.isOpen(row, col)) {
            int index = this.toIndex(row, col);
            return this.ds.connected(0, index);
        }
        return false;
    }
    public     int numberOfOpenSites()       // number of open sites
    {
        int sum = 0;
        for (boolean site: this.sites) {
            if (site) { sum++; }
        }
        // need to account for two open virtual sites
        return sum - 2;
    }
    public boolean percolates()              // does the system percolate?
    {
        // true if top and bottom virtual sites are connected
        return this.ds.connected(0, n*n+1);
    }
    public static void main(String[] args)   
    {
        // test client (optional)
        int n = 5;
        Percolation p = new Percolation(n);
        int inconsistent = 0;
        for (int i = 0; i < 5; i++) {
            int row = StdRandom.uniform(5) + 1
            int col = StdRandom.uniform(5) + 1
            p.open(row, col)
            if (p.isOpen(row, col) == false && p.isFull(row, col) == true) {
                inconsistent++;
            }
        }
        System.out.println("inconsistent opens: " + inconsistent);
    }
}
// two possibilites:
    // map (0,0) to (1,1) in Percolation.java
        // need to map everything from (0,0) to (1,1)
    // rig it so that if it takes a row, col pair from input, reformat them
