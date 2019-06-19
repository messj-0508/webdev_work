public class AddDigits {

  public static void main(String[] args) throws Exception {
    System.out.println(new AddDigits().addDigits(38));
  }

  public int addDigits(int num) {
    if (num == 0) return 0;
    return num % 9 == 0 ? 9 : num % 9;
  }
}
