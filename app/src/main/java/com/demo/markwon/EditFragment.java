package com.demo.markwon;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditFragment extends Fragment {

    private EditText editText;
    private OnTextChangeListener listener;

    public interface OnTextChangeListener {
        void onTextChanged(String text);
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        editText = view.findViewById(R.id.editText);
        
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (listener != null) {
                    listener.onTextChanged(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    public String getText() {
        return editText != null ? editText.getText().toString() : "";
    }
}