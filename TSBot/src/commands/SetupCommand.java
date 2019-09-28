package commands;

import java.util.Arrays;
import java.util.List;

import cards.Operations;
import game.GameData;
import game.PlayerList;
import map.MapManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetupCommand extends Command {

	public static boolean USSR = false;
	public static boolean USA = false;
	public static boolean hreq = false;
	public static int handicap = 2;
	
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (!GameData.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (!(USSR||USA)) {
			sendMessage(e, "That's not the part of the game you're looking for.");
			return;
		}
		if (hreq) {
			GameData.ops = new Operations(USA?0:1, Math.abs(handicap), true, false, false, false, false);
		}
		else if (USSR&&e.getAuthor().equals(PlayerList.getSSR())) {
			GameData.ops = new Operations(1, 6, true, false, false, false, false);
		}
		else if (USA&&e.getAuthor().equals(PlayerList.getUSA())) {
			GameData.ops = new Operations(0, 7, true, false, false, false, false);
		}
		else {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? China's abstracted as a card and the rest of the world has a board space each.");
			return;
		}
		if (args.length%2!=1) {
			sendMessage(e, ":x: An influence value must be associated with every listed country.");
			return;
		}
		int[] countries = new int[(args.length-1)/2];
		int[] amt = new int[(args.length-1)/2];
		for (int i=1; i<args.length; i+=2) {
			countries[(i-1)/2] = MapManager.find(args[i]);
			if (countries[(i-1)/2]==-1) {
				sendMessage(e, ":x: "+args[i]+" isn't a country or alias of one.");
				return;
			}
			if (!hreq) {
				if (countries[(i-1)/2] > 20) {
					sendMessage(e, ":x: You sure you aren't going to reinforce your positions in Europe?");
					return;
				}
				if ((USSR && MapManager.get(countries[(i-1)/2]).region==1)||(USA && MapManager.get(countries[(i-1)/2]).region==2)) {
					sendMessage(e, ":x: Stay on your side of the curtain.");
					return;
				}
			}
			else {
				if ((USSR&&MapManager.get(countries[(i-1)/2]).influence[1]==0)||(USA&&MapManager.get(countries[(i-1)/2]).influence[0]==0)) {
					sendMessage(e, ":x: Handicap influence can only be placed within countries that already hold your influence.");
					return;
				}
			}
			try {
				amt[(i-1)/2] = Integer.parseInt(args[i+1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Those aren't numbers.");
				return;
			}
			if (amt[(i-1)/2] <=0) {
				sendMessage(e, ":x: Positive numbers only.");
				return;
			}
		}
		boolean result = GameData.ops.influence(countries, amt);
		if (!result) {
			return;
		}
		if (USSR) {
			USSR = false;
			USA = true;
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", please place seven influence markers in Western Europe. (Use TS.setup)").complete();
		}
		else if (USA) {
			USA = false;
			if (handicap==0) {
				GameData.endSetupPhase();
				GameData.txtchnl.sendMessage("Both players may now select their headlines. (`TS.play **[card]** h`)").complete();
			}
			else {
				hreq = true;
				if (handicap>0) {
					USA = true;
					GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", please place your handicap influence. (Use TS.setup)").complete();
				}
				else {
					USSR = true;
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", please place your handicap influence. (Use TS.setup)").complete();
				}
			}
		}
		else if (hreq) {
			handicap = 0;
			hreq = false;
			USSR = false;
			USA = false;
			GameData.endSetupPhase();
			GameData.txtchnl.sendMessage("Both players may now select their headlines. (`TS.play **[card]** h`)").complete();
		}
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.setup");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Allows the player to place their starting influence.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Setup influence";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.setup <country> <influence> ad infinitum. You will be prompted to use this command when needed.\n"
				+ "- Example: TS.setup ddr 1 polska 4 bg 1");
	}

}
