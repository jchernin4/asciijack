package asciijack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
	private List<Card> dealerHand;
	private List<Card> playerHand;

	public App() {
		dealerHand = new ArrayList<>();
		playerHand = new ArrayList<>();
	}

	public static void main(String[] args) {
		new App().run();
	}

	private void run() {
		setup();
	}

	private void setup() {
		System.out.print("\033[H\033[2J");
		System.out.flush();

		playerHand.add(Deck.getRandomCard(true));
		dealerHand.add(Deck.getRandomCard(true));
		playerHand.add(Deck.getRandomCard(true));
		dealerHand.add(Deck.getRandomCard(false));

		System.out.println("Dealer");
		printHand(dealerHand);

		System.out.println("You (" + calcHand(playerHand) + ")");
		printHand(playerHand);
	}

	public void printHand(List<Card> hand) {
		for (int i = 0; i < hand.get(0).toStringLines().length; i++) {
			for (int j = 0; j < hand.size(); j++) {
				System.out.print(hand.get(j).toStringLines()[i]);

				if (j < hand.size() - 1) {
					System.out.print("\t");
				} else {
					System.out.println();
				}
			}
		}
	}

	public int calcHand(List<Card> hand) {
		Collections.sort(hand, (o1, o2) -> o1.getCard() - o2.getCard());

		int total = 0;
		int aceCount = 0;
		for (Card c : hand) {
			if (c.getCard() == 14) {
				aceCount++;
				total += 11;
				continue;
			}

			if (c.getCard() <= 10) {
				total += c.getCard();
				continue;
			}

			total += 10;
		}

		while (total > 21 && aceCount > 0) {
			total -= 10;
			aceCount--;
		}

		return total;
	}
}
