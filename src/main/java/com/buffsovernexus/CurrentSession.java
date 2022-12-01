package com.buffsovernexus;

import com.buffsovernexus.entity.Account;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Season;

public class CurrentSession {
    public static Long accountId, scenarioId, seasonId, postSeasonId;


    public static void logout() {
        accountId = null;
    }
    public static boolean isLoggedIn() { return accountId != null; }

}
