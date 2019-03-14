public class Test{
    public static void main(String[] args) {
        int index = 0;
        int n = 3;
        int row = ((index - 1) / n) + 1;
        int col = ((index - 1) % n) + 1;
        System.out.println("row: " + row + " col: " + col);
    }
}
