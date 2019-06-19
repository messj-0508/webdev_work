import java.util.ArrayList;
import java.util.List;

public class Subsets {

  public static void main(String[] args) throws Exception {
    int[] n = {1, 2, 3};
    List<List<Integer>> result = new Subsets().subsets(n);
  }

  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>()); // empty subset
    for (int i = 0, l = nums.length; i < l; i++) {
      for (int j = 0, resLen = result.size(); j < resLen; j++) {
        List<Integer> newList = new ArrayList<>(result.get(j));
        newList.add(nums[i]);
        result.add(newList);
      }
    }
    return result;
  }
}
