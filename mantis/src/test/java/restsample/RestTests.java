package restsample;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {

    @Test
    public void createIssueTests() throws IOException {
        skipIfNotFixed(334);
        Set<Issues> oldIssues = getIssues();
        Issues newIssue = new Issues().withSubject("Teeeest bug bug bug").withDescription("Fix it");
        int idIssue = createIssue(newIssue);
        Set<Issues> newIssues = getIssues();
        oldIssues.add(newIssue.withId(idIssue));
        assertEquals(newIssues, oldIssues);
    }
}
