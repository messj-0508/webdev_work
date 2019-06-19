import java.util.BitSet;

public class CountPrimes {

  public static void main(String[] args) throws Exception {
    System.out.println(new CountPrimes().countPrimes(999187));
  }

  public int countPrimes(int n) {
    if (n == 0 || n == 1 || n == 2) return 0;
    else if (n == 3) return 1;
    BitSet set = new BitSet();
    n = n - 1;
    int sqRt = (int) Math.sqrt(n);
    int count = n;
    for (int i = 2; i <= sqRt; i++) {
      if (!set.get(i)) {
        for (int j = 2; (i * j) <= n; j++) {
          if (!set.get(i * j)) {
            count--;
            set.set(i * j);
          }
        }
      }
    }
    return count - 1;
  }
}
