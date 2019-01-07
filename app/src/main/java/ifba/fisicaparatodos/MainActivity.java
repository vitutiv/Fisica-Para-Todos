package ifba.fisicaparatodos;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.webkit.*;
public class MainActivity extends CommonMethods implements NavigationView.OnNavigationItemSelectedListener {
    private WebView mainWebView;
    private byte x = 0;
    private boolean isBackBtnPressed;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Menu menu;
    private boolean isNightModeEnabled = false;
    private SharedPreferences appSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appSettings = getSharedPreferences("UserInfo", 0);
        isNightModeEnabled = appSettings.getBoolean("NightModeEnabled", false);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        setToolBarSize(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        menu = toolbar.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
        mainWebView = drawer.findViewById(R.id.mainWebView);
        setupWebView();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (isNightModeEnabled){
            navigationView.getMenu().findItem(R.id.nav_nightMode).setIcon(R.drawable.ic_checkbox_enabled);
        }else{
            navigationView.getMenu().findItem(R.id.nav_nightMode).setIcon(R.drawable.ic_checkbox_disabled);
        }
        return true;
    }
    //Configura o webview
    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView(){
        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setAppCacheEnabled(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        loadURL("file:///android_asset/index.html");
        mainWebView.setWebViewClient(new WebViewClient() {
            //Método comum
            boolean shouldOverrideUrlLoading(String url){
                Intent i;
                if (url.contains("@androidActivity:")) {
                    if (url.contains("animation")) {
                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                            showToast(getResources().getString(R.string.message_androidVersionBelowLollipop));
                            return true;
                        }
                        i = new Intent(getApplicationContext(), SketchActivity.class);
                    } else if (url.contains("quiz")){
                        i = new Intent(getApplicationContext(), QuizActivity.class);
                    }else{
                        showToast(getResources().getString(R.string.message_unsupported));
                        return true;
                    }
                    i.putExtra("URL", url);
                    startActivity(i);
                }
                return (url.contains("@androidActivity:"));

            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            //Método atual
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(request.getUrl().toString());
            }
            @SuppressWarnings("deprecation")
            @Override
            //Método descontinuado
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return shouldOverrideUrlLoading(url);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                //Se o modo noturno estiver ativado, mudar a folha de estilos
                if (isNightModeEnabled) {
                    enableNightMode();
                }
                //Caso a url contenha algum desses, alterar a cor e o titulo
                menu.findItem((R.id.action_goForward)).setVisible(view.canGoForward());
                if (hasHardwareBackKey()) {
                    menu.findItem((R.id.action_goBack)).setVisible(view.canGoBack());
                }
                setColorAndTitle(url);
                if (url.contains("universe")) {
                    navigationView.getMenu().getItem(1).setChecked(true);
                } else if (url.contains("water")) {
                    navigationView.getMenu().getItem(2).setChecked(true);
                } else if (url.contains("air")) {
                    navigationView.getMenu().getItem(3).setChecked(true);
                }else if (url.contains("colors")) {
                    navigationView.getMenu().getItem(4).setChecked(true);
                } else if (url.contains("energy")) {
                    navigationView.getMenu().getItem(5).setChecked(true);
                } else if (url.contains("motion")) {
                    navigationView.getMenu().getItem(6).setChecked(true);
                } else {
                    if (url.contains("about")) {
                        navigationView.getMenu().getItem(7).setChecked(true);
                    }else{
                        navigationView.getMenu().getItem(0).setChecked(true);
                    }
                }
            }
        });
    }
    //Ao pressionar o botao voltar do dispositivo
    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        isBackBtnPressed = true;
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else if (x == 1){
            super.onBackPressed();
            return;
        }else{
            goBack();
            x = 1;
        }
        short time;
        if (mainWebView.getUrl().contains("index") && !mainWebView.canGoBack()){
            time = 2000;
        }else{
            time = 250;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                x = 0;
            }
        }, time);
        isBackBtnPressed = false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_goForward:
                goForward();
                break;
            case R.id.action_goBack:
                goBack();
                break;
            default:
                showToast(getResources().getString(R.string.message_unsupported));
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //Ao selecionar um item do menu de navegacao
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Mostra a pagina inicial do tema selecionado no menu
        switch (item.getItemId()) {
            case R.id.nav_water:
                loadURL("file:///android_asset/water/water.html");
                break;
            case R.id.nav_air:
                loadURL("file:///android_asset/air/air.html");
                break;
            case R.id.nav_colors:
                loadURL("file:///android_asset/colors/colors.html");
                break;
            case R.id.nav_energy:
                loadURL("file:///android_asset/energy/energy.html");
                break;
            case R.id.nav_motion:
                loadURL("file:///android_asset/motion/motion.html");
                break;
            case R.id.nav_universe:
                loadURL("file:///android_asset/universe/universe.html");
                break;
            case R.id.nav_about:
                loadURL("file:///android_asset/about.html");
                break;
            //Ativa ou desativa o modo noturno
            case R.id.nav_nightMode:
                isNightModeEnabled = !isNightModeEnabled;
                if (isNightModeEnabled){
                    enableNightMode();
                    item.setIcon(R.drawable.ic_checkbox_enabled);
                }else {
                    mainWebView.reload();
                    item.setIcon(R.drawable.ic_checkbox_disabled);
                }
                SharedPreferences.Editor editor = appSettings.edit();
                editor.putBoolean("NightModeEnabled",isNightModeEnabled);
                editor.apply();
                break;
            default:
                loadURL("file:///android_asset/index.html");
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loadURL(String filepath) {
        mainWebView.loadUrl(filepath);
    }
    @Override
    //Muda a cor da barra de navegaçao, notificaçao e barra lateral
    protected void setColor(int colorPrimary, int colorPrimaryDark) {
        super.setColor(colorPrimary, colorPrimaryDark);
        findViewById(R.id.navDrawerHeader).setBackground(new ColorDrawable(ContextCompat.getColor(this, colorPrimary)));
    }
    //Avancar]
    private void goForward() {
        if (mainWebView.canGoForward()) {
            mainWebView.goForward();
        } else {
            showToast(getResources().getString(R.string.message_lastPage));
        }
    }
    //Voltar
    private void goBack() {
        if (mainWebView.canGoBack()) {
            mainWebView.goBack();
        } else if (!drawer.isDrawerOpen(GravityCompat.START)) {
            if (isBackBtnPressed) {
                showToast(getResources().getString(R.string.message_tapToClose));
            } else {
                showToast(getResources().getString(R.string.message_firstPage));
            }
        }
    }
    private void enableNightMode(){
        loadURL("javascript:nightMode()");
    }
}