package cz.butab.humanpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Class c = HIDKeyCodes.class;

        List<String> items = new ArrayList<>();
        for (Field f : c.getFields()) {
            if (f.getType() == int.class && (f.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) != 0) {
                items.add(f.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner s;
        s = findViewById(R.id.spinnerA);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonAKey));

        s = findViewById(R.id.spinnerB);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonBKey));

        s = findViewById(R.id.spinnerC);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonCKey));

        s = findViewById(R.id.spinnerD);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonDKey));

        View.OnClickListener l = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // save
                Spinner s = (Spinner) findViewById(R.id.spinnerA);
                KeyMapper.Tab6.ButtonAKey= getKeycode((String)s.getSelectedItem());
                s = (Spinner) findViewById(R.id.spinnerB);
                KeyMapper.Tab6.ButtonBKey= getKeycode((String)s.getSelectedItem());
                s = (Spinner) findViewById(R.id.spinnerC);
                KeyMapper.Tab6.ButtonCKey= getKeycode((String)s.getSelectedItem());
                s = (Spinner) findViewById(R.id.spinnerD);
                KeyMapper.Tab6.ButtonDKey = getKeycode((String)s.getSelectedItem());

                finish();
                Intent myIntent = new Intent(SettingsActivity.this,MainActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        };

        findViewById(R.id.btnSave).setOnClickListener(l);
    }

    private int getSelectedIndex(List<String> items, int keycode) {
        return items.indexOf(getNameOf(keycode));
    }

    private String getNameOf(int keycode) {
        Class c = HIDKeyCodes.class;
        for (Field f : c.getFields()) {
            try {
                if (f.getType() == int.class && (f.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) != 0 && (int)f.get(null) == keycode) {
                    return f.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private int getKeycode(String keycodeName){
        Class c = HIDKeyCodes.class;
        for (Field f : c.getFields()) {
            try {
                if (f.getType() == int.class && (f.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) != 0 && f.getName() == keycodeName) {
                    return (int)f.get(null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return HIDKeyCodes.Arrow_Up;
    }
}
