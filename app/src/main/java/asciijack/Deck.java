package asciijack;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	private static ArrayList<Card> generatedCards = new ArrayList<Card>();

	public static Card getRandomCard() {
		Random r = new Random();

		Card c = null;
		do {
			c = new Card(r.nextInt(2, 15), r.nextInt(0, 4));

		} while (generatedCards.contains(c));

		generatedCards.add(c);

		return c;
	}

	public static void shuffle() {
		generatedCards.clear();
	}
}
