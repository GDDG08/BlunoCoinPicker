package com.zzh.blunocoin.tool;
import android.animation.*;
import android.app.*;
import android.bluetooth.BluetoothAdapter;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.support.annotation.*;
import android.view.*;

import com.zzh.blunocoin.bluno.BluetoothLeService;
import com.zzh.blunocoin.bluno.BlunoLibrary;
import com.zzh.blunocoin.data.*;
import com.zzh.blunocoin.fragment.*;

public class MyFragment extends BlunoLibrary
{
	Boolean first=true;
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        /**
         * 在下面几种情况下不用监听过渡动画
         **/
        if(this instanceof MainFragment/*||Varinfo.osver<19*/||first){
			first=false;
			return null;
        }else{
			Animator anim=null;
			try
			{
				anim = method(nextAnim);
			}
			catch (Resources.NotFoundException e)
			{
				/*if (MyFragment.this instanceof MemberFragment||MyFragment.this instanceof RankFragment)
				{}else{
				Varinfo.mainactivity.StudentName(getActivity());}*/
				return null;
			}

        return anim;
		}
	}

	private Animator method(int nextAnim) throws Resources.NotFoundException
	{
		final Animator anim = AnimatorInflater.loadAnimator(getActivity(), nextAnim);
        anim.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationStart(Animator animation)
                {
                    Varinfo.page_progress.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable(){

                            @Override
                            public void run()
                            {
                                Varinfo.page_container.setVisibility(View.GONE);
                            }}, 200);
                    new Handler().postDelayed(new Runnable(){

                            @Override
                            public void run()
                            {
								//Varinfo.page_scroll.scrollTo(0,0);
                                Varinfo.page_container.setVisibility(View.VISIBLE);
                            }}, 200);
                }

                @Override
                public void onAnimationEnd(Animator animation)
                {

                    new Handler().postDelayed(new Runnable(){

                            @Override
                            public void run()
                            {
                                Varinfo.page_progress.setVisibility(View.INVISIBLE);
								
                            }}, 200);
                }
            });
		
		return anim;
	}

    @Override
    public void onConectionStateChange(connectionStateEnum theconnectionStateEnum) {

    }

    @Override
    public void onSerialReceived(String theString) {

    }

	/*public Context context;
	@Override
	public void onAttach(Context con)
	{
		// TODO: Implement this method
		super.onAttach(context);
		context=getActivity();
	}*/
    /*private Activity activity;

    public Context getContext(){
        if(activity == null){
            return ZZHApplication.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity = getActivity();
    }*/
    /*@Override
    public void onBackPressed()
    {
        if (SpotManager.getInstance(getApplicationContext()).isSpotShowing()) {
            SpotManager.getInstance(getApplicationContext()).hideSpot();
        }else{
            super.onBackPressed();}*/
   /* private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (isSupportHidden) {
				ft.hide(this);
			} else {
				ft.show(this);
			}
			ft.commit();
		}
}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }*/

    @Override
    public void onStart() {
//        if (Varinfo.paused){
            mBluetoothAdapter=Varinfo.mBluetoothAdapter;
            mBluetoothLeService=Varinfo.mBluetoothLeService;
            //onResumeProcess();
            System.out.println("Paused恢复");
//        }
        IntentFilter filter = new IntentFilter(mBluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        getActivity().registerReceiver(mGattUpdateReceiver, filter);
        filter = new IntentFilter(mBluetoothLeService.ACTION_GATT_CONNECTED);
        getActivity().registerReceiver(mGattUpdateReceiver, filter);
        filter = new IntentFilter(mBluetoothLeService.ACTION_DATA_AVAILABLE);
        getActivity().registerReceiver(mGattUpdateReceiver, filter);
        filter = new IntentFilter(mBluetoothLeService.ACTION_GATT_DISCONNECTED);
        getActivity().registerReceiver(mGattUpdateReceiver, filter);
        super.onStart();
        // this.updateUI();
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        getActivity().unregisterReceiver(mGattUpdateReceiver);
//
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);                    //onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }
	
}
