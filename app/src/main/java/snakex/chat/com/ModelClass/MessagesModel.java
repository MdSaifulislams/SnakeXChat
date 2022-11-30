package snakex.chat.com.ModelClass;

public class MessagesModel {
String senderId
		,receiverId
		,timeMils
		,messageText
		,messageKey
		;


public MessagesModel(String senderId, String receiverId, String timeMils, String messageText, String messageKey) {
	 this.senderId = senderId;
	 this.receiverId = receiverId;
	 this.timeMils = timeMils;
	 this.messageText = messageText;
	 this.messageKey = messageKey;
}


public MessagesModel() {
}

public String getSenderId() {
	 return senderId;
}

public String getReceiverId() {
	 return receiverId;
}

public String getTimeMils() {
	 return timeMils;
}

public String getMessageText() {
	 return messageText;
}

public String getMessageKey() {
	 return messageKey;
}
}
