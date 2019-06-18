class Solution {
public:
    int removeDuplicates(vector<int>& nums) {
        int len = nums.size();
        if(len == 1 || len == 0)
            return len;
        int i=1;
        for(int j=1; j<len; j++){
            if(nums[j] != nums[j-1]){
                nums[i++] = nums[j];
            }
        }
        return i;
    }
};