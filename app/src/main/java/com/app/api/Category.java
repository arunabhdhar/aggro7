package com.app.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.aggro.R;
import com.app.appInterface.OnSuccess;
import com.app.holder.GroupItem;
import com.app.modal.AppDetail;

import java.util.HashMap;

/**
 * Created by ericbasendra on 21/07/15.
 */
public class Category {

    public enum AggroCategory{
        APPS,BUSINESS,LIFESTYLE,NEWS_AND_MAGAZINES,
        EDUCATION,TRANSPORTATION,PRODUCTIVITY,
        GAMES,TRAVEL_AND_LOCAL,SPORTS,HEALTH_AND_FITNESS,
        MUSIC_AND_AUDIO,COMICS,COMMUNICATION,FINANCE,MEDIA_AND_VIDEO,
        MEDICAL,PERSONALIZATION,PHOTOGRAPHY,SHOPPING,SOCIAL,TOOLS,WEATHER,
        LIBRARIES_AND_DEMO,GAME_ARCADE,GAME_PUZZLE,GAME_CARD,GAME_CASUAL,GAME_RACING,GAME_SPORTS,
        GAME_ACTION,GAME_ADVENTURE,GAME_BOARD,GAME_CASINO,GAME_EDUCATIONAL,GAME_FAMILY,GAME_MUSIC,
        GAME_ROLE_PLAYING,GAME_SIMULATION,GAME_STRATEGY,GAME_TRIVIA,GAME_WORD,APP_WALLPAPER,APP_WIDGETS;

        public static AggroCategory aggroCategory (String myEnumString) {
            try {
                return valueOf(myEnumString);
            } catch (Exception ex) {
                // For error cases
                return APPS;
            }
        }
    }

    AggroCategory aggroCategory;
    private String country;
    private Context context;

    private RequestQueue mRequestQueue;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public Category(Context context){
        this.context = context;
        sp = context.getSharedPreferences(context.getResources().getString(R.string.aggro_prefs),Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public Category(AggroCategory aggroCategory){
        this.aggroCategory = aggroCategory;
        sp = context.getSharedPreferences(context.getResources().getString(R.string.aggro_prefs),Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public Category(Context context, String country, AggroCategory aggroCategory){
        this.context = context;
        this.country = country;
        this.aggroCategory = aggroCategory;
        sp = context.getSharedPreferences(context.getResources().getString(R.string.aggro_prefs),Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void getAppsByCategory(){
        switch (aggroCategory){
            case APPS:
             getTopSellingApps(context.getResources().getString(R.string.cat_all_apps));
                break;
            case BUSINESS:
                getTopSellingApps(context.getResources().getString(R.string.cat_bussiness));
                break;
            case LIFESTYLE:
                getTopSellingApps(context.getResources().getString(R.string.cat_lifestyle));
                break;
            case NEWS_AND_MAGAZINES:
                getTopSellingApps(context.getResources().getString(R.string.cat_news));
                break;
            case EDUCATION:
                getTopSellingApps(context.getResources().getString(R.string.cat_education));
                break;
            case TRANSPORTATION:
                getTopSellingApps(context.getResources().getString(R.string.cat_transporation));
                break;
            case PRODUCTIVITY:
                getTopSellingApps(context.getResources().getString(R.string.cat_productiity));
                break;
            case GAMES:
                getTopSellingApps(context.getResources().getString(R.string.cat_games));
                break;
            case TRAVEL_AND_LOCAL:
                getTopSellingApps(context.getResources().getString(R.string.cat_travel));
                break;
            case SPORTS:
                getTopSellingApps(context.getResources().getString(R.string.cat_sports));
                break;
            case HEALTH_AND_FITNESS:
                getTopSellingApps(context.getResources().getString(R.string.cat_health));
                break;
            case MUSIC_AND_AUDIO:
                getTopSellingApps(context.getResources().getString(R.string.cat_entertainment));
                break;

            default:
                System.out.println("Not comes under the predefined category");
                break;

        }
    }

    private void getTopSellingApps(String string) {
        loadApiGetMethod(string);
    }

    private void loadApiGetMethod(String category){
        int limit = 5;
        mRequestQueue = Volley.newRequestQueue(context);
        String url = "https://42matters.com/api/1/apps/top_google_charts.json?" + "list_name=topselling_free"+ "&cat_key=" + category + "&country=" + country + "&limit=" + limit + "&access_token=" + context.getResources().getString(R.string.aggro_access_token);

        GsonRequest<AppDetail> myReq = new GsonRequest<AppDetail>(
                Request.Method.GET,
                url,
                AppDetail.class,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        mRequestQueue.add(myReq);
    }

    private void loadApiPostMethod(){
        
    }

    private Response.Listener<AppDetail> createMyReqSuccessListener() {
        return new Response.Listener<AppDetail>() {
            @Override
            public void onResponse(AppDetail response) {
                try {
                    Log.e("APP NAME",response.getCategoryName());
                    GroupItem groupItem = new GroupItem();
                    groupItem.appLists = response.getAppList();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = VolleyErrorHelper.getMessage(error, context);
                Log.e("EROOR MESSG","" + error.getMessage().toString());
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

    public void setMyEnum(AggroCategory myEnum) {
        editor.putString("MyEnum", myEnum.toString());
        editor.commit();
    }

    public AggroCategory getMyEnum() {
        String myEnumString = sp.getString("MyEnum", AggroCategory.APPS.toString());
        return AggroCategory.aggroCategory(myEnumString);
    }
}
