package com.example.fragment;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.listener.MyItemLongClickListener;
import com.example.listener.MyItemOnclickListener;
import com.example.myhandler.ListviewUpdateHandler;
import com.example.myplayer.MainActivity;
import com.example.myplayer.R;
import com.example.vo.MusicVO;
import com.example.vo.MyConstent;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AllMusicFragment extends Fragment {

	private ListView listview;
	private List<MusicVO> list;
	private Uri uri;
	private ParcelFileDescriptor pfd;
	private FileDescriptor fd;
	private ListViewAdapter listviewadapter;
	public static ListviewUpdateHandler myhandler;
	private int whichlist;
	public AllMusicFragment(List<MusicVO> list,int whichlist) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.whichlist=whichlist;
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_layout, container, false);
		listview=(ListView)view.findViewById(R.id.fragmentlistview);
		listviewadapter=new ListViewAdapter(inflater,list,MyConstent.ALL_MUSIC_LIST,(MainActivity)getActivity());
		myhandler=new ListviewUpdateHandler(listviewadapter);
		listview.setAdapter(listviewadapter);
		listview.setOnItemClickListener(new MyItemOnclickListener((MainActivity)getActivity(),
				whichlist));
		listview.setOnItemLongClickListener(new MyItemLongClickListener(inflater,
        		(MainActivity)getActivity(), whichlist));
		ViewGroup p = (ViewGroup)view.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        } 
		return view;
	}
	
	
	public static class ListViewAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<MusicVO> list;
		private int islove;
		private MainActivity activity;
		public ListViewAdapter(LayoutInflater inflater,List<MusicVO> list,int islove,MainActivity activity) {
			// TODO Auto-generated constructor stub
			this.inflater=inflater;
			this.list=list;
			this.islove=islove;
			this.activity=activity;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(list==null)
				return 0;
			else
				return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			// return list.get(position).music_id;
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null)
				convertView=inflater.inflate(R.layout.music_item_layout, null);
			MusicVO music=list.get(position);
			((TextView)convertView.findViewById(R.id.music_name)).setText(music.getMusic_name());
			((TextView)convertView.findViewById(R.id.singer)).setText(music.getSinger_name());
			((TextView)convertView.findViewById(R.id.file_size)).setText(music.getFile_size());
			((TextView)convertView.findViewById(R.id.music_time)).setText(music.getDuration());
			ImageView singer_picture=(ImageView)convertView.findViewById(R.id.singer_picture);
			if(islove==MyConstent.MYLOVE_MUSIC_LIST)
				;
//				singer_picture.setImageResource(R.drawable.love);
//			uri = Uri.parse(music.music_uri);
//			try {
//				pfd = getActivity().getContentResolver().openFileDescriptor(uri, "r");
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//			if(pfd != null) {
//				fd = pfd.getFileDescriptor();
//			}
//			if(fd!=null){
//				Bitmap bitmap=BitmapFactory.decodeFileDescriptor(fd);
//				singer_picture.setImageBitmap(bitmap);
//			}
//			try {
//				if(pfd!=null)
//					pfd.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			Uri uri = Uri.parse(music.music_uri);
//			Bitmap bm =getArtwork(activity, music.music_id, -1);
	/*		
			Bitmap bm=null;
			Uri uri = Uri.parse("content://media/external/audio/media/" + music.music_id+ "/albumart");  
            ParcelFileDescriptor pfd;
			try {
				pfd = activity.getContentResolver().openFileDescriptor(uri, "r");
				if (pfd != null) {  
	                FileDescriptor fd = pfd.getFileDescriptor();  
	                bm = BitmapFactory.decodeFileDescriptor(fd);  
	            }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			if(bm!=null)
//			BitmapDrawable bmpDraw = new BitmapDrawable(bm); 
				singer_picture.setImageBitmap(bm);
			*/
			return convertView;
		}
		
	    public static Bitmap getArtwork(Context context, long song_id, long album_id) {  
            if (album_id < 0) {  
                // This is something that is not in the database, so get the album art directly  
                // from the file.  
                if (song_id >= 0) {  
                    Bitmap bm = getArtworkFromFile(context, song_id, -1);  
                    if (bm != null) {  
                        return bm;  
                    }  
                }  
                return null;  
            }  
            ContentResolver res = context.getContentResolver();  
            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);  
            if (uri != null) {  
                InputStream in = null;  
                try {  
                    in = res.openInputStream(uri);  
                    return BitmapFactory.decodeStream(in, null, sBitmapOptions);  
                } catch (FileNotFoundException ex) {  
                    Bitmap bm = getArtworkFromFile(context, song_id, album_id);  
                    if (bm != null) {  
                        if (bm.getConfig() == null) {  
                            bm = bm.copy(Bitmap.Config.RGB_565, false);  
                        }  
                    return bm;}
                } finally {  
                    try {  
                        if (in != null) {  
                            in.close();  
                        }  
                    } catch (IOException ex) {  
                    }  
                }  
            }  
              
            return null;  
        }  
          
        private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {  
            Bitmap bm = null;  
            if (albumid < 0 && songid < 0) {  
                throw new IllegalArgumentException("Must specify an album or a song id");  
            }  
            try {  
                if (albumid < 0) {  
                    Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");  
                    ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");  
                    if (pfd != null) {  
                        FileDescriptor fd = pfd.getFileDescriptor();  
                        bm = BitmapFactory.decodeFileDescriptor(fd);  
                    }  
                } else {  
                    Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);  
                    ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");  
                    if (pfd != null) {  
                        FileDescriptor fd = pfd.getFileDescriptor();  
                        bm = BitmapFactory.decodeFileDescriptor(fd);  
                    }  
                }  
            } catch (FileNotFoundException ex) {  
       
            }  
            return bm;  
        }  
          
        private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");  
        private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();  
		
	}


}
