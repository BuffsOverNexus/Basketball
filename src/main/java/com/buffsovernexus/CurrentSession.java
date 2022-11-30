package com.buffsovernexus;

import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Season;

public class CurrentSession {
    public static Long account_id;
    public static Long scenario_id;
    public static Long season_id;

    public static void logout() {
        account_id = null;
    }
    public static boolean isLoggedIn() { return account_id != null; }

    public static Season season;
    public static Scenario scenario;

}
