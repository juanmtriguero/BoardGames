package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Embeddable
@Access(AccessType.PROPERTY)
public class PlayerForm {

	private String username;
	private String password;
	private String confirmPassword;

	private String name;
	private String email;
	private String avatar;

	private Boolean acceptConditions;

	@Size(min=5, max=32)
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min=5, max=32)
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Size(min=5, max=32)
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@Email
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@URL
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@NotNull
	public Boolean getAcceptConditions() {
		return acceptConditions;
	}

	public void setAcceptConditions(Boolean acceptConditions) {
		this.acceptConditions = acceptConditions;
	}

}
