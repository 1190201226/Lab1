package P1;

import static org.junit.Assert.*;

import org.junit.Test;

public class MagicSquareTest {

	@Test
	public void isLegalMagicSquaretest() {
		assertEquals(true, MagicSquare.isLegalMagicSquare("src/P1/txt/1.txt"));
		assertEquals(true, MagicSquare.isLegalMagicSquare("src/P1/txt/2.txt"));
		assertEquals(false, MagicSquare.isLegalMagicSquare("src/P1/txt/3.txt"));
		assertEquals(false, MagicSquare.isLegalMagicSquare("src/P1/txt/4.txt"));
		assertEquals(false, MagicSquare.isLegalMagicSquare("src/P1/txt/5.txt"));
	}
	

	@Test
	public void generateMagicSquaretest() {
		assertEquals(false, MagicSquare.generateMagicSquare(-2));
		assertEquals(false, MagicSquare.generateMagicSquare(3));
		assertEquals(true, MagicSquare.generateMagicSquare(-5));
		assertEquals(false, MagicSquare.generateMagicSquare(4));
		
	}
}
