package com.hapticon.t_habits.service;

import android.os.Messenger;
import android.os.RemoteException;


public interface IServiceRequest {
	void execute(Messenger service) throws RemoteException;;
}
