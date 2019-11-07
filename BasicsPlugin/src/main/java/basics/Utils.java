package basics;

import io.anuke.arc.collection.Array;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.entities.type.Player;

public class Utils {

    public static String inArray(Player player, String arg) {
        Array<Player> players = Vars.playerGroup.all();
        for (Player other : players) {
            if (other.name.contains(arg.toLowerCase())) {
                player.setNet(other.x, other.y);
                return "[green]Found player: " + other.name + "\n";
            }
        }
        return "[scarlet]Sorry, it doesn't seem this player exists.. ";
    }

}
