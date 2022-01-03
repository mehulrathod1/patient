package com.in.patient.retrofit;

import com.google.gson.annotations.SerializedName;

public class TimeSlotItem{

	private String slotTime;

	private String status;

	public void setSlotTime(String slotTime){
		this.slotTime = slotTime;
	}

	public String getSlotTime(){
		return slotTime;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public TimeSlotItem(String slotTime, String status) {
		this.slotTime = slotTime;
		this.status = status;
	}

	@Override
 	public String toString(){
		return 
			"TimeSlotItem{" + 
			"slot_time = '" + slotTime + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}