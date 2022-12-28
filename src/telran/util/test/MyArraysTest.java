package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import telran.util.MyArrays;

class MyArraysTest {
	static final int  N_NUMBERS = 10000;
	static final int N_RUNS = 1000;
	Integer numbers[] = { 13, 2, -8, 47, 100, 10, -7, 7 };
	String strings[] = { "ab", "abm", "abmb", "abmbc" };
	Comparator<Integer> evenOddComparator = this::evenOddCompare;

	@Test
	@Disabled
	void sortTest() {
		String[] strings = { "abcd", "lmn", "zz" };
		String[] expected = { "zz", "lmn", "abcd" };

		MyArrays.sort(strings, new StringLengthComparator());
		assertArrayEquals(expected, strings);

	}

	@Test

	void evenOddTest() {

		Integer expected[] = { -8, 2, 10, 100, 47, 13, 7, -7 };
		MyArrays.sort(numbers, evenOddComparator);
		assertArrayEquals(expected, numbers);
	}

	@Test
	void binarySearchTest() {
		String strings[] = { "ab", "abm", "abmb", "abmbc" };
		Comparator<String> comp = new StringsComparator();
		assertEquals(0, MyArrays.binarySearch(strings, "ab", comp));
		assertEquals(2, MyArrays.binarySearch(strings, "abmb", comp));
		assertEquals(3, MyArrays.binarySearch(strings, "abmbc", comp));
		assertEquals(-1, MyArrays.binarySearch(strings, "a", comp));
		assertEquals(-3, MyArrays.binarySearch(strings, "abma", comp));
		assertEquals(-5, MyArrays.binarySearch(strings, "lmn", comp));
	}

	@Test
	void filterTest() {
		int dividor = 2;
		String subStr = "m";
		Predicate<Integer> predEven = t -> t % dividor == 0;
		Predicate<String> predSubstr = s -> s.contains(subStr);
		String expectedStr[] = { "abm", "abmb", "abmbc" };
		Integer expectedNumbers[] = { 2, -8, 100, 10 };
		assertArrayEquals(expectedStr, MyArrays.filter(strings, predSubstr));
		assertArrayEquals(expectedNumbers, MyArrays.filter(numbers, predEven));

	}

	int evenOddCompare(Integer o1, Integer o2) {
		int remainder = Math.abs(o1) % 2;
		int res = remainder - Math.abs(o2) % 2;
		if (res == 0) {
			res = remainder != 0 ? Integer.compare(o2, o1) : Integer.compare(o1, o2);
		}
		return res;
	}

	@Test
	void filterTest_Negate() {

		String subStr = "m";
		String[] strings = { "a", "b", "m", "ab", "am", "bm" };
		Predicate<String> predSubstr = s -> s.contains(subStr);
		String expectedStr[] = { "a", "b", "ab" };
		assertArrayEquals(expectedStr, MyArrays.removeIf(strings, predSubstr));

	}
	@Test
	void booleanContainsTest() {
		String  subStr = "m";
		String[] strings = { "a", "b", "m", "ab", "am", "bm" };
		String[] empty = {""};
		String[] withoutPattern = { "a","b","ab","am","bm"};
		String[] withNull = { "a","b","ab","am","bm", null};
		
		assertEquals(true, MyArrays.contains(strings, subStr));
		assertEquals(false, MyArrays.contains(empty, subStr));
		assertEquals(false, MyArrays.contains(withoutPattern, subStr));
		assertEquals(false, MyArrays.contains(withoutPattern, null));
		assertEquals(true, MyArrays.contains(withNull, null));
	}
	@Test
	void removeRepeatedTest() {
		
		String[] strings = { "a", "b", "b", "ab", "am", "bm" };
		String[] expected = { "a", "b", "ab", "am", "bm" };
		
		assertArrayEquals(expected, MyArrays.removeRepeated(strings));
		
	}
	@Test
	void isNullPredicateTest() {
		
		String[] strings = { "a", "b", "b", "ab", "am", "bm", null};
		String[] expected = { "a", "b", "b", "ab", "am", "bm" };
		MyArrays.removeIf(strings, new isNullPredicate <String> ());
		assertArrayEquals(expected, MyArrays.removeIf(strings, new isNullPredicate <String> ()));
	}
	@Test
	void joinFunctionalTest() {
		
		String expected = "13,2,-8,47,100,10,-7,7,13,47,7,-7";
		assertEquals(expected, MyArrays.join(numbers, ","));
	}
	@Test
	void joinPerformanceTest() {
		Integer [] largeArray = getLargeNumbersArray();
		for (int i = 0; i < N_RUNS; i++)
			MyArrays.join(largeArray,",");
	
		
	}

	Integer[] getLargeNumbersArray() {
		Integer[] res = new Integer [N_NUMBERS];
		Arrays.fill(res, 1000);
		return res;
	}
}
