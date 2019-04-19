import datastructs.HashTable;

public class Testing {
    private static final double INV_PHI = 2.0 / (1 + Math.sqrt(5));

    public static int compress(int hash) {
        double temp = hash * INV_PHI;
        temp = temp - (int) temp;
        return (int) Math.abs(9 * temp);
    }
    
    public static void main(String[] args) {
        String test = "ETA";
        int r = 2;
        char[] chars = test.toCharArray();
        int sum = 0;
        int pow = 1;
        for(int i = chars.length - 1; i >= 0; i--) {
            sum += chars[i] * pow;
            pow *= r;
        }
        System.out.println(sum);
        System.out.println(compress(sum));
    }

}
