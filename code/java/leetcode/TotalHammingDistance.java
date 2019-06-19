public class TotalHammingDistance {

  public static void main(String[] args) throws Exception {
    int[] A = {1000000000, 4, 14, 2};
    System.out.println(new TotalHammingDistance().totalHammingDistance(A));
  }

  public int totalHammingDistance(int[] nums) {
    int sum = 0;
    for (int i = 0; i < 32; i++) {
      int numOfOnes = 0;
      int p = (1 << i);
      for (int num : nums) {
        if ((num & p) > 0) {
          numOfOnes++;
        }
      }
      sum += ((nums.length - numOfOnes) * numOfOnes);
    }
    return sum;
  }
}
