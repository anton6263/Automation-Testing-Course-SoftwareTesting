package tests;

import model.ContactData;
import model.GroupData;
import model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void ensurePrecondition() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
            if (app.db().contacts().size() == 0) {
                app.contact().create(new ContactData()
                        .withFirstname("Anton").withLastname("Arteev").withEmail("test@test.com").withAddress("address").withHomePhone("+79433333"));
            }
        }
    }

    @Test
    public void testRemoveContactFromGroup() {

        ContactData contact = app.db().contacts().iterator().next();
        if (contact.getGroups().size() == 0) {
            GroupData group = app.db().groups().iterator().next();
            app.contact().addToGroup(contact, group);
        }
        int id = contact.getId();
        ContactData contactDelete = app.db().contactsInGroup(id);
        Groups group = contactDelete.getGroups();
        app.contact().removeContact(contactDelete);
        assertThat(app.db().contactsInGroup(contact.getId()).getGroups().contains(group), equalTo(false));
    }
}
