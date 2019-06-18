/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        ListNode *ans = new ListNode(-1), *cur = ans;
        int upbit = 0;
        while(l1||l2){
            int val1 = l1?l1->val:0;
            int val2 = l2?l2->val:0;
            int sum = val1 + val2 + upbit;
            upbit = sum / 10;
            cur->next = new ListNode(sum % 10);
            cur = cur->next;
            if (l1) l1 = l1->next;
            if (l2) l2 = l2->next;
        }
        if (upbit) cur->next = new ListNode(1);
        return ans->next;
    }
};