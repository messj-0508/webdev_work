public class FirstBadVersion {
  public static void main(String[] args) throws Exception {
    System.out.println(new FirstBadVersion().firstBadVersion(2126753390));
  }

  public int firstBadVersion(int n) {
    int low = 0, high = n;
    while (low < high) {
      int mid = (low + high) >>> 1;
      if (isBadVersion(mid)) {
        high = mid;
      } else low = mid + 1;
    }
    return high;
  }

  private boolean isBadVersion(int n) {
    if (n >= 1702766719) return true;
    return false;
  }
}
