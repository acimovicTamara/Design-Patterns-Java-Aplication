package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.sound.sampled.DataLine;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import command.CmdAddShape;
import command.CmdDeselectShape;

import command.CmdSelectShape;
import command.CmdUpdateCircle;
import command.CmdUpdateDonut;
import command.CmdUpdateHexagon;
import command.CmdUpdateLine;
import command.CmdUpdatePoint;
import command.CmdUpdateRectangle;
import command.Command;
import command.positions.CmdBringToBack;
import command.positions.CmdBringToFront;
import command.positions.CmdToBack;
import command.positions.CmdToFront;
import command.CmdRemoveShape;
import dialog.DialogCircle;
import dialog.DialogDonut;
import dialog.DialogLine;
import dialog.DialogPoint;
import dialog.DialogRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import hexagon.Hexagon;
import observer.Observer;
import observer.Subject;
import strategy.DrawingStrategy;
import strategy.FileManager;
import strategy.LogStrategy;

public class DrawingController implements Subject {
	private DrawingModel model;
	private DrawingFrame frame;
	private Point startPoint;
	private Shape selectShape;

	private int i = 0;
	private int size;
	private LogStrategy strategy;
	private boolean done = false;

	private Command cmdLast;
	private Command cmdPrev;
	private Command cmdLastRedo;
	private Shape shape = null;
	private Shape oldShape = null;

	private Color colInner = Color.BLACK;
	private Color colOuter = Color.WHITE;
	private LinkedList<Command> undoCmd = new LinkedList<Command>();

	private LinkedList<Command> redoCmd = new LinkedList<Command>();

