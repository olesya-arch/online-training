package epam.jwd.online_training.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CheckLastNameValidTest {
    private String stringForValidation;

    public CheckLastNameValidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"Kipelov"},
                {"Ford"},
                {"Orlova"},
        });
    }

    @Test
    public void shouldReturnTrueWhenSurnameValid() throws Exception {
        assertTrue(Validator.checkLastName(stringForValidation));
    }
}
