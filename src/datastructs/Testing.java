package datastructs;

public class Testing {

    protected static int findNextPrime(int n) {
        if (n % 2 == 0) {
            n++;
        }
        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    /**
     * Returns (base^power) % mod
     * 
     * @param base
     *            The base of the exponent
     * @param power
     *            The exponent
     * @param mod
     *            The value to take the mod of
     * @return (base^power) % mod
     */
    protected static int modPower(int x, int power, int mod) {
        long base = x;
        base = base % mod;

        long result = 1;
        while (power > 0) {
            if (power % 2 == 1) {
                result = (result * base) % mod;
            }
            power >>= 1;
            base = (base * base) % mod;
        }
        return (int) result;
    }

    /**
     * @author Neil Dey The Miller Rabin Test for primes up to 2^32. The values
     *         checked will be a = 2, 7, and 63.
     * 
     *         To check primes up to 2^64, we must check far more values: a = 2, 3,
     *         5, 7, 11, 13, 17, 19, 23, 29, 31, and 37.
     * 
     *         Thus, as written, this only guarantees primeness for 32-bit integers.
     * 
     * 
     * @param n
     *            A 32-bit integer
     * @return Whether or not n is prime
     */
    protected static boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        if (n == 2 || n == 3) {
            return true;
        }
        int d = n - 1;
        int r = 0;
        while (d % 2 == 0) {
            d >>= 1;
            r++;
        }
        int a = 2;
        outer: for (int i = 0; i < 3; i++) {
            if (i == 1) {
                a = 7;
            } else if (i == 2) {
                a = 63;
            }
            int x = modPower(a, d, n);
            if (x == 1 || x == n - 1) {
                continue outer;
            }
            for (int j = 0; j < r - 1; j++) {
                x = modPower(x, 2, n);
                if (x == 1) {
                    return false;
                }
                if (x == n - 1) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(findNextPrime(43853 * 2 + 1));
    }

}
