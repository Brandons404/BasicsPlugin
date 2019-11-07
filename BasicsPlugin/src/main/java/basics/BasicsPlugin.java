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

import java.lang.*;



public class BasicsPlugin extends Plugin {

    public BasicsPlugin(){

        Events.on(GameOverEvent.class, event -> {
            home.setHome(0, 0);
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
            player.sendMessage("location: " + (int)player.x + " " + (int)player.y);
        });

        handler.<Player>register("sethome", "Sets the home location.", (args, player) -> {
            home.setHome((int)player.x, (int)player.y);
            Call.sendMessage("Home set at: " + home.getX() +", " + home.getY());
        });

        handler.<Player>register("home","Teleports you home", (args, player) -> {
            if (home.getX() == 0 && home.getY() == 0) {
                player.sendMessage("There's no home set! Set a home location with [green]/sethome[].");
            } else {
                player.setNet(home.getX(), home.getY());
                player.set(home.getX(), home.getY());
            }
        });

        handler.<Player>register("gameover", "Immediately ends game if the player is an admin.", (args, player) -> {
            if(player.isAdmin) {
                Call.sendMessage("[scarlet]Server: " + player.name + "[yellow] can't be patient and they skipped the map.");
                Events.fire(new GameOverEvent(Team.crux));
            } else {
                player.sendMessage("[scarlet]You must be an admin to use this command.");
            }
        });

        handler.<Player>register("suicide", "This command kills you.", (args, player) -> {
            player.kill();
        });

        handler.<Player>register("tp", "<player>", "Teleports you to player", (args, player) -> {
            player.sendMessage(inArray(player, args[0].toLowerCase()));
        });
    }
}
