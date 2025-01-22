package reconhecimentofacial.lp2;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;



public class Camera extends JFrame {

    private JLabel cameraScreen;

    private JButton btnCapture;

    private VideoCapture capture;

    private Mat image;
    
        private boolean clicked = false;
    
        public Camera() {
    
            setLayout(null);
    
            cameraScreen = new JLabel();
            cameraScreen.setBounds(0, 0, 640, 480);
            add(cameraScreen);
    
            btnCapture = new JButton("Captura");
            btnCapture.setBounds(300, 480, 80, 40);
            add(btnCapture);
    
            btnCapture.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    clicked = true;
    
                }
    
            });
    
            setSize(new Dimension(640, 560));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }
    
        public void startCamera(){
            capture = new VideoCapture(0);
            image = new Mat();
            byte[] imageData;
            
            if (!capture.isOpened()) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir a câmera!");
                return;
            }

            ImageIcon icon;
            while(true){
                capture.read(image);
    
                final MatOfByte buf = new MatOfByte();
                Imgcodecs.imencode(".jpg", image, buf);
    
                imageData = buf.toArray();
    
                icon = new ImageIcon(imageData);
                cameraScreen.setIcon(icon);
    
                if(clicked){
                    String name = JOptionPane.showInputDialog(this, "Digite o nome da imagem");
                    if(name == null){
                        name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                    }

                    Imgcodecs.imwrite("imagens/" + name + ".jpg", image);
                    clicked = false;
                }
        }
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Camera camera = new Camera();
                new Thread(new Runnable() {
                    @Override
                    public void run(){
                        camera.startCamera();
                    }
                }).start();
            }
        });
    }
}
