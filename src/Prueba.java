
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class VentanaInicio extends JFrame{
		static JTextArea textAreaSi, textAreaNo;
		static JProgressBar progressSi, ProgressNo;
		JButton btnIniciar, btnLimpiar;
		
		
	public VentanaInicio() {
		getContentPane().setLayout(null);
		setSize(500, 500);
		setTitle("Análisis de datos en Twitter");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		JLabel lblSi = new JLabel("Respuesta SÍ");
		lblSi.setBounds(70, 10, 100, 30);
		add(lblSi);
		
		JLabel lblNo = new JLabel("Respuesta NO");
		lblNo.setBounds(290, 10, 110, 30);
		add(lblNo);
		
		textAreaSi = new JTextArea();
		textAreaSi.setLineWrap(true);
		textAreaSi.setBounds(30, 40, 150, 200);
		textAreaSi.setEditable(false);
		textAreaSi.setBorder(null);
		add(textAreaSi);

		JScrollPane scroll1 = new JScrollPane(textAreaSi);
		scroll1.setBounds(30, 40, 150, 200);
		add(scroll1);

		JLabel labelNo = new JLabel("Resultados NO");
		labelNo.setBounds(245, 10, 100, 30);
		//labelNo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		add(labelNo);

		textAreaNo = new JTextArea();
		textAreaNo.setLineWrap(true);
		textAreaNo.setBounds(210, 40, 150, 200);
		textAreaNo.setEditable(false);
		textAreaNo.setBorder(null);
		add(textAreaNo);

		JScrollPane scroll2 = new JScrollPane(textAreaNo);
		scroll2.setBounds(210, 40, 150, 200);
		add(scroll2);

		JLabel si = new JLabel("Si.");
		si.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		si.setBounds(30, 270, 100, 30);
		add(si);

		progressSi = new JProgressBar(0, 10000);
		progressSi.setBounds(80, 270, 260, 30);
		progressSi.setStringPainted(true);
		add(progressSi);

		JLabel no = new JLabel("No.");
		no.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		no.setBounds(30, 310, 100, 30);
		add(no);

		ProgressNo = new JProgressBar(0, 10000);
		ProgressNo.setBounds(80, 310, 260, 30);
		ProgressNo.setStringPainted(true);
		add(ProgressNo);

		btnIniciar = new JButton("Generar");
		btnIniciar.setBounds(60, 360, 120, 30);
		add(btnIniciar);

		btnIniciar.addActionListener(new ActionListener() {
			HiloLlenado hl;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread hilo1 = new Thread(new HiloLlenado());
				hilo1.start();
				try {
					hilo1.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
System.out.println("Hilo 1 fin");
				Thread hilo2 = new Thread(new HiloHistograma());
				hilo2.start();	
			}
		});

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(210, 360, 120, 30);
		add(btnLimpiar);

		btnLimpiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textAreaSi.setText("");
				textAreaNo.setText("");
				progressSi.setValue(0);
				ProgressNo.setValue(0);
			}
		});
	}
		
}//ClassVentanaInicio

class HiloLlenado implements Runnable {
	String[] arreglo = new String[10000];

	@Override
	public void run() {
		VentanaInicio.textAreaSi.setText("");
		VentanaInicio.textAreaNo.setText("");
		int cont1 = 0, cont2 = 0, cont3 = 0;
		String respuestas[] = { "SI", "NO" };
		for (int i = 0; i < arreglo.length; i++) {
			arreglo[i] = respuestas[new Random().nextInt(respuestas.length)];

			if (arreglo[i].equals("SI")) {
				VentanaInicio.textAreaSi.append((cont1 + 1) + ".- " + arreglo[i] + "\n");
				cont1++;
			} else if (arreglo[i].equals("NO")) {
				VentanaInicio.textAreaNo.append((cont2 + 1) + ".- " + arreglo[i] + "\n");
				cont2++;
			}
			System.out.println(cont3);
			cont3++;
		}
	}
}

class HiloHistograma implements Runnable {
	String[] arreglo;
	public HiloHistograma() {
		HiloLlenado hi = new HiloLlenado();
		arreglo = hi.arreglo;
	}
	
	@Override
	public void run() {
	
	VentanaInicio ventana;
		for (int i = 0; i < arreglo.length; i++) {
			System.out.println("entra"+arreglo[i]);
			
			if (arreglo[i].equals("SI")) {
				VentanaInicio.progressSi.setValue(i);
				VentanaInicio.progressSi.repaint();
				VentanaInicio.progressSi.setForeground(new Color(8, 106, 211));
			} else {
				VentanaInicio.ProgressNo.setValue(i);
				VentanaInicio.ProgressNo.repaint();
				VentanaInicio.ProgressNo.setForeground(new Color(211, 8, 8));
			}
		}

	}
}//ClassHiloHistograma



public class Prueba {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new VentanaInicio();
				
			}
		});

	}//main

}//Clase
