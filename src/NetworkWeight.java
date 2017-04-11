import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLDouble;
import org.jblas.DoubleMatrix;
import java.io.IOException;


/**
 * Created by Peichun on 2017/2/17.
 */
public class NetworkWeight {
    DoubleMatrix theta1;
    DoubleMatrix theta2;
    DoubleMatrix theta3;

    public NetworkWeight(String pathname) throws IOException {
        MatFileReader reader = new MatFileReader(pathname);
        this.theta1 = new DoubleMatrix(((MLDouble)(reader.getMLArray("Theta1"))).getArray());
        this.theta2 = new DoubleMatrix(((MLDouble)(reader.getMLArray("Theta2"))).getArray());
        this.theta3 = new DoubleMatrix(((MLDouble)(reader.getMLArray("Theta3"))).getArray());
    }


    public static void main(String[] args) throws IOException {
        NetworkWeight networkWeight = new NetworkWeight("./data/THETA.mat");
    }

}
