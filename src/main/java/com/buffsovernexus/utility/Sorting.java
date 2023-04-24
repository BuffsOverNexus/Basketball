package com.buffsovernexus.utility;

import com.buffsovernexus.entity.SeasonTeam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorting {

    public static void sortSeason(List<SeasonTeam> seasonTeams) {
        seasonTeams.sort(Comparator.comparingDouble(SeasonTeam::getPercentage).reversed());
    }
}
