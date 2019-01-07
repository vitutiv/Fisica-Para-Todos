package ifba.fisicaparatodos;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.*;
import android.widget.FrameLayout;
import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;
import ifba.fisicaparatodos.Sketches.Motion.*;
import ifba.fisicaparatodos.Sketches.Energy.*;
import ifba.fisicaparatodos.Sketches.Water.*;
import ifba.fisicaparatodos.Sketches.Universe.*;
public class SketchActivity extends CommonMethods{
    private PApplet sketch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        frame.setFitsSystemWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        String value = getIntent().getStringExtra("URL");
        if (value.contains("universe")){
            setTitle(getResources().getString(R.string.universe));
            setColor(R.color.universeColorPrimary, R.color.universeColorPrimaryDark);
            sketch = new SketchSolarSystem();
        }else if (value.contains("water")) {
            setTitle(getResources().getString(R.string.water));
            setColor(R.color.waterColorPrimary, R.color.waterColorPrimaryDark);
            sketch = new SketchBottle();
        }else if (value.contains("air")){
            setTitle(getResources().getString(R.string.air));
            setColor(R.color.airColorPrimary, R.color.airColorPrimaryDark);
        }else if (value.contains("energy")) {
            setTitle(getResources().getString(R.string.energy));
            setColor(R.color.energyColorPrimary, R.color.energyColorPrimaryDark);
            sketch = new SketchEnergy();
        }else if (value.contains("motion")) {
            setTitle(getResources().getString(R.string.motion));
            setColor(R.color.motionColorPrimary, R.color.motionColorPrimaryDark);
            sketch = new SketchCar();
        }else{
            showToast(getResources().getString(R.string.message_unsupported));
        }
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onNewIntent(@NonNull Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_help:
                boolean failed = false;
                Intent i = new Intent(this, HelpActivity.class);
                if (sketch instanceof SketchSolarSystem){
                    SketchSolarSystem sk = (SketchSolarSystem)sketch;
                    String planetName = sk.getPlanetName();
                    if (planetName != null){
                        planetName = planetName.toLowerCase();
                    }
                    if ("mercúrio".equals(planetName)) {
                        planetName = "mercury";
                    } else if ("vênus".equals(planetName)) {
                        planetName = "venus";
                    } else if ("terra".equals(planetName)) {
                        planetName = "earth";
                    } else if ("marte".equals(planetName)) {
                        planetName = "mars";
                    } else if ("júpiter".equals(planetName)) {
                        planetName = "jupiter";
                    } else if ("saturno".equals(planetName)) {
                        planetName = "saturn";
                    } else if ("urano".equals(planetName)) {
                        planetName = "uranus";
                    } else if ("netuno".equals(planetName)) {
                        planetName = "neptune";
                    } else {
                        failed = true;
                        i.putExtra("url", "file:///android_asset/universe/universe.html");
                    }
                    i.putExtra("category", "universe");
                    if (!failed) {
                        i.putExtra("url", "file:///android_asset/universe/solarSystemPlanets/" + planetName + ".html");
                    }
                    failed  = false;
                }else if (sketch instanceof SketchCar){
                    i.putExtra("category", "motion");
                }else if (sketch instanceof SketchEnergy) {
                    i.putExtra("category", "energy");
                }else if (sketch instanceof SketchBottle){
                    i.putExtra("category", "water");
                }else{
                    failed = true;
                }
                if (!failed) {
                    startActivity(i);
                    break;
                }
            default:
                showToast(getResources().getString(R.string.message_unsupported));
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }
}