package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    private WebDriver wd;
    private String browser;
    private SignUpHelper signInHelper;
    private FtpHelper ftp;
    private MailHelper mail;
    private LogInHelper loginHelper;
    private NavigationHelper navigationHelper;
    private UserHelper userHelper;
    private DbHelper dbHelper;
    private SoapHelper soap;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if (wd != null) {
            wd.quit();
        }
    }

    public HttpSession newSession(){
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public SignUpHelper signUp() {
        if (signInHelper == null) {
            signInHelper = new SignUpHelper(this);
        }
        return signInHelper;
    }

    public LogInHelper logIn() {
        if (loginHelper == null) {
            loginHelper = new LogInHelper(this);
        }
        return loginHelper;
    }

    public NavigationHelper goTo() {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(this);
        }
        return navigationHelper;
    }

    public UserHelper user() {
        if (userHelper == null) {
            userHelper = new UserHelper(this);
        }
        return userHelper;
    }

    public FtpHelper ftp(){
        if(ftp == null) {
            ftp = new FtpHelper(this);
        }
        return ftp;
    }

    public DbHelper db() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(this);
        }
        return dbHelper;
    }

    public WebDriver getDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        if (wd == null){
            if (browser.equals(BrowserType.CHROME)) {
                wd = new ChromeDriver(options);
            } else {
                wd = new FirefoxDriver();
            }
            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wd.get(properties.getProperty("web.baseUrl"));
        }
        return wd;
    }

    public MailHelper mail() {
        if(mail == null) {
            mail = new MailHelper(this);
        }
        return mail;
    }

    public SoapHelper soap() {
        if(soap == null) {
            soap = new SoapHelper(this);
        }
        return soap;
    }
}
