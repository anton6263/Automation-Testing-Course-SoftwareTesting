package restsample;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class TestBase {

    private Executor getExecutor() {
        return Executor.newInstance().auth("b31e382ca8445202e66b03aaf31508a3", "");
    }

    protected Set<Issues> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json?limit=100"))
                .returnContent().asString();
        JsonElement parse = new JsonParser().parse(json);
        JsonElement issues = parse.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issues>>() {}.getType());
    }

    protected String getIssueStatus(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/" + issueId + ".json"))
                .returnContent().asString();
        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();
        JsonArray issues = parsed.getAsJsonArray("issues");
        JsonObject issue = issues.get(0).getAsJsonObject();
        return issue.get("state_name").getAsString();
    }

    protected int createIssue(Issues newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                        .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                                new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parse = new JsonParser().parse(json);
        return parse.getAsJsonObject().get("issue_id").getAsInt();
    }

    public boolean isIssueOpen(int issueId) throws IOException {
        if (getIssueStatus(issueId).equals("Closed")) {
            return false;
        }
        return true;
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
