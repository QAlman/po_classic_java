package common.dto;

public class User {
    private String firstName;
    private String cardNumber;
    private String phone;
    private String passWord;
    private String surname;
    private String birthdate;
    private String userId;
    private String email;
    private String patronymic;
    private String sex;

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthDate(String birthDate) {
        this.birthdate = birthDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return
                ("{" +
                        "firstName: " + firstName + ", " +
                        "surname: " + surname + ", " +
                        "patronymic: " + patronymic + ", " +
                        "cardNumber: " + cardNumber + ", " +
                        "phone: " + phone + ", " +
                        "passWord: " + passWord + ", " +
                        "birthdate: " + birthdate + ", " +
                        "email: " + email + ", " +
                        "sex: " + sex + ", " +
                        "}");
    }
}
