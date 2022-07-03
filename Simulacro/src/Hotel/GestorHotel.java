package Hotel;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class GestorHotel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<Habitacion> modelListaHabitaciones = new DefaultListModel<Habitacion>();
	private DefaultListModel<Cliente> modelListaClientes = new DefaultListModel<Cliente>();
	private ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private ArrayList<Planta> plantas = new ArrayList<Planta>();
	private Planta planta1 = new Planta(1);
	private Planta planta2 = new Planta(2);
	private Planta planta3 = new Planta(3);
	private Habitacion [] habs;
	private JFileChooser escogerFichero;
	private FileWriter escritor;
	private PrintWriter printEscritor;
	private String rutaAbsoluta;
	private File rutaArchivo;
	private FileOutputStream fileOutput;
	private ObjectOutputStream outputStream;
	private FileInputStream fileInput;
	private ObjectInputStream inputStream;
	private int seleccion;
	private int index = 0;
	private JPanel contentPane;
	private JComboBox<Habitacion> comboBox = new JComboBox<Habitacion>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestorHotel frame = new GestorHotel();
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
	public GestorHotel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		
		// BARRA MENÚ
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenu mnClientes = new JMenu("Clientes");
		menuBar.add(mnClientes);
		
		JMenu mnHabitaciones = new JMenu("Habitaciones");
		menuBar.add(mnHabitaciones);
		
		JMenu mnHotel = new JMenu("Hotel");
		menuBar.add(mnHotel);
		
		JMenu mnImpresora = new JMenu("Imprimir");
		menuBar.add(mnImpresora);
		
		JMenuItem mntmCerrar = new JMenuItem("Cerrar");
		mnArchivo.add(mntmCerrar);
		
		JMenuItem mntmBorrar = new JMenuItem("Borrar");
		mnArchivo.add(mntmBorrar);
		
		JMenuItem mntmBorrarTodo = new JMenuItem("Borrar todo");
		mnArchivo.add(mntmBorrarTodo);
		
		JMenuItem mntmCargarCliente = new JMenuItem("Cargar Clientes");
		mnClientes.add(mntmCargarCliente);
		
		JMenuItem mntmGuardarCliente = new JMenuItem("Guardar Clientes");
		mnClientes.add(mntmGuardarCliente);
		
		JMenuItem mntmCargarHabitacion = new JMenuItem("Cargar Habitaciones");
		mnHabitaciones.add(mntmCargarHabitacion);
		
		JMenuItem mntmGuardarHabitacion = new JMenuItem("Guardar Habitaciones");
		mnHabitaciones.add(mntmGuardarHabitacion);
		
		JMenuItem mntmCargarHotel = new JMenuItem("Cargar estado del Hotel");
		mnHotel.add(mntmCargarHotel);
		
		JMenuItem mntmGuardarHotel = new JMenuItem("Guardar estado del Hotel");
		mnHotel.add(mntmGuardarHotel);
		
		JMenuItem mntmImprimirClientes = new JMenuItem("Imprimir Clientes");
		mnImpresora.add(mntmImprimirClientes);
		
		JMenuItem mntmImprimirHabitaciones = new JMenuItem("Imprimir Habitaciones");
		mnImpresora.add(mntmImprimirHabitaciones);
		
		// CONTENEDOR DE LA APP
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		// PESTAÑAS
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "name_61022721705500");
		
		JPanel panelHotel = new JPanel();
		tabbedPane.addTab("Estado del Hotel", null, panelHotel, null);
		panelHotel.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		tabbedPane.addTab("Clientes", null, panelCliente, null);
		panelCliente.setLayout(null);
		
		JPanel panelHabitacion = new JPanel();
		tabbedPane.addTab("Habitaciones", null, panelHabitacion, null);
		panelHabitacion.setLayout(null);
		
		// INICIALIZAR
		
		inicializarHabitaciones();
		
		// PESTAÑA HOTEL
		
		JList<Cliente> listClientes = new JList<Cliente>(modelListaClientes);
		listClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listClientes.setBounds(25, 25, 350, 350);
		panelHotel.add(listClientes);
		
		JList<Habitacion> listHabitaciones = new JList<Habitacion>(modelListaHabitaciones);
		listHabitaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listHabitaciones.setBounds(400, 25, 350, 350);
		panelHotel.add(listHabitaciones);
		
		JLabel lblListaClientes = new JLabel("Listado Clientes");
		lblListaClientes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblListaClientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaClientes.setBounds(25, 5, 350, 20);
		panelHotel.add(lblListaClientes);
		
		JLabel lblListaHabitaciones = new JLabel("Listado Habitaciones");
		lblListaHabitaciones.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblListaHabitaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaHabitaciones.setBounds(400, 5, 350, 20);
		panelHotel.add(lblListaHabitaciones);
		
		// PESTAÑA CLIENTE
		
		JLabel lblNuevoCliente = new JLabel("Nuevo cliente");
		lblNuevoCliente.setBounds(40, 10, 150, 25);
		lblNuevoCliente.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelCliente.add(lblNuevoCliente);
		
		JLabel lblLlegada = new JLabel("Día Llegada");
		lblLlegada.setBounds(10, 100, 150, 25);
		lblLlegada.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelCliente.add(lblLlegada);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 50, 150, 25);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelCliente.add(lblNombre);
		
		JLabel lblSalida = new JLabel("Día Salida");
		lblSalida.setBounds(10, 150, 150, 25);
		lblSalida.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelCliente.add(lblSalida);
		
		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setBounds(400, 15, 100, 20);
		lblClientes.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelCliente.add(lblClientes);
		
		JLabel lblDatosCliente = new JLabel("Cliente");
		lblDatosCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDatosCliente.setBounds(10, 220, 340, 25);
		panelCliente.add(lblDatosCliente);
		
		JLabel lblDatosHabitacion = new JLabel("Habitación");
		lblDatosHabitacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosHabitacion.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDatosHabitacion.setBounds(10, 275, 340, 25);
		panelCliente.add(lblDatosHabitacion);
		
		JTextArea txtNombreCliente = new JTextArea();
		txtNombreCliente.setBounds(100, 50, 250, 25);
		panelCliente.add(txtNombreCliente);
		
		JTextArea txtDiaLlegada = new JTextArea();
		txtDiaLlegada.setBounds(100, 100, 250, 25);
		panelCliente.add(txtDiaLlegada);
		
		JTextArea txtDiaSalida = new JTextArea();
		txtDiaSalida.setBounds(100, 150, 250, 25);
		panelCliente.add(txtDiaSalida);
		
		JTextArea textAreaDatosHabitacion = new JTextArea();
		textAreaDatosHabitacion.setEditable(false);
		textAreaDatosHabitacion.setBounds(10, 300, 340, 25);
		panelCliente.add(textAreaDatosHabitacion);
		
		JTextArea textAreaDatosCliente = new JTextArea();
		textAreaDatosCliente.setEditable(false);
		textAreaDatosCliente.setBounds(10, 245, 340, 25);
		panelCliente.add(textAreaDatosCliente);
		
		JList<Cliente> listaClientes = new JList<Cliente>(modelListaClientes);
		listaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaClientes.setBounds(400, 40, 350, 350);
		panelCliente.add(listaClientes);
		
		JButton btnAñadirCliente = new JButton("Añadir");
		btnAñadirCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAñadirCliente.setBounds(30, 340, 85, 25);
		panelCliente.add(btnAñadirCliente);
		
		JButton btnBorrarCliente = new JButton("Borrar");
		btnBorrarCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBorrarCliente.setBounds(250, 340, 85, 25);
		panelCliente.add(btnBorrarCliente);
		
		JButton btnMostrarCliente = new JButton("Mostrar");
		btnMostrarCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarCliente.setBounds(140, 340, 85, 25);
		panelCliente.add(btnMostrarCliente);
		
		comboBox.setBounds(10, 190, 340, 25);
		panelCliente.add(comboBox);
		
		// PESTAÑA HABITACIONES
		
		JLabel lblHabitaciones = new JLabel("Habitaciones");
		lblHabitaciones.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHabitaciones.setBounds(400, 15, 100, 20);
		panelHabitacion.add(lblHabitaciones);
		
		JLabel lblNuevaHabitacion = new JLabel("Nueva habitación");
		lblNuevaHabitacion.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNuevaHabitacion.setBounds(40, 10, 200, 25);
		panelHabitacion.add(lblNuevaHabitacion);
		
		JLabel lblPlanta = new JLabel("Planta");
		lblPlanta.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPlanta.setBounds(10, 50, 150, 25);
		panelHabitacion.add(lblPlanta);
		
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumero.setBounds(10, 100, 150, 25);
		panelHabitacion.add(lblNumero);
		
		JLabel lblLibre = new JLabel("¿Está libre?");
		lblLibre.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLibre.setBounds(10, 150, 90, 25);
		panelHabitacion.add(lblLibre);
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCliente.setBounds(10, 200, 340, 25);
		panelHabitacion.add(lblCliente);
		
		JTextArea textAreaPlanta = new JTextArea();
		textAreaPlanta.setBounds(100, 50, 250, 25);
		panelHabitacion.add(textAreaPlanta);
		
		JTextArea textAreaNumero = new JTextArea();
		textAreaNumero.setBounds(100, 100, 250, 25);
		panelHabitacion.add(textAreaNumero);
		
		JTextArea textAreaCliente = new JTextArea();
		textAreaCliente.setEditable(false);
		textAreaCliente.setBounds(10, 240, 340, 25);
		panelHabitacion.add(textAreaCliente);
		
		JCheckBox chckbxSI = new JCheckBox("SI");
		chckbxSI.setEnabled(false);
		chckbxSI.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxSI.setBounds(150, 150, 50, 25);
		chckbxSI.setVisible(false);
		panelHabitacion.add(chckbxSI);
		
		JCheckBox chckbxNO = new JCheckBox("NO");
		chckbxNO.setEnabled(false);
		chckbxNO.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxNO.setBounds(200, 150, 50, 25);
		chckbxNO.setVisible(false);
		panelHabitacion.add(chckbxNO);
		
		JList<Habitacion> listaHabitaciones = new JList<Habitacion>(modelListaHabitaciones);
		listaHabitaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaHabitaciones.setBounds(400, 40, 350, 350);
		panelHabitacion.add(listaHabitaciones);
		
		JButton btnAñadirHabitacion = new JButton("Añadir");
		btnAñadirHabitacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAñadirHabitacion.setBounds(25, 290, 85, 25);
		panelHabitacion.add(btnAñadirHabitacion);
		
		JButton btnBorrarHabitacion = new JButton("Borrar");
		btnBorrarHabitacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBorrarHabitacion.setBounds(250, 290, 85, 25);
		panelHabitacion.add(btnBorrarHabitacion);
		
		JButton btnMostrarHabitación = new JButton("Mostrar");
		btnMostrarHabitación.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarHabitación.setBounds(140, 290, 85, 25);
		panelHabitacion.add(btnMostrarHabitación);
		
		JButton btnNuevaPlanta = new JButton("Añadir nueva Planta");
		btnNuevaPlanta.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNuevaPlanta.setBounds(25, 345, 310, 25);
		panelHabitacion.add(btnNuevaPlanta);
		
		// EVENTOS BARRA MENU
		
			// ARCHIVO
		
		mntmCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		mntmBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNombreCliente.setText("");
				txtDiaLlegada.setText("");
				txtDiaSalida.setText("");
				textAreaDatosHabitacion.setText("");
				textAreaDatosCliente.setText("");
				textAreaPlanta.setText("");
				textAreaNumero.setText("");
				textAreaCliente.setText("");
			}
		});
		
		mntmBorrarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				modelListaHabitaciones.clear();
				modelListaClientes.clear();
				comboBox.removeAllItems();
				
				txtNombreCliente.setText("");
				txtDiaLlegada.setText("");
				txtDiaSalida.setText("");
				textAreaDatosHabitacion.setText("");
				textAreaDatosCliente.setText("");
				textAreaPlanta.setText("");
				textAreaNumero.setText("");
				textAreaCliente.setText("");
			}
		});
		
			// CLIENTES
		
		mntmCargarCliente.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				clientes.clear();
				modelListaClientes.clear();
				try {
					fileInput = new FileInputStream(seleccionarDestino());
					inputStream = new ObjectInputStream(fileInput);
					clientes = (ArrayList<Cliente>) inputStream.readObject();
					inputStream.close();
					fileInput.close();
				} catch (IOException i) {
                    i.printStackTrace();
                    return;
				} catch (ClassNotFoundException c) {
                    System.out.println("Clase Cliente no encontrada");
                    c.printStackTrace();
                    return;
				}
				for (int i = 0; i < clientes.size(); i++) {
					modelListaClientes.add(i, clientes.get(i));
				}
			}
		});
		
		mntmGuardarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileOutput = new FileOutputStream("clientes.ser");
					outputStream = new ObjectOutputStream(fileOutput);
					outputStream.writeObject(clientes);
					outputStream.close();
					fileOutput.close();
					System.out.println("Datos guardados correctamente");
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
			// HABITACIONES
		
		mntmCargarHabitacion.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				habitaciones.clear();
				modelListaHabitaciones.clear();
				try {
					fileInput = new FileInputStream(seleccionarDestino());
					inputStream = new ObjectInputStream(fileInput);
					habitaciones = (ArrayList<Habitacion>) inputStream.readObject();
					inputStream.close();
					fileInput.close();
				} catch (IOException i) {
                    i.printStackTrace();
                    return;
				} catch (ClassNotFoundException c) {
                    System.out.println("Clase Habitacion no encontrada");
                    c.printStackTrace();
                    return;
				}
				for (int i = 0; i < habitaciones.size(); i++) {
					modelListaHabitaciones.add(i, habitaciones.get(i));
				}
			}
		});
		
		mntmGuardarHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileOutput = new FileOutputStream("habitaciones.ser");
					outputStream = new ObjectOutputStream(fileOutput);
					outputStream.writeObject(habitaciones);
					outputStream.close();
					fileOutput.close();
					System.out.println("Datos guardados correctamente");
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
			// HOTEL
		
		mntmCargarHotel.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				clientes.clear();
				habitaciones.clear();
				modelListaClientes.clear();
				modelListaHabitaciones.clear();
				comboBox.removeAllItems();
				
				try {
					System.out.println("Primero seleccionamos las habitaciones");
					fileInput = new FileInputStream(seleccionarDestino());
					inputStream = new ObjectInputStream(fileInput);
					habitaciones = (ArrayList<Habitacion>) inputStream.readObject();
					inputStream.close();
					fileInput.close();
					System.out.println("Luego seleccionamos los clientes");
					fileInput = new FileInputStream(seleccionarDestino());
					inputStream = new ObjectInputStream(fileInput);
					clientes = (ArrayList<Cliente>) inputStream.readObject();
					inputStream.close();
					fileInput.close();
				} catch (IOException i) {
                    i.printStackTrace();
                    return;
				} catch (ClassNotFoundException c) {
                    System.out.println("Clase no encontrada");
                    c.printStackTrace();
                    return;
				}
				for (int i = 0; i < habitaciones.size(); i++) {
					modelListaHabitaciones.add(i, habitaciones.get(i));
					comboBox.addItem(habitaciones.get(i));
				}
				for (int i = 0; i < clientes.size(); i++) {
					modelListaClientes.add(i, clientes.get(i));
				}
			}
		});					
		
		mntmGuardarHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileOutput = new FileOutputStream("habitaciones.ser");
					outputStream = new ObjectOutputStream(fileOutput);
					outputStream.writeObject(habitaciones);
					outputStream.close();
					fileOutput.close();
					fileOutput = new FileOutputStream("clientes.ser");
					outputStream = new ObjectOutputStream(fileOutput);
					outputStream.writeObject(clientes);
					outputStream.close();
					fileOutput.close();
					System.out.println("Datos del hotel guardados correctamente");
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
			// IMPRESORA
		
		mntmImprimirClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					escritor = new FileWriter("clientes.txt");
					printEscritor = new PrintWriter(escritor);
					for (Cliente c : clientes) {
						printEscritor.println(c.toString());
					}
					
				}  catch (Exception e1) {
		            e1.printStackTrace();
		        } finally {
		           try { if (null != escritor) { escritor.close(); }  
		           } catch (Exception e2) { e2.printStackTrace(); }
		        }
				String titulo = "Estos son los clientes del hotel";
				generaImpresion(titulo);
			}
		});
		
		mntmImprimirHabitaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					escritor = new FileWriter("habitaciones.txt");
					printEscritor = new PrintWriter(escritor);
					for (Habitacion hab : habitaciones) {
						printEscritor.println(hab.toString());
					}
					
				}  catch (Exception e1) {
		            e1.printStackTrace();
		        } finally {
		           try { if (null != escritor) { escritor.close(); }  
		           } catch (Exception e2) { e2.printStackTrace(); }
		        }
				String titulo = "Estas son las habitaciones del hotel";
				generaImpresion(titulo);
			}
		});
		
		// EVENTOS VISTA
		
			// CLIENTES
		
		btnAñadirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				index = (int) comboBox.getSelectedIndex();
				Habitacion habitacionElegida = habitaciones.get(index);
				if (habitacionElegida.isLibre()) {
					
					String nombre = txtNombreCliente.getText();
					int entrada = Integer.parseInt(txtDiaLlegada.getText());
					int salida = Integer.parseInt(txtDiaSalida.getText());
					Cliente nuevoCliente = new Cliente(nombre, entrada, salida);
					habitacionElegida.ocuparHab(nuevoCliente);
					
					habitaciones.set(index, habitacionElegida);
					modelListaHabitaciones.removeAllElements();
					for (Habitacion hab : habitaciones) {
						modelListaHabitaciones.addElement(hab);
					}
					
					clientes.add(nuevoCliente);
					modelListaClientes.addElement(nuevoCliente);
					
					comboBox.removeAllItems();
					for (Habitacion hab : habitaciones) {
						comboBox.addItem(hab);
					}
					
					
				} else {
					System.out.println("Habitacion ocupada");
				}
			}
		});
		
		btnBorrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = listaClientes.getSelectedIndex();
				modelListaClientes.remove(index);
				clientes.remove(index);
				
				Habitacion habitacionLibre = habitaciones.get(index);
				habitacionLibre.liberarHab();
				habitaciones.set(index, habitacionLibre);
				
				modelListaHabitaciones.removeAllElements();
				for (Habitacion hab : habitaciones) {
					modelListaHabitaciones.addElement(hab);
				}
			}
		});
		
		btnMostrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = listaClientes.getSelectedIndex();
				Cliente c = clientes.get(index);
				Habitacion h = habitaciones.get(index);
				textAreaDatosCliente.setText(c.toString());
				textAreaDatosHabitacion.setText(h.toString());
			}
		});
		
			// HABITACION
		
		btnAñadirHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int planta = Integer.parseInt(textAreaPlanta.getText());
				int numero = Integer.parseInt(textAreaNumero.getText());
				boolean libre = true;
				Habitacion nuevaHabitacion = new Habitacion(planta, numero, libre);
				habitaciones.add(nuevaHabitacion);
				modelListaHabitaciones.addElement(nuevaHabitacion);
				comboBox.addItem(nuevaHabitacion);
			}
		});
		
		btnBorrarHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = listaHabitaciones.getSelectedIndex();
				modelListaHabitaciones.removeElementAt(index);
				habitaciones.remove(index);
				comboBox.removeItemAt(index);
			}
		});
		
		btnMostrarHabitación.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = listaHabitaciones.getSelectedIndex();
				Habitacion habitacionEscogida = modelListaHabitaciones.get(index);
				textAreaPlanta.setText("" + habitacionEscogida.getPlanta());
				textAreaNumero.setText("" + habitacionEscogida.getNumero());
				if (habitacionEscogida.isLibre()) {
					chckbxSI.setVisible(true);
					chckbxNO.setVisible(false);
				} else {
					chckbxNO.setVisible(true);
					chckbxSI.setVisible(false);
					Cliente c = habitacionEscogida.getCliente();
					textAreaCliente.setText(c.toString());
				}
			}
		});
		
		btnNuevaPlanta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Planta nuevaPlanta = new Planta(plantas.size() + 1);
				plantas.add(nuevaPlanta);
				habs = nuevaPlanta.getHabitaciones();
				for (Habitacion hab : habs) {
					modelListaHabitaciones.addElement(hab);
					comboBox.addItem(hab);
					habitaciones.add(hab);
				}
			}
		});
	}
	
	// MÉTODOS DE LA CLASE
	
	public void inicializarHabitaciones() {
		plantas.add(planta1);
		plantas.add(planta2);
		plantas.add(planta3);
		
		habs = planta1.getHabitaciones();
		for (Habitacion hab : habs) {
			modelListaHabitaciones.addElement(hab);
			comboBox.addItem(hab);
			habitaciones.add(hab);
		}
		habs = planta2.getHabitaciones();
		for (Habitacion hab : habs) {
			modelListaHabitaciones.addElement(hab);
			comboBox.addItem(hab);
			habitaciones.add(hab);
		}
		habs = planta3.getHabitaciones();
		for (Habitacion hab : habs) {
			modelListaHabitaciones.addElement(hab);
			comboBox.addItem(hab);
			habitaciones.add(hab);
		}
	}
	
	public String seleccionarDestino() {
		try {
			escogerFichero = new JFileChooser();
			seleccion = escogerFichero.showOpenDialog(contentPane);
			
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				rutaArchivo = escogerFichero.getSelectedFile();
				rutaAbsoluta = rutaArchivo.getAbsolutePath();
			}
		} catch (Exception e1) {
			
		}
		return rutaAbsoluta;
	}
	
	public void generaImpresion(String titulo) {
		try {
			File fichero = new File(seleccionarDestino());
			Pantalla frame = new Pantalla(titulo, fichero);
			frame.setVisible(true);
		} catch (Exception eX) {
			eX.printStackTrace();
		}
	}
}
