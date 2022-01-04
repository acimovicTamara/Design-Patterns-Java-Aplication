package strategy;

import java.io.File;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class FileManager implements OpenSaveStrategy {

	private OpenSaveStrategy openSaveStrategy;

	public FileManager(OpenSaveStrategy openSaveStrategy) {
		// super();
		this.openSaveStrategy = openSaveStrategy;
	}

	@Override
	public void save(DrawingModel model, DrawingFrame frame, File file) {
		openSaveStrategy.save(model, frame, file);

	}

	@Override
	public void open(DrawingModel model, DrawingFrame frame, File file) {
		openSaveStrategy.open(model, frame, file);

	}

}
