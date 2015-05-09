package com.hapticon.t_habits.service;

import java.util.ArrayList;

import com.hapticon.t_habits.controllers.AppController;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class THabitsService extends Service {

	public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_UNREGISTER_CLIENT = 2;
    
 	ArrayList<Messenger> mClients = new ArrayList<Messenger>();
 	AppController mController = null;
 	
 	final Messenger mMessenger = new Messenger(new IncomingHandler());
 	
 	public THabitsService() {
 		
	}
	
 	@Override
 	  public int onStartCommand(Intent intent, int flags, int startId) {
 	    return Service.START_STICKY;
 	  }
 	
 	@Override
	public IBinder onBind(Intent arg) {
		return mMessenger.getBinder();
	}
 	
//	private void sendMessageToUI(int intvaluetosend) {
//  for (int i=mClients.size()-1; i>=0; i--) {
//      try {
//          // Send data as an Integer
//          mClients.get(i).send(Message.obtain(null, MSG_SET_INT_VALUE, intvaluetosend, 0));
//
//          //Send data as a String
//          Bundle b = new Bundle();
//          b.putString("str1", "ab" + intvaluetosend + "cd");
//          Message msg = Message.obtain(null, MSG_SET_STRING_VALUE);
//          msg.setData(b);
//          mClients.get(i).send(msg);
//
//      } catch (RemoteException e) {
//          // The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
//          mClients.remove(i);
//      }
//  }
//}
 	
 	class IncomingHandler extends Handler { // Handler of incoming messages from clients.
        @SuppressWarnings("unchecked")
		@Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_REGISTER_CLIENT:
            	mClients.add(msg.replyTo);
                break;
            case MSG_UNREGISTER_CLIENT:
            	mClients.remove(msg.replyTo);
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }

}
