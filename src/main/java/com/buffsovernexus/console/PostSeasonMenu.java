package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.PostSeason;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Objects;

public class PostSeasonMenu {

    public PostSeasonMenu() {
        // First check if there is a post season based on the season id
        Session session = Database.sessionFactory.openSession();
        PostSeason postSeason = verifyPostSeason(session);
    }

    private PostSeason verifyPostSeason(Session session) {
        PostSeason postSeason = null;
        try {
            Transaction transaction = session.beginTransaction();
            postSeason = session.createQuery(
                    String.format("FROM PostSeason WHERE season='%s' and scenario = '%s'", CurrentSession.seasonId, CurrentSession.scenarioId),
                    PostSeason.class).uniqueResult();

            if (Objects.isNull(postSeason)) {
                // Create the postSeason
                postSeason = new PostSeason();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return postSeason;
    }
}
