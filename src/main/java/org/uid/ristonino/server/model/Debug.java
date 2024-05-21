package org.uid.ristonino.server.model;

public class Debug {
    public static final boolean IS_ACTIVE = true;
    public static final double width = 1200;
    public static final double height = 600;

    private static final Debug instance = new Debug();
    private Debug() {}
    public static Debug getInstance() {return instance; }

    public static void print(String message) {
        if (IS_ACTIVE) {
            System.out.println(message);
        }
    }
}
