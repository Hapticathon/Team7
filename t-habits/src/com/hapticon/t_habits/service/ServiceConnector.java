package com.hapticon.t_habits.service;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class ServiceConnector {

	Messenger mService = null;
    boolean mIsBound;
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	Context ctx = null;
	
	Queue<IServiceRequest> mServiceRequestQueue = new LinkedBlockingQueue<IServiceRequest>(16);
	
	public ServiceConnector(Context context) {
		this.ctx = context;
		
		Intent i = new Intent(ctx, THabitsService.class);
		ctx.startService(i);
		
		doBindService();
	}

	private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            try {
                Message msg = Message.obtain(null, THabitsService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (RemoteException e) {
            }
            IServiceRequest sd;
            while ((sd = mServiceRequestQueue.poll()) != null) {
                try {
                    sd.execute(mService);
                } catch (RemoteException e) {
                }
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };
    
    void doBindService() {
    	if (!mIsBound) {
    		boolean result = ctx.bindService(new Intent(ctx, THabitsService.class), mConnection, Context.BIND_AUTO_CREATE);
    		if (result) {
    			mIsBound = true;
    		} else {
    			new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						doBindService();
					}
    				
    			}, 1000);
    		}
	        
    	}
    }
    
    public void doUnbindService() {
        if (mIsBound) {
            mServiceRequestQueue.clear();
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null, THabitsService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                }
            }
            ctx.unbindService(mConnection);
            mIsBound = false;
        }
    }
    
//    public void sendUsersLocationToService(GeoLoc location) {
//	    Message msg = Message.obtain(null, NaviGoService.MSG_LOCATION_CHANGED, 0, 0, location);
//	    msg.replyTo = mMessenger;
//	    executeRequest(new MessageRequest(msg));
//    }
    
    private void executeRequest(IServiceRequest sd) {
        if (mService != null) {
            try {
                sd.execute(mService);
                return;
            } catch (RemoteException e) {
                mIsBound = false;
            }
        }
        mServiceRequestQueue.offer(sd);
        doBindService();
    }
    
	class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            default:
                super.handleMessage(msg);
            }
        }
    }

}
