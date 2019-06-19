import java.util.*;

public class TopKFrequentWords {

  class Pair {
    String word;
    int freq;

    Pair(String word, int freq) {
      this.word = word;
      this.freq = freq;
    }
  }

  /**
   * Main method
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    String[] words = {"i", "love", "leetcode", "i", "love", "coding"};
    List<String> sorted = new TopKFrequentWords().topKFrequent(words, 2);
    sorted.stream().forEach(System.out::println);
  }

  public List<String> topKFrequent(String[] words, int k) {
    Map<String, Integer> map = new HashMap<>();
    for (String w : words) {
      map.putIfAbsent(w, 0);
      int freq = map.get(w);
      map.put(w, freq + 1);
    }
    Queue<Pair> pq =
        new PriorityQueue<>(
            (o1, o2) ->
                (o1.freq == o2.freq)
                    ? o2.word.compareTo(o1.word)
                    : Integer.compare(o1.freq, o2.freq));
    for (String w : map.keySet()) {
      int f = map.get(w);
      pq.offer(new Pair(w, f));
      if (pq.size() > k) {
        pq.poll();
      }
    }
    List<String> result = new ArrayList<>();
    while (!pq.isEmpty()) {
      result.add(pq.poll().word);
    }
    Collections.reverse(result);
    return result;
  }
}
