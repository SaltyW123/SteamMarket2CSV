package uk.co.wybar.steamAPI;

import kong.unirest.*;

import uk.co.wybar.steamAPI.exception.NotLoggedInException;
import uk.co.wybar.steamAPI.exception.WrongQueryException;

/**
 * Class to retrieve the price history of an item
 */
public class MarketHistory extends APIHandler {
    private static final String baseURL = "https://steamcommunity.com/market/pricehistory/";

    /**
     * Normal constructor to register the session
     *
     * @param sessionCookies Cookie that contains the steamLoginSecure session value
     */
    public MarketHistory(Cookies sessionCookies) {
        super(sessionCookies);
    }

    /**
     * Call the API that returns the price history of an item
     *
     * @param appId          The Steam App ID of the game to retrieve items from
     * @param marketHashName The market_hash_name of the item to retrieve price history from
     * @return All price history entries recorded
     * @throws NotLoggedInException Thrown if user not logged in or session cookie has expired
     * @throws WrongQueryException  Thrown if the parameters in the query are incorrect
     */
    public MarketHistoryJson callAPI(int appId, String marketHashName) throws NotLoggedInException, WrongQueryException {
        HttpResponse<JsonNode> priceHistory = Unirest.get(baseURL)
                .queryString("appid", appId)
                .queryString("market_hash_name", marketHashName)
                .cookie(APIHandler.getSessionCookies())
                .asJson()
                .ifFailure(r -> System.out.println("Could not do pricehistory request: " + r.getStatusText()));

        MarketHistoryJson marketData = new MarketHistoryJson(priceHistory.getBody());

        if (marketData.isEmpty()) {
            throw new NotLoggedInException("pricehistory");
        }
        if (!marketData.isSuccess()) {
            throw new WrongQueryException("pricehistory",
                    priceHistory.getBody() == null ? "null" : priceHistory.getBody().toString(),
                    "appid: " + appId,
                    "market_hash_name: " + marketHashName);
        }

        return marketData;
    }
}
