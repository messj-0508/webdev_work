public class ExcelSheetColumnTitle {

  private static final String CONST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static void main(String[] args) throws Exception {
    System.out.println(new ExcelSheetColumnTitle().convertToTitle(52));
  }

  public String convertToTitle(int n) {
    StringBuilder ans = new StringBuilder();
    while (n > 0) {
      int mod = n % 26;
      n /= 26;
      if (mod == 0) {
        ans.append('Z');
        n -= 1;
      } else {
        ans.append(CONST.charAt(mod - 1));
      }
    }
    return ans.reverse().toString();
  }
}
