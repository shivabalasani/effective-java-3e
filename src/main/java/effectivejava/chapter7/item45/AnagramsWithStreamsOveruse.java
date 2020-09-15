package effectivejava.chapter7.item45;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * 
 * If you find this code hard to read, don’t worry; you’re not alone. It is
 * shorter, but it is also less readable, especially to programmers who are not
 * experts in the use of streams. Overusing streams makes programs hard to read
 * and maintain.
 *
 */
//Overuse of streams - don't do this!
public class AnagramsWithStreamsOveruse {
	public static void main(String[] args) throws IOException {
		Stream<String> strings = Stream.of("eat", "tea", "tan", "ate", "nat", "bat", "ban", "bet", "nab");
		int minGroupSize = 1;

		// Path dictionary = Paths.get(args[0]);
		// try (Stream<String> words = Files.lines(dictionary)) {
		try (Stream<String> words = strings) {
			words.collect(groupingBy(word -> word.chars().sorted()
					.collect(StringBuilder::new, (sb, c) -> sb.append((char) c), StringBuilder::append).toString()))
					.values().stream().filter(group -> group.size() >= minGroupSize)
					.map(group -> group.size() + ": " + group).forEach(System.out::println);
		}
	}
}