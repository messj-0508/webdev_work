class Solution {
public:
    int lengthOfLongestSubstring(string s) 
    {
        vector<int>v(128,0);            // 记录字符出现的位置
        int t=0;int ans=0;
        for(int i=0;i<s.length();i++)
        {
            t=max(t,v[s[i]]);           // 计算该字符当前位置
            ans=max(ans,i-t+1);         // 比较当前无重复子串的长度和历史结果，求最大值
            v[s[i]]=i+1;                // 记录该字符当前位置
        }
        return ans;
    }             
};