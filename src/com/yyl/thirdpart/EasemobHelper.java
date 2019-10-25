package com.yyl.thirdpart;

import java.net.URL;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.easemob.server.example.comm.Constants;
import com.easemob.server.example.comm.HTTPMethod;
import com.easemob.server.example.httpclient.apidemo.EasemobMessages;
import com.easemob.server.example.httpclient.utils.HTTPClientUtils;
import com.easemob.server.example.jersey.apidemo.EasemobIMUsers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EasemobHelper {

	private static JsonNodeFactory factory = new JsonNodeFactory(false);
	
	public static String CreateEaseMobUser()
	{
		String userName = "";
		for(int i=0;i<12;i++)
		{
			userName = userName+(char) (Math.random ()*26+'a');		
		}
		
		Random random = new Random();
		userName += Math.abs(random.nextInt())%10000;
		
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",userName);
        datanode.put("password", Constants.DEFAULT_PASSWORD);
        ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
		//{"action":"post","application":"d80ce9e0-cecd-11e4-9231-6505094b1e6e","path":"/users","uri":"https://a1.easemob.com/youelink/youelink/users","entities":[{"uuid":"d99b5caa-cece-11e4-bae3-178b2e95c2c1","type":"user","created":1426834853738,"modified":1426834853738,"username":"tcoyvmzc8986","activated":true}],"timestamp":1426834853736,"duration":32,"organization":"youelink","applicationName":"youelink","statusCode":200}
        
        if (null != createNewIMUserSingleNode) {
        	return userName;
        }
        else{
        	return null;
        }
	}
	
	public static String LoadMyFirends(String userName)
	{
		ObjectNode obj = EasemobIMUsers.getFriends(userName);
		
		if (null != obj) 
		{
			String buddys = "";
			for (JsonNode objNode : obj.get("data"))  
			{  
				buddys += objNode.toString().replaceAll("\"", "'") + ",";
			}
			
			if(buddys.endsWith(","))
				buddys = buddys.substring(0,buddys.length()-1);
			
			return buddys;
		} 
		else {
			return null;
		}
	}
	
	public static void SendMessage(String sender,String target,String msg) 
	{
		String targetTypeus = "users";
		ObjectNode ext = factory.objectNode();
		ArrayNode targetusers = factory.arrayNode();
		targetusers.add(target);
		
		ObjectNode txtmsg = factory.objectNode();
		txtmsg.put("msg", msg);
		txtmsg.put("type", "txt");
		ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(targetTypeus,
				targetusers, txtmsg, sender, ext);
		if (null != sendTxtMessageusernode) 
		{
			//LOGGER.info("给用户发一条文本消息: " + sendTxtMessageusernode.toString());
		}
	}
}
