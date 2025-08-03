package uk.co.wybar.steamAPI;

import kong.unirest.Cookie;
import kong.unirest.Cookies;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Base class for API calls that require user to be logged in
 */
public class APIHandler {
    private static Cookies sessionCookies;

    /**
     * Singleton (ish) constructor
     *
     * @param sessionCookies A Cookie containing the steamLoginSecure with value
     */
    public APIHandler(ArrayList<Cookie> sessionCookies) {
        if (APIHandler.sessionCookies.isEmpty()) {
            APIHandler.sessionCookies = (Cookies) sessionCookies;
        } else {
            updateSessionCookie((Cookies) sessionCookies);
        }
    }

    protected static Cookies getSessionCookies() {
        return sessionCookies;
    }

    /**
     * Replace the current session cookie
     *
     * @param sessionCookies A cookie containing the steamLoginSecure with value
     */
    public static void updateSessionCookie(Cookies sessionCookies) {
        APIHandler.sessionCookies = sessionCookies;
    }
}