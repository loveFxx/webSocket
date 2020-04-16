package mocean.ws.util;

public class WsResMessage {
	private String name;
	private String context;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getContext() {
        return context;
    }

    @Override
	public String toString() {
		return "WsResMessage [name=" + name + ", context=" + context + "]";
	}

	public void setContext(String context) {
        this.context = context;
    }
    

}
