package com.ptit.author.config;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.LoggerFactory;


public class HandleSendPhoneNumber {
    public static final String ACCOUNT_SID = "ACa33c30305da13c5f09c14c12592a159f";
    public static final String AUTH_TOKEN = "938433ca450df1b33c540d2fcae27c80";
    public static String sendMessage(String messages,String phoneNumber){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        if(phoneNumber.startsWith("0")){
            phoneNumber="+84"+phoneNumber.substring(1);
        }
      try {
          Message message = Message.creator(new PhoneNumber(phoneNumber),
                  new PhoneNumber("+18622775065"),
                  messages).create();
          return "success";
      }
      catch (Exception e){
          System.out.println(e);
          return "error";
      }

    }

    public static void main(String[] args) {
        sendMessage(" Mã xác minh của bạn là 265453","+84376867937");
    }
}
