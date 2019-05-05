package com.example.admin.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public class CountyActivity extends AppCompatActivity {
    private TextView textView;
    //private Button button;
    private ListView listView;
    private List<String> data=new ArrayList<>();
    private String[] wids={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid",0);
        int cid = intent.getIntExtra("cid",0);
        Log.i("我们接收到了id",""+pid);
        Log.i("我们接收到了id",""+cid);
        this.textView=(TextView) findViewById(R.id.abc);
        //this.button = (Button)findViewById(R.id.button);
        this.listView = (ListView) findViewById(R.id.list_view2);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CountyActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.list_view2);
        listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击哪一个",""+position+":"+CountyActivity.this.wids[position]);
                Intent intent = new Intent(CountyActivity.this,WeatherActivity.class);
                intent.putExtra("wid",CountyActivity.this.wids[position]);
                startActivity(intent);
            }
        });


        String weatherUrl =  "http://guolin.tech/api/china/"+pid+"/"+cid;
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                String[] result=parseJSONObject(responseText);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
    private String[] parseJSONObject(String responseText)  {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(responseText);
            String[] result = new String[jsonArray.length()];
            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                this.data.add(jsonObject.getString("name"));
                this.wids[i] = jsonObject.getString("weather_id");
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
