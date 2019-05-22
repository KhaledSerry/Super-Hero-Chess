package model.pieces.heroes;

import model.game.Game;
import model.game.Player;

public class Armored extends NonActivatablePowerHero {

	private boolean armorUp;
	final private String Type="Armored";
	public String getType() {
		return Type;
	}

	public Armored(Player player, Game game, String name) {
		super(player, game, name);
		this.armorUp = true;
		if(player.equals(game.getPlayer1())) {
			setImage("SQB.png");
		}else {
			setImage("GH.png");
		}
	}

	public boolean isArmorUp() {
		return armorUp;
	}

	public void setArmorUp(boolean armorUp) {
		this.armorUp = armorUp;
		if(!armorUp) {
			if(this.getOwner().equals(this.getGame().getPlayer1())) 
			{
				setImage("SQN.png");
			}
			else {
				setImage("GHD.png");
			}
		}
	}

	public String toString(){
		return "<html> Owner: "+this.getOwner().getName()+"<BR>"+"Name: " + this.getName()+" ("+this.Type+")"+"<BR>"+"Armor UP: "+this.isArmorUp()+"</html>";
	}
}
