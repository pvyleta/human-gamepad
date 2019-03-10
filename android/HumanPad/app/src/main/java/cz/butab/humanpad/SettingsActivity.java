package cz.butab.humanpad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        s = (Spinner) findViewById(R.id.spinnerA);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonAKey));

        s = (Spinner) findViewById(R.id.spinnerB);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonBKey));

        s = (Spinner) findViewById(R.id.spinnerC);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonCKey));

        s = (Spinner) findViewById(R.id.spinnerD);
        s.setAdapter(adapter);
        s.setSelection(getSelectedIndex(items, KeyMapper.Tab6.ButtonDKey));
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
}
