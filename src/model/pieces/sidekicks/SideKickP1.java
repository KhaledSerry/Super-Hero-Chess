package model.pieces.sidekicks;

import model.game.Direction;
import model.game.Game;
import exceptions.UnallowedMovementException;

public class SideKickP1 extends SideKick {

	public SideKickP1(Game game, String name) {
		super(game.getPlayer1(), game, name);
		setImage("MK.png");
	}

	@Override
	public void moveDown() throws UnallowedMovementException {
		throw new UnallowedMovementException("Meeseeks can't go Down",this, Direction.DOWN);
	}

	@Override
	public void moveDownRight() throws UnallowedMovementException {
		throw new UnallowedMovementException("Meesesks can't go This way",this, Direction.DOWNRIGHT);
	}

	@Override
	public void moveDownLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException("Meeseks can't go This way",this, Direction.DOWNLEFT);
	}
	
	
}
