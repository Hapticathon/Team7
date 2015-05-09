package com.hapticon.t_habits.service;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessageRequest implements IServiceRequest {

	Message msg = null;
	
	public MessageRequest(Message msg) {
		this.msg = msg;
	}

	@Override
	public void execute(Messenger service) throws RemoteException {
		service.send(msg);
	}

}
