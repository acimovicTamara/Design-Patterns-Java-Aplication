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

public class DialogLine extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textStartX;
	private JTextField textStartY;
	private JTextField textEndX;
	private JTextField textEndY;
	private JButton btnColor;
	private boolean ok;
	private Color col;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogLine dialog = new DialogLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogLine() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setModal(true);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Line");
		contentPanel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblStartPointX = new JLabel("StartPointX:");
		textStartX = new JTextField();
		textStartX.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char t=e.getKeyChar();
				if(t=='-') {
					e.consume();
					getToolkit().beep();
				}
			}
		});
		textStartX.setColumns(10);
		JLabel lblStartPointY = new JLabel("StartPointY:");
		textStartY = new JTextField();
		textStartY.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char t=e.getKeyChar();
				if(t=='-') {
					e.consume();
					getToolkit().beep();
				}
			}
		});
		textStartY.setColumns(10);
		JLabel lblEndPointX = new JLabel("EndPointX:");
		textEndX = new JTextField();
		textEndX.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char t=e.getKeyChar();
				if(t=='-') {
					e.consume();
					getToolkit().beep();
				}
			}
		});
		textEndX.setText("");
		textEndX.setColumns(10);
		JLabel lblEndPointY = new JLabel("EndPointY:");
		textEndY = new JTextField();
		textEndY.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char t=e.getKeyChar();
				if(t=='-') {
					e.consume();
					getToolkit().beep();
				}
			}
		});
		textEndY.setText("");
		textEndY.setColumns(10);
		
		btnColor = new JButton("Color");
		btnColor.setBackground(col);
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				col=JColorChooser.showDialog(null, "Choose a color!", null);	
				btnColor.setBackground(col);

			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblStartPointX)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblStartPointY)
							.addGap(10)
							.addComponent(textStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblEndPointX)
							.addGap(18)
							.addComponent(textEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblEndPointY)
							.addGap(18)
							.addComponent(textEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnColor))
					.addContainerGap(258, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartPointX)
						.addComponent(textStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStartPointY))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndPointX)
						.addComponent(textEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndPointY)
						.addComponent(textEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnColor)
					.addContainerGap(80, Short.MAX_VALUE))
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
						try {							
							setOk(true);
							dispose();
							}
							catch(NumberFormatException a)
							{
								JOptionPane.showMessageDialog(new JFrame(), "Popunite sva polja ili proverite tip podataka koji ste uneli!", "Greska!", JOptionPane.ERROR_MESSAGE);
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

	public String getTextStartX() {
		return textStartX.getText();
	}

	public void setTextStartX(String textStartX) {
		this.textStartX.setText(textStartX);
	}

	public String getTextStartY() {
		return textStartY.getText();
	}

	public void setTextStartY(String textStartY) {
		this.textStartY.setText(textStartY);
	}

	public String getTextEndX() {
		return textEndX.getText();
	}

	public void setTextEndX(String textEndX) {
		this.textEndX.setText(textEndX);
	}

	public String getTextEndY() {
		return textEndY.getText();
	}

	public void setTextEndY(String textEndY) {
		this.textEndY.setText(textEndY);
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}

	public void setTextStartXEdit(boolean b) {
		this.textStartX.setEditable(b);
	}

	public void setTextStartYEdit(boolean b) {
		this.textStartY.setEditable(b);
	}

	public void setTextEndXEdit(boolean b) {
		this.textEndX.setEditable(b);
	}

	public void setTextEndYEdit(boolean b) {
		this.textEndY.setEditable(b);
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

}
