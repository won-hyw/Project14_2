package kr.hs.emirim.w2019.project14_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imgv;
    EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgv = findViewById(R.id.imgv);
        edit1 = findViewById(R.id.edit1);
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                edit1.setText("현재 배터리 충전량 : " + remain + "%\n");
                if(remain >= 90) {
                    imgv.setImageResource(R.drawable.battery100);
                }else if(remain >= 70) {
                    imgv.setImageResource(R.drawable.battery80);
                }else if(remain >= 40) {
                    imgv.setImageResource(R.drawable.battery40);
                }else if(remain >= 10) {
                    imgv.setImageResource(R.drawable.battery20);
                }else {
                    imgv.setImageResource(R.drawable.battery0);
                }

                int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                switch (plug) {
                    case 0 :
                        edit1.append("전원 연결 : 안됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_AC :
                        edit1.append("전원 연결 : 어댑터로 연결됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB :
                        edit1.append("전원 연결 : USB 연결됨");
                        break;
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(br, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }
}