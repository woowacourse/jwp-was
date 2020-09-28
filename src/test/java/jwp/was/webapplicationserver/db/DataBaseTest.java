package jwp.was.webapplicationserver.db;

import jwp.was.webapplicationserver.configure.ConfigureMaker;

public class DataBaseTest {

    private static final ConfigureMaker CONFIGURE_MAKER = ConfigureMaker.getInstance();
    private static final DataBase dataBase = CONFIGURE_MAKER.getConfigure(DataBase.class);

    public static void clear() {
        dataBase.clear();
    }
}
