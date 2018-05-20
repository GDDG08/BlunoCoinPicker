package com.zzh.hfs.plus;

import android.app.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.design.widget.*;
import android.view.*;
import com.github.chrisbanes.photoview.*;
import com.zzh.hfs.plus.data.*;
import android.support.v7.app.*;

public class PhotoViewActivity extends AppCompatActivity
{

	private String name;

	private final Drawable da=Varinfo.pic_view;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_photoview);
		//setTheme(R.style.
		name=getIntent().getExtras().getString("examName");
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.photo_fab);
        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
					MainActivity.save(PhotoViewActivity.this,((BitmapDrawable)da),"Paper-"+Varinfo.active_user+"-"+name,true);
					
				}
            });
		content();
    }
    void content()
    {
		//da=Varinfo.pic_view;
		PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
		photoView.setImageDrawable(da);
	}
}
