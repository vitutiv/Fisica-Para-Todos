package ifba.fisicaparatodos;
import android.app.ActivityManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.widget.Toast;
import java.util.Objects;
public abstract class CommonMethods extends AppCompatActivity{
    void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
    //Verifica se o dispositivo possui a tecla de hardware
    boolean hasHardwareBackKey(){
        return KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
    }
    // Um método que retorna a altura da status bar
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    void setToolBarSize(Toolbar toolbar){
        if (toolbar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }

    void setColorAndTitle(String u){
        String url = u.toLowerCase();
        if (url.contains("universe")) {
            setColor(R.color.universeColorPrimary, R.color.universeColorPrimaryDark);
            setTitle(R.string.universe);
        } else if (url.contains("water")) {
            setColor(R.color.waterColorPrimary, R.color.waterColorPrimaryDark);
            setTitle(R.string.water);
        } else if (url.contains("air")) {
            setColor(R.color.airColorPrimary, R.color.airColorPrimaryDark);
            setTitle(R.string.air);
        }else if (url.contains("colors")) {
            setColor(R.color.colorsColorPrimary, R.color.colorsColorPrimaryDark);
            setTitle(R.string.colors);
        } else if (url.contains("energy")) {
            setColor(R.color.energyColorPrimary, R.color.energyColorPrimaryDark);
            setTitle(R.string.energy);
        } else if (url.contains("motion")) {
            setColor(R.color.motionColorPrimary, R.color.motionColorPrimaryDark);
            setTitle(R.string.motion);
        } else {
            setColor(R.color.colorPrimary, R.color.colorPrimaryDark);
            if (url.contains("about")) {
                setTitle(R.string.about);
            }else{
                setTitle(R.string.app_name);
            }
        }
    }
    void setColor (int colorPrimary, int colorPrimaryDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, colorPrimaryDark)));
            ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null,null , ContextCompat.getColor(this, colorPrimaryDark));
            this.setTaskDescription(taskDescription);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, colorPrimary)));
        }
    }
}