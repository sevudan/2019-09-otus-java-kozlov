package ru.otus.atm;


public class Execute {

    public static void main(String[] args) throws Exception{

        Atm amtAction = new AtmImpl();

        amtAction.addCash(5,100); //500
        amtAction.addCash(2,500); //1000
        amtAction.addCash(1,1000); //3000
        amtAction.addCash(1,5000); //10000
        amtAction.getCash(500);
        System.out.printf("%s %s", amtAction.getTotalCash().get("TotalMoney"), amtAction.getTotalCash().get("TotalBanknotes"));
    }
}
