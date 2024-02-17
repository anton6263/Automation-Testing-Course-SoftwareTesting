package appmanager;

import model.UserData;
import org.openqa.selenium.By;

public class UserHelper extends HelperBase {

    public UserHelper(ApplicationManager app) {
        super(app);
    }

    public void choose(UserData id) {
        click(By.cssSelector("a[href='manage_user_edit_page.php?user_id=" + id.getId() + "']"));
    }

    public void resetPassword() {
        click(By.cssSelector("input[value='Сбросить пароль']"));
    }

    public String getEmail(UserData userEmail) {
        return userEmail.getEmail();
    }

    public void confirmNewPassword (String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }
}
