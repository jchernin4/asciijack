package asciijack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
		Scanner sc = new Scanner(System.in);
		clearConsole();

		playerHand.add(Deck.getRandomCard(true));
		dealerHand.add(Deck.getRandomCard(false));
		playerHand.add(Deck.getRandomCard(true));
		dealerHand.add(Deck.getRandomCard(true));

		int playerHandCalc = calcHand(playerHand, false);

		printHands(false);

		if (playerHandCalc == 21) {
			System.out.println("Blackjack! You won!");
			sc.close();
			return;
		}

gameloop:
		while (true) {
			System.out.print("\n\nEnter your choice ([s]tand, [h]it, s[p]lit): ");
			String resp = sc.nextLine();

			clearConsole();

			switch (resp.toLowerCase()) {
				case "s":
					finishDealingDealer();
					dealerHand.get(0).setRevealed(true);
					printHands(true);
					System.out.println("Standing...");

					int dealerHandCalc = calcHand(dealerHand, false);
					playerHandCalc = calcHand(playerHand, false);

					if (dealerHandCalc > 21) {
						System.out.println("Dealer busted! You win!");

					} else if (dealerHandCalc < playerHandCalc) {
						System.out.println("You won!");

					} else if (dealerHandCalc > playerHandCalc) {
						System.out.println("You lost.");

					} else {
						System.out.println("Tie!");
					}

					break gameloop;

				case "h":
					playerHand.add(Deck.getRandomCard(true));
					printHands(false);
					System.out.println("Hitting...");

					if (calcHand(playerHand, false) > 21) {
						System.out.println("Busted! Dealer wins.");
						break gameloop;
					}
					break;

				case "p":
					printHands(false);
					System.out.println("Splitting...");
					break;

				default:
					break;
			}
		}

		sc.close();
	}

	private void finishDealingDealer() {
		while (calcHand(dealerHand, false) <= 16) {
			dealerHand.add(Deck.getRandomCard(true));
		}
	}

	private void printHands(boolean revealDealer) {
		int playerHandCalc = calcHand(playerHand, false);
		int dealerHandCalc = calcHand(dealerHand, !revealDealer);

		System.out.println("Dealer (" + dealerHandCalc + ")");
		printHand(dealerHand);

		System.out.println("You (" + playerHandCalc + ")");
		printHand(playerHand);
	}

	private void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
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

	public int calcHand(List<Card> hand, boolean ignoreHidden) {
		List<Card> sorted = new ArrayList<>(hand);

		Collections.sort(sorted, (o1, o2) -> o1.getCard() - o2.getCard());

		int total = 0;
		int aceCount = 0;
		for (Card c : sorted) {
			if (ignoreHidden && !c.getRevealed())
				continue;

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
