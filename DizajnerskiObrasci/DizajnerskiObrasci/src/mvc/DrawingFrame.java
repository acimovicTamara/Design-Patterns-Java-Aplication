package mvc;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import observer.Observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;

public class DrawingFrame extends JFrame implements Observer {

	/**
	 * 
	 */
	private DrawingController controller;
	private DrawingView view = new DrawingView();
	private DefaultListModel<String> dlm = new DefaultListModel<>();

	private JPanel contentPane;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnHexagon;

	private JToggleButton tglbtnSelection;
	private JButton btnModify;
	private JButton btnDelete;

	private JButton btnUndo;
	private JButton btnRedo;

	private JToolBar toolBar_1;
	private JButton btnTofront;
	private JButton btnToback;
	private JButton btnBringToBack;
	private JButton btnBringToFront;

	private JButton btnInnerColor;
	private JButton btnOuterColor;

	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenDrawing;
	private JMenuItem mntmSaveDrawing;
	private JSeparator separator;
	private JMenuItem mntmOpenLog;
	private JMenuItem mntmSaveLog;
	private JSeparator separator_1;
	private JMenuItem mntmExit;
	private JButton btnNext;
	private JList<String> listLog = new JList<String>(dlm);
	private JScrollPane scrollPane = new JScrollPane(listLog);

	public DrawingFrame() {

		setTitle("IT14-2017 Tamara Acimovic");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.LIGHT_GRAY);

		ButtonGroup buttonGroup = new ButtonGroup();

		JToolBar toolBar = new JToolBar();

		contentPane.add(view, BorderLayout.CENTER);

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		getContentPane().add(toolBar, BorderLayout.NORTH);

		listLog.setFont(new Font("Cambria Math", Font.PLAIN, 14));
		scrollPane.setViewportView(listLog);

		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		mnFile.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		menuBar.add(mnFile);

