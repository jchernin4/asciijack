package asciijack;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {
	private ArrayList<Card> cards;
	private boolean completed;

	public Hand(ArrayList<Card> cards) {
		this.cards = cards;
		completed = false;
	}

	public Hand() {
		cards = new ArrayList<Card>();
		completed = false;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public ArrayList<Card> getSortedCards() {
		ArrayList<Card> sorted = new ArrayList<>(cards);
		Collections.sort(sorted, (o1, o2) -> o1.getCard() - o2.getCard());
		return sorted;
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public void removeCard(Card card) {
		cards.remove(card);
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
