package br.com.stephboas.ms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.stephboas.ms.exception.ExceptionExplosion;

public class Board {
	
	private int lines;
	private int columns;
	private int mines;
	
	private final List<Field> fields = new ArrayList<>();
	
	public Board(int lines, int columns, int mines) {
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		connectNeighbors();
		randomMines();
	}
	
	public void open(int line, int column) {
		try {
			fields.parallelStream().filter(f -> f.getLine() == line && f.getColumn() == column)
			.findFirst()
			.ifPresent(f -> f.open());
			
		} catch(ExceptionExplosion e) {
			fields.forEach(f -> f.setOpen(true));
			throw e;
		}
	}
	
	public void marked(int line, int column) {
		fields.parallelStream().filter(f -> f.getLine() == line && f.getColumn() == column)
		.findFirst()
		.ifPresent(f -> f.switchMarkup());
	}

	private void generateFields() {
		for(int line = 0; line < lines; line++) {
			for(int column = 0; column < columns; column++) {
				fields.add(new Field(line, column));
			}
		}
	}
	
	private void connectNeighbors() {
		for(Field c1: fields) {
			for(Field c2: fields) {
				c1.addNeighbor(c2);
			}
		}
	}
	
	private void randomMines() {
		long readyMines = 0;
		Predicate<Field> mine = f -> f.isUndermine();
		
		do {
			readyMines = fields.stream().filter(mine).count();
			int random = (int) (Math.random() * fields.size());
			fields.get(random).undermine();
		} while(readyMines < mines);
	}
	
	public boolean goalAchieved() {
		return fields.stream().allMatch(f -> f.goalAchieved());
	}
	
	public void restart() {
		fields.stream().forEach(f -> f.restart());
		randomMines();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for(int c = 0; c < columns; c++) {
				sb.append(" ");
				sb.append(c);
				sb.append(" ");
			}
		
			sb.append("\n");
		
			int i = 0;
			for(int l = 0; l < lines; l++) {
				sb.append(1);
				sb.append(" ");
				for(int c = 0; c < columns; c++) {
					sb.append(" ");
					sb.append(fields.get(i));
					sb.append(" ");
					i++;
				}
				
				sb.append("\n");
			}
		return sb.toString();
	}
}
