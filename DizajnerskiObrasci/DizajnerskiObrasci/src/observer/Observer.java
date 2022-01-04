package observer;

public interface Observer {

	void update (int numOfSelected, int numOfElements, int numOfUndoCommands, int numOfRedoCommands);
}
