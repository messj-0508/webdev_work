public class HammingDistance {

  public static void main(String[] args) throws Exception {}

  public int hammingDistance(int x, int y) {
    int z = (x ^ y);
    int count = 0;
    for (int i = 0; i < 31; i++) {
      if ((z & (1 << i)) > 0) {
        count++;
      }
    }
    return count;
  }
}