		mntmOpenDrawing = new JMenuItem("Import painting");
		mntmOpenDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.openDrawing();
			}
		});
		mntmOpenDrawing.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		mnFile.add(mntmOpenDrawing);

		mntmSaveDrawing = new JMenuItem("Export painting");
		mntmSaveDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveDrawing();
			}
		});
		mntmSaveDrawing.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		mnFile.add(mntmSaveDrawing);

		separator = new JSeparator();
		mnFile.add(separator);

		mntmOpenLog = new JMenuItem("Import log");
		mntmOpenLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.openLog();
			}
		});
		mntmOpenLog.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		mnFile.add(mntmOpenLog);

		mntmSaveLog = new JMenuItem("Export log");
		mntmSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		mntmSaveLog.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		mnFile.add(mntmSaveLog);

		separator_1 = new JSeparator();
		mnFile.add(separator_1);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setFont(new Font("Cambria Math", Font.PLAIN, 16));
		mnFile.add(mntmExit);

		contentPane = new JPanel();
		menuBar.add(contentPane);

		toolBar_1 = new JToolBar(JToolBar.VERTICAL);
		getContentPane().add(toolBar_1, BorderLayout.WEST);

		tglbtnPoint = new JToggleButton("Point");
		tglbtnPoint.setFont(new Font("Tahoma", Font.BOLD, 10));

		toolBar.add(tglbtnPoint);
		buttonGroup.add(tglbtnPoint);

		tglbtnLine = new JToggleButton("Line");
		tglbtnLine.setFont(new Font("Tahoma", Font.BOLD, 10));

		toolBar.add(tglbtnLine);
		buttonGroup.add(tglbtnLine);

		tglbtnRectangle = new JToggleButton("Rectangle");
		tglbtnRectangle.setFont(new Font("Tahoma", Font.BOLD, 10));

		toolBar.add(tglbtnRectangle);
		buttonGroup.add(tglbtnRectangle);

		tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.setFont(new Font("Tahoma", Font.BOLD, 10));
		toolBar.add(tglbtnCircle);
		buttonGroup.add(tglbtnCircle);

		tglbtnDonut = new JToggleButton("Donut");
		tglbtnDonut.setFont(new Font("Tahoma", Font.BOLD, 10));
		toolBar.add(tglbtnDonut);
		buttonGroup.add(tglbtnDonut);

		tglbtnHexagon = new JToggleButton("Hexagon");
		tglbtnHexagon.setFont(new Font("Tahoma", Font.BOLD, 10));
		toolBar.add(tglbtnHexagon);
		buttonGroup.add(tglbtnHexagon);

		tglbtnSelection = new JToggleButton("Selection");
		tglbtnSelection.setFont(new Font("Tahoma", Font.BOLD, 10));

		toolBar.add(tglbtnSelection);
		buttonGroup.add(tglbtnSelection);

		btnModify = new JButton("Modify");
		btnModify.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnModify.setEnabled(false);
		btnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				controller.btnModifyClicked(e);

			};
		});

		toolBar.add(btnModify);
		buttonGroup.add(btnModify);

		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDelete.setEnabled(false);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.delete(e);

			};
		});
		toolBar.add(btnDelete);
		buttonGroup.add(btnDelete);

		btnUndo = new JButton("Undo");
		btnUndo.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.btnClickUndo();
			}
		});

		toolBar.add(btnUndo);
		buttonGroup.add(btnUndo);

		btnRedo = new JButton("Redo");
		btnRedo.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});

		toolBar.add(btnRedo);

		btnNext = new JButton("Next");
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.next();
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNext.setBackground(Color.WHITE);
		toolBar_1.add(btnNext);

		btnToback = new JButton("To Back");
		btnToback.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnToback.setEnabled(false);
		btnToback.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnToback.isEnabled()) {
					controller.toBack();

				}

			}
		});

		toolBar.add(btnToback);

		btnTofront = new JButton("To Front");
		btnTofront.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnTofront.setEnabled(false);
		btnTofront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnTofront.isEnabled()) {
					controller.toFront();

				}
			}
		});

		toolBar.add(btnTofront);

		btnBringToFront = new JButton("Bring To Front");
		btnBringToFront.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnBringToFront.setEnabled(false);
		btnBringToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.bringToFront();
			}
		});

		toolBar.add(btnBringToFront);

		btnBringToBack = new JButton("Bring To Back");
		btnBringToBack.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnBringToBack.setEnabled(false);
		btnBringToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.bringToBack();
			}
		});

		toolBar.add(btnBringToBack);

		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnInnerColor.setBackground(Color.WHITE);
		btnInnerColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.addInnerColor();
			}
		});

		toolBar_1.add(btnInnerColor);

		btnOuterColor = new JButton("Outer Color");
		btnOuterColor.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnOuterColor.setBackground(Color.BLACK);
		btnOuterColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.addOuterColor();
			}
		});
		toolBar_1.add(btnOuterColor);

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.AFTER_LAST_LINE);
		JList list = new JList();

		list.setModel(dlm);
		scrollPane.setViewportView(list);

		scrollPane.setBounds(586, 452, 784, 461);

	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller2) {
		controller = controller2;

	}

	public boolean getTglbtnPoint() {
		return tglbtnPoint.isSelected();
	}

	public boolean getTglbtnLine() {
		return tglbtnLine.isSelected();
	}

	public boolean getTglbtnRectangle() {
		return tglbtnRectangle.isSelected();
	}

	public boolean getTglbtnCircle() {
		return tglbtnCircle.isSelected();
	}

	public boolean getTglbtnDonut() {
		return tglbtnDonut.isSelected();
	}

	public boolean getTglbtnSelection() {
		return tglbtnSelection.isSelected();
	}

	public boolean getbtnModify() {
		return btnModify.isSelected();
	}

	public boolean getbtnDelete() {
		return btnDelete.isSelected();
	}

	public boolean getTglbtnHexagon() {
		return tglbtnHexagon.isSelected();
	}

	public boolean getbtnUndo() {
		return btnUndo.isSelected();
	}

	public boolean getbtnTofront() {
		return btnTofront.isSelected();
	}

	public boolean getbtnToback() {
		return btnToback.isSelected();
	}

	public boolean getbtnBringToBack() {
		return btnBringToBack.isSelected();
	}

	public boolean getbtnBringToFront() {
		return btnBringToFront.isSelected();
	}

	public boolean getbtnRedo() {
		return btnRedo.isSelected();
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JButton btnModify) {
		this.btnModify = btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}

	public JButton getBtnTofront() {
		return btnTofront;
	}

	public void setBtnTofront(JButton btnTofront) {
		this.btnTofront = btnTofront;
	}

	public JButton getBtnToback() {
		return btnToback;
	}

	public void setBtnToback(JButton btnToback) {
		this.btnToback = btnToback;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public void setBtnBringToBack(JButton btnBringToBack) {
		this.btnBringToBack = btnBringToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public void setBtnBringToFront(JButton btnBringToFront) {
		this.btnBringToFront = btnBringToFront;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}

	public void setBtnOuterColor(JButton btnOuterColor) {
		this.btnOuterColor = btnOuterColor;
	}

	public void setTglbtnSelection(JToggleButton tglbtnSelection) {
		this.tglbtnSelection = tglbtnSelection;
	}

	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	public void setDlm(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}

	@Override
	public void update(int numOfSelected, int numOfElements, int numOfUndoCommands, int numOfRedoCommands) {

		if (numOfUndoCommands > 0) {
			btnUndo.setEnabled(true);
		} else {
			btnUndo.setEnabled(false);
		}

		if (numOfRedoCommands > 0) {
			btnRedo.setEnabled(true);
		} else {
			btnRedo.setEnabled(false);
		}

		if (numOfSelected > 0) {
			this.btnDelete.setEnabled(true);
		} else {
			this.btnDelete.setEnabled(false);
		}

		if (numOfSelected == 1) {
			this.btnModify.setEnabled(true);
			this.btnBringToBack.setEnabled(true);
			this.btnBringToFront.setEnabled(true);
			this.btnToback.setEnabled(true);
			this.btnTofront.setEnabled(true);
		} else {
			this.btnModify.setEnabled(false);
			this.btnBringToBack.setEnabled(false);
			this.btnBringToFront.setEnabled(false);
			this.btnToback.setEnabled(false);
			this.btnTofront.setEnabled(false);
		}

	}

}