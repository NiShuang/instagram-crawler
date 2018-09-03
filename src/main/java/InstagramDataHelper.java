import net.dongliu.requests.Requests;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ciel on 2018/8/23
 */
public class InstagramDataHelper {

    public static String getFirstData(String username) {
        String url = String.format("https://www.instagram.com/%s/", username);
        String resp = Requests.get(url).send().readToText();
        Pattern pattern = Pattern.compile("window._sharedData = (.*?);</script>");
        Matcher matcher =  pattern.matcher(resp);
        if(!matcher.find()) return null;
        String result = matcher.group();
        String jsonString = result.substring(result.indexOf('{'), result.lastIndexOf('}') + 1);
        return jsonString;
    }

    public static String getPostData(String queryHash, String id, int first, String after) {
        String url = formatRequestUrl(queryHash, id, first, after);
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("cache-control", "no-cache");
        headers.put("cookie", "mid=W3KH6wAEAAF5sYu9JNP_Xbj1Q3PQ; mcd=3; csrftoken=TAELP53Sc2zeYnAjsikeI5CaPUpxR7KP; shbid=12530; ds_user_id=3655666981; rur=FTW; csrftoken=TAELP53Sc2zeYnAjsikeI5CaPUpxR7KP; sessionid=IGSCa908e42b01991971fa49abae00824110ba71903589af5f0a3308510fb91b087b%3AAshAcZIuDHp3XA762zuMc55f0dYfIYjL%3A%7B%22_auth_user_id%22%3A3655666981%2C%22_auth_user_backend%22%3A%22accounts.backends.CaseInsensitiveModelBackend%22%2C%22_auth_user_hash%22%3A%22%22%2C%22_platform%22%3A4%2C%22_token_ver%22%3A2%2C%22_token%22%3A%223655666981%3AU8TQBejePEPPpgii11hZc0cU6S3nQLxW%3Afd233bdc5e0fe4e3267233ed38f2511b4494b8c3cfbbb0f1edf963bc0f049936%22%2C%22last_refreshed%22%3A1534989742.8133029938%7D; urlgen=\"{\\\"103.206.189.72\\\": 136993}:1fsgaV:DMcJHyN6tTfjiBtC1nW7Y46M0iQ\"");
        headers.put("pragma", "no-cache");
        headers.put("upgrade-insecure-requests", "1");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        String resp = Requests.get(url).headers(headers).send().readToText();
        return resp;
    }

    public static String formatRequestUrl(String queryHash, String id, int first, String after) {
        StringBuilder url = new StringBuilder();
        url.append("https://www.instagram.com/graphql/query/?query_hash=").append(queryHash);
        url.append("&variables=%7B%22id%22%3A%22").append(id);
        url.append("%22%2C%22first%22%3A").append(first);
        url.append("%2C%22after%22%3A%22").append(after).append("%22%7D");
        return url.toString();
    }
}
