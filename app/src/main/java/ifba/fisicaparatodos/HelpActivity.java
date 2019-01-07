package ifba.fisicaparatodos;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HelpActivity extends CommonMethods {
    private String category;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            setToolBarSize(toolbar);
        }
        WebView helpWebView = findViewById(R.id.mainWebView);
        WebSettings webSettings = helpWebView.getSettings();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        Intent i = this.getIntent();
        assert category != null;
        category  = i.getStringExtra("category").toLowerCase();
        String link = i.getStringExtra("url");
        if (category == null && link == null){
            showToast(getResources().getString(R.string.message_unsupported));
            finish();
        }
        setColorAndTitle(category);
        if (link == null) {
            helpWebView.loadUrl("file:///android_asset/" + category + "/" + category + ".html");
        }else{
            helpWebView.loadUrl(link);
        }
        helpWebView.setWebViewClient(new WebViewClient() {
            @Override
            @SuppressWarnings("deprecation")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                onBrowserLoading(view, url);
                return true; // Handle By application itself
            }
        });
    }
    //Enquanto o navegador estiver carregando
    private void onBrowserLoading(WebView view, String url){
        if (url.contains("@androidActivity:") || !url.contains(category)) {
           showToast(getResources().getString(R.string.message_duringQuiz));
        } else {
            view.loadUrl(url);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                showToast(getResources().getString(R.string.message_unsupported));
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}