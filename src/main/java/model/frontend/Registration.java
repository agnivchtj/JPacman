package model.frontend;

import model.backend.PasswordHasher;
import model.dao.DaoManager;
import model.dao.StatsDao;
import model.dao.UserDao;
import model.entity.Stats;
import model.entity.User;

public class Registration {

    private static transient DaoManager daoManager;
    private static transient PasswordHasher passwordHasher;
    private static transient User user;

    /**
     * Constructor.
     */
    public Registration() {

    }

    /**
     * Registers the user.
     * @param username The username for the registration
     * @param password The password for the registration
     * @return True if the user was successfully registered
     *          False otherwise
     */
    // statsDao is clearly defined and initialised.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static boolean userRegistration(String username, String password) {

        try {
            daoManager = new DaoManager();
            passwordHasher = new PasswordHasher();
            UserDao userDao = (UserDao) daoManager.getDao(DaoManager.Table.USER);
            user = new User(username);
            user.setHashedPassword(passwordHasher.hashPassword(password));
            user.setUserId(userDao.getLastUserIdAdded() + 1);
            user.setStats(new Stats(user.getUserId()));

            StatsDao statsDao = (StatsDao) daoManager.getDao(DaoManager.Table.STATS);
            Stats stat = new Stats(user.getUserId());
            stat.setTotalPoints(0);
            stat.setBlocksWalked(0);
            stat.setPelletsEaten(0);
            stat.setGamesPlayed(0);
            stat.setGamesWon(0);
            stat.setMaxLevel(0);
            stat.setMaxScore(0);

            if (userDao.save(user)) {
                statsDao.save(stat);
                daoManager.close();
                return true;
            }

            daoManager.close();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
