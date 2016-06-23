package by.epam.shop.generator;

import java.util.Random;

public class RandomGenerator {
	private final Random rand = new Random();
	private long randomNumber;

	public long getRandomNumber() {
		randomNumber = rand.nextLong();
		return randomNumber;
	}
}
