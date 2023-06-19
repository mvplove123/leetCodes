package leetcode.array;

import org.example.JacksonUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ArrayTest {


    @Test //1.两数之和
    public void test1twoSum() {
        int[] candidates = new int[]{2, 3, 6, 7};
        int target = 5;
        int[] result = twoSum(candidates, target);
        System.out.println("1.两数之和:" + JacksonUtil.toString(result));
    }

    @Test //3. 无重复字符的最长子串
    public void test3lengthOfLongestSubstring() {
        String s = "abcabcbb";
        int result = lengthOfLongestSubstring(s);
        System.out.println("3. 无重复字符的最长子串:" + result);
    }


    @Test
    public void test39CombinationSum() {
        int[] candidates = new int[]{2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> res = combinationSum(candidates, target);
        System.out.println("输出 => " + res);
    }


    @Test
    public void test46Permute() {

        int[] candidates = new int[]{2, 3, 6, 7};
        List<List<Integer>> result = permute(candidates);
        System.out.println(result);
    }

    @Test  //53.最大子数组和
    public void test53maxSubArray() {
        int[] candidates = new int[]{2, 3, -6, 7};
        int result = maxSubArray(candidates);
        System.out.println(result);
    }

    @Test
    public void test79exist() {
        char[][] board = {{'A', 'B', 'C'}, {'D', 'E', 'F'}, {'G', 'H', 'I'}};
        System.out.println(exist(board, "ABEH"));
    }

    @Test
    public void test128LongestConsecutive() {

        int[] nums = new int[]{1, 3, 2, 5, 1};
        int result = longestConsecutive(nums);
        System.out.println(result);
    }

    @Test //128. 比较版本号
    public void test165CompareVersion() {

        String version1 = "1.2.01";
        String version2 ="1.001";
        int result = compareVersion(version1,version2);
        System.out.println("128. 比较版本号:"+result);
    }

    @Test //206.链表翻转
    public void test206reverseList() {
        ListNode node1 = new ListNode(2);
        ListNode node = new ListNode(1, node1);
        ListNode result = reverseList(node);
        System.out.println(JacksonUtil.toString(result));
    }


    @Test
    public void test406ReconstructQueue() {

        int[][] people = new int[][]{{7, 0}, {4, 4}, {6, 1}, {5, 0}, {7, 1}, {5, 2}};
        int[][] result = reconstructQueue(people);
        System.out.println(result);
    }

    @Test
    public void test494FindTargetSumWays() {

        //todo 494看不懂
        int[] nums = new int[]{1, 1, 1, 1, 1};
        int target = 3;
        int result = findTargetSumWays(nums, target);
        System.out.println(result);
    }


    //无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {

        int result = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int start = 0, end = 0; end < s.length(); end++) {

            char letter = s.charAt(end);

            if (map.containsKey(letter)) {
                start = Math.max(map.get(letter), start);
            }
            result = Math.max(end + 1 - start, result);
            map.put(letter, end + 1);
        }

        return result;
    }

    public static int findTargetSumWays(int[] nums, int s) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++)
            sum += nums[i];
        // 绝对值范围超过了sum的绝对值范围则无法得到
        if (Math.abs(s) > Math.abs(sum)) return 0;
        int len = nums.length;
        int range = sum * 2 + 1;//因为要包含负数所以要两倍，又要加上0这个中间的那个情况
        int[][] dp = new int[len][range];//这个数组是从总和为-sum开始的
        //加上sum纯粹是因为下标界限问题，赋第二维的值的时候都要加上sum
        // 初始化   第一个数只能分别组成+-nums[i]的一种情况
        dp[0][sum + nums[0]] += 1;
        dp[0][sum - nums[0]] += 1;
        for (int i = 1; i < len; i++) {
            for (int j = -sum; j <= sum; j++) {
                if ((j + nums[i]) > sum) {//+不成立 加上当前数大于了sum   只能减去当前的数
                    dp[i][j + sum] = dp[i - 1][j - nums[i] + sum] + 0;
                } else if ((j - nums[i]) < -sum) {//-不成立  减去当前数小于-sum   只能加上当前的数
                    dp[i][j + sum] = dp[i - 1][j + nums[i] + sum] + 0;
                } else {//+-都可以
                    dp[i][j + sum] = dp[i - 1][j + nums[i] + sum] + dp[i - 1][j - nums[i] + sum];
                }
            }
        }
        return dp[len - 1][sum + s];
    }

    /**
     * 、53. 最大子数组和
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        //先设置最小值
        int result = Integer.MIN_VALUE;
        //临时求和变量
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            //和累加
            sum += nums[i];
        }
        //更新最小值
        if (sum > result) {
            result = sum;
        }
        //连续和为负--只会让我们的结果变小-不如更新遍历，求和为0
        if (sum <= 0) {
            sum = 0;
        }

        return result;
    }


    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b) -> {
            // 如果身高相同，k就按从小到大排序 [7,0],[7,1]
            if (a[0] == b[0]) {
                return a[1] - b[1];
            } else {
                // 身高从大到小
                return b[0] - a[0];
            }
        });
        LinkedList<int[]> queue = new LinkedList<>();
        for (int[] array : people) {
            queue.add(array[1], array);
        }
        return queue.toArray(new int[people.length][]);
    }


    @Test
    public void testTopKFrequent() {

        int[] nums = new int[]{1, 1, 2, 3, 3};
        int[] result = topKFrequent(nums, 2);
        System.out.println(result);
    }

    //两数之和
    public int[] twoSum(int[] nums, int target) {

        int len = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {

            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};

    }

    public int compareVersion(String version1, String version2) {

        String[] ss1 = version1.split("\\.");
        String[] ss2 = version2.split("\\.");

        int m = ss1.length;
        int n = ss2.length;
        int i=0,j=0;

        while(i<m || j<n){
            int a=0,b=0;
            if(i<n){
                a = Integer.parseInt(ss1[i++]);
            }
            if(j<m){
                b = Integer.parseInt(ss2[j++]);
            }
            if(a!=b){
                return a>b? 1:-1;
            }

        }
        return  0 ;
    }

        public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> frequentCount = new TreeMap<>();
        //频次统计
        for (int i = 0; i < nums.length; i++) {
            if (frequentCount.get(nums[i]) == null) {
                frequentCount.put(nums[i], 1);
            } else {
                int count = frequentCount.get(nums[i]);
                frequentCount.put(nums[i], count + 1);
            }
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(frequentCount.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue() - o1.getValue());

        int[] resultArray = new int[k];
        for (int i = 0; i < k; i++) {
            resultArray[i] = (list.get(i).getKey());
        }

        return resultArray;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    //链表翻转
    public ListNode reverseList(ListNode head) {

        ListNode pre = null;
        ListNode node = head;
        while (node != null) {

            ListNode nodeNext = node.next;
            node.next = pre;
            pre = node;
            node = nodeNext;
        }
        return pre;
    }


    public int longestConsecutive(int[] nums) {
        //边界判断
        if (nums.length <= 1) {
            return nums.length;
        }

        //去重处理
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestNum = 0;
        for (int num : numSet) {
            //set包不含前一个数
            if (!numSet.contains(num - 1)) {
                //当前数作为首数字
                int currentNum = num;
                //初始长度为1
                int currentLongestNum = 1;

                //如果set里包含当前当前数字的下一个，则继续+1判断是否在set里，直到不满足条件
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentLongestNum++;
                }
                //比较记录最大长度的值
                longestNum = Math.max(longestNum, currentLongestNum);
            }
        }
        return longestNum;
    }


    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }
        Deque<Integer> path = new ArrayDeque<>();
        dfs(candidates, 0, len, target, path, res);
        return res;
    }

    private void dfs(int[] candidates, int begin, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin; i < len; i++) {
            path.addLast(candidates[i]);
            System.out.println("递归之前 => " + path + "，剩余 = " + (target - candidates[i]));
            dfs(candidates, i, len, target - candidates[i], path, res);
            path.removeLast();
            System.out.println("递归之后 => " + path);
        }
    }


    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        int len = nums.length;
        if (len == 0) {
            return result;
        }

        Deque<Integer> path = new ArrayDeque<>();
        boolean[] used = new boolean[len];
        dfsPermute(nums, len, 0, result, used, path);
        return result;
    }

    private void dfsPermute(int[] nums, int len, int depth, List<List<Integer>> result, boolean[] used, Deque<Integer> path) {

        if (len == depth) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.add(nums[i]);
                used[i] = true;
                dfsPermute(nums, len, depth + 1, result, used, path);
                used[i] = false;
                path.removeLast();
            }
        }

    }


    public boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfsExist(words, i, j, board, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsExist(char[] words, int i, int j, char[][] board, int index) {
        //边界控制
        if (i < 0 || i >= board.length || j >= board[0].length || j < 0 || board[i][j] != words[index]) {
            return false;
        }
        if (index == words.length - 1) {
            return true;
        }
        char temp = board[i][j];
        board[i][j] = '.';
        boolean res = dfsExist(words, i + 1, j, board, index + 1) || dfsExist(words, i - 1, j, board, index + 1) || dfsExist(words, i, j + 1, board, index + 1) || dfsExist(words, i, j - 1, board, index + 1);
        board[i][j] = temp;
        return res;
    }
}
