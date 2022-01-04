package strategy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class LogStrategy implements OpenSaveStrategy {
	private ArrayList<String> logList = new ArrayList<String>();

	@Override
	public void save(DrawingModel model, DrawingFrame frame, File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file + ".txt"));

			for (int i = 0; i < frame.getDlm().getSize(); i++) {
				writer.write(frame.getDlm().getElementAt(i));
				writer.newLine();
			}
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void open(DrawingModel model, DrawingFrame frame, File file) {

		model.getShapes().clear();
		frame.getDlm().clear();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

			String line;

			while ((line = reader.readLine()) != null) {
				logList.add(line);
			}
			reader.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public ArrayList<String> getLogList() {
		System.out.println("Log strategy - Log list size: " + logList.size());
		return logList;
	}

	public void setLogList(ArrayList<String> logList) {
		this.logList = logList;
	}

}
