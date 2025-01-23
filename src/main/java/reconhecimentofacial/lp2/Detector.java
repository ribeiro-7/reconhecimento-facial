package reconhecimentofacial.lp2;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;


public class Detector {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //colocar nome da imagem.jpg
        Mat image = Imgcodecs.imread("imagens/faces.jpg");

        detectarESalvar(image);
    }

    public static void detectarESalvar(Mat image) {

        MatOfRect faces = new MatOfRect();

        Mat grayFrame = new Mat();
        Imgproc.cvtColor(image, grayFrame, Imgproc.COLOR_RGB2GRAY);

        Imgproc.equalizeHist(grayFrame, grayFrame);

        int height = grayFrame.height();
        int absoluteFaceSize = 0;
        if(Math.round(height * 0.2f) > 0){
            absoluteFaceSize = Math.round(height * 0.2f);
        }

        CascadeClassifier faceCascade = new CascadeClassifier();

        faceCascade.load("data/haarcascade_frontalface_alt2.xml");
        faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0|Objdetect.CASCADE_SCALE_IMAGE, new Size(absoluteFaceSize, absoluteFaceSize), new Size());

        Rect[] faceArray = faces.toArray();
        for(int i = 0; i < faceArray.length; i++){
            Imgproc.rectangle(image, faceArray[i], new Scalar(0, 0, 255), 3);

        }

        Imgcodecs.imwrite("imagens/output.jpg", image);
        if (faceArray.length > 0) {
            System.out.println("Rostos detectados com sucesso! Total de rostos: " + faceArray.length);
        } else {
            System.out.println("Não foi possível reconhecer nenhum rosto na imagem.");
        }

    }
}
