import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;


/**
 * Created by Peichun on 2017/2/17.
 */
public class Predict {

    private DoubleMatrix sigmoid(DoubleMatrix z){
        return ((MatrixFunctions.exp(z.neg())).add(1)).rdiv(1);
    }

    private DoubleMatrix AddOnes(int m, DoubleMatrix matrix){
        return DoubleMatrix.concatHorizontally(DoubleMatrix.ones(m, 1), matrix);
    }

    private DoubleMatrix ComputeMatrix(int m, DoubleMatrix matrix, DoubleMatrix theta){
        return sigmoid(AddOnes(m, matrix).mmul(theta.transpose()));
    }

    public String GetString(String image_file_path, String mat_file_path) throws Exception{
        NetworkWeight networkweight = new NetworkWeight(mat_file_path);
        ImageToMatrix img = new ImageToMatrix(image_file_path);
        int m = img.X.getRows();
        DoubleMatrix h1 = ComputeMatrix(m, img.X, networkweight.theta1);
        DoubleMatrix h2 = ComputeMatrix(m, h1, networkweight.theta2);
        DoubleMatrix h3 = ComputeMatrix(m, h2, networkweight.theta3);
        int[] p= h3.rowArgmaxs();
        char[] wordlist = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        return String.valueOf(wordlist[p[0]])+String.valueOf(wordlist[p[1]])+String.valueOf(wordlist[p[2]])+String.valueOf(wordlist[p[3]]);
    }

    public static void main(String[] args) throws Exception {
        Predict predict = new Predict();
        String string = predict.GetString("./data/test1.jpg", "./data/THETA.mat");
        System.out.println(string);

    }

}
