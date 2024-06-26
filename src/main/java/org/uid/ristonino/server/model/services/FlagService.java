package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Flag;

import java.util.ArrayList;

public class FlagService {
    private ArrayList<Flag> flags;
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    private static FlagService instance = new FlagService();
    public static FlagService getInstance() {return instance; }

    public FlagService() {
        flags = db.getFlags();
    }
    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public boolean addFlag(Flag f) {
        return flags.add(f) && db.createFlag(f) >= 0;
    }

    public void removeFlag(Flag selectedItem) {
        flags.remove(selectedItem);
        db.removeFlag(selectedItem);
    }
}
