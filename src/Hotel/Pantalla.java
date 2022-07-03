package Hotel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Pantalla extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private String titulo;
	private String linea;
	private String text = "";
	private File fichero;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String titulo = null;
					File fichero = null;
					Pantalla frame = new Pantalla(titulo, fichero);
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
	public Pantalla(String titulo, File fichero) {
		this.titulo = titulo;
		this.fichero = fichero;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblTitulo.setBounds(10, 10, 565, 25);
		contentPane.add(lblTitulo);
		
		JTextArea textAreaImpresora = new JTextArea();
		textAreaImpresora.setBounds(10, 45, 565, 470);
		textAreaImpresora.setText(leerFichero(fichero));
		contentPane.add(textAreaImpresora);
		
		JButton btnPropina = new JButton("Enviar 20€ al autor por tan buen trabajo");
		btnPropina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTitulo.setText("¡Era Broma! Que te lo has creido ehhhhh.");
				textAreaImpresora.setText("Bueno, que leches, que me lo he currado, anda, dame arrrgo, buscate, rancio.");
			}
		});
		btnPropina.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPropina.setBounds(10, 525, 350, 25);
		contentPane.add(btnPropina);
		
		JButton btnSalir = new JButton("Salir y volver al gestor");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir.setBounds(390, 525, 185, 25);
		contentPane.add(btnSalir);
	}

	public String leerFichero(File fichero) {
		try {
			fileReader = new FileReader(fichero);
			bufferedReader = new BufferedReader(fileReader);
			
			linea = "";
			while ((linea = bufferedReader.readLine()) != null) {
				text += linea + "\n";
		}
		      
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
	         try{                    
	        	 if(fileReader != null){   
	            	fileReader.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }

		}
		return text;
	}	
}
