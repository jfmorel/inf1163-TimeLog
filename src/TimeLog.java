public final class TimeLog {

    private static TimeLog INSTANCE;
    private String info = "Initial info class";
    
    private TimeLog() {        
    }
    
    public static TimeLog getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TimeLog();
        }
        
        return INSTANCE;
    }
    
    public String getInfo() {
    	return this.info;
    }
}