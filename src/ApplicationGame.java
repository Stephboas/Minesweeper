import br.com.stephboas.ms.model.Board;
import br.com.stephboas.ms.vision.TerminalBoard;

public class ApplicationGame {

	public static void main(String[] args) {

		Board board = new Board(6, 6, 3);
		new TerminalBoard(board);
	}

}
