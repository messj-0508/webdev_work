import java.util.StringTokenizer;

public class CompareVersionNumbers {


  public static void main(String[] args) throws Exception {
    System.out.println(new CompareVersionNumbers().compareVersion("1.11.1", "1.11"));
  }

  public int compareVersion(String version1, String version2) {
    StringTokenizer st1 = new StringTokenizer(version1, ".");
    StringTokenizer st2 = new StringTokenizer(version2, ".");
    while (st1.hasMoreTokens() & st2.hasMoreTokens()) {
      int token1 = Integer.parseInt(st1.nextToken());
      int token2 = Integer.parseInt(st2.nextToken());
      if (token1 > token2) return 1;
      else if (token2 > token1) return -1;
    }
    if (st1.countTokens() > st2.countTokens()) {
      while (st1.hasMoreTokens()) {
        if (Integer.parseInt(st1.nextToken()) > 0) return 1;
      }
    } else if (st2.countTokens() > st1.countTokens()) {
      while (st2.hasMoreTokens()) {
        if (Integer.parseInt(st2.nextToken()) > 0) return -1;
      }
    }
    return 0;
  }
}
