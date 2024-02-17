package tests;

import appmanager.ApplicationManager;
import model.ContactData;
import model.Contacts;
import model.GroupData;
import model.Groups;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {
    // Logger logger = LoggerFactory.getLogger(TestBase.class);


    protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet())));
        }
    }

    public void verifyContactListInUi() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contacts();
            Contacts uiContacts = app.contact().all();
            assertThat(uiContacts, equalTo(dbContacts.stream().map((g) -> new ContactData().withId(g.getId())
                    .withFirstname(g.getFirstname()).withLastname(g.getLastname())
                    .withEmail(g.getEmail()))
                    .collect(Collectors.toSet())));
        }
    }

    /*
    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("Start test" + m.getName() + "with parametrs" + Arrays.asList(p));
    }

    @AfterMethod
    public void logTestStop(Method m) {
        logger.info("Stop test" + m.getName());
    }
     */
}
