package epam.jwd.online_training.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CheckPasswordValidTest {
    private String stringForValidation;

    public CheckPasswordValidTest(String stringForValidation){
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"1111OOOO"},
                {"dkfjwrclf7455"},
                {"RkdhTa1245"},
        });
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsValid() throws Exception {
        assertTrue(Validator.checkPassword(stringForValidation));
    }
}
