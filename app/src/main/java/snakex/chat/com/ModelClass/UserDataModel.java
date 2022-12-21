package snakex.chat.com.ModelClass;

public class UserDataModel {

String
		UserId
		,CoverPhotoUrl
		, Name
,ProfilePhotoUrl
		,userEmail
		,userPassword

		;

public UserDataModel(String userId, String coverPhotoUrl, String name, String profilePhotoUrl, String userEmail, String userPassword) {
	 UserId = userId;
	 CoverPhotoUrl = coverPhotoUrl;
	 Name = name;
	 ProfilePhotoUrl = profilePhotoUrl;
	 this.userEmail = userEmail;
	 this.userPassword = userPassword;
}

public UserDataModel() {
}


public String getName() {
	 return Name;
}

public void setName(String name) {
	 Name = name;
}

public String getUserEmail() {
	 return userEmail;
}

public void setUserEmail(String userEmail) {
	 this.userEmail = userEmail;
}

public String getUserPassword() {
	 return userPassword;
}

public void setUserPassword(String userPassword) {
	 this.userPassword = userPassword;
}

public String getCoverPhotoUrl() {
	 return CoverPhotoUrl;
}

public void setCoverPhotoUrl(String coverPhotoUrl) {
	 CoverPhotoUrl = coverPhotoUrl;
}

public String getProfilePhotoUrl() {
	 return ProfilePhotoUrl;
}

public void setProfilePhotoUrl(String profilePhotoUrl) {
	 ProfilePhotoUrl = profilePhotoUrl;
}

public String getUserId() {
	 return UserId;
}

public void setUserId(String userId) {
	 UserId = userId;
}
}
