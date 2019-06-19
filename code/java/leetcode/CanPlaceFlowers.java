public class CanPlaceFlowers {

  public static void main(String[] args) throws Exception {
    int[] n = {1, 0, 0, 0, 1};
    System.out.println(new CanPlaceFlowers().canPlaceFlowers(n, 1));
  }

  public boolean canPlaceFlowers(int[] flowerbed, int n) {

    int[] T = new int[flowerbed.length + 4];
    for (int i = 0, j = 2; i < flowerbed.length; i++) T[j++] = flowerbed[i];
    T[0] = 1;
    T[T.length - 1] = 1;
    int total = 0, count = 0;
    for (int i = 1; i < T.length; i++) {
      if (T[i] == 0) count++;
      else {
        if ((count % 2) == 0) total += ((count / 2) - 1);
        else total += (count / 2);
        count = 0; // reset
      }
    }
    return (total >= n);
  }
}
