import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ciel on 2018/9/3
 */
public class Main {
    public static void main(String[] args) {
        String account = "cielni";     // ins 账号名
        String queryHash = "a5164aed103f24b03e7b7747a2d94e3c";
        String id;
        int first = 12;

        JSONArray mediaArray = new JSONArray();
        String jsonData = InstagramDataHelper.getFirstData(account);
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        id = jsonObject.getJSONObject("entry_data").getJSONArray("ProfilePage").getJSONObject(0).getJSONObject("graphql").getJSONObject("user").getString("id");
        JSONObject mediaData = jsonObject.getJSONObject("entry_data").getJSONArray("ProfilePage").getJSONObject(0).getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media");
        mediaArray.addAll(mediaData.getJSONArray("edges"));
        boolean hasNext = mediaData.getJSONObject("page_info").getBooleanValue("has_next_page");
        String endCursor = mediaData.getJSONObject("page_info").getString("end_cursor");

        while(hasNext) {
            try {
                jsonData = InstagramDataHelper.getPostData(queryHash, id, first, endCursor);
//                System.out.println(jsonData);
                mediaData = JSONObject.parseObject(jsonData).getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media");
                mediaArray.addAll(mediaData.getJSONArray("edges"));
                hasNext = mediaData.getJSONObject("page_info").getBooleanValue("has_next_page");
                endCursor = mediaData.getJSONObject("page_info").getString("end_cursor");
            } catch (Exception e) {
                System.out.println(e);
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e.printStackTrace();
                }
                continue;
            }
        }

        System.out.println(mediaArray.toJSONString());
    }
}
