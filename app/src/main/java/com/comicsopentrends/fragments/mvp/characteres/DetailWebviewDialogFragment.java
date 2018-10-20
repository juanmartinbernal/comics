package com.comicsopentrends.fragments.mvp.characteres;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comicsopentrends.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
public class DetailWebviewDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.containerOptionsBackChat)
    LinearLayout containerOptionsBackChat;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.progressBarLoadUrl)
    ProgressBar progressBarLoadUrl;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.titleWebView)
    TextView mTitle;

    private String url;
    private String title;

    public DetailWebviewDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_webview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = this.getArguments();
        if (b != null) {
            url = b.getString("url");
            title = b.getString("title");
        }
        mTitle.setText(title);
        //Scroll bars should not be hidden
        webView.setScrollbarFadingEnabled(false);
        //Enable JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(view.getProgress() == 100) {
                    progressBarLoadUrl.setVisibility(View.GONE);
                }
            }
        });

        if (!TextUtils.isEmpty(url))
            webView.loadUrl("" + url);
        else
            webView.loadUrl("http://www.google.com");

        containerOptionsBackChat.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        webView.setOnKeyListener(keyListener);
    }

    View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_DarkActionBar);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.containerOptionsBackChat:
                dismiss();
                break;
            case R.id.imgBack:
                dismiss();
                break;
            default:
                break;
        }
    }
}