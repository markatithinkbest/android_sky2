package Tool;

import java.util.ArrayList;

public class TakeHttpUrl {
	
public static ArrayList<String> getUrl(String strings){
	ArrayList<String> imgs = new ArrayList<String>();
	String[] names = strings.split(":\"");
	for (int i = 0; i < names.length; i++){
		String[] urls = names[i].split("\";");
//		 System.out.println(names[i]);
		 for (int j = 0; j < urls.length; j++){
			 if(urls[j].startsWith("http:") == true){
				if(urls[j].endsWith(".png") == true ||urls[j].endsWith(".jpg") == true
						|| urls[j].endsWith(".PNG") == true ||urls[j].endsWith(".JPG") == true){
				imgs.add(urls[j]);
//				 System.out.println(urls[j]);
				}
			}
		 }
	}
//	for(int i=0; names.length;i++){
////		if(name.startsWith("http:") == true){
////			if(name.endsWith(".png") == true ||name.endsWith(".jpg") == true){
////			imgs.add(name);
////			 System.out.println(name);
////			}
////		}
//	}
	return imgs;
	
	
}

}
