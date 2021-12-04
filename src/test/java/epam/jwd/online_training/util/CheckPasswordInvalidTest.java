package epam.jwd.online_training.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class CheckPasswordInvalidTest {
    private String stringForValidation;

    public CheckPasswordInvalidTest(String stringForValidation){
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> valuesForTest(){
        return Arrays.asList(new Object[][]{
                {"1111*-OOOO"},
                {"dkflf11.7455"},
                {"R,hTa1245"},
                {"s s l"},
                {"kidhdb^jsb/54"},
        });
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsValid() throws Exception {
        assertFalse(Validator.checkPassword(stringForValidation));
    }
}
