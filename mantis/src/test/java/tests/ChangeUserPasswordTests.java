package tests;

import model.MailMessage;
import model.UserData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangeUserPasswordTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void changeUserPassword() throws MessagingException, IOException {
        String password = "password";
        app.logIn().asAdministrator("administrator", "root");
        app.goTo().managePage();
        app.goTo().manageUsersPage();
        UserData user = app.db().users().iterator().next();
        app.user().choose(user);
        String email = app.user().getEmail(user);
        app.user().resetPassword();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.user().confirmNewPassword(confirmationLink, password);
        assertTrue(app.newSession().loginWithNewPassword(user, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}
