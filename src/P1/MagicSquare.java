package P1;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class MagicSquare {

    public static boolean generateMagicSquare(int n) {
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        return true;
    }

    public static boolean isLegalMagicSquare(String fileName) throws Exception {
        boolean flag = true;

        Vector<Vector<Integer>> matrix = MagicSquare.importMatrix(fileName);
        Vector<Integer> sumRows = new Vector<Integer>();
        Vector<Integer> sumCols = new Vector<Integer>();
        Integer diag = 0;
        Integer opDiag = 0;

        //每行求和
        for (int i = 0; i < matrix.size(); i++) {
            int sum = 0;
            for (int j = 0; j < matrix.get(i).size(); j++) {
                sum += matrix.get(i).get(j);
            }
            sumRows.add(sum);
        }

        //每列求和
        for (int i = 0; i < matrix.size(); i++) {
            int sum = 0;
            for (int j = 0; j < matrix.size(); j++) {
                sum += matrix.get(j).get(i);
            }
            sumCols.add(sum);
        }

        //求对角线、反对角线和
        for (int i = 0; i < matrix.size(); i++) {
            diag += matrix.get(i).get(i);
            opDiag += matrix.get(matrix.size() - i - 1).get(i);
        }

        //比较每行的和
        for (int i = 0; i < matrix.size() - 1; i++) {
            if (!sumRows.get(i).equals(sumRows.get(i + 1))) {
                flag = false;
                break;
            }
        }

        //比较每列的和
        for (int i = 0; i < matrix.size() - 1; i++) {
            if (!sumCols.get(i).equals(sumCols.get(i + 1))) {
                flag = false;
                break;
            }
        }

        if (!diag.equals(opDiag)) {
            flag = false;
        }

        if (!(diag.equals(sumCols.get(0)) && diag.equals(sumRows.get(0)))) {
            flag = false;
        }

        return flag;
    }

    private static int CharToInt(Stack<Character> number) {
        int n = 0;
        for (int i = 0; !number.empty(); i++) {
            n = n + (int) (number.pop() - '0') * (int) (Math.pow(10, i) + 0.5);
        }
        return n;
    }

    private static Vector<Vector<Integer>> importMatrix(String fileName) throws Exception {
        Stack<Character> number = new Stack<Character>();
        ;
        Vector<Vector<Integer>> matrix = new Vector<Vector<Integer>>();
        int matrixNum = 0;
        matrix.add(new Vector<Integer>());

        File file = new File(fileName);
        FileInputStream f1 = new FileInputStream(file);

        //按字符读入数据并判断、存储
        for (int i = 0; i < file.length(); i++) {
            char ch = (char) (f1.read());
            if (ch >= '0' && ch <= '9') {
                number.add(ch);
            } else {
                if (ch == '\t') {
                    matrix.get(matrixNum).add(CharToInt(number));
                } else if (ch == '\n') {
                    matrix.get(matrixNum).add(CharToInt(number));
                    matrix.add(new Vector<Integer>());
                    matrixNum++;
                } else {
                    throw new Exception("存在非法字符");
                }
            }
        }
        matrix.get(matrixNum).add(CharToInt(number));
        f1.close();//关闭文件

        //检测是否是方阵
        for (int i = 0; i < matrix.size() - 1; i++) {
            if (matrix.get(i).size() != matrix.get(i + 1).size()) {
                throw new Exception("行列不等");
            }
        }
        if (matrix.size() != matrix.get(0).size()) {
            throw new Exception("行列不等");
        }

        return matrix;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            boolean flag;
            try {
                flag = isLegalMagicSquare("src/P1/txt/" + i + ".txt");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (!flag) {
                System.out.println("第" + i + "个矩阵不是MagicSquare");
            } else {
                System.out.println("第" + i + "个矩阵是MagicSquare！");
            }
        }
    }
}

