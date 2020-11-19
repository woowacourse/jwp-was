package jwp.was.webserver.db;

import jwp.was.webapplicationserver.configure.maker.ConfigureMaker;
import jwp.was.webapplicationserver.db.DataBase;

public class DataBaseTest {

    private static final ConfigureMaker CONFIGURE_MAKER = ConfigureMaker.getInstance();
    private static final DataBase dataBase = CONFIGURE_MAKER.getConfigure(DataBase.class);

    public static void clear() {
        dataBase.clear();
    }
}
