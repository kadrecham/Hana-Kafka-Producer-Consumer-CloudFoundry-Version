package hello;

import org.json.JSONArray;
import org.json.JSONObject;

public class Environment {
	
	private JSONObject jsonObj;
	
	public Environment (String name){
		String myEnv = "{}";
    	myEnv = java.lang.System.getenv("VCAP_SERVICES");
    	jsonObj = new JSONObject(myEnv);
    	JSONArray jsonArr = jsonObj.getJSONArray(name);
    	jsonObj = jsonArr.getJSONObject(0);
    	jsonObj = jsonObj.getJSONObject("credentials");
	}
	
	public String getURL(){
		return jsonObj.getString("url");
	}
	
	public String getPort(){
		return jsonObj.getString("port");
	}
	
	public String getUser(){
		return jsonObj.getString("user");
	}
	
	public String getPassword(){
		return jsonObj.getString("password");
	}
	
	public String getUsername(){
		return jsonObj.getString("username");
	}
	
	public String getDBname(){
		return jsonObj.getString("dbname");
	}
	
	public String getDriver(){
		return jsonObj.getString("driver");
	}
	
	public String getBrokers(){
		return jsonObj.getString("brokers");
	}
	
	public String getZk(){
		return jsonObj.getString("zk");
	}

}
