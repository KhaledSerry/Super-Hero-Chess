package model.pieces.heroes;

import java.awt.Point;

import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import exceptions.InvalidPowerTargetException;
import exceptions.InvalidPowerUseException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

public class Medic extends ActivatablePowerHero {

	final private String Type="Medic";
	public String getType() {
		return Type;
	}
	public Medic(Player player, Game game, String name) {
		super(player, game, name);
		if(player==game.getPlayer1()) {
			setImage("PB.png");
		}else {
			setImage("JC.png");
		}
		setSong("Medic.wav");
	}

	@Override
	public void moveUpRight() throws UnallowedMovementException {
		throw new UnallowedMovementException("Medic doesn't move diagonal",this, Direction.UPRIGHT);
	}

	@Override
	public void moveUpLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException("Medic doesn't move diagonal",this, Direction.UPLEFT);
	}

	@Override
	public void moveDownRight() throws UnallowedMovementException {
		throw new UnallowedMovementException("Medic doesn't move diagonal",this, Direction.DOWNRIGHT);
	}

	@Override
	public void moveDownLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException("Medic doesn't move diagonal",this, Direction.DOWNLEFT);
	}

	@Override
	public void usePower(Direction direction, Piece target, Point newPos)
			throws InvalidPowerUseException, WrongTurnException {

		super.usePower(direction, target, newPos);

		Point destination = getDirectionPos(new Point(getPosI(), getPosJ()),
				direction);
		adjustBounds(destination);
		Cell destinaionCell = getGame().getCellAt(destination.x, destination.y);

		if (destinaionCell.getPiece() == null) {
			if (target.getOwner() != getOwner()) {
				throw new InvalidPowerTargetException("You can't rev an enemy",this, target);
			}

			if (getOwner().getDeadCharacters().contains(target)) {

				destinaionCell.setPiece(target);

				if (target instanceof ActivatablePowerHero) {
					((ActivatablePowerHero) target).setPowerUsed(false);

				}
				if (target instanceof Armored)
					((Armored) target).setArmorUp(true);

				target.setPosI(destination.x);
				target.setPosJ(destination.y);
				getOwner().getDeadCharacters().remove(target);
				setPowerUsed(true);
				getGame().switchTurns();

			} else {
				throw new InvalidPowerTargetException("He's still alive, dude", this, target);
			}

		} else {
			throw new InvalidPowerTargetException("Someone already there",this, target);
		}

	}

	public String toString(){
		return "<html> Owner: "+this.getOwner().getName()+"<BR>"+"Name: " + this.getName()+" ("+this.Type+")"+"<BR>"+"Power Used: "+this.isPowerUsed()+"</html>";
	}
}
