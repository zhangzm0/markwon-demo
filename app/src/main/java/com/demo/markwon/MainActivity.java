package com.demo.markwon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 vp = findViewById(R.id.viewpager);
        vp.setAdapter(new FragmentStateAdapter(this) {
				@NonNull
				@Override
				public Fragment createFragment(int position) {
					return position == 0
                        ? EditorFragment.newInstance()
                        : PreviewFragment.newInstance();
				}
				@Override
				public int getItemCount() {
					return 2;
				}
			});

        TabLayout tab = findViewById(R.id.tab);
        new TabLayoutMediator(tab, vp, (t, pos) ->
		t.setText(pos == 0 ? "输入" : "预览")
        ).attach();
    }
}

