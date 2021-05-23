package com.ptit.author.config;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.LoggerFactory;


public class HandleSendPhoneNumber {
    public static final String ACCOUNT_SID = "AC1fe1788069786393da6aa84d8f3cd68b";
    public static final String AUTH_TOKEN = "d63a58e370282ac573470d352cec6652";
    public static String sendMessage(String messages,String phoneNumber){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        if(phoneNumber.startsWith("0")){
            phoneNumber="+84"+phoneNumber.substring(1);
        }
      try {
          Message message = Message.creator(new PhoneNumber(phoneNumber),
                  new PhoneNumber("+13093063855"),
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
