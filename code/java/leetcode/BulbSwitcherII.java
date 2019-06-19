public class BulbSwitcherII {

  public static void main(String[] args) throws Exception {
    System.out.println(new BulbSwitcherII().flipLights(1, 1000));
  }

  public int flipLights(int n, int m) {
    if (n == 0 || m == 0) return 1;
    if (n == 1 && m >= 1) return 2;
    if (n == 2) {
      if (m == 1) return 3;
      if (m >= 2) return 4;
    } else if (n >= 3) {
      if (m == 1) return 4;
      if (m == 2) return 7;
    }
    return 8;
  }
}
