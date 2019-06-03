public class MajorityElement {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] test_input1 = {3,2,3};
        assert solution.majorityElement(test_input1)==3:"案例1测试失败";
        System.out.println("案例1测试通过");
        int[] test_input2 = {2,2,1,1,1,2,2};
        assert solution.majorityElement(test_input2)==2:"案例2测试失败";
        System.out.println("案例2测试通过");
    }
    //题目名：求众数
    static class Solution {
        public int majorityElement(int[] nums) {
            int len = nums.length;
            int res = nums[0];
            int times = 1;
            for(int i=1; i<len; i++){
                if (res == nums[i]){
                    times += 1;
                }else {
                    times -= 1;
                    if (times < 0){
                        res = nums[i];
                        times = 1;
                    }
                }
            }
            return res;
        }
    }
}