	private List<Observer> observers = new ArrayList<Observer>();;
	private int numOfUndoCommands;
	private int numOfRedoCommands;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
	}

	/*----------------------ISCRTAVANJE OBLIKA -------------------*/
	public void mouseClicked(MouseEvent arg0) {
		if (frame.getTglbtnPoint()) {

			Point p = new Point(arg0.getX(), arg0.getY());
			DialogPoint dp = new DialogPoint();
			dp.getBtnColor().setVisible(false);
			dp.setTextXEdit(false);
			dp.setTextYEdit(false);
			dp.setTextX(Integer.toString(p.getX()));
			dp.setTextY(Integer.toString(p.getY()));
			dp.setVisible(true);
			p.setCol(frame.getBtnOuterColor().getBackground());

			CmdAddShape cmdAddShape = new CmdAddShape(model, p);

			cmdAddShape.execute();

			addCommandsUndo(cmdAddShape);
			checkRedo();

			frame.getDlm().addElement("Draw: " + p.toString());
			frame.repaint();

		} else if (frame.getTglbtnLine()) {
			if (startPoint == null) {
				startPoint = new Point(arg0.getX(), arg0.getY());
			} else {
				Line l = new Line(startPoint,
						new Point(arg0.getX(), arg0.getY(), frame.getBtnOuterColor().getBackground()));
				DialogLine dl = new DialogLine();
				dl.getBtnColor().setVisible(false);
				dl.setTextEndXEdit(false);
				dl.setTextEndYEdit(false);
				dl.setTextStartXEdit(false);
				dl.setTextStartYEdit(false);
				dl.setTextStartX(Integer.toString(l.getStartPoint().getX()));
				dl.setTextStartY(Integer.toString(l.getStartPoint().getY()));
				dl.setTextEndX(Integer.toString(l.getEndPoint().getX()));
				dl.setTextEndY(Integer.toString(l.getEndPoint().getY()));
				dl.setVisible(true);
				l.setCol(frame.getBtnOuterColor().getBackground());

				CmdAddShape cmdAddShape = new CmdAddShape(model, l);
				cmdAddShape.execute();

				addCommandsUndo(cmdAddShape);
				checkRedo();

				frame.getDlm().addElement("Draw: " + l.toString());
				frame.repaint();
				startPoint = null;
			}
		} else if (frame.getTglbtnRectangle()) {
			Point p = new Point(arg0.getX(), arg0.getY());
			DialogRectangle dr = new DialogRectangle();
			dr.getBtnEdgeColor().setVisible(false);
			dr.getBtnFillColor().setVisible(false);
			dr.setTextXcor(Integer.toString(p.getX()));
			dr.setTextYcor(Integer.toString(p.getY()));
			dr.setTextXcorEditable(false);
			dr.setTextYcorEditable(false);
			dr.setVisible(true);

			if (dr.isOk()) {
				int width = Integer.parseInt(dr.getTextWidth());
				int height = Integer.parseInt(dr.getTextHeight());

				if (height == 0 || width == 0) {

					JOptionPane.showMessageDialog(new JFrame(), "Height and width must be positive numbers.", "Error!",
							JOptionPane.WARNING_MESSAGE);
				} else {

					Rectangle rct = new Rectangle(p, height, width);
					rct.setEdgeColor(frame.getBtnOuterColor().getBackground());
					rct.setFillColor(frame.getBtnInnerColor().getBackground());

					CmdAddShape cmdAddShape = new CmdAddShape(model, rct);
					cmdAddShape.execute();

					addCommandsUndo(cmdAddShape);
					checkRedo();

					frame.getDlm().addElement("Draw: " + rct.toString());
					frame.repaint();
				}
			}
		} else if (frame.getTglbtnHexagon()) {
			Point center = new Point(arg0.getX(), arg0.getY());
			DialogCircle dh = new DialogCircle();
			dh.getBtnEdgeColor().setVisible(false);
			dh.getBtnFillColor().setVisible(false);
			dh.setTitle("Hexagon");
			dh.setTextXEdit(false);
			dh.setTextYEdit(false);
			dh.setTextX(Integer.toString(center.getX()));
			dh.setTextY(Integer.toString(center.getY()));
			dh.setVisible(true);

			if (dh.isOk()) {
				int radius = Integer.parseInt(dh.getTextRadius());
				if (radius == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "Radius must be greater than 0", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {

					HexagonAdapter h = new HexagonAdapter(arg0.getX(), arg0.getY(), radius);
					h.setColor(frame.getBtnOuterColor().getBackground());
					h.setFillColor(frame.getBtnInnerColor().getBackground());

					CmdAddShape cmdAddShape = new CmdAddShape(model, h);
					cmdAddShape.execute();

					addCommandsUndo(cmdAddShape);
					checkRedo();

					frame.getDlm().addElement("Draw: " + h.toString());
					frame.repaint();
				}
			}
		} else if (frame.getTglbtnCircle()) {
			Point center = new Point(arg0.getX(), arg0.getY());
			DialogCircle dc = new DialogCircle();
			dc.getBtnEdgeColor().setVisible(false);
			dc.getBtnFillColor().setVisible(false);
			dc.setTextXEdit(false);
			dc.setTextYEdit(false);
			dc.setTextX(Integer.toString(center.getX()));
			dc.setTextY(Integer.toString(center.getY()));
			dc.setVisible(true);

			if (dc.isOk()) {
				int radius = Integer.parseInt(dc.getTextRadius());
				if (radius == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "Radius must be greater than 0", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {

					Circle c = new Circle(center, radius);
					c.setEdgeColor(frame.getBtnOuterColor().getBackground());
					c.setFillColor(frame.getBtnInnerColor().getBackground());

					CmdAddShape cmdAddShape = new CmdAddShape(model, c);
					cmdAddShape.execute();

					addCommandsUndo(cmdAddShape);
					checkRedo();

					frame.getDlm().addElement("Draw: " + c.toString());
					frame.repaint();
				}

			}
		} else if (frame.getTglbtnDonut()) {
			Point center = new Point(arg0.getX(), arg0.getY());
			center.setCol(frame.getBtnOuterColor().getBackground());
			DialogDonut dd = new DialogDonut();
			dd.getBtnEdgeColor().setVisible(false);
			dd.getBtnFillColor().setVisible(false);
			dd.setTextX(Integer.toString(center.getX()));
			dd.setTextY(Integer.toString(center.getY()));
			dd.setTextXEditable(false);
			dd.setTextYEditable(false);
			dd.setVisible(true);

			if (dd.isOk()) {
				int innerRadius = Integer.parseInt(dd.getTextInnerR());
				int outerRadius = Integer.parseInt(dd.getTextOuterR());
				if (innerRadius == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "Inner radius must be greater than 0", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else if (innerRadius >= outerRadius) {
					JOptionPane.showMessageDialog(new JFrame(), "Outer radius must be greater than inner radius",
							"Error", JOptionPane.WARNING_MESSAGE);
				} else {
					Donut donut = new Donut(center, outerRadius, innerRadius);
					donut.setEdgeColor(frame.getBtnOuterColor().getBackground());
					donut.setsEdgeColor(frame.getBtnOuterColor().getBackground());
					donut.setFillColor(frame.getBtnInnerColor().getBackground());

					CmdAddShape cmdAddShape = new CmdAddShape(model, donut);
					cmdAddShape.execute();

					addCommandsUndo(cmdAddShape);
					checkRedo();

					frame.getDlm().addElement("Draw: " + donut.toString());
					frame.repaint();
				}
			}
		} else if (frame.getTglbtnSelection()) {
			Point p = new Point(arg0.getX(), arg0.getY());
			int prev = 0;
			int ii = 0;

			ArrayList<Shape> shapes = new ArrayList<Shape>();
			shapes = (ArrayList<Shape>) model.getShapes();

			ArrayList<Shape> shapesover = new ArrayList<Shape>();

			for (int it = 0; it <= shapes.size() - 1; it++) {
				if (shapes.get(it).contains(p.getX(), p.getY())) {
					shapesover.add(shapes.get(it));
					prev++;
				}
			}
			if (prev > 1) {

				if (JOptionPane.showConfirmDialog(new JFrame(), "Do you want to (de)select just last painted shape?",
						"Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					for (ii = 0; ii < shapesover.size() - 1; ii++) {
					}

					if (shapesover.get(ii).contains(p.getX(), p.getY())) {

						if (shapesover.get(ii).isSelected() == false) {
							logSelect(model, shapesover.get(ii));

						} else {

							CmdDeselectShape cmdDeselectShape = new CmdDeselectShape(model, shapesover.get(ii));
							cmdDeselectShape.execute();

							System.out.println("Selected test: " + shapesover.get(ii).isSelected());
							System.out.println("Current selected: " + model.getNumOfSelected());

							addCommandsUndo(cmdDeselectShape);
							frame.getDlm().addElement("Deselect: " + shapesover.get(ii).toString());
						}
					}
					ii = 0;

				} else {
					System.out.println("NO");
				}

			} else {

				for (int i = 0; i <= shapes.size() - 1; i++) {
					if (shapes.get(i).contains(p.getX(), p.getY())) {

						if (shapes.get(i).isSelected() == false) {

							logSelect(model, shapes.get(i));
							selectShape = shapes.get(i);

							System.out.println("Test shape = " + shapes.get(i).toString());
							System.out.println("Current selected: " + model.getNumOfSelected());

						} else {

							CmdDeselectShape cmdDeselectShape = new CmdDeselectShape(model, shapes.get(i));
							cmdDeselectShape.execute();

							System.out.println("Selected test: " + shapes.get(i).isSelected());
							System.out.println("Current selected: " + model.getNumOfSelected());

							addCommandsUndo(cmdDeselectShape);
							frame.getDlm().addElement("Deselect: " + shapes.get(i).toString());
						}
					}
				}
			}
		}

		frame.repaint();
	}

	/*-------------------MODIFY-------------------*/

	public void btnModifyClicked(MouseEvent arg0) {

		selectShape = model.getAllSelected().get(0);

		System.out.println("Test shape = " + model.getAllSelected().get(0));

		if (selectShape != null) {
			if (selectShape instanceof Point) {
				Point oldPoint = (Point) selectShape;
				DialogPoint dlgP = new DialogPoint();
				dlgP.setTextX(Integer.toString((oldPoint).getX()));
				dlgP.setTextY(Integer.toString((oldPoint).getY()));
				dlgP.setColor((oldPoint).getCol());
				dlgP.setVisible(true);

				if (dlgP.isOk()) {
					Point newPoint = new Point(Integer.parseInt(dlgP.getTextX()), Integer.parseInt(dlgP.getTextY()),
							dlgP.getColor());

					CmdUpdatePoint cmdPoint = new CmdUpdatePoint(oldPoint, newPoint);
					cmdPoint.execute();
					frame.repaint();

					addCommandsUndo(cmdPoint);
					frame.getDlm().addElement("Update: " + selectShape.toString());
				}
			} else if (selectShape instanceof Line) {
				Line oldLine = (Line) selectShape;

				DialogLine dlgL = new DialogLine();
				dlgL.setTextStartX(Integer.toString((oldLine).getStartPoint().getX()));
				dlgL.setTextStartY(Integer.toString((oldLine).getStartPoint().getY()));
				dlgL.setTextEndX(Integer.toString((oldLine).getEndPoint().getX()));
				dlgL.setTextEndY(Integer.toString((oldLine).getEndPoint().getY()));
				dlgL.setCol((oldLine).getCol());
				dlgL.setVisible(true);

				if (dlgL.isOk()) {
					Line newLine = new Line(
							new Point(Integer.parseInt(dlgL.getTextStartX()), Integer.parseInt(dlgL.getTextStartY())),
							new Point(Integer.parseInt(dlgL.getTextEndX()), Integer.parseInt(dlgL.getTextEndY())),
							dlgL.getCol());

					CmdUpdateLine cmdLine = new CmdUpdateLine(oldLine, newLine);
					cmdLine.execute();
					frame.repaint();

					addCommandsUndo(cmdLine);
					frame.getDlm().addElement("Update: " + selectShape.toString());
				}
			} else if (selectShape instanceof Rectangle) {
				Rectangle oldRectangle = (Rectangle) selectShape;

				DialogRectangle dlgR = new DialogRectangle();
				dlgR.setTextXcor(Integer.toString((oldRectangle).getUpperLeftPoint().getX()));
				dlgR.setTextYcor(Integer.toString((oldRectangle).getUpperLeftPoint().getY()));
				dlgR.setTextWidth(Integer.toString((oldRectangle).getWidth()));
				dlgR.setTextHeight(Integer.toString((oldRectangle).getHeight()));
				dlgR.setFillColor((oldRectangle).getFillColor());
				dlgR.setEdgeColor((oldRectangle).getEdgeColor());
				dlgR.setVisible(true);

				if (dlgR.isOk()) {
					Point upperLeftPoint = new Point(Integer.parseInt(dlgR.getTextXcor()),
							Integer.parseInt(dlgR.getTextYcor()));
					int height = Integer.parseInt(dlgR.getTextHeight());
					int width = Integer.parseInt(dlgR.getTextWidth());

					if (height == 0 || width == 0) {
						JOptionPane.showMessageDialog(new JFrame(), "Height and width must be positive numbers.",
								"Error!", JOptionPane.WARNING_MESSAGE);
					} else {
						Rectangle newRectangle = new Rectangle(upperLeftPoint, height, width);
						newRectangle.setEdgeColor(dlgR.getEdgeColor());
						newRectangle.setFillColor(dlgR.getFillColor());

						CmdUpdateRectangle cmdRectangle = new CmdUpdateRectangle(oldRectangle, newRectangle);
						cmdRectangle.execute();
						frame.repaint();

						addCommandsUndo(cmdRectangle);
						frame.getDlm().addElement("Update: " + selectShape.toString());

						System.out.println("Test shape = " + selectShape.toString());
					}
				}
			} else if (selectShape instanceof HexagonAdapter) {
				HexagonAdapter oldHexagon = (HexagonAdapter) selectShape;

				DialogCircle dlgH = new DialogCircle();
				dlgH.setTitle("Hexagon");
				dlgH.setTextX(Integer.toString((oldHexagon).getHexagon().getX()));
				dlgH.setTextY(Integer.toString((oldHexagon).getHexagon().getY()));
				dlgH.setTextRadius(Integer.toString((oldHexagon).getHexagon().getR()));
				dlgH.setFillColor((oldHexagon).getHexagon().getAreaColor());
				dlgH.setEdgeColor((oldHexagon).getHexagon().getBorderColor());
				dlgH.setVisible(true);

				if (dlgH.isOk()) {
					int radius = Integer.parseInt(dlgH.getTextRadius());

					if (radius == 0) {
						JOptionPane.showMessageDialog(new JFrame(), "Radius must be greater than 0", "Error",
								JOptionPane.WARNING_MESSAGE);
					} else {
						HexagonAdapter newHexagon = new HexagonAdapter(Integer.parseInt(dlgH.getTextX()),
								Integer.parseInt(dlgH.getTextY()), Integer.parseInt(dlgH.getTextRadius()));
						newHexagon.setColor(dlgH.getEdgeColor());
						newHexagon.setFillColor(dlgH.getFillColor());

						CmdUpdateHexagon cmdHexagon = new CmdUpdateHexagon(oldHexagon, newHexagon);
						cmdHexagon.execute();
						frame.repaint();

						addCommandsUndo(cmdHexagon);
						frame.getDlm().addElement("Update: " + selectShape.toString());
					}
				}
			} else if (selectShape instanceof Donut)

			{
				Donut oldDonut = (Donut) selectShape;

				DialogDonut dlgD = new DialogDonut();
				dlgD.setTextX(Integer.toString((oldDonut).getCenter().getX()));
				dlgD.setTextY(Integer.toString((oldDonut).getCenter().getY()));
				dlgD.setTextInnerR(Integer.toString((oldDonut).getInnerRadius()));
				dlgD.setTextOuterR(Integer.toString((oldDonut).getRadius()));
				dlgD.setEdgeColor(((oldDonut).getsEdgeColor()));
				dlgD.setFillColor(((oldDonut).getFillColor()));
				dlgD.setVisible(true);

				if (dlgD.isOk()) {

					int innerRadius = Integer.parseInt(dlgD.getTextInnerR());
					int outerRadius = Integer.parseInt(dlgD.getTextOuterR());
					if (innerRadius == 0) {
						JOptionPane.showMessageDialog(new JFrame(), "Radius must be greater than 0", "Error",
								JOptionPane.WARNING_MESSAGE);
					} else if (innerRadius >= outerRadius) {
						JOptionPane.showMessageDialog(new JFrame(), "Outer radius must be greater than inner radius",
								"Error", JOptionPane.WARNING_MESSAGE);
					} else {
						Donut newDonut = new Donut(
								new Point(Integer.parseInt(dlgD.getTextX()), Integer.parseInt(dlgD.getTextY())),
								outerRadius, innerRadius);
						newDonut.setsEdgeColor(dlgD.getEdgeColor());
						newDonut.setFillColor(dlgD.getFillColor());

						CmdUpdateDonut cmdDonut = new CmdUpdateDonut(oldDonut, newDonut);
						cmdDonut.execute();
						frame.repaint();

						addCommandsUndo(cmdDonut);
						frame.getDlm().addElement("Update: " + selectShape.toString());
					}
				}
			} else if (selectShape instanceof Circle) {
				Circle oldCircle = (Circle) selectShape;

				DialogCircle dlgC = new DialogCircle();
				dlgC.setTextX(Integer.toString((oldCircle).getCenter().getX()));
				dlgC.setTextY(Integer.toString((oldCircle).getCenter().getY()));
				dlgC.setTextRadius(Integer.toString((oldCircle).getRadius()));
				dlgC.setFillColor((oldCircle).getFillColor());
				dlgC.setEdgeColor((oldCircle).getEdgeColor());
				dlgC.setVisible(true);

				if (dlgC.isOk()) {

					int radius = Integer.parseInt(dlgC.getTextRadius());

					if (radius == 0) {
						JOptionPane.showMessageDialog(new JFrame(), "Radius must be greater than 0", "Error",
								JOptionPane.WARNING_MESSAGE);
					} else {
						Circle newCircle = new Circle(
								new Point(Integer.parseInt(dlgC.getTextX()), Integer.parseInt(dlgC.getTextY())),
								Integer.parseInt(dlgC.getTextRadius()));
						newCircle.setEdgeColor(dlgC.getEdgeColor());
						newCircle.setFillColor(dlgC.getFillColor());

						CmdUpdateCircle cmdCircle = new CmdUpdateCircle(oldCircle, newCircle);
						cmdCircle.execute();
						frame.repaint();

						addCommandsUndo(cmdCircle);
						frame.getDlm().addElement("Update: " + selectShape.toString());
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "You must select a shape.", "Error!",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/*------------DELETE-----------------------*/
	public void delete(MouseEvent e) {
		if (model.getAllSelected().size() == 0)
			return;

		ArrayList<Shape> shapesForDelete = new ArrayList<>();
		for (Shape shape : model.getShapes()) {
			if (shape.isSelected()) {
				shapesForDelete.add(shape);
			}
		}

		int answer = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete shapes?", "Delete",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			frame.getDlm().addElement("Delete: " + shapesForDelete.get(0).toString());

			CmdRemoveShape cmdRemoveShape = new CmdRemoveShape(shapesForDelete, model);
			cmdRemoveShape.execute();

			addCommandsUndo(cmdRemoveShape);
			frame.repaint();
		}

	}

	public void toFront() {
		Iterator<Shape> it = model.getShapes().iterator();
		while (it.hasNext()) {
			Shape shape = it.next();
			if (shape.isSelected()) {
				if (model.getShapes().indexOf(shape) == model.getShapes().size() - 1) {
					JOptionPane.showMessageDialog(null, "Element is alrady in front!");

					break;
				} else {
					if (model.getShapes().indexOf(shape) != model.getShapes().size() - 1) {
						CmdToFront cmdToFront = new CmdToFront(model, shape);
						frame.getDlm().addElement("ToFront:" + shape);
						cmdToFront.execute();
						addCommandsUndo(cmdToFront);
						frame.repaint();
						break;
					}
				}
			}
		}
	}

	public void toBack() {
		Iterator<Shape> iterator = model.getShapes().iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();

			if (shape.isSelected()) {

				if (model.getShapes().indexOf(shape) == 0) {
					JOptionPane.showMessageDialog(null, "Element is alrady in back!");
					break;

				} else {
					CmdToBack toBack = new CmdToBack(model, shape);
					frame.getDlm().addElement("ToBack:" + shape);
					toBack.execute();
					addCommandsUndo(toBack);
					frame.repaint();

					break;
				}
			}
		}
	}

	public void bringToBack() {
		Iterator<Shape> iterator = model.getShapes().iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();

			if (shape.isSelected()) {

				if (model.getShapes().indexOf(shape) == 0) {

					JOptionPane.showMessageDialog(null, "Element is alrady in back!");

					break;
				} else {
					CmdBringToBack bringToBack = new CmdBringToBack(model, shape);
					frame.getDlm().addElement("BringToBack:" + shape);
					bringToBack.execute();

					addCommandsUndo(bringToBack);
					frame.repaint();

					break;
				}
			}
		}
	}

	public void bringToFront() {
		Iterator<Shape> iterator = model.getShapes().iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();

			if (shape.isSelected()) {

				if (model.getShapes().indexOf(shape) == model.getShapes().size() - 1) {
					JOptionPane.showMessageDialog(null, "Element is alrady in front!");

					break;

				} else {
					CmdBringToFront bringToFront = new CmdBringToFront(model, shape);
					frame.getDlm().addElement("BringToFront:" + shape);
					bringToFront.execute();

					addCommandsUndo(bringToFront);
					frame.repaint();

					break;
				}

			}
		}
	}

	public void btnClickUndo() {
		cmdLast = undoCmd.getLast();

		for (int i = 0; i < undoCmd.size() - 1; i++) {
			cmdPrev = undoCmd.get(i);
		}
		undoCmd.getLast().unexecute();
		addCommandsRedo(undoCmd.getLast());
		frame.getBtnRedo().setEnabled(true);
		removeCommandsUndo(undoCmd.removeLast());

		if (undoCmd.size() > 0) {
			if (cmdLast instanceof CmdRemoveShape && cmdPrev instanceof CmdRemoveShape) {
				btnClickUndo();
			}
		} else
			frame.getBtnUndo().setEnabled(false);

		frame.getDlm().addElement("Undo() ");
		frame.repaint();
	}

	public void redo() {
		cmdLast = redoCmd.getLast();

		for (int i = 0; i < redoCmd.size() - 1; i++) {
			cmdPrev = redoCmd.get(i);
		}
		redoCmd.getLast().execute();
		addCommandsUndo(redoCmd.getLast());
		removeCommandsRedo(redoCmd.removeLast());

		if (redoCmd.size() > 0) {
			if (cmdLast instanceof CmdRemoveShape && cmdPrev instanceof CmdRemoveShape) {
				redo();
			}
		} else
			frame.getBtnRedo().setEnabled(false);

		frame.getDlm().addElement("Redo() ");
		frame.repaint();
	}

	public void checkRedo() {

		if (redoCmd.size() > 0) {
			for (int i = 0; i < redoCmd.size() - 1; i++) {
				cmdPrev = redoCmd.get(i);
			}
			cmdLastRedo = redoCmd.getLast();

			if (cmdLastRedo instanceof CmdRemoveShape || cmdLastRedo instanceof CmdAddShape) {
				removeCommandsRedo(redoCmd.removeLast());

				if (cmdLastRedo instanceof CmdRemoveShape && cmdPrev instanceof CmdRemoveShape) {
					removeCommandsRedo(redoCmd.removeLast());
				}
			}
		}

		if (redoCmd.size() == 0)
			frame.getBtnNext().setEnabled(true);
		return;
	}

	public void undoLog() {
		cmdLast = undoCmd.getLast();

		undoCmd.getLast().unexecute();
		addCommandsRedo(undoCmd.getLast());
		frame.getBtnRedo().setEnabled(true);
		removeCommandsUndo(undoCmd.removeLast());

		if (undoCmd.size() == 0) {
			frame.getBtnUndo().setEnabled(false);
		}

		frame.getDlm().addElement("Undo() " + cmdLast.toString());
		frame.repaint();
	}

	public void redoLog() {
		cmdLast = redoCmd.getLast();

		redoCmd.getLast().execute();
		addCommandsUndo(redoCmd.getLast());
		removeCommandsRedo(redoCmd.removeLast());

		if (redoCmd.size() == 0) {
			frame.getBtnRedo().setEnabled(false);
			done = true;
		}

		frame.getDlm().addElement("Redo() ");
		frame.repaint();
	}

	public void saveLog() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Save log");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text (.txt)", "txt");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showSaveDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {
			System.out.println("Successfully saved " + fileChooser.getSelectedFile().getName() + " file!");

			File file = fileChooser.getSelectedFile();

			FileManager manager = new FileManager(new LogStrategy());
			manager.save(model, frame, file);
		}

		fileChooser.setVisible(false);

		frame.repaint();
	}

	public void openLog() {
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Open log");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text (.txt)", "txt");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showOpenDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {

			System.out.println("Successfully opened " + fileChooser.getSelectedFile().getName() + " file!");

			File file = fileChooser.getSelectedFile();
			strategy = new LogStrategy();
			FileManager manager = new FileManager(strategy);
			manager.open(model, frame, file);

			fileChooser.setVisible(false);
			undoCmd.clear();
			redoCmd.clear();
			setNumOfRedoCommands(0);
			setNumOfUndoCommands(0);

			// list size da znam do kad citam
			size = strategy.getLogList().size();

			// crtaj redom
			frame.getBtnNext().setEnabled(true);
			next();
		}
	}

	public void next() {
		if (i < size) {

			String line = strategy.getLogList().get(i);
			System.out.println("Log line: " + line + " " + i);

			if (line.contains("Point")) {

				int x = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf("y=") + 2, line.indexOf(", selected")));
				String isSelected = line.substring(line.indexOf("ed=") + 3, line.indexOf(", color"));
				String color = line.substring(line.lastIndexOf(":") + 1, line.indexOf("]"));
				Color col = Color.BLACK;
				col = new Color(Integer.parseInt(color));
				shape = new Point(x, y, col);

				if (isSelected.equals("true")) {
					shape.setSelected(true);
				} else {
					shape.setSelected(false);
				}

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}
			} else if (line.contains("Delete")) {
				ArrayList<Shape> shapes = new ArrayList<>();
				for (Shape shape : model.getShapes()) {
					if (shape.isSelected()) {
						shapes.add(shape);
					}
				}
				frame.getDlm().addElement("Delete: " + shapes.get(0).toString());

				CmdRemoveShape cmdRemoveShape = new CmdRemoveShape(shapes, model);
				cmdRemoveShape.execute();

				addCommandsUndo(cmdRemoveShape);
				frame.repaint();
			} else if (line.contains("BringToBack")) {

				CmdBringToBack cmdBringToBack = new CmdBringToBack(model, model.getSelected(0));
				frame.getDlm().addElement("BringToBack: " + model.getSelected(0).toString());
				cmdBringToBack.execute();

				addCommandsUndo(cmdBringToBack);
			} else if (line.contains("ToBack")) {
				System.out.println("model u toBack " + shape);
				CmdToBack cmdToBack = new CmdToBack(model, model.getSelected(0));
				frame.getDlm().addElement("ToBack: " + model.getSelected(0));
				cmdToBack.execute();

				addCommandsUndo(cmdToBack);
			} else if (line.contains("BringToFront")) {

				CmdBringToFront cmdBringToFront = new CmdBringToFront(model, model.getSelected(0));
				frame.getDlm().addElement("BringToFront: " + model.getSelected(0));
				cmdBringToFront.execute();

				addCommandsUndo(cmdBringToFront);
			} else if (line.contains("ToFront")) {

				CmdToFront cmdToFront = new CmdToFront(model, model.getSelected(0));
				frame.getDlm().addElement("ToFront: " + model.getSelected(0));
				cmdToFront.execute();

				addCommandsUndo(cmdToFront);
			} else if (line.contains("Line")) {

				int x = Integer.parseInt(line.substring(line.indexOf("[") + 1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf("]")));
				int x1 = Integer.parseInt(line.substring(line.lastIndexOf("[") + 1, line.lastIndexOf(",")));
				int y1 = Integer.parseInt(line.substring(line.lastIndexOf(",") + 1, line.lastIndexOf("]")));
				String isSelected = line.substring(line.indexOf("ed=") + 3, line.indexOf(" color"));
				String color = line.substring(line.lastIndexOf(":") + 1);
				Color col = Color.BLACK;
				col = new Color(Integer.parseInt(color));

				shape = new Line(new Point(x, y), new Point(x1, y1), col);

				if (isSelected.equals("true")) {
					shape.setSelected(true);
				} else {
					shape.setSelected(false);
				}

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}
			} else if (line.contains("Rectangle")) {

				int x = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf("y=") + 2, line.indexOf("]")));
				int height = Integer.parseInt(line.substring(line.lastIndexOf("ht=") + 3, line.lastIndexOf(", width")));
				int width = Integer.parseInt(line.substring(line.indexOf("th=") + 3, line.lastIndexOf(", selected")));
				String isSelected = line.substring(line.indexOf("ed=") + 3, line.indexOf(", fillColor"));
				String fillColor = line.substring(line.lastIndexOf("lColor=") + 7, line.lastIndexOf(","));
				String edgeColor = line.substring(line.lastIndexOf("=") + 1);
				Color fillCol = Color.WHITE;
				Color edgeCol = Color.BLACK;

				fillCol = new Color(Integer.parseInt(fillColor));
				edgeCol = new Color(Integer.parseInt(edgeColor));

				shape = new Rectangle(new Point(x, y), height, width, edgeCol, fillCol);
				if (isSelected.equals("true")) {
					shape.setSelected(true);
				} else {
					shape.setSelected(false);
				}

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}
			} else if (line.contains("Circle")) {

				int x = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf("y=") + 2, line.indexOf("]")));
				int radius = Integer.parseInt(line.substring(line.indexOf("us=") + 3, line.indexOf(", selected")));
				String isSelected = line.substring(line.indexOf("ed:") + 3, line.indexOf(", edge"));
				String edgeColor = line.substring(line.indexOf("or:") + 3, line.lastIndexOf(","));
				String fillColor = line.substring(line.lastIndexOf(":") + 1);
				Color ec = Color.BLACK;
				Color fc = Color.WHITE;

				System.out.println("isselected " + isSelected);

				ec = new Color(Integer.parseInt(edgeColor));
				fc = new Color(Integer.parseInt(fillColor));

				shape = new Circle(new Point(x, y), radius, fc, ec);
				if (isSelected.equals("true")) {
					shape.setSelected(true);
				} else {
					shape.setSelected(false);
				}

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}
			} else if (line.contains("Donut")) {
				int x = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf("y=") + 2, line.indexOf("]")));
				int radius = Integer.parseInt(line.substring(line.indexOf("us=") + 3, line.indexOf(", selected")));
				String isSelected = line.substring(line.indexOf("ted=") + 4, line.indexOf(", edge"));
				String edgeColor = line.substring(line.indexOf("or:") + 3, line.indexOf(", fill"));
				String fillColor = line.substring(line.lastIndexOf(":") + 1, line.lastIndexOf(","));
				int innerR = Integer.parseInt(line.substring(line.lastIndexOf("=") + 1));
				Color ec = Color.BLACK;
				Color fc = Color.WHITE;

				ec = new Color(Integer.parseInt(edgeColor));
				fc = new Color(Integer.parseInt(fillColor));

				shape = new Donut(new Point(x, y), radius, innerR, ec, fc);

				if (isSelected.equals("true")) {
					shape.setSelected(true);
				} else {
					shape.setSelected(false);
				}

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}
			} else if (line.contains("Heksagon")) {
				int x = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
				int y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
				int r = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(", selected")));
				String isSelected = line.substring(line.indexOf("ted=") + 4, line.indexOf(", edge"));
				String edgeColor = line.substring(line.indexOf("or:") + 3, line.lastIndexOf(","));
				String fillColor = line.substring(line.lastIndexOf(":") + 1);
				Color ec = Color.BLACK;
				Color fc = Color.WHITE;

				fc = new Color(Integer.parseInt(fillColor));
				ec = new Color(Integer.parseInt(edgeColor));

				HexagonAdapter hex = new HexagonAdapter(new Hexagon(x, y, r));

				hex.getHexagon().setBorderColor(ec);
				hex.getHexagon().setAreaColor(fc);
				shape = hex;

				if (isSelected.equals("true")) {
					shape.setSelected(true);
				} else {
					shape.setSelected(false);
				}

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}
			}
			if (line.contains("Update")) {
				if (shape instanceof Point) {
					Point newPoint = (Point) shape;

					CmdUpdatePoint cmd = new CmdUpdatePoint((Point) model.getSelected(0), newPoint);
					cmd.execute();

					addCommandsUndo(cmd);
					frame.getDlm().addElement("Update: " + newPoint.toString());
				} else if (shape instanceof Line) {
					Line newLine = (Line) shape;

					CmdUpdateLine cmd = new CmdUpdateLine((Line) model.getSelected(0), newLine);
					cmd.execute();

					addCommandsUndo(cmd);
					frame.getDlm().addElement("Update: " + newLine.toString());
				} else if (shape instanceof Rectangle) {
					Rectangle newRectangle = (Rectangle) shape;
					System.out.println("new s " + newRectangle);

					CmdUpdateRectangle cmd = new CmdUpdateRectangle((Rectangle) model.getSelected(0), newRectangle);
					cmd.execute();

					addCommandsUndo(cmd);
					frame.getDlm().addElement("Update: " + newRectangle.toString());
				} else if (shape instanceof HexagonAdapter) {
					HexagonAdapter newHexagon = (HexagonAdapter) shape;
					System.out.println("new s " + shape);
					System.out.println("old s " + oldShape);

					CmdUpdateHexagon cmd = new CmdUpdateHexagon((HexagonAdapter) model.getSelected(0), newHexagon);
					cmd.execute();

					addCommandsUndo(cmd);
					frame.getDlm().addElement("Update: " + shape.toString());
				} else if (shape instanceof Donut) {
					Donut newDonut = (Donut) shape;
					System.out.println("new s " + newDonut);

					CmdUpdateDonut cmd = new CmdUpdateDonut((Donut) model.getSelected(0), newDonut);
					cmd.execute();

					addCommandsUndo(cmd);
					frame.getDlm().addElement("Update: " + newDonut.toString());
				} else if (shape instanceof Circle) {
					Circle newCircle = (Circle) shape;
					System.out.println("new s " + newCircle);

					CmdUpdateCircle cmd = new CmdUpdateCircle((Circle) model.getSelected(0), newCircle);
					cmd.execute();

					addCommandsUndo(cmd);
					frame.getDlm().addElement("Update: " + newCircle.toString());
				}

			} else if (line.contains("Draw")) {
				drawLog(shape);
			} else if (line.contains("Undo")) {
				undoLog();

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}

			} else if (line.contains("Redo")) {
				redoLog();

				if (i == size - 1) {
					frame.getBtnNext().setEnabled(false);
				}
			} else if (line.contains("Select")) {
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (shape.equals(model.getShapes().get(i))) {
						shape = model.getShapes().get(i);
						// selectedList.add(s);
					}
				}
				logSelect(model, shape);
			} else if (line.contains("Deselect")) {
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (shape.equals(model.getShapes().get(i))) {
						shape = model.getShapes().get(i);
						// selectedList.remove(s);
					}
				}
				logDeselect(shape);
			}
			i++;
			frame.repaint();
		}
	}

	public void drawLog(Shape s) {
		CmdAddShape cmdAddShape = new CmdAddShape(model, s);
		cmdAddShape.execute();

		addCommandsUndo(cmdAddShape);
		checkRedo();

		frame.getDlm().addElement("Draw: " + s.toString());
	}

