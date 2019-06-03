
public class SuperEggDrop {
    public static void main(String[] args) {

    }
    static class Solution{
        public int superEggDrop(int K, int N) {
            int m = (int) Math.ceil(Math.pow(N, 1/K));
            int fs = 0, fe = N;
            for (int k= K-1; k>=0; k--){
                for (int i=0; i<m; i++){
                    int edge = fs + (int) Math.pow(m, i);
                    int top = (int) Math.min(Math.pow(m, i+1),fe);
                    if (fe - fs == 1)
                        return fs;
                }
            }
            return fe;
        }
    }
}
