package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String,Order>order_DB=new HashMap<>();
    HashMap<String,DeliveryPartner>Partner_DB=new HashMap<>();
    HashMap<String, ArrayList<String>>order_partner_DB=new HashMap<>();

    ArrayList<String>OrderList=new ArrayList<>();



    public void addOrder(Order order) {
        order_DB.put(order.getId(),order);
    }


    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        Partner_DB.put(partnerId,deliveryPartner);


    }
    public void addOrderPartnerPair(String orderId, String partnerId) {
       if(!order_partner_DB.containsKey(orderId))
       {
           ArrayList<String> list=new ArrayList<>();
           list.add(orderId);
           order_partner_DB.put(partnerId,list);

       }
       else {
           ArrayList<String> list = order_partner_DB.get(partnerId);
           list.add(orderId);
           order_partner_DB.put(partnerId, list);
           int n = list.size();
           DeliveryPartner partner = Partner_DB.get(partnerId);
           partner.setNumberOfOrders(n);
       }
        OrderList.add(orderId);
    }

    public Order getOrderById(String orderId) {
        return order_DB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return Partner_DB.get(partnerId) ;

    }

    public int getOrderCountByPartnerId(String partnerId) {
       return Partner_DB.get(partnerId).getNumberOfOrders();

    }

    public List<String> getOrdersByPartnerId(String partnerId) {

        return order_partner_DB.get(partnerId);
    }

    public List<String> getAllOrders() {
return OrderList;
    }

    public Integer getCountOfUnassignedOrders() {

         return order_DB.size() -OrderList.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
       ArrayList<String>orders= order_partner_DB.get(partnerId);

        int hours=0;
        int minutes=0;

        hours= Integer.parseInt(time.substring(0,2));
        minutes= Integer.parseInt(time.substring(3,5));
        hours=hours*60;
        int deliveryTime=hours+minutes;
        int ans=0;

       for(String s:orders)
       {

           int t=order_DB.get(s).getDeliveryTime();

           if(t>deliveryTime)
           {
               ans++;
           }
       }
        return ans;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int Time=0;
        ArrayList<String>orders= order_partner_DB.get(partnerId);
        for(String s:orders)
        {

            int t=order_DB.get(s).getDeliveryTime();

            if(t>Time)
            {
                Time=t;
            }
        }
        //make t to string
        String ans="";

        if(Time>=600)
        {
            int n=Time/60;
            ans=ans+(char)n;
            Time=Time/60;
        }
        else if (Time<600)
        {
            if(Time<60)
            {
                ans="00";
            }
            else
            {
                ans="0";
                int n=Time/60;
                ans=ans+(char)n;
                Time=Time/60;
            }

        }

        ans=ans+":";
        ans=ans+(char)Time;

        return ans;
    }

    public void deletePartnerById(String partnerId) {
        Partner_DB.remove(partnerId);
        order_partner_DB.remove(partnerId);

    }
    public void deleteOrderById(String orderId) {
        order_DB.remove(orderId);
        OrderList.remove(orderId);
        for(String s:order_partner_DB.keySet())
        {
         ArrayList<String>list=new ArrayList<>();
         list=order_partner_DB.get(s);
            for(int i=0;i< list.size();i++)
            {
                ArrayList<String> id=order_partner_DB.get(i);
                if(id.equals(orderId)) {
                    list.remove(i);
                    order_partner_DB.put(s,list);
                    break;
                }

            }
        }


    }
}
