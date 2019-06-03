public class SingleNumber {

    public static void main(String[] args) {
        int[] test_input1 = {2,2,1};
        assert singleNumber(test_input1)==1:"案例1测试失败";
        System.out.println("案例1测试通过");
        int[] test_input2 = {4,1,2,1,2};
        assert singleNumber(test_input2)==4:"案例2测试失败";
        System.out.println("案例2测试通过");
    }
    //题目名：只出现一次的数字
    public static int singleNumber(int[] nums) {
        int len = nums.length;
        int res = nums[0];
        for (int i=1; i<len; i++){
            res ^= nums[i];
        }
        return res;
    }
}
