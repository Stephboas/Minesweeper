package br.com.stephboas.ms.vision;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.stephboas.ms.exception.ExceptionExplosion;
import br.com.stephboas.ms.exception.LeaveException;
import br.com.stephboas.ms.model.Board;

public class TerminalBoard {

	private Board board;
	private Scanner input = new Scanner(System.in);
	
	public TerminalBoard(Board board) {
		this.board = board;
		
		runGame();
	}

	private void runGame() {
		try {
			boolean continueGame = true;
			
			while(continueGame) {
				cicloDoJogo();
				
				System.out.println("Outra partida? (S/n) ");
				String resposta = input.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continueGame = false;
				} else {
					board.restart();
				}
			}
		} catch (LeaveException e) {
			System.out.println("Tchau!!!");
		} finally {
			input.close();
		}
	}

	private void cicloDoJogo() {
		try {
			
			while(!board.goalAchieved()) {
				System.out.println(board);
				
				String digitado = capturarValorDigitado("Digite (x, y): ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
					.map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");
				
				if("1".equals(digitado)) {
					board.open(xy.next(), xy.next());
				} else if("2".equals(digitado)) {
					board.marked(xy.next(), xy.next());
				}
			}
			
			System.out.println(board);
			System.out.println("Você venceu!!!");
		} catch(ExceptionExplosion e) {
			System.out.println(board);
			System.out.println("Game Over!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = input.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new LeaveException();
		}
		
		return digitado;
	}
}
