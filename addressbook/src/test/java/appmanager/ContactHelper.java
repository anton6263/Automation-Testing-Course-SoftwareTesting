package appmanager;

import model.ContactData;
import model.Contacts;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void goToHomePage() {
        click(By.linkText("home"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("email"), contactData.getEmail());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("address"), contactData.getAddress());
    }

    public void goToCreateContactPage(){
        click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void acceptAlert() {
        wd.switchTo().alert().accept();
    }

    public void editContactById(int id) {
        wd.findElement(By.xpath("//a[@href='edit.php?id=" + id + "']")).click();
    }

    public void updateContact() {
        click(By.name("update"));
    }

    public void create(ContactData contact) {
        goToCreateContactPage();
        fillContactForm(contact);
        submitContactCreation();
        contactsCash = null;
        goToHomePage();
    }

    public void modify(ContactData contact) {
        editContactById(contact.getId());
        fillContactForm(contact);
        updateContact();
        contactsCash = null;
        goToHomePage();
    }

    public void delete (ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        acceptAlert();
        contactsCash = null;
        goToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    private boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException ex){
          return false;
        }

    }
    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=entry]"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String name = element.findElement(By.xpath(".//td[3]")).getText();
            String lastname = element.findElement(By.xpath(".//td[2]")).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(name).withLastname(lastname);
            contacts.add(contact);
        }
        return contacts;
    }

    public void selectGroupForContact(ContactData contact, GroupData group) {
        if (contact.getGroups().size() > 0)
            Assert.assertEquals(contact.getGroups().size(), 1);
        new Select(wd.findElement(By.name("to_group")))
                .selectByVisibleText(group.getName());
    }

    public void filterOfNoneGroups(){
        new Select(wd.findElement(By.name("group")))
                .selectByVisibleText("[none]");
    }

    private Contacts contactsCash = null;


    public Contacts all() {
        if (contactsCash != null) {
            return new Contacts(contactsCash);
        }
        contactsCash = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=entry]"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String lastname = element.findElement(By.xpath(".//td[2]")).getText();
            String name = element.findElement(By.xpath(".//td[3]")).getText();
            String allPhones = element.findElement(By.xpath(".//td[6]")).getText();
            String allEmail = element.findElement(By.xpath(".//td[5]")).getText();
            String address = element.findElement(By.xpath(".//td[4]")).getText();
            contactsCash.add(new ContactData().withId(id).withFirstname(name).withLastname(lastname)
                    .withAllPhones(allPhones).withAllEmail(allEmail).withAddress(address));
        }
        return new Contacts(contactsCash);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        editContactById(contact.getId());
        String name = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(name).withLastname(lastname)
                .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone).withEmail(email)
                .withEmail2(email2).withEmail3(email3).withAddress(address);
    }

    public void addToGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        selectGroupForContact(contact, group);
        click(By.name("add"));
        goToHomePage();
    }

    public void removeContact(ContactData contact) {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(contact.getGroups().iterator().next().getName());
        selectContactById(contact.getId());
        removeFromGroup();
    }

    private void removeFromGroup() {
        wd.findElement(By.name("remove")).click();
    }
}
