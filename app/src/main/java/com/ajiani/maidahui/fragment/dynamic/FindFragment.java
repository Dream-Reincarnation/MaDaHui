package com.ajiani.maidahui.fragment.dynamic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ajiani.maidahui.R;
import com.ajiani.maidahui.Utils.JumpUtils;
import com.ajiani.maidahui.activity.Main3Activity;
import com.ajiani.maidahui.activity.dynamic.PersonActivity;
import com.ajiani.maidahui.adapter.dynamic.RecommedAdapter;
import com.ajiani.maidahui.base.BaseFragment;
import com.ajiani.maidahui.bean.dynamic.VideoListBean;
import com.ajiani.maidahui.bean.mine.VideoInfoBean;
import com.ajiani.maidahui.mInterface.dynamic.DynamicIn;
import com.ajiani.maidahui.presenters.dynamic.DynamicPresenter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class FindFragment extends BaseFragment<DynamicIn.videoListView, DynamicPresenter> implements DynamicIn.videoListView {
    @BindView(R.id.dyna_recomm_rel)
    RecyclerView dynaRecommRel;
    @BindView(R.id.recommed_smart)
    SmartRefreshLayout attentSmart;
    private RecommedAdapter recommedAdapter;
    private ArrayList<String> list;
    private int page=1;

    @Override
    public void onResume() {
        super.onResume();
        page=1;
    }

    @Override
    protected void initData() {
       attentSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
           @Override
           public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
               page++;
               HashMap<String, String> hashMap = new HashMap<>();
               hashMap.put("page",page+"");
               mPresenter.getListData(hashMap);
           }
       });
       attentSmart.setOnRefreshListener(new OnRefreshListener() {
           @Override
           public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               page=1;
               recommedAdapter.mList.clear();
               HashMap<String, String> hashMap = new HashMap<>();
               hashMap.put("page",page+"");
               mPresenter.getListData(hashMap);
           }
       });
    }

    @Override
    protected void initView() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page",page+"");
        mPresenter.getListData(hashMap);
        ArrayList<VideoInfoBean.DataBean> recommedBeans = new ArrayList<>();
        dynaRecommRel.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recommedAdapter = new RecommedAdapter(recommedBeans);
        dynaRecommRel.setAdapter(recommedAdapter);
        recommedAdapter.notifyDataSetChanged();
        recommedAdapter.setOnLike(new RecommedAdapter.onLikeListener() {
            @Override
            public void onLike(int posstion) {
                String video_type = recommedAdapter.mList.get(posstion).getVideo_type();
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < recommedAdapter.mList.size(); i++) {
                    strings.add(recommedAdapter.mList.get(i).getAid()+"");
                }
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("list", strings);

                bundle.putString("posstion", posstion+"");
                //点击播放  视频列表
                if(video_type.equals("video")){
                    JumpUtils.gotoActivity(getActivity(), Main3Activity.class,bundle);
                    EventBus.getDefault().postSticky(recommedAdapter.mList);
                }
            }
        });
        recommedAdapter.setOnHead(new RecommedAdapter.onHeadListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onHead(int posstion) {
                //跳转页面
                Bundle bundle = new Bundle();
                bundle.putString("id",recommedAdapter.mList.get(posstion).getUser_id()+"");
                JumpUtils.gotoActivity(getActivity(),PersonActivity.class,bundle);

            }
        });
    }

    @Override
    protected DynamicPresenter preparePresenter() {
        return new DynamicPresenter();
    }

    @Override
    protected int createLayout() {
        return R.layout.recommend;
    }

    @Override
    public void listSuccess(String success) {
        VideoListBean videoListBean = new Gson().fromJson(success, VideoListBean.class);
        List<VideoInfoBean.DataBean> data = videoListBean.getData();
        recommedAdapter.mList.addAll(data);
        recommedAdapter.notifyDataSetChanged();
        if(attentSmart!=null){
            attentSmart.finishRefresh();
            attentSmart.finishLoadMore();
        }

    }

    @Override
    public void followList(String success) {

    }

    @Override
    public void error(String error) {
        attentSmart.finishRefresh();
        attentSmart.finishLoadMore();
    }
}
