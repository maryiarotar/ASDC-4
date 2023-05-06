import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MyVector {

    int n; //размерность
    static int sizeDim1;
    static int sizeDim2;
    static int sizeDim3;
    static int sizeDim4;
    private int dimensions[];

    //Например, если Mas2D : Array [n1..k1] [n2..k2] of < Тип >
    //то количество байтов в памяти: ByteSize = (k1-n1+1)*(k2-n2+1)*SizeOf(Тип)
    private int byteSize;
    int numberOfElements;
    private int[] vector; //здесь 4-хмерный массив превращаем в вектор
    public int[][][][] multiDimensionalArray; //для проверки
    private int d_j[];

    private int sum_lj_mul_dj;

   public MyVector(int n){

       this.n = n;

       d_j = new int[n];
       findD_j();
       makeSum_lj_mul_dj(); //для определяющего вектора храним значение
       this.dimensions = new int[]{sizeDim1, sizeDim2, sizeDim3, sizeDim4};

       initializeVector();

       sizeDim1 = Dimension.dim1.getEnd() - Dimension.dim1.getStart() + 1;
       sizeDim2 = Dimension.dim2.getEnd() - Dimension.dim2.getStart() + 1;
       sizeDim3 = Dimension.dim3.getEnd() - Dimension.dim3.getStart() + 1;
       sizeDim4 = Dimension.dim4.getEnd() - Dimension.dim4.getStart() + 1;

       this.byteSize = (sizeDim1) * (sizeDim2) * (sizeDim3) * (sizeDim4) * Integer.BYTES;

       this.numberOfElements = byteSize/Integer.BYTES;
       this.vector = new int [numberOfElements];
       multiDimensionalArray = new int[sizeDim1][sizeDim2][sizeDim3][sizeDim4];
   }



    private void findD_j() {

        int D_j = 1; //Dn = D4
        for (int i = n - 1; i >= 0; i--) {

            this.d_j[i] = D_j;
            int D_prev = D_j;

            //D(j+1), где j=n-1, n-2, ... 1;
            //Dj=(h(j+1)-l(j+1)+1)×D(j+1)
            D_j = (Dimension.getDim(i + 1).getEnd() - Dimension.getDim(i + 1).getStart() + 1) * D_prev;

        }
    }

    //прямой доступ к элементам для реализации "представление матриц по строкам"
    public int getElementByDirectWay(int i1, int i2, int i3, int i4){

       int i_j[] = {i1, i2, i3, i4};

       int sum = 0;

       for (int j = 0; j < n; j++){

           //V[sum_1_n(i_j - l_j) × Dj]
           sum += (i_j[j] - Dimension.getDim(j+1).getStart()) * d_j[j];

       }
       return  sum;
    }



    public int getElementByDefiningVector(int i1, int i2, int i3, int i4){

        int i_j[] = {i1, i2, i3, i4};

       //В определяющий вектор записываются следующие величины:
        //a) размерность матрицы (n);                                           // this.n
        //b) граничные значения для каждого индекса (lj, hj, j=1,2,...,n);      // enum Dimension
        //c) количество элементов в матрице;                                    // this.numberOfElements
        //d) числа Dj для j=1,2,...,n;                                          // this.d_j[]
        //e) sum_1_n ( l_j * D_j);                                              // this.sum_lj_mul_dj

        /* все значения уже есть в этом классе; будем считать, что извлекаем их из опред-го вектора */

        int sum2 = 0;
        for (int j = 0; j < n; j++){
            sum2 += i_j[j] * d_j[j];
        }

        return sum2 - this.sum_lj_mul_dj;
    }


    public static int getElementByIliffeVector(int i1, int i2, int i3, int i4){

        if (vectorIliffe.size() > 0){

            int ii = 0, jj=0, kk=0, ll=0;
            for (int i = Dimension.getDim(1).getStart(); i < i1; i++, ii++){}
            for (int j = Dimension.getDim(2).getStart(); j < i2; j++, jj++){}
            for (int k = Dimension.getDim(3).getStart(); k < i3; k++, kk++){}
            for (int l = Dimension.getDim(4).getStart(); l < i4; l++, ll++){}

            return vectorIliffe.get(ii).get(jj).get(kk).get(ll);
        }
        return 0;
    }


    private static List<List<List<List<Integer>>>> vectorIliffe;

    public static void makeIliffeVector() {
        int filler = 0;
        vectorIliffe = new ArrayList<>(sizeDim1);
        for (int i = 0; i < sizeDim1; i++) {
            List<List<List<Integer>>> level1 = new ArrayList<>(sizeDim2);
            for (int j = 0; j < sizeDim2; j++) {
                List<List<Integer>> level2 = new ArrayList<>(sizeDim3);
                for (int k = 0; k < sizeDim3; k++) {
                    List<Integer> level3 = new ArrayList<>(sizeDim4);
                    for (int l = 0; l < sizeDim4; l++) {
                        level3.add(filler);
                        //System.out.println(" el: " + filler);
                        filler++;
                    }
                    level2.add(level3);
                }
                level1.add(level2);
            }
            vectorIliffe.add(level1);
        }
    }




    private void makeSum_lj_mul_dj(){
        int sum = 0;

        for (int j = 0; j < n; j++){
            sum += (Dimension.getDim(j+1).getStart()) * d_j[j];
        }

        this.sum_lj_mul_dj = sum;
    }



    public int getByteSize() {
       return byteSize;
   }


   public String toString(){

       String str = "matrix["+(Dimension.dim1.getEnd() - Dimension.dim1.getStart() + 1)+"]["
               + (Dimension.dim2.getEnd() - Dimension.dim2.getStart() + 1) + "]["
               + (Dimension.dim3.getEnd() - Dimension.dim3.getStart() + 1) + "]["
               + (Dimension.dim4.getEnd() - Dimension.dim4.getStart() + 1) + "]";

       return str;
   }



    public void initializeVector(){
        for (int i=0; i < numberOfElements; i++) {
            vector[0] = i;
        }
    }


    //заполнение по строкам
    public void initializeArray(){
        int value = 0;
        for (int i1=0, v1 = Dimension.dim1.getStart(); v1 <= Dimension.dim1.getEnd(); i1++, v1++) {
            for (int i2=0, v2 = Dimension.dim2.getStart(); v2 <= Dimension.dim2.getEnd(); i2++, v2++) {
                for (int i3=0, v3 = Dimension.dim3.getStart(); v3 <= Dimension.dim3.getEnd(); i3++, v3++) {
                    for (int i4=0, v4 = Dimension.dim4.getStart(); v4 <= Dimension.dim4.getEnd(); i4++, v4++) {
                        System.out.println(v1 + " " + v2 + " "+ v3 + " " + v4 + " -> {" + value++ + "}");
                        multiDimensionalArray[i1][i2][i3][i4] = value;
                    }
                    System.out.println("    -----------");
                }
                System.out.println("  --------------");
            }
            System.out.println("----------------");
        }
    }

}
