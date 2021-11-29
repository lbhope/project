package com.zero.materialdesign.viewpager2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zero.materialdesign.R;
import com.zero.materialdesign.coordinator.adapter.AuthorRecyclerAdapter2;
import com.zero.materialdesign.coordinator.bean.AuthorInfo;
import com.zero.materialdesign.databinding.Fragment1Binding;
import com.zero.materialdesign.utils.DimenUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OneFragment extends Fragment {


    private Fragment1Binding binding;

    public static Fragment newIntance() {
        OneFragment fragment = new OneFragment();
        return fragment;
    }

    private AuthorRecyclerAdapter2 adapter;
    private LinearLayoutManager layoutManager;
    private View headerBlock;
    private View tv_header_bar;
    private int headerHeight;
    private int sumDy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Fragment1Binding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final int barHeight = DimenUtils.getStatusBarHeight(getContext());
        layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new AuthorRecyclerAdapter2();
        adapter.setNewData(AuthorInfo.createTestData());
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
//                if (firstItemPosition >= 1) {
//                    binding.tvListview.setVisibility(View.VISIBLE);
//                } else {
//                    binding.tvListview.setVisibility(View.GONE);
//                }

                int[] rect = new int[2];
                tv_header_bar.getLocationInWindow(rect);
                if (rect[1] - DimenUtils.dp2px(100) - barHeight <=0) {
                    Log.e("tagurl","rect[1]: "+rect[1]);
                    binding.tvHeaderLayer.setVisibility(View.VISIBLE);
                } else {
                    binding.tvHeaderLayer.setVisibility(View.GONE);
                }

                int[] rect2 = new int[2];
                headerBlock.getLocationInWindow(rect2);
                Log.e("tagurl","rect[2]: "+rect2[1] + " rect[1]: "+rect[1]);
                if (rect2[1]<=DimenUtils.dp2px(40)){
                    binding.tvListview.setVisibility(View.VISIBLE);
                }else {
                    binding.tvListview.setVisibility(View.GONE);
                }
            }
        });

        adapter.bindToRecyclerView(binding.recyclerView);
        addHeadView();

    }

    private void addHeadView() {
        headerBlock = LayoutInflater.from(getContext()).inflate(R.layout.layout_header, null);
        tv_header_bar = headerBlock.findViewById(R.id.tv_header_bar);
        adapter.setHeaderView(headerBlock);
        headerBlock.post(new Runnable() {
            @Override
            public void run() {
                headerHeight = headerBlock.getMeasuredHeight();
            }
        });
    }


}
