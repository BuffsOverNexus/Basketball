package com.buffsovernexus;

public class CurrentSession {
    public static Long account_id;
    public static Long scenario_id;
    public static Long season_id;

    public static void logout() {
        account_id = null;
    }
    public static boolean isLoggedIn() { return account_id != null; }

}
