/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import agents.BookBuyerAgent;


/**
 *
 * @author Slonder
 */
public class BookBuyerGui extends JFrame{
    private BookBuyerAgent miAgente;
	
	private JTextField tituloF;
	
	public BookBuyerGui(BookBuyerAgent a) {
		super(a.getLocalName());
		
		miAgente = a;
		
                //especificamos los elementos de la ventana
                
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(new JLabel("Título del libro: "));
		tituloF = new JTextField(15);
		p.add(tituloF);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Comprar");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String title = tituloF.getText().trim();
						
                                        //méthod to buy book
					miAgente.pedidoCompra(title);
					tituloF.setText("");
				}catch(Exception e) {
					JOptionPane.showMessageDialog(BookBuyerGui.this, "Invalid values","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
		  public void windowClosing(WindowEvent e) {
		    miAgente.doDelete();
		  }
		});
		
		setResizable(false);
	}
	
	public void showGui() {
            pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int centerX = (int)screenSize.getWidth() / 2;
            int centerY = (int)screenSize.getHeight() / 2;

            setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
            super.setVisible(true);
	}
}
