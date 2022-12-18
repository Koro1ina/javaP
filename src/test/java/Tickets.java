import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tickets {

    @Test
    // тесты с позитивным сценарием
        public void difference60()
        {
            double actualResult = getRefund("13.10.2003", "13:20", 1200.00, "14.08.2003", "13:20" );
            if (actualResult == 1){
                System.out.println("Неверно введены данные");
            }
            Assert.assertEquals(1200.0, actualResult);

    }

    @Test
    public void difference40()
    {
        double actualResult = getRefund("13.10.2003", "13:20", 1200.00, "03.09.2003", "13:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(1200.0, actualResult);

    }

    @Test
    public void difference20()
    {
        double actualResult = getRefund("13.10.2003", "13:20", 1200.00, "23.09.2003", "13:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(840.0, actualResult);

    }

    @Test
    public void difference5()
    {
        double actualResult = getRefund("13.10.2003", "13:20", 1200.00, "08.10.2003", "13:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(480.0, actualResult);

    }

    @Test
    public void difference1Day()
    {
        double actualResult = getRefund("13.10.2003", "13:20", 1200.00, "12.10.2003", "13:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(240.0, actualResult);

    }

    @Test
    public void difference23hour()
    {
        double actualResult = getRefund("13.10.2003", "13:20", 1200.00, "12.10.2003", "14:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(0.0, actualResult);

    }

    // тесты с негативным сценарием

    @Test
    public void incorrectDateEntry()
    {
        double actualResult = getRefund("13.10.03", "13:20", 1200.00, "12.10.2003", "14:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(1.0, actualResult);

    }

    @Test
    public void incorrectTimeEntry()
    {
        double actualResult = getRefund("13.10.2003", "13:2", 1200.00, "12.10.2003", "14:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(1.0, actualResult);

    }

    @Test
    public void theDifferenceIsGreater()
    {
        double actualResult = getRefund("13.10.2003", "13:2", 1200.00, "12.10.2002", "14:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(1.0, actualResult);

    }

    @Test
    public void Prise0()
    {
        double actualResult = getRefund("13.10.2003", "13:2", 0, "05.10.2003", "14:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(1.0, actualResult);

    }

    @Test
    public void incorrectTimeEntryReturn()
    {
        double actualResult = getRefund("13.10.2003", "13:2", 0, "05.10.20", "14:20" );
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }
        Assert.assertEquals(1.0, actualResult);

    }


    public double getRefund(String dateСoncert, String timeConcert, double price, String dateReturn, String timeReturn) {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date dateOne = null;
        Date dateTwo = null;



        try {
            dateOne = format.parse(dateСoncert);
            dateTwo = format.parse(dateReturn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Количество дней между датами в миллисекундах
        long difference = dateOne.getTime() - dateTwo.getTime();

        // Перевод количества дней между датами из миллисекунд в дни
        int days = (int) (difference / (24 * 60 * 60 * 1000)); // миллисекунды / (24ч * 60мин * 60сек * 1000мс)
        // проверка на правильное заполнение
        if (dateСoncert.length() != 10 || timeConcert.length() != 5 || dateReturn.length() != 10 || timeReturn.length() != 5 || days >60 || price < 0.0){
            return 1;
        }else{
            String[] timeCon = timeConcert.split(":");
            String[] timeRet = timeReturn.split(":");
            // переводим время в минуты
            Integer timeC = (Integer.valueOf(timeCon[0]) * 60 ) + Integer.valueOf(timeCon[1]);
            Integer timeR = (Integer.valueOf(timeRet[0]) * 60 ) + Integer.valueOf(timeRet[1]);

            /*- от 60 дней до 40 дней( включительно) 100% от суммы.
                - от 40 дней до 20 дней ( включительно) 70% от суммы.
                - от 20 дней до 5 дней (включительно) 40% от суммы.
                - от 5 дней до 1 дня ( включительно) 20 % от суммы.
                - менее чем за 24 часа сумма не возвращается.*/


            if (days <= 60 && days >= 40) return price;
            if (days < 40 && days >= 20) return price * 0.7;
            if (days < 20 && days >= 5) return price * 0.4;
            if (days < 5 && days >= 1 && timeC >= timeR) return price * 0.2;
            if (days <= 1 && timeC < timeR) return 0.00;
            return 0;
        }

    }

}