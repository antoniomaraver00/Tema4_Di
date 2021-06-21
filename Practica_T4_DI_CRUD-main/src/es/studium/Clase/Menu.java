package es.studium.Clase;

import java.awt.BorderLayout; 
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class Menu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setTitle("MENU PRINCIPAL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Art\u00EDculos");
		menuBar.add(mnNewMenu);
		
		//Vinculo AltaArticulos con el menuItem Alta Artiiculos
		JMenuItem mntmNewMenuItem = new JMenuItem("Alta Art\u00EDculos");
		mnNewMenu.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				AltaArticulos altaarticulos = new AltaArticulos();
				
			}
		});
		
			
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Baja Art\u00EDculos");
		mnNewMenu.add(mntmNewMenuItem_1);
		mntmNewMenuItem_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				BajaArt bajaArticulos = new BajaArt();
				
			}
		});
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Consultar Art\u00EDculos");
		mnNewMenu.add(mntmNewMenuItem_2);
		mntmNewMenuItem_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				ConsultarArticulos consulta = new ConsultarArticulos();
				
			}
		});
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Modificar Art\u00EDculos");
		mnNewMenu.add(mntmNewMenuItem_3);
		mntmNewMenuItem_3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				ModificiarArticulos modificar = new ModificiarArticulos();
				
			}
		});
		
		JMenu mnNewMenu_1 = new JMenu("Tickets");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Alta Tickets");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		mntmNewMenuItem_4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				AltaTickets tickets= new AltaTickets();
				
			}
		});
		
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Consultar Tickets");
		mnNewMenu_1.add(mntmNewMenuItem_6);
		mntmNewMenuItem_6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				ConsultarTickets consultar = new ConsultarTickets();
				
			}
		});
		
		
		
	}
}
