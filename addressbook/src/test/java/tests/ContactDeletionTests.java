package tests;

import model.ContactData;
import model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    // Цикл на создание контакта если нет ни одного
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withLastname("Arteev").withEmail("test@test.com").withHomepage("https://github.com/anton6263"));
        }
        /*
        if (app.contact().list().size() == 0){
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withLastname("Arteev").("test@test.com").withHomepage("https://github.com/anton6263"));
            app.goTo().homePage();
        }
        */
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(before.size() -1));
        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before.without(deletedContact)));
        verifyContactListInUi();
    }
}

        /*
        Старый список (before) содержит на один элемент больше, чем новый (after), соответственно нам нужно удалить элемент старого списка, чтобы их сравнить
        before.remove(contact);
        Сравниваем два списка (Среда разработки сама сравнивает без цикла)
        Assert.assertEquals(before, after);
        */
