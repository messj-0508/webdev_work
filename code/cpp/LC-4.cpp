public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;                                         //统计两个数组长度
        if (m < n) return findMedianSortedArrays(nums2, nums1);                         //确保第一个数组长度更大
        if (n == 0) return (nums1[(m - 1) / 2] + nums1[m / 2]) / 2.0;                   //若第二个数组为空，则直接返回第一个数组中位值：(L + R)/2 = (A[(N-1)/2] + A[N/2])/2
        int left = 0, right = 2 * n;                                                    
        while (left <= right) {
            int mid2 = (left + right) / 2;
            int mid1 = m + n - mid2;
            double L1 = mid1 == 0 ? Double.MIN_VALUE : nums1[(mid1 - 1) / 2];           
            double L2 = mid2 == 0 ? Double.MIN_VALUE : nums2[(mid2 - 1) / 2];
            double R1 = mid1 == m * 2 ? Double.MAX_VALUE : nums1[mid1 / 2];
            double R2 = mid2 == n * 2 ? Double.MAX_VALUE : nums2[mid2 / 2];
            if (L1 > R2) left = mid2 + 1;                                               //确保： L2 <= R1 && L2 <= R2
            else if (L2 > R1) right = mid2 - 1;                                         //确保： L2 <= R1 && L2 <= R2
            else return (Math.max(L1, L2) + Math.min(R1, R2)) / 2;
        }
        return -1;
    }
}