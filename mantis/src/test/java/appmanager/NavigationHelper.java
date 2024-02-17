package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void managePage() {
        click(By.xpath("//div[@id='sidebar']/ul/li[6]/a/span"));
    }

    public void manageUsersPage() {
        click(By.xpath("//div[2]/div[2]/div/ul/li[2]/a"));
    }
}
