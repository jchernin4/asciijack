/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package asciijack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class AppTest {
	@Test
	void appCalcHands() {
		App app = new App();

		List<Card> testHand = new ArrayList<>();
		testHand.add(new Card(14, 0));
		testHand.add(new Card(10, 0));

		int calc = app.calcHand(testHand);
		assertEquals(calc, 21, "Calc was actually " + calc);

		testHand.add(new Card(4, 0));

		calc = app.calcHand(testHand);
		assertEquals(calc, 15, "Calc was actually " + calc);

		testHand.add(new Card(11, 0));
		calc = app.calcHand(testHand);
		assertEquals(calc, 25, "Calc was actually " + calc);

		testHand.add(new Card(13, 0));
		calc = app.calcHand(testHand);
		assertEquals(calc, 35, "Calc was actually " + calc);
	}
}
