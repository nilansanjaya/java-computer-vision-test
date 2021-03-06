import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VideoFaceDetect extends JFrame {
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VideoFaceDetect frame = new VideoFaceDetect();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VideoFaceDetect() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		new MyThread().start();
	}

	VideoCap videoCap = new VideoCap();
	FaceDetector face = new FaceDetector();

	/**
	 * Paint video frame in to content pane
	 */
	public void paint(Graphics g) {
		g = contentPane.getGraphics();
		g.drawImage(face.detectFace(videoCap.getOneFrame()), 0, 0, this);
	}

	/**
	 * Thread to handle the video feed
	 */
	class MyThread extends Thread {
		@Override
		public void run() {
			for (;;) {
				repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}