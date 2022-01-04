package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class DialogDonut extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textX;
	private JTextField textY;
	private JTextField textInnerR;
	private JTextField textOuterR;
	private Color fillColor;
	private Color edgeColor;
	private boolean ok;
	private JButton btnEdgeColor;
	private JButton btnFillColor;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogDonut dialog = new DialogDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogDonut() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Donut");
		contentPanel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setModal(true);
		JLabel lblCenterX = new JLabel("CenterX:");
		JLabel lblCenterY = new JLabel("CenterY:");
		JLabel lblRadius = new JLabel("OuterRadius:");
		JLabel lblInnerRadius = new JLabel("InnerRadius:");
		textX = new JTextField();
		textX.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char t=e.getKeyChar();
				if(t=='-') {
					e.consume();
					getToolkit().beep();
				}
			}
		});
		textX.setText("");
		textX.setColumns(10);
		textY = new JTextField();
		textY.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char t=e.getKeyChar();
				if(t=='-') {
					e.consume();
					getToolkit().beep();
				}
			}
		});
		textY.setText("");
		textY.setColumns(10);
		textOuterR = new JTextField();
		textOuterR.setText("");
		textOuterR.setColumns(10);
		textInnerR = new JTextField();
		textInnerR.setText("");
		textInnerR.setColumns(10);
		
		btnFillColor = new JButton("Fill color");
		btnFillColor.setBackground(fillColor);
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillColor=JColorChooser.showDialog(null, "Choose a inner color!", fillColor);
			    btnFillColor.setBackground(fillColor);
			}
		});
		
		btnEdgeColor = new JButton("Edge color");
		btnEdgeColor.setBackground(edgeColor);
		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edgeColor=JColorChooser.showDialog(null, "Choose a outer color!", null);
			    btnEdgeColor.setBackground(edgeColor);
			}
		});
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblRadius)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblCenterX)
									.addComponent(lblCenterY)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textOuterR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblInnerRadius)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textInnerR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnFillColor)
						.addComponent(btnEdgeColor)
						)
					)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterX)
						.addComponent(textX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterY)
						.addComponent(textY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(textOuterR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerRadius)
						.addComponent(textInnerR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnFillColor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEdgeColor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try
						{
						setOk(true);
						dispose();
						}
						catch(NumberFormatException ex)
						{
							JOptionPane.showMessageDialog(new JFrame(), "Popunite sva polja ili proverite tip podataka koji ste uneli!", "Greska", JOptionPane.WARNING_MESSAGE);
							
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(new JFrame(), "Poluprecnici moraju biti pozitivni i unutrasnji poluprecnik mora biti manji od spoljasnjeg!", "Greska", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getEdgeColor() {
		return edgeColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor =edgeColor;
	}

	public void setTextXEditable(boolean b)
	{
		this.textX.setEditable(b);
	}
	
	public void setTextYEditable(boolean b)
	{
		this.textY.setEditable(b);
	}
	
	public void setTextInnerREditable(boolean b)
	{
		this.textInnerR.setEditable(b);
	}
	
	public void setTextOuterREditable(boolean b)
	{
		this.textOuterR.setEditable(b);
	}
	

	public String getTextInnerR() {
		return textInnerR.getText();
	}

	public void setTextInnerR(String textInnerR) {
		this.textInnerR.setText(textInnerR);
	}

	public String getTextOuterR() {
		return textOuterR.getText();
	}

	public void setTextOuterR(String textOuterR) {
		this.textOuterR.setText(textOuterR);
	}

	public String getTextX() {
		return textX.getText();
	}

	public void setTextX(String textX) {
		this.textX.setText(textX);
	}

	public String getTextY() {
		return textY.getText();
	}

	public void setTextY(String textY) {
		this.textY.setText(textY);
	}
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public void setBtnEdgeColor(JButton btnEdgeColor) {
		this.btnEdgeColor = btnEdgeColor;
	}

	public JButton getBtnFillColor() {
		return btnFillColor;
	}

	public void setBtnFillColor(JButton btnFillColor) {
		this.btnFillColor = btnFillColor;
	}
	
}