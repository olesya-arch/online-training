package epam.jwd.online_training.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CheckFirstNameValidTest {
    private String stringForValidation;

    public CheckFirstNameValidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"David"},
                {"Sergey"},
                {"Olesya"},
        });
    }

    @Test
    public void shouldReturnTrueWhenNameValid() throws Exception {
        assertTrue(Validator.checkFirstName(stringForValidation));
    }
}
