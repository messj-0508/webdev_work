public class GasStation {

  public static void main(String[] args) throws Exception {
    int[] gas = {10, 20, 30, 10};
    int[] cost = {5, 30, 10, 10};
    System.out.println(new GasStation().canCompleteCircuit(gas, cost));
  }

  public int canCompleteCircuit(int[] gas, int[] cost) {
    int debt = 0, sum = 0, start = 0;
    for (int i = 0; i < gas.length; i++) {
      sum += gas[i] - cost[i];
      if (sum < 0) {
        debt += sum;
        sum = 0;
        start = i + 1;
      }
    }
    debt += sum;
    return debt >= 0 ? start : -1;
  }
}
