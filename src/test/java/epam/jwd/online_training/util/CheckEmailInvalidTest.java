package epam.jwd.online_training.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class CheckEmailInvalidTest {
    private String stringForValidation;

    public CheckEmailInvalidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"@gmail.com"},
                {"lesik@"},
                {".@mail.ru"},
        });
    }

    @Test
    public void shouldReturnTrueWhenEmailValid() throws Exception {
        assertFalse(Validator.checkEmail(stringForValidation));
    }
}
