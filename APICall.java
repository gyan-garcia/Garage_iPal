import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.awt.Image;


public class APICall
{  
    //might not take a string
    public string callCognitiveServices(BufferedImage img){

        try
        {
            //Create string representation of image

    	// write it to byte array in-memory (jpg format)
    	ByteArrayOutputStream b = new ByteArrayOutputStream();
    	ImageIO.write(img, "jpg", b);

    	// do whatever with the array...
   	 byte[] jpgByteArray = b.toByteArray();

   	 // convert it to a String with 0s and 1s        
   	 StringBuilder sb = new StringBuilder();
    	for (byte by : jpgByteArray)
        	sb.append(Integer.toBinaryString(by & 0xFF));

    	

            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "{subscription key}");


            // Request body
            StringEntity reqEntity = new StringEntity(sb.toString());
            //fixme 
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}