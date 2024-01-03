package asciijack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class App {
	private static Hand dealerHand;
	private static ArrayList<Hand> playerHands;

	private static Scanner sc;

	public static void main(String[] args) {
		dealerHand = new Hand();
		playerHands = new ArrayList<>();

		sc = new Scanner(System.in);

		boolean running = true;
		while (running) {
			if (playerHands.size() == 0) {
				shuffleDeck();
				continue;
			}

			boolean foundIncomplete = false;
			for (Hand hand : playerHands) {
				if (!hand.isCompleted()) {
					foundIncomplete = true;
					break;
				}
			}

			if (!foundIncomplete) {
				finishDealingDealer();

				for (Hand hand : new ArrayList<>(playerHands)) {
					int playerScore = calcHand(hand, false);
					int dealerScore = calcHand(dealerHand, false);

					dealerHand.getCards().get(0).setRevealed(true);

					clearConsole();
					printAllHands(hand, true);

					if (playerScore > 21) {
						// TODO: This should probably be called immediately, not after all hands are
						// played
						System.out.println("Busted! You lost.");
						System.out.println("THIS SHOULD NOT HAPPEN!! UH OH!!!!");

					} else if (dealerScore > 21) {
						System.out.println("Dealer busted! You win!");

					} else if (playerScore > dealerScore) {
						System.out.println("You won!");

					} else if (dealerScore > playerScore) {
						System.out.println("You lost.");

					} else {
						System.out.println("Tie!");
					}

					playerHands.remove(hand);

					try {
						Thread.sleep(3000);
					} catch (Exception e) {
					}
				}

				shuffleDeck();
				continue;
			}

			playHand();
		}

		sc.close();
	}

	private static void playHand() {
		boolean done = false;
		String statusLine = "";
		while (!done) {
			Hand currentHand = playerHands.get(0);
			clearConsole();
			printAllHands(currentHand, false);

			if (!statusLine.equals("")) System.out.println(statusLine);
			System.out.printf("%n%nEnter your choice ([s]tand, [h]it, s[p]lit, [q]uit): ");
			String resp = sc.nextLine();

			switch (resp.toLowerCase()) {
				case "s":
					System.out.println("Standing...");
					currentHand.setCompleted(true);
					Collections.rotate(playerHands, -1);
					done = true;

					try {
						Thread.sleep(2000);
					} catch (Exception e) {
					}
					break;

				case "h":
					statusLine = "Hitting...";
					currentHand.addCard(Deck.getRandomCard(true));

					if (calcHand(currentHand, false) > 21) {
						clearConsole();
						printAllHands(currentHand, false);
						System.out.println("Hitting...");
						System.out.printf("%n%nBusted! Dealer wins.");
						playerHands.remove(0);
						done = true;

						try {
							Thread.sleep(2000);
						} catch (Exception e) {
						}
					}
					break;

				case "p":
					statusLine = "Splitting...";
					if (currentHand.getCards().size() > 2 || currentHand.getCards().get(0).getCard() != currentHand.getCards().get(1).getCard()) {
						statusLine = "Can't split!";
						continue;
					}

					playerHands.remove(currentHand);
					playerHands.add(new Hand(new ArrayList<>(Arrays.asList(currentHand.getCards().get(0), Deck.getRandomCard(true)))));
					playerHands.add(new Hand(new ArrayList<>(Arrays.asList(currentHand.getCards().get(1), Deck.getRandomCard(true)))));
					done = true;

					try {
						Thread.sleep(2000);
					} catch (Exception e) {
					}

					break;

				case "q":
					System.out.println("Quitting...");
					sc.close();
					System.exit(0);
					break;

				default:
					statusLine = "Unknown comand " + resp.toLowerCase() + " entered.";
					break;
			}
		}
	}

	private static void shuffleDeck() {
		System.out.println("Shuffling deck...");
		Deck.shuffle();

		dealerHand = new Hand();
		playerHands.clear();
		playerHands.add(new Hand());

		Hand currentHand = playerHands.get(0);

		currentHand.addCard(Deck.getRandomCard(true));
		dealerHand.addCard(Deck.getRandomCard(false));
		currentHand.addCard(Deck.getRandomCard(true));
		dealerHand.addCard(Deck.getRandomCard(true));
	}

	private static int calcHand(Hand hand, boolean ignoreHidden) {
		ArrayList<Card> sorted = hand.getSortedCards();

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

	private static void finishDealingDealer() {
		while (calcHand(dealerHand, false) <= 16) {
			dealerHand.addCard(Deck.getRandomCard(true));
		}
	}

	private static void printAllHands(Hand currentHand, boolean revealDealer) {
		int playerHandCalc = calcHand(currentHand, false);
		int dealerHandCalc = calcHand(dealerHand, !revealDealer);

		System.out.println("Dealer (" + dealerHandCalc + ")");
		printHand(dealerHand);

		System.out.println(playerHands.size() > 1 ? "You (Hand " + playerHands.size() + ") (" + playerHandCalc + ")"
				: "You (" + playerHandCalc + ")");
		printHand(currentHand);
	}

	private static void printHand(Hand hand) {
		for (int i = 0; i < hand.getCards().get(0).toStringLines().length; i++) {
			for (int j = 0; j < hand.getCards().size(); j++) {
				System.out.print(hand.getCards().get(j).toStringLines()[i]);

				if (j < hand.getCards().size() - 1) {
					System.out.printf("    ");
				} else {
					System.out.println();
				}
			}
		}
	}

	private static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
