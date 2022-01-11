package com.in.patient.retrofit;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("Time Slot")
	@Expose
	private List<TimeSlotItem> timeSlot;

	public void setTimeSlot(List<TimeSlotItem> timeSlot){
		this.timeSlot = timeSlot;
	}

	public List<TimeSlotItem> getTimeSlot(){
		return timeSlot;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"time Slot = '" + timeSlot + '\'' + 
			"}";
		}
}