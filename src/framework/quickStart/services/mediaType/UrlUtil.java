package framework.quickStart.services.mediaType;

import org.json.JSONArray;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import framework.quickStart.util.Properties;

public class UrlUtil {

	public static JSONArray getOtherPicturesAsJsonArray(String pictures)
	{				
		JSONArray picsArray = new JSONArray(pictures);
		
		for(int i=0; i< picsArray.length(); i++)
			picsArray.put(i, getPictureUrl(picsArray.getString(i)));
		
		return picsArray;
	}
	
	public static String getPictureUrl(String pictureKey)
	{
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		ServingUrlOptions servingUrlOptions = ServingUrlOptions.Builder.withBlobKey(new BlobKey(pictureKey));

		return  imagesService.getServingUrl(servingUrlOptions);
	}
	
	public static String getItemUrl(java.util.UUID itemId)
	{
		return Properties.getServiceRootPath() + "useritems/" + itemId.toString();
	}
	
	public static String getExchangeMatchUrl(java.util.UUID itemId)
	{
		return Properties.getServiceRootPath() + "exchangematch/" + itemId.toString();
	}
	
	public static String getBuySellMatchUrl(java.util.UUID itemId)
	{
		return Properties.getServiceRootPath() + "buysellmatch/" + itemId.toString();
	}
	
	public static String getFreeMatchUrl(java.util.UUID itemId)
	{
		return Properties.getServiceRootPath() + "freematch/" + itemId.toString();
	}
}