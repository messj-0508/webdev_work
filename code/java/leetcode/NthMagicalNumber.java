import java.math.BigInteger;

public class NthMagicalNumber {


  public static void main(String[] args) {
    System.out.println(new NthMagicalNumber().nthMagicalNumber(3, 2, 4));
  }

  public int nthMagicalNumber(int N, int A, int B) {
    final int CONST = 1000000007;
    BigInteger bigInteger = new BigInteger(String.valueOf(A));
    long aL = (long) A * B;
    long lcm = aL / bigInteger.gcd(new BigInteger(String.valueOf(B))).longValue();
    long l = 0, h = Long.MAX_VALUE;
    while (l <= h) {
      long m = l + (h - l) / 2;
      int status = check(N, m, A, B, lcm);
      if (status == 0) {
        long modA = m % A;
        long modB = m % B;
        if (modA == 0 || modB == 0) return (int) (m % CONST);
        else if (modA < modB) return (int) ((m - modA) % CONST);
        else return (int) ((m - modB) % CONST);
      } else if (status == -1) {
        l = m + 1;
      } else {
        h = m - 1;
      }
    }
    return 0;
  }

  private int check(int N, long num, int A, int B, long lcm) {
    long sum = (num / A) + (num / B);
    long common = num / lcm;
    sum -= common;
    if (sum == N) return 0;
    else if (sum > N) return 1;
    else return -1;
  }
}
