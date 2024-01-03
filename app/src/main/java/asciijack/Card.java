package asciijack;

public class Card {
	private static char[] suitSymbols = { '♠', '♦', '♥', '♣' };

	private int card, suit;
	private boolean revealed;

	public Card(int card, int suit, boolean revealed) {
		this.card = card;
		this.suit = suit;
		this.revealed = revealed;
	}

	public int getCard() {
		return card;
	}

	public int getSuit() {
		return suit;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}

	public boolean getRevealed() {
		return revealed;
	}

	public String toString() {
		return revealed ? getCardString() : getBlankCardString();
	}

	private String getCardString() {
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

	private String getBlankCardString() {
		return String.format("┌─────────┐%n" +
				"│░░░░░░░░░│%n" +
				"│░░░░░░░░░│%n" +
				"│░░░░░░░░░│%n" +
				"│░░░░░░░░░│%n" +
				"│░░░░░░░░░│%n" +
				"│░░░░░░░░░│%n" +
				"│░░░░░░░░░│%n" +
				"└─────────┘");
	}

	public String[] toStringLines() {
		return toString().split("\n");
	}

	public char cardToChar(int c) {
		if (c <= 9) {
			return Character.forDigit(c, 10);
		}

		switch (c) {
			case 10:
				return 'T';
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
