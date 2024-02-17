package appmanager;

import model.UserData;
import org.openqa.selenium.By;

public class LogInHelper extends HelperBase {

    public LogInHelper(ApplicationManager app) {
        super(app);
    }

    public void asAdministrator(String username, String password) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        String currentUrl  = wd.getCurrentUrl();
        if (currentUrl.contains("account_page.php")) {
            wd.get(app.getProperty("web.baseUrl") + "/logout_page.php");
        }
        type(By.name("username"), username);
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }
}
