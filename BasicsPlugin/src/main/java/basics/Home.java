package basics;

import java.util.HashMap;
import basics.Pair;

public class Home {
    private Map<String, Pair> homes = new HashMap<>();
    public void setHome(String uuid, Pair pair) {
        homes.put(uuid, pair);
    }
    public Pair getHome(String uuid) {
        return homes.getOrDefault(uuid, null);
    }
}

