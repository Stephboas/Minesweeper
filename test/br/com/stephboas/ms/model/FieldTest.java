package br.com.stephboas.ms.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.stephboas.ms.exception.ExceptionExplosion;

public class FieldTest {
	
	private Field field;
	
	@BeforeEach
	void startField() {
		field = new Field(3, 3);
	}
 	
	@Test
	void neighborDistanceTestLeft() {
		Field neighbor = new Field(3, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void neighborDistanceTestRight() {
		Field neighbor = new Field(3, 4);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void neighborDistanceTestOnTop() {
		Field neighbor = new Field(2, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void neighborDistanceTestDown() {
		Field neighbor = new Field(4, 3);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void diagonalDistanceTest() {
		Field neighbor = new Field(2, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void diagonalNoNeighboorTest() {
		Field neighbor = new Field(1, 2);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testDefaultValueCheckedAttribute() {
		assertFalse(field.isMarked());
	}
	
	@Test
	void testToggleMarkup() {
		assertFalse(field.isMarked());
	}
	
	@Test
	void testToggleMarkupTwoCalls() {
		field.switchMarkup();
		field.switchMarkup();
		assertFalse(field.isMarked());
	}
	
	@Test
	void TestOpenUnmarkedMineField() {
		assertTrue(field.open());
	}
	
	@Test
	void TestOpenMineField() {
		field.switchMarkup();
		field.undermine();
		assertFalse(field.open());
	}
	
	@Test
	void testOpenMineUnmarked() {
		field.undermine();
		
		assertThrows(ExceptionExplosion.class, () -> {
			field.open();	
		});
	}
	
	@Test
	void testOpenWithNeighbors() {
		Field neighbor01 = new Field(2, 2);
		Field neighbor02 = new Field(1, 1);
		Field neighbor03 = new Field(1, 1);
		neighbor03.undermine();
		
		neighbor01.addNeighbor(neighbor02);
		field.addNeighbor(neighbor01);
		
		field.open();
		
		assertTrue(neighbor01.isOpen() && neighbor03.isClosed());
	}
	
	@Test
	void testGoalAchieved() {
		Field testGoalAchieve01 = new Field(2, 2);
		Field testGoalAchieve02 = new Field(2, 4);
		
		testGoalAchieve01.goalAchieved();
		testGoalAchieve02.goalAchieved();
		
		assertFalse(testGoalAchieve01.isOpen() && testGoalAchieve01.undermine());
		assertFalse(testGoalAchieve02.isMarked());
		
	}
}

	