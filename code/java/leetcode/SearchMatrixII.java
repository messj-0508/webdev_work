public class SearchMatrixII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] test_input1 = {  {1,   4,  7, 11, 15},
                                 {2,   5,  8, 12, 19},
                                 {3,   6,  9, 16, 22},
                                 {10, 13, 14, 17, 24},
                                 {18, 21, 23, 26, 30}  };
        assert solution.searchMatrix(test_input1, 5)==true:"案例1测试失败";
        System.out.println("案例1测试通过");
        assert solution.searchMatrix(test_input1, 20)==false:"案例2测试失败";
        System.out.println("案例2测试通过");
    }
    static class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            int m = matrix.length;
            if (m == 0)
                return false;
            int n=matrix[0].length;
            if (n == 0)
                return false;
            int row = 0, col = n-1;
            while (row < m && col >= 0){
                if (matrix[row][col] == target)
                    return true;
                else if (matrix[row][col] >target)
                    col--;
                else if (matrix[row][col] <target)
                    row++;
            }
            return false;
        }
    }
}
