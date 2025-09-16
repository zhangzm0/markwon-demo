package com.demo.markwon;

import android.os.*;
import android.text.Spanned;
import android.view.*;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import io.noties.markwon.Markwon;
import io.noties.markwon.ext.latex.JLatexMathPlugin;
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin;
import io.noties.markwon.ext.tables.TablePlugin;
import io.noties.markwon.ext.tasklist.TaskListPlugin;
import io.noties.markwon.html.HtmlPlugin;
import io.noties.markwon.image.coil.CoilImagesPlugin;
import io.noties.markwon.linkify.LinkifyPlugin;
import io.noties.markwon.syntax.Prism4jThemeDarkula;
import io.noties.markwon.syntax.SyntaxHighlightPlugin;

public class PreviewFragment extends Fragment {

    private TextView preview;
    private Markwon markwon;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public static PreviewFragment newInstance() {
        return new PreviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle b) {
        View root = inflater.inflate(R.layout.fragment_preview, c, false);
        preview = root.findViewById(R.id.preview);

        // 初始化 Markwon 全家桶
        markwon = Markwon.builder(requireContext())
                .usePlugin(CoilImagesPlugin.create(requireContext()))
                .usePlugin(JLatexMathPlugin.create(preview.getTextSize()))
                .usePlugin(StrikethroughPlugin.create())
                .usePlugin(TablePlugin.create(requireContext()))
                .usePlugin(TaskListPlugin.create(requireContext()))
                .usePlugin(HtmlPlugin.create())
                .usePlugin(LinkifyPlugin.create())
                .usePlugin(SyntaxHighlightPlugin.create(Prism4jThemeDarkula.create(), true))
                .build();

        root.findViewById(R.id.btn_refresh).setOnClickListener(v -> renderNow());
        root.findViewById(R.id.btn_stream).setOnClickListener(v -> simulateStream());

        return root;
    }

    /* 立即渲染当前编辑器内容 */
    private void renderNow() {
        EditorFragment ed = (EditorFragment) getParentFragmentManager()
                .findFragmentByTag("f0"); // ViewPager2 默认 tag
        if (ed == null) return;
        String md = ed.getMarkdown();
        markwon.setMarkdown(preview, md);
    }

    /* 模拟 AI 流式回答：逐字追加 */
    /* 模拟 AI 流式回答：逐字追加【用户输入】 */
	private void simulateStream() {
		// 1. 取到用户输入
		EditorFragment ed = (EditorFragment) getParentFragmentManager()
            .findFragmentByTag("f0");
		if (ed == null) return;
		final String src = ed.getMarkdown();
		if (src.trim().isEmpty()) {          // 空输入友好提示
			markwon.setMarkdown(preview, "*（请先输入一些 Markdown）*");
			return;
		}

		// 2. 清空预览，开始“打字机”
		preview.setText("");
		new Thread(() -> {
			for (int i = 0; i <= src.length(); i++) {
				final String part = src.substring(0, i);
				handler.post(() -> markwon.setMarkdown(preview, part));
				try { Thread.sleep(40); } catch (InterruptedException ignore) {}
			}
		}).start();
	}
}
