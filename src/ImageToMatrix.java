import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import org.jblas.DoubleMatrix;
import org.jblas.ranges.IntervalRange;

//import org.ujmp.core.Matrix;

/**
 * Created by Peichun on 2017/2/17.
 */

public class ImageToMatrix {

    DoubleMatrix filteredmatrix, X;

    public ImageToMatrix(String pathname) throws Exception {

//      打开图片，并将RGB转成灰度值，将灰度值保存在矩阵中
        File file = new File(pathname);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();

        double[][] grayImage = new double[height][width];
        int rgb, red, green, blue;
        for(int i= 0 ; i < width ; i++){
            for(int j = 0 ; j < height; j++){
                rgb = bi.getRGB(i, j);
                red = (rgb & 0xff0000) >> 16;
                green = (rgb & 0xff00) >> 8;
                blue = (rgb & 0xff);
                grayImage[j][i] = ((float)red*0.3+(float)green*0.59+(float)blue*0.11);
            }
        }

//      对灰度值矩阵进行滤波去噪
        double temp;
        for(int i= 1 ; i < width-1 ; i++){
            for(int j = 1 ; j < height-1; j++){
                temp = 0.3*grayImage[j-1][i] + 0.4*grayImage[j][i] + 0.3*grayImage[j+1][i];
                grayImage[j][i] = (temp>90)?1:0;
            }
        }
        for(int i = 0; i<width; i++){
            grayImage[0][i]=1;
        }
        this.filteredmatrix =  new DoubleMatrix(grayImage);
//        Matrix showimage = Matrix.Factory.importFromArray(grayImage);
//        showimage.showGUI();

//      对验证码进行剪切
        DoubleMatrix template = filteredmatrix.getRows(new IntervalRange(0, 20));
        DoubleMatrix x1 = (template.getColumns(new IntervalRange(4, 17))).reshape(1,260);
        DoubleMatrix x2 = (template.getColumns(new IntervalRange(17, 30))).reshape(1,260);
        DoubleMatrix x3 = (template.getColumns(new IntervalRange(30, 43))).reshape(1,260);
        DoubleMatrix x4 = (template.getColumns(new IntervalRange(43, 56))).reshape(1,260);
        this.X= DoubleMatrix.concatVertically(DoubleMatrix.concatVertically(x1, x2), DoubleMatrix.concatVertically(x3, x4));

    }


    public static void main(String[] args) throws Exception {
        ImageToMatrix img = new ImageToMatrix("./data/test.jpg");
        img.X.print();


    }
}
