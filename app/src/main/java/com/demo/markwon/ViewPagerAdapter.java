package com.demo.markwon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final EditFragment editFragment;
    private final PreviewFragment previewFragment;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        editFragment = new EditFragment();
        previewFragment = new PreviewFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return editFragment;
        } else {
            return previewFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public EditFragment getEditFragment() {
        return editFragment;
    }

    public PreviewFragment getPreviewFragment() {
        return previewFragment;
    }
}