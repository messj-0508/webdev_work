import java.util.Arrays;

public class BurstBalloons {

  public static void main(String[] args) throws Exception {
    int[][] baloons = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
    System.out.println(new BurstBalloons().findMinArrowShots(baloons));
  }

  public int findMinArrowShots(int[][] points) {
    if (points.length == 0) return 0;
    Arrays.sort(points, ((o1, o2) -> o1[1] - o2[1]));
    int count = 0;
    int leftMost = points[0][1];
    for (int i = 1; i < points.length; i++) {
      if (leftMost < points[i][0]) {
        count++;
        leftMost = points[i][1];
      }
    }
    return count + 1;
  }
}
