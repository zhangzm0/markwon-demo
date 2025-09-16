package com.demo.markwon;

import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

public class EditorFragment extends Fragment {
    private static final String ARG_TEXT = "text";
    private EditText editor;

    public static EditorFragment newInstance() {
        return new EditorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle b) {
        View root = inflater.inflate(R.layout.fragment_editor, c, false);
        editor = root.findViewById(R.id.editor);
        if (b != null) editor.setText(b.getString(ARG_TEXT));
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_TEXT, editor.getText().toString());
    }

    /* 供外部取当前文本 */
    public String getMarkdown() {
        return editor == null ? "" : editor.getText().toString();
    }
}
