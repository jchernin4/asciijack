package asciijack;

public class Card {
	private static char[] suitSymbols = { '♠', '♦', '♥', '♣' };

	private int card, suit;

	public Card(int card, int suit) {
		this.card = card;
		this.suit = suit;
	}

	public int getCard() {
		return card;
	}

	public int getSuit() {
		return suit;
	}

	public String toString() {
		char symbol = suitToSymbol(suit);
		char cardChar = cardToChar(card);

		return String.format("┌─────────┐%n" + 
			"│%c        │%n" + 
			"│         │%n" + 
			"│         │%n" + 
			"│    %c    │%n" + 
			"│         │%n" + 
			"│         │%n" + 
			"│        %c│%n" + 
			"└─────────┘", cardChar, symbol, cardChar);
	}

	public String[] getCardLines() {
		return toString().split("\n");
	}

	public char cardToChar(int c) {
		if (c <= 10) {
			return Character.forDigit(c, 10);
		}

		switch (c) {
			case 11:
				return 'J';
			case 12:
				return 'Q';
			case 13:
				return 'K';
			case 14:
				return 'A';
			default:
				return 'x';
		}
	}

	public char suitToSymbol(int s) {
		return suitSymbols[s];
	}
}
