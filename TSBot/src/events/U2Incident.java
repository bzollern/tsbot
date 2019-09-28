package events;

import java.awt.Color;

import cards.CardList;
import game.GameData;

public class U2Incident extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Plane Shot Down Over USSR Territory")
		.setDescription("NASA press release states plane was a research aircraft")
		.setColor(Color.red);
		builder.changeVP(-1);
		builder.addField("Cover-up Story", "If either side plays " + CardList.getCard(32) + " for the event later this turn, the USSR gains 1 VP.", false);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "060";
	}

	@Override
	public String getName() {
		return "U-2 Incident";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "The USSR gains 1 VP. If UN Intervention is played for the event later this turn, the USSR gains 1 additional VP.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}
