package br.com.stephboas.ms.model;

import java.util.ArrayList;
import java.util.List;

import br.com.stephboas.ms.exception.ExceptionExplosion;

public class Field {
	
	private final int line;
	private final int column;
	
	private boolean open = false;
	private boolean mine = false;
	private boolean marked = false;
	
	private List<Field> neighbors = new ArrayList<>();
	
	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	boolean addNeighbor(Field neighbor) {
		boolean differentLine = line != neighbor.line;
		boolean differentColumn = line != neighbor.column;
		boolean diagonal = differentLine && differentColumn;
		
		int deltaLine = Math.abs(line) - neighbor.line;
		int deltaColumn = Math.abs(column) - neighbor.column;
		int deltaInGeneral = deltaColumn + deltaLine;
		
		if(deltaInGeneral == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		}else if(deltaInGeneral == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		} else {
			return false;
		}
	}
	
	void switchMarkup() {
		if(!open) {
			marked = !marked;
		}
	}
	
	boolean open() {
		if(!open && !marked) {
			open = true;
			
			if(mine) {
				throw new ExceptionExplosion();
			}
			
			if(safeNeighborhood()) {
				neighbors.forEach(n -> n.open());
			}
			return true;
		} else {
			return false;
		}
	}
	
	boolean safeNeighborhood() {
		return neighbors.stream().noneMatch(n -> n.mine);
	}
	
	boolean undermine() {
		if(!mine) {
			mine = true;
		}
		
		return false;
	}
	
	public boolean isUndermine() {
		return mine;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	void setOpen(boolean open) {
		this.open = open;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isClosed() {
		return !isOpen();
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	boolean goalAchieved() {
		boolean discoveredField = !mine && open;
		boolean protectedField = mine && marked;
		return discoveredField || protectedField;
	}
	
	long minesInTheNeighborhood() {
		return neighbors.stream().filter(n -> n.mine).count();
	}
	
	void restart() {
		open = false;
		mine =  false;
		marked = false;
	}
	
	public String toString() {
		if(marked) {
			return "x";
		} else if(open && mine) {
			return "*";
		} else if(open && minesInTheNeighborhood() > 0) {
			return Long.toString(minesInTheNeighborhood());
		} else if(open) {
			return " ";
		} else {
			return "?";
		}
	}
}
