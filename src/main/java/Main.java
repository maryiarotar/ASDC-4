import java.util.Scanner;

import static java.lang.String.format;

public class Main {

    public static void main(String[] args) {

        //Задан многомерный массив/вектор B.
        //размерность массива
        int n = 4;;
        //интервалы изменения индексов: 1..3;0..2;-3..-1;4..7
        //используем enum Dimension тк мне лень по-нормальному писать,
        //для изменения индексов менять значения в enum напрямую или переделать конструктор для myVector
        /*
        int f1 = 1; int f2 = 3;
        int s1 = 0; int s2 = 2;
        int t1 = -3; int t2 = -1;
        int p1 = 4; int p2 = 7;
        */


        MyVector vec = new MyVector(4);

        System.out.println(vec.toString());
        System.out.println(vec.getByteSize() + " bytes [" + vec.getByteSize()/Integer.BYTES + " elements]");
        System.out.println("arr[1..3][0..2][-3..-1][4..7]");




        vec.initializeArray();


        System.out.println("_______________________для представления по строкам_______________________");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите первое число: ");
        int num1 = scanner.nextInt();

        System.out.print("Введите второе число: ");
        int num2 = scanner.nextInt();

        System.out.print("Введите третье число: ");
        int num3 = scanner.nextInt();

        System.out.print("Введите четвертое число: ");
        int num4 = scanner.nextInt();

        long startTime = System.nanoTime();
        //можно сверить по заполненной от 0 до n-1 таблице, что элементы совпадают
        System.out.println("\nПРЯМОЙ ДОСТУП! ::: Index of element A["+num1+"]["+num2+"]["+num3+"]["+num4+"] = "+
                vec.getElementByDirectWay(num1, num2, num3, num4));
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " ns");


        startTime = System.nanoTime();
        System.out.println("\nОПРЕДЕЛЯЮЩИЙ ВЕКТОР! ::: Index of element A["+num1+"]["+num2+"]["+num3+"]["+num4+"] = "+
                vec.getElementByDefiningVector(num1, num2, num3, num4));
        endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " ns");


        MyVector.makeIliffeVector();
        startTime = System.nanoTime();
        System.out.println("\nВЕКТОР АЙЛИФА! ::: Index of element A["+num1+"]["+num2+"]["+num3+"]["+num4+"] = "+
                MyVector.getElementByIliffeVector(num1, num2, num3, num4));
        endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + " ns");
        /*
        for (int a[][][] : vec.multyDimensionalArray){
            for (int b[][] : a){
                for (int c[] : b){
                    for (int d : c){
                        System.out.print(format("%4d",d));
                    }
                }
            }
            System.out.println();
        }
        */
    }

}
