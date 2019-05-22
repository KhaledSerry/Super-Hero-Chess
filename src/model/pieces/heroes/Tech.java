package model.pieces.heroes;

import java.awt.Point;

import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import exceptions.InvalidPowerTargetException;
import exceptions.InvalidPowerUseException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

public class Tech extends ActivatablePowerHero {

	final private String Type="Tech";
	public String getType() {
		return Type;
	}
	public Tech(Player player, Game game, String name) {
		super(player, game, name);
		if(player.equals(game.getPlayer1())) {
			setImage("R.png");
		}else {
		setImage("EM.png");}
	}

	@Override
	public void moveUp() throws UnallowedMovementException {
		throw new UnallowedMovementException("FYI, Tech moves only diagonal",this, Direction.UP);
	}

	@Override
	public void moveDown() throws UnallowedMovementException {
		throw new UnallowedMovementException("FYI, Tech moves only diagonal",this, Direction.DOWN);
	}

	@Override
	public void moveRight() throws UnallowedMovementException {
		throw new UnallowedMovementException("FYI, Tech moves only diagonal",this, Direction.RIGHT);
	}

	@Override
	public void moveLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException("FYI, Tech moves only diagonal",this, Direction.LEFT);
	}

	@Override
	public void usePower(Direction d, Piece target, Point newPos)
			throws InvalidPowerUseException, WrongTurnException {

		super.usePower(d, target, newPos);

		// Teleport
		if (newPos != null) {

			if (target.getOwner() == getOwner()) {

				if (getGame().getCellAt(newPos.x, newPos.y).getPiece() != null) {
					throw new InvalidPowerTargetException("Are you blind, Someone is already there",this, target);
				} else {

					getGame().getCellAt(newPos.x, newPos.y).setPiece(target);
					getGame().getCellAt(target.getPosI(), target.getPosJ())
							.setPiece(null);
					target.setPosI(newPos.x);
					target.setPosJ(newPos.y);
					setPowerUsed(true);
					setSong("TTS.wav");
				}
			} else {
				throw new InvalidPowerTargetException("OMG, are helping the enemy, you cant teleport them",this, target);
			}
		} else {

			// Hack Power
			if (target instanceof Hero && target.getOwner() != this.getOwner()) {

				if (target instanceof Armored) {

					if (((Armored) target).isArmorUp()) {
						((Armored) target).setArmorUp(false);
						setPowerUsed(true);
						setSong("THS.wav");
					} else
						throw new InvalidPowerTargetException("His armor is already down, bruuh", this,target);
				}

				if (target instanceof ActivatablePowerHero) {

					if (!((ActivatablePowerHero) target).isPowerUsed()) {
						((ActivatablePowerHero) target).setPowerUsed(true);
						setPowerUsed(true);
						setSong("THS.wav");
					} else {
						throw new InvalidPowerTargetException("He already used his power before, cant hack 'em again", this,
								target);
					}
				}
				
			}

			// Restore Power
			if (target instanceof Hero && target.getOwner() == this.getOwner()) {

				if (target instanceof Armored) {

					if (!((Armored) target).isArmorUp()) {
						((Armored) target).setArmorUp(true);
						setPowerUsed(true);
						setSong("THS.wav");
					} else
						throw new InvalidPowerTargetException("His armor is still up", this,target);

				}

				if (target instanceof ActivatablePowerHero) {

					if (((ActivatablePowerHero) target).isPowerUsed()) {
						((ActivatablePowerHero) target).setPowerUsed(false);
						setPowerUsed(true);
						setSong("THS.wav");
					} else {
						throw new InvalidPowerTargetException("He hasn't used his power yet", this,target);
					}
				}
				
			}
		}
	}
	public String toString(){
		return "<html> Owner: "+this.getOwner().getName()+"<BR>"+"Name: " + this.getName()+" ("+this.Type+")"+"<BR>"+"Power Used: "+this.isPowerUsed()+"</html>";
	}
}
