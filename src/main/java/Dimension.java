
public enum Dimension {

    dim1 (1, 3),
    dim2 (0, 2),
    dim3 (-3, -1),
    dim4 (4, 7);

    private int start;
    private int end;

    Dimension(int start, int end){
        this.start = start;
        this.end = end;
    }

    Dimension(){}

    public static Dimension getDim(int n) {
        switch (n) {
            case 1:
                return Dimension.dim1;
            case 2:
                return Dimension.dim2;
            case 3:
                return Dimension.dim3;
            case 4:
                return Dimension.dim4;
        }
        return null;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }


}
