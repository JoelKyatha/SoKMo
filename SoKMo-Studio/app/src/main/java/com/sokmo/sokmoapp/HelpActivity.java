package com.sokmo.sokmoapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HelpActivity extends AppCompatActivity {

    private WebView nwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();
        nwebview=(WebView) findViewById(R.id.activity_main_webview);
        WebSettings websettings=nwebview.getSettings();
        websettings.setJavaScriptEnabled(true);
        nwebview.loadUrl("https://github.com/JoelKyatha/SoKMo");
        nwebview.setWebViewClient(new


                                          WebViewClient(){
                                              public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                                                  try {
                                                      webView.stopLoading();
                                                  } catch (Exception e) {
                                                  }

                                                  if (webView.canGoBack()) {
                                                      webView.goBack();
                                                  }

                                                  webView.loadUrl("about:blank");
                                                  AlertDialog alertDialog = new AlertDialog.Builder(HelpActivity.this).create();
                                                  alertDialog.setTitle("Error");
                                                  alertDialog.setMessage("Check your internet connection and try again.");
                                                  alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                                                      public void onClick(DialogInterface dialog, int which) {
                                                          finish();
                                                          startActivity(getIntent());
                                                      }
                                                  });

                                                  alertDialog.show();
                                                  super.onReceivedError(webView, errorCode, description, failingUrl);
                                              }
                                          });
    }


    @Override
    public void onBackPressed() {
        if(nwebview.canGoBack() )
        {
            nwebview.goBack();
        }
        else {
            super.onBackPressed();
        }
    }


}
