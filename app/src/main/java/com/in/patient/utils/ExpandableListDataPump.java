package com.in.patient.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ");


        List<String> football = new ArrayList<String>();
        football.add("ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans .");

        List<String> basketball = new ArrayList<String>();
        basketball.add("ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans ans .");


        expandableListDetail.put("question 1 ?", cricket);
        expandableListDetail.put("question 2 ?", football);
        expandableListDetail.put("question 3 ?", basketball);
        return expandableListDetail;

    }
}
