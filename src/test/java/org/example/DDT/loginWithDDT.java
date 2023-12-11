package org.example.DDT;

import org.example.Utils.UtilExcel;
import org.testng.annotations.Test;

public class loginWithDDT {

    @Test(dataProvider = "getData",dataProviderClass = UtilExcel.class)
    public void testLoginData(String username,String password){
        System.out.println("Username - "+username);
        System.out.println("Password - "+password);

    }
}
