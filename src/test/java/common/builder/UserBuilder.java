package common.builder;

import common.dto.User;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class UserBuilder {
    User user = new User();

    public UserBuilder withFirstName(String name){
        this.user.setFirstName(name);
        return this;
    }

    public UserBuilder withLastName(String lastname){
        this.user.setSurname(lastname);
        return this;
    }

    public UserBuilder withPhone(String phone){
        this.user.setPhone(phone);
        return this;
    }
    public UserBuilder withEmail(String email){
        this.user.setEmail(email);
        return this;
    }

    public UserBuilder withDefaultPassword(){
        this.user.setPassWord(getProperty("site.password"));
        return this;
    }

    public UserBuilder withPassword(String password){
        this.user.setPassWord(password);
        return this;
    }

    public UserBuilder withSex(String sex){
        this.user.setSex(sex);
        return this;
    }

    public UserBuilder withBirthDate(String birthDate) {
        this.user.setBirthDate(birthDate);
        return this;
    }

    public User execute(){
        return user;
    }

    public UserBuilder withPatronimic(String patronimic) {
        this.user.setPatronymic(patronimic);
        return this;
    }

    public UserBuilder withUserId(String userId) {
        this.user.setUserId(userId);
        return this;
    }

    public UserBuilder withCard(String cardNumber) {
        this.user.setCardNumber(cardNumber);
        return this;
    }
}
