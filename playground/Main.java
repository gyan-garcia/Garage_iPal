import java.net.*;
import java.io.*;

public class Main {
	/*
	// Code to use sockets on java

	*/
	
	// This code does a rest API request
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
	    System.out.println("Hello World!"); // Display the string.
		
		
		Socket s = new Socket("192.168.0.100", 8888);
		DataInputStream din = new DataInputStream(s.getInputStream());
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

		/*String str="",str2="";
		while(!str.equals("stop"))
		{
			str=br.readLine();
			dout.writeUTF(str);
			dout.flush();
			str2=din.readUTF();
			System.out.println("Server says: " + str2);
		}*/
		
		dout.close();
		s.close();
		/*URL url = new URL("http://10.105.164.9:9999/api/people_detection/0/get_distance_from_people2");
        //Insert your JSON query request
        String query = "{'PARAM1': 'VALUE','PARAM2': 'VALUE','PARAM3': 'VALUE','PARAM4': 'VALUE'}";
        //It change the apostrophe char to double colon char, to form a correct JSON string
        query=query.replace("'", "\"");

        try{
            //make connection
            URLConnection urlc = url.openConnection();
            //It Content Type is so importan to support JSON call
            urlc.setRequestProperty("Content-Type", "application/xml");

            System.out.println("Conectando: " + url.toString());
            //use post mode
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            //send query
            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(query);
            System.out.println("Consulta: " + query);
            ps.close();

            //get result
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String l = null;
            while ((l=br.readLine())!=null) {
                System.out.println(l);
            }
            br.close();
        } catch (Exception e){
            System.out.println("Error ocurrido");
            System.out.println(e.toString());
        }*/
		
	}

}