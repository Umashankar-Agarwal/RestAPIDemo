package files;

import org.testng.annotations.DataProvider;

public class dataprovider {

    @DataProvider(name = "LibraryAPIData")
    public static Object[][] parameterLibraryAPI() {
        return new Object[][]{
            {
                "ram", "1231"
            },
            {
                "rani", "13123"
            },
            {
                "karan", "1412321"
            }

        };
    }
}
