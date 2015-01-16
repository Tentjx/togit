import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;



public class Equals {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
	}

	
	
	public List<String> selectAlbums(List<Music> list){
		
		List<String> albumList=new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
  			Music music=list.get(i);
  			String albumString=music.getAlbum();

  			if (!albumList.contains(albumString)) {
  				albumList.add(albumString);

        }
		}  
		return albumList;
		
	}
	
	
	public HashSet<String> selectAlbum(List<Music> list){
		String albumName=null ;
		HashSet<String> set=new HashSet<String>();

			for (int i = 0; i < list.size(); i++) {

				Music music=list.get(i);
				albumName=music.getAlbum();
				
					
				set.add(albumName); 

				
			}
			return set;
		

		}
	
	public List<String> selectAlbumName(HashSet<String> set) {
		String albumNameString=null;
		List<String> singerList=new ArrayList<String>();
			Iterator<String> it=set.iterator();
			if(it.hasNext()){
				albumNameString=it.next();
				singerList.add(albumNameString);

			}
			
		
		return  singerList;
		
	}
	
}
