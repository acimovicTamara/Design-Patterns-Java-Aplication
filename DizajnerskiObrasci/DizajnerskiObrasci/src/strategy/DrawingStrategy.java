package strategy;

import java.util.List;

import geometry.Shape;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class DrawingStrategy implements OpenSaveStrategy {

	@Override
	public void save(DrawingModel model, DrawingFrame frame, File file) {
		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(file + ".pnt"));

			objectOut.writeObject(model.getShapes());

			objectOut.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedWriter writer;

		try {
			writer = new BufferedWriter(new FileWriter(file + ".txt"));

			for (int i = 0; i < frame.getDlm().getSize(); i++) {
				writer.write(frame.getDlm().getElementAt(i));
				writer.newLine();
			}
			writer.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void open(DrawingModel model, DrawingFrame frame, File file) {
		try {
			frame.getDlm().clear();

			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));

			model.setShapes((List<Shape>) objectInput.readObject());

			objectInput.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		frame.getDlm().clear();

		String path = file.getPath();
		path = path.replace(".pnt", ".txt");

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line;

			while ((line = br.readLine()) != null) {
				frame.getDlm().addElement(line);
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	}

}
