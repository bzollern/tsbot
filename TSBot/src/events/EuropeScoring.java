package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.Country;
import map.MapManager;

public class EuropeScoring extends Card {
	private static final int presence = 3;
	private static final int domination = 7;
	@Override
	public void onEvent(int sp, String[] args) {
		int[] totalCountries = {0,0};
		int[] battlegrounds = {0,0};
		String[] strings = {"","","",""};
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Europe Scoring")
			.setDescription("")
			.setColor(new Color(153,110,255))
			.setFooter("\"Yes, it is Europe, from the Atlantic to the Urals, "
					+ "it is Europe, it is the whole of Europe, "
					+ "that will decide the fate of the world.\"\n"
					+ "- Charles de Gaulle, 1959", Launcher.url("countries/fr.png"));
		int vp = 0;
		for (int i = 0; i<21; i++) { //Austria and Egypt, resp.
			Country c = MapManager.get(i);
			if (c.isControlledBy()!=-1) {
				totalCountries[c.isControlledBy()]++;
				if (c.isBattleground) {
					battlegrounds[c.isControlledBy()]++;
					strings[c.isControlledBy()+2] += c;
				}
				else {
					strings[c.isControlledBy()] += c;
				}
				if (c.id==14 && c.isControlledBy()==0) vp++; //ro
				if (c.id==13 && c.isControlledBy()==0) vp++; //pl
				if (c.id==7 && c.isControlledBy()==0) vp++; //fi
				if (c.id==3 && c.isControlledBy()==1) vp--; //ca
			}
		}
		builder.addField(":flag_us:", strings[0]+"|"+strings[2], false);
		builder.addField(":flag_su:", strings[1]+"|"+strings[3], false);
		vp += battlegrounds[0]-battlegrounds[1];
		if (battlegrounds[0]==5) {
			GameData.txtchnl.sendMessage(builder.build());
			GameData.endGame(0, 2);
			return;
		}
		else if (battlegrounds[1]==5) {
			GameData.txtchnl.sendMessage(builder.build());
			GameData.endGame(1, 2);
			return;
		}
		if (battlegrounds[0]>battlegrounds[1]&&totalCountries[0]>totalCountries[1]) vp += domination;
		else if (totalCountries[0]>0) vp += presence;
		
		if (battlegrounds[1]>battlegrounds[0]&&totalCountries[1]>totalCountries[0]) vp -= domination;
		else if (totalCountries[1]>0) vp -= presence;
		builder.changeVP(vp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "002";
	}

	@Override
	public String getName() {
		return "Europe Scoring";
	}

	@Override
	public int getOps() {
		return 0;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true; //no extra info needed
	}

	@Override
	public String getDescription() {
		return "Scores Europe on a scale of 3/7/autovictory. +1 for battlegrounds, +1 for each country you control that borders the other superpower (Canada, Finland, Poland, Romania).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}
