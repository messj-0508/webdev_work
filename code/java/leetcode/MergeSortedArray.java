public class MergeSortedArray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] test_input1 = {  {1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}  };
        int[] nums1 = {1,2,3,0,0,0};
        int[] nums2 = {2,5,6};
        int m = 3, n = 3;
        int[] ans = {1,2,2,3,5,6};
        solution.merge(nums1, m, nums2, n);
        for (int i=0; i<(m+n); i++){
            if (nums1[i] != ans[i])
                System.out.println("案例1测试失败");
        }
        System.out.println("案例1测试通过");
    }
    static class Solution {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            int i = m-1;
            int j = n-1;
            int idx = m+n-1;
            while (i>=0 && j>=0){
                if (nums1[i]>=nums2[j]){
                    nums1[idx--] = nums1[i--];
                }else {
                    nums1[idx--] = nums2[j--];
                }
            }
            if (j>=0){
                while (j>=0)
                    nums1[idx--] = nums2[j--];
            }
        }
    }
}
