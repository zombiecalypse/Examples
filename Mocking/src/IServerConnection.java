
public interface IServerConnection {
	public void connectToServer();
	public void requestData();
	public String pull();
	public void terminateConnection();
}
