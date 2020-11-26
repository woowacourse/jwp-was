package application.controller;

import application.db.DataBase;
import org.junit.jupiter.api.BeforeEach;

public abstract class UserControllerTest {

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }
}
