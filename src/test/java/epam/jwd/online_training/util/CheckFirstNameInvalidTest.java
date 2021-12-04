package epam.jwd.online_training.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class CheckFirstNameInvalidTest {
    private String stringForValidation;

    public CheckFirstNameInvalidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest() {
        return Arrays.asList(new Object[][]{
                {"david"},
                {"Ser5gey"},
                {"Ole-sya"},
        });
    }

    @Test
    public void shouldReturnTrueWhenNameValid() throws Exception {
        assertFalse(Validator.checkFirstName(stringForValidation));
    }
}
