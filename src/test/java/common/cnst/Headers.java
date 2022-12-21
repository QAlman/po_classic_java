package common.cnst;

import io.restassured.http.Header;

public interface Headers {
    Header CONTENT_TYPE_JSON = new Header("content-type","application/json");
    Header CONTENT_TYPE_URLENCODED = new Header("content-type","application/x-www-form-urlencoded");
    Header SET_COOKIE = new Header("Set-Cookie","");
    Header COOKIE = new Header("cookie","");
    Header AUTHORIZATION_BEARER = new Header("Authorization","");
    Header AUTHORIZATION_LENTOCHKA = new Header("Authorization", f"{CODE}");
    Header BASIC_AUTH = new Header("Authorization", f"{CODE});

}
