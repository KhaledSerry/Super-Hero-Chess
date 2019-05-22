package model.pieces.sidekicks;

import model.game.Direction;
import model.game.Game;
import exceptions.UnallowedMovementException;

public class SideKickP2 extends SideKick {

	public SideKickP2(Game game, String name) {
		super(game.getPlayer2(), game, name);
		setImage("GP.png");
	}

	@Override
	public void moveUp() throws UnallowedMovementException {
		throw new UnallowedMovementException("Gazorpazorp can't go Up",this, Direction.UP);
	}

	@Override
	public void moveUpRight() throws UnallowedMovementException {
		throw new UnallowedMovementException("Gazorpazorp can't go this Way",this, Direction.UPRIGHT);
	}

	@Override
	public void moveUpLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException("Gazorpazorp can't go this Way",this, Direction.UPLEFT);
	}
}
