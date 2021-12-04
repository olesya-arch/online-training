package epam.jwd.online_training.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CheckEmailValidTest {
    private String stringForValidation;

    public CheckEmailValidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"lesik974@gmail.com"},
                {"hgtf10@test.net"},
                {"ivanov5@mail.ru"},
        });
    }

    @Test
    public void shouldReturnTrueWhenEmailValid() throws Exception {
        assertTrue(Validator.checkEmail(stringForValidation));
    }
}
