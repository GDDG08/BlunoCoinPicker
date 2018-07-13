package com.zzh.blunocoin.fragment;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.view.*;
import android.support.v7.widget.Toolbar;
import com.zzh.blunocoin.*;
import com.zzh.blunocoin.data.*;
import com.zzh.blunocoin.tool.*;
import android.widget.*;

public class MainFragment extends MyFragment
{

    private View view;

    private Context context;

    @Override
    public void onResume()
    {
        Varinfo.page = 1;
       // getActivity().setTitle(R.string.app_name);
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(!hidden){
			Varinfo.page = 1;
			getActivity().setTitle(R.string.app_name);}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 1;
        view = inflater.inflate(R.layout.content_main, container, false);
        //context = getContext();
        context=getActivity();
        setHasOptionsMenu(true);
        content();
       // MT.finish();
       Varinfo.page_container.setVisibility(View.VISIBLE);
       Varinfo.page_progress.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void content()
    {

    }




}
