package basics;

import io.anuke.arc.*;
import io.anuke.arc.collection.Array;
import io.anuke.arc.util.*;
import io.anuke.mindustry.entities.type.*;
import io.anuke.mindustry.game.EventType.*;
import io.anuke.mindustry.game.Team;
import io.anuke.mindustry.gen.*;
import io.anuke.mindustry.plugin.Plugin;
import io.anuke.mindustry.Vars;
import basics.Utils;
import basics.Home;
import basics.Pair;

import java.lang.*;



public class BasicsPlugin extends Plugin{
    Home home;
    public BasicsPlugin(){
        home = new Home();
        Events.on(GameOverEvent.class, event -> {
            home.clear();
        });

        //Events.on(PlayerConnect.class, event -> {
            //event.player.sendMessage("[yellow]Welcome to the server! Here is our discord: [cyan]https://discord.gg/9999999 \n");
        //});
    }

    @Override
    public void registerClientCommands(CommandHandler handler){

        handler.<Player>register("ping","A simple ping command that plays table tennis.", (args, player) -> {
            player.sendMessage("Pong!");
        });

        handler.<Player>register("pos","Relays your location as 'x , y'.", (args, player) -> {
            player.sendMessage("location: " + player.x + " " + player.y);
        });

        handler.<Player>register("sethome", "Sets the home location.", (args, player) -> {
            Pair<Float, Float> loc = new Pair<>(player.getX(), player.getY());
            home.setHome(player.getInfo().id, loc);
            Call.sendMessage("Home set at: " + player.getX() +", " + player.getY());
        });

        handler.<Player>register("home", "<player>", "Teleports you home", (args, player) -> {
            if(args.length() > 0) {
                if(!(player.isAdmin())) {
                    player.sendMessage("Only admins can teleport to other people's home!");
                    return;
                }
                Player other;
                for(Player p: Vars.playerGroup.all()) {
                    if(p.name.toLowerCase().contains(args[0].toLowerCase()) {
                     player other = p;
                     break;
                    }
                }
                if(other == null) {
                    player.sendMessage("Player not found!");
                    return;
                }
                Pair<Float, Float> loc = home.getHome(player.getInfo().id);
                if(loc == null) {
                    player.sendMessage("Player have not set a home yet!");
                    return;
                }
                player.setNet(loc.getFirst(), loc.getSecond());
                player.set(loc.getFirst(), loc.getSecond());
                return;
            }
            Pair<Float, Float> loc = home.getHome(player.getInfo().id);
            if(loc == null) {
                player.sendMessage("You have not yet set a home!");
                return;
            }
            player.setNet(loc.getFirst(), loc.getSecond());
            player.set(loc.getFirst(), loc.getSecond());
        });

        handler.<Player>register("gameover", "Immediately ends game if the player is an admin.", (args, player) -> {
            if(player.isAdmin) {
                Call.sendMessage("[scarlet]Server: " + player.name + "[yellow] can't be patient and they skipped the map.");
                Events.fire(new GameOverEvent(Team.crux));
                return;
            }
            player.sendMessage("[scarlet]You must be an admin to use this command.");

        });

        handler.<Player>register("suicide", "This command kills you.", (args, player) -> {
            player.kill();
        });

        handler.<Player>register("tp", "<player>", "Teleports you to player", (args, player) -> {
            player.sendMessage(Utils.inArray(player, args[0].toLowerCase()));
        });
    }
}