//SAVE DRAWING
	public void saveDrawing() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Save drawing");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("drawing (.pnt)", "pnt");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showSaveDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {

			File file = fileChooser.getSelectedFile();

			FileManager fileManager = new FileManager(new DrawingStrategy());
			fileManager.save(model, frame, file);
		}

		fileChooser.setVisible(false);

		frame.repaint();
	}

//OPEN DRAWING
	public void openDrawing() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Open drawing");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("drawing (.pnt)", "pnt");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showOpenDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {
			System.out.println("Successfully opened " + fileChooser.getSelectedFile().getName() + " file!");

			File file = fileChooser.getSelectedFile();

			FileManager drawingManager = new FileManager(new DrawingStrategy());
			drawingManager.open(model, frame, file);

			fileChooser.setVisible(false);

			undoCmd.clear();
			redoCmd.clear();
			setNumOfRedoCommands(0);
			setNumOfUndoCommands(0);

			frame.repaint();
		}
	}

	public void logSelect(DrawingModel m, Shape s) {
		CmdSelectShape cmd = new CmdSelectShape(m, s);
		cmd.execute();
		addCommandsUndo(cmd);

		frame.getDlm().addElement("Select: " + s.toString());
	}

	public void logDeselect(Shape s) {
		CmdDeselectShape cmd = new CmdDeselectShape(model, s);
		cmd.execute();
		addCommandsUndo(cmd);

		frame.getDlm().addElement("Deselect: " + s.toString());
	}

	@Override
	public void addObserver(Observer observer) {

		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {

		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(model.getNumOfSelected(), model.getNumOfElements(), numOfUndoCommands, numOfRedoCommands);
		}
	}

