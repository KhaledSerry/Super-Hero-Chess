package model.pieces.heroes;

import java.awt.Point;

import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import exceptions.InvalidPowerUseException;
import exceptions.PowerAlreadyUsedException;
import exceptions.WrongTurnException;

public abstract class ActivatablePowerHero extends Hero {

	private boolean powerUsed;

	public ActivatablePowerHero(Player player, Game game, String name) {
		super(player, game, name);
	}

	public void usePower(Direction d, Piece target, Point newPos)
			throws InvalidPowerUseException, WrongTurnException {

		if (getOwner() != getGame().getCurrentPlayer())
			throw new WrongTurnException("Not your turn",this);

		if (powerUsed)
			throw new PowerAlreadyUsedException("You already used this power",this);

	}

	public boolean isPowerUsed() {
		return powerUsed;
	}

	public void setPowerUsed(boolean powerUsed) {
		this.powerUsed = powerUsed;
	}
	public String toString() {
		return super.toString() +"\n" + "Power Used:"+powerUsed;
	}
}
