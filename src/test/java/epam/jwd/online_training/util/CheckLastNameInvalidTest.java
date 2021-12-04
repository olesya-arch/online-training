package epam.jwd.online_training.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class CheckLastNameInvalidTest {
    private String stringForValidation;

    public CheckLastNameInvalidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"ivanov"},
                {"Bow-"},
                {"SuShA"},
        });
    }

    @Test
    public void shouldReturnFalseWhenSurnameValid() throws Exception {
        assertFalse(Validator.checkLastName(stringForValidation));
    }
}
