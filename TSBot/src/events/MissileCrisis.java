package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;

public class MissileCrisis extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle(sp==0?"Turkish Missile Crisis!":"Cuban Missile Crisis!")
		.setDescription(sp==0?"American Missiles Threaten Moscow":"Soviet Missiles in Cuba Within Striking Distance of Washington")
		.setFooter("", "")
		.setColor(sp==0?Color.blue:Color.red);
		builder.changeDEFCON(2-GameData.getDEFCON());
		builder.addField("One Minute to Midnight", "**All attempts by " + (sp==0?"the USSR":"the USA") + "to conduct a coup will lose them the game by Thermonuclear War.**", false);
		HandManager.addEffect(400+((sp+1)%2));
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "040";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Cuban Missile Crisis";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "**Sets DEFCON to 2**. For the rest of the turn, any coup conducted by your opponent will immediately cause them to lose the game by thermonuclear war. *This effect can be cancelled earlier at any point if the affected opponent removes two influence from a certain country: the USSR must do so from Cuba, and the USA can select one of West Germany or Turkey.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: The word `resolve` and a country. For the USSR, must write a valid alias for Cuba. For the USA, the country can be either West Germany or Turkey. May be performed at any moment.";
	}

}