//UNDO
	public LinkedList<Command> getCommandsUndo() {
		return undoCmd;
	}

	public void setCommandsUndo(LinkedList<Command> commandsUndo) {
		this.undoCmd = commandsUndo;
	}

	public void addCommandsUndo(Command c) {
		undoCmd.add(c);
		setNumOfUndoCommands(this.undoCmd.size());

	}

	public void removeCommandsUndo(Command c) {
		undoCmd.remove(c);
		setNumOfUndoCommands(this.undoCmd.size());
	}

//REDO
	public LinkedList<Command> getCommandsRedo() {
		return redoCmd;
	}

	public void setCommandsRedo(LinkedList<Command> commandsRedo) {
		this.redoCmd = commandsRedo;
	}

	public void addCommandsRedo(Command c) {
		redoCmd.add(c);
		setNumOfRedoCommands(this.redoCmd.size());
	}

	public void removeCommandsRedo(Command c) {
		redoCmd.remove(c);
		setNumOfRedoCommands(this.redoCmd.size());
	}

//NumOfUndo/RedoCommands
	public int getNumOfUndoCommands() {
		return numOfUndoCommands;
	}

	public void setNumOfUndoCommands(int numOfUndoCommands) {
		this.numOfUndoCommands = numOfUndoCommands;
		notifyObservers();
	}

	public int getNumOfRedoCommands() {
		return numOfRedoCommands;
	}

	public void setNumOfRedoCommands(int numOfRedoCommands) {
		this.numOfRedoCommands = numOfRedoCommands;
		notifyObservers();
	}

	public Shape getTestShape() {
		return selectShape;
	}

	public void setTestShape(Shape testShape) {
		this.selectShape = testShape;
	}

	public void addOuterColor() {

		colOuter = JColorChooser.showDialog(null, "Choose a color!", colOuter);
		if (colOuter != null) {
			frame.getBtnOuterColor().setBackground(colOuter);
		}
	}

	public void addInnerColor() {

		colInner = JColorChooser.showDialog(null, "Choose a color!", colInner);
		if (colInner != null) {
			frame.getBtnInnerColor().setBackground(colInner);
		}
	}

	public DrawingModel getModel() {
		return model;
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

}
