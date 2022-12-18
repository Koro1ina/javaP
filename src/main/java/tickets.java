import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class tickets {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите дату концерта (пример: 12.12.2012): ");
        String dateCon = scanner.next();

        System.out.print("Введите время концерта (пример: 00:00): ");
        String timeCon = scanner.next();

        System.out.print("Введите стоимость билета: ");
        double price = scanner.nextDouble();

        System.out.print("Введите дату сдачи билета(пример: 12.12.2012): ");
        String dateRet = scanner.next();

        System.out.print("Введите время сдачи билета (пример: 00:00): ");
        String timeRet = scanner.next();

        double actualResult = getRefund(dateCon, timeCon, price, dateRet, timeRet);
        if (actualResult == 1){
            System.out.println("Неверно введены данные");
        }else{
            System.out.println("Результат: " + actualResult);
        }
    }

    public static double getRefund(String dateСoncert, String timeConcert, double price, String dateReturn, String timeReturn) {

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
        if (dateСoncert.length() != 10 || timeConcert.length() != 5 || dateReturn.length() != 10 || timeReturn.length() != 5 || days >60){
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
