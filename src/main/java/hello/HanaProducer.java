package hello;

import java.sql.*;

public class HanaProducer {
	
	static Environment myEnv = new Environment("hana");
	static final String JDBC_DRIVER = myEnv.getDriver();//"com.sap.db.jdbc.Driver";  
    static final String DB_URL = myEnv.getURL();//"jdbc:sap://10.253.67.138:30041/?currentschema=USR_4Z0XZA95F970Y9KR8XM9E9SFX";
    static final String USER = myEnv.getUser();//"USR_4Z0XZA95F970Y9KR8XM9E9SFX";
    static final String PASS = myEnv.getPassword();//"_eHS9r1RWI9dy81j0bjg8nXkaMrmxBgU";
    private Connection conn = null;
    private Statement stmt = null;
    
	public HanaProducer () { 
    try {
		Class.forName( JDBC_DRIVER );
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		stmt = conn.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void write(PojoLocationScanResult obj, String devID) throws SQLException{
		int max = 0;
		//LocationScanResult[] results = obj.getLocationScanResult();
		ResultSet rs = readQuery("select MAX(SCANNUM) from SIGNALSTRINGTH");
		while(rs.next()){
			max = rs.getInt("MAX(SCANNUM)");
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		for (LocationScanResult element :obj.getLocationScanResult()){
		max ++; 
		//String sql = "insert into SIGNALSTRINGTH values("+max+", '"+element.getBSSID()+"', '"+element.getLevel()+"', '"+devID+"', "+timestamp.getTime()+")";
		String sql = "insert into SIGNALSTRINGTH values("+max+", '"+element.getBSSID()+"', '"+element.getLevel()+"', '"+devID+"',"+timestamp.getTime()+")";
		writeQuery(sql);
		
		//System.out.println(element.getBSSID());

		}

		}
	public ResultSet readQuery(String sql){
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void writeQuery(String sql){
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
}
