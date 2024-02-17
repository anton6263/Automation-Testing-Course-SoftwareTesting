package appmanager;

import org.openqa.selenium.By;

public class SignUpHelper extends HelperBase {

    public SignUpHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[type='submit']"));
    }

    public void finish(String confirmationLink, String password, String name) {
        wd.get(confirmationLink);
        type(By.name("realname"), name);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }
}
