package tests;

import model.ContactData;
import model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactInGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
            if (app.db().contacts().size() == 0) {
                app.contact().create(new ContactData()
                        .withFirstname("Anton").withLastname("Arteev").withEmail("test@test.com").withAddress("address").withHomePhone("+79433333"));
            }
        }
        app.contact().filterOfNoneGroups();
        Set<ContactData> contactsWithoutGroup = app.contact().all();
        if (contactsWithoutGroup.size() == 0){
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withLastname("Arteev").withEmail("test@test.com").withAddress("address").withHomePhone("+79433333"));
            app.goTo().homePage();
            app.contact().filterOfNoneGroups();
        }
    }

    @Test
    public void testAddContactInGroup() {
        Set<ContactData> contacts = app.contact().all();
        ContactData contactNoneGroup = contacts.iterator().next();
        int contactId = contactNoneGroup.getId();
        ContactData contact = app.db().contactsInGroup(contactId);
        GroupData group = app.db().groups().iterator().next();
        app.contact().addToGroup(contact, group);
        assertThat(app.db().contactsInGroup(contact.getId()).getGroups().contains(group), equalTo(true));
        verifyContactListInUi();
    }
}