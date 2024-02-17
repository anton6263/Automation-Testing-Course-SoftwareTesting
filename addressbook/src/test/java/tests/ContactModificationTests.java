package tests;

import model.ContactData;
import model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    // Цикл на создание контакта если нет ни одного
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withLastname("Arteev").withEmail("test@test.com").withHomepage("https://github.com/anton6263"));
        }
        /* Получение списков из пользовательского интерфейса
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withLastname("Arteev").withEmail("test@test.com").withHomepage("https://github.com/anton6263"));
        }
        */
    }

    @Test
    public void testContactModification(){
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Anton").withLastname("Arteev").withEmail("test@test.com").withHomePhone("+7434555").withAddress("ул. Пушкина");
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUi();
    }
}
        /*
        Удаляем последний элемент
        before.remove(modifiedContact);

        Добавляем тот, который модифицорован
        before.add(contact);

        Сравниваем два списка до и после по максимальному индентификатору
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);


        Делаем множественные списки и сравниваем
        Assert.assertEquals(before, after);
        */
