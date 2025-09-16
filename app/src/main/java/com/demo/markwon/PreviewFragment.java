package com.demo.markwon;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.noties.markwon.Markwon;
import java.util.Random;

public class PreviewFragment extends Fragment {

    private TextView textPreview;
    private Button btnRefresh;
    private Button btnAI;
    private Markwon markwon;
    private String currentText = "";
    private Handler handler;
    private Random random;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        textPreview = view.findViewById(R.id.textPreview);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        btnAI = view.findViewById(R.id.btnAI);

        // 初始化Markwon
        markwon = Markwon.create(requireContext());
        handler = new Handler(Looper.getMainLooper());
        random = new Random();

        btnRefresh.setOnClickListener(v -> refreshPreview());
        btnAI.setOnClickListener(v -> simulateAIResponse());

        return view;
    }

    public void setMarkdownText(String text) {
        this.currentText = text;
        refreshPreview();
    }

    private void refreshPreview() {
        if (markwon != null && currentText != null) {
            markwon.setMarkdown(textPreview, currentText);
        }
    }

    private void simulateAIResponse() {
        if (currentText == null || currentText.isEmpty()) {
            markwon.setMarkdown(textPreview, "# 请输入内容\n\n请在编辑页面输入一些Markdown内容，然后点击AI回答按钮");
            return;
        }

        // 清空当前预览
        textPreview.setText("AI正在思考...");

        // 模拟AI处理延迟
        handler.postDelayed(() -> {
            startStreamingResponse(currentText);
        }, 1000);
    }

    private void startStreamingResponse(String text) {
        // 将文本分割成字符数组，用于模拟逐字输出
        final char[] characters = text.toCharArray();
        final StringBuilder streamingText = new StringBuilder();

        // 先显示一些AI提示
        streamingText.append("# AI回答\n\n");
        markwon.setMarkdown(textPreview, streamingText.toString());

        // 模拟流式输出
        for (int i = 0; i < characters.length; i++) {
            final int index = i;
            // 随机延迟，模拟真实的流式响应
            int delay = 20 + random.nextInt(50); // 20-70ms的随机延迟

            handler.postDelayed(() -> {
                streamingText.append(characters[index]);

                // 每输出一定数量的字符就更新一次显示，避免过于频繁的刷新
                if (index % 10 == 0 || index == characters.length - 1) {
                    markwon.setMarkdown(textPreview, streamingText.toString());
                }

                // 如果是最后一个字符，确保最终更新
                if (index == characters.length - 1) {
                    handler.post(() -> {
                        markwon.setMarkdown(textPreview, streamingText.toString());
                    });
                }
            }, i * delay);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 移除所有回调，防止内存泄漏
        handler.removeCallbacksAndMessages(null);
    }
}
