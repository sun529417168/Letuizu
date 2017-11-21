package cn.com.library.refresh;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.kevin.wraprecyclerview.WrapAdapter;

import java.util.LinkedList;
import java.util.List;

import cn.com.library.R;
import cn.com.library.base.BaseRecyclerAdatper;
import cn.com.library.base.RecyclerHolder;
import cn.com.library.utils.ToastUtils;

import static android.view.View.*;

/**
 * 版权所有：XXX有限公司</br>
 * <p>
 * PullRefreshActivity
 *
 * @author zhou.wenkai ,Created on 2015-12-30 11:21:46
 * @author mender，Modified Date Modify Content:
 * @description Major Function：PullToRefresh框架之天猫刷新头部
 * <p>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 */
public class PullRefreshActivity extends AppCompatActivity {

    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    // 自定义可添加头部、尾部 RecyclerView数据适配器包装类
    private WrapAdapter<RecyclerViewAdapter> mWrapAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private LinkedList<Integer> mListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_main);

        mListItems = new LinkedList<>();
        mListItems.add(R.mipmap.tm_recycler0);
        mListItems.add(R.mipmap.tm_recycler2);
        mListItems.add(R.mipmap.tm_recycler4);

        initViews();
        initEvents();
    }

    private void initViews() {
        mPullToRefreshRecyclerView = (PullToRefreshRecyclerView) this.findViewById(R.id.main_act_recyclerview);
        mPullToRefreshRecyclerView.setHeaderLayout(new TmallHeaderLayout(this));
        mPullToRefreshRecyclerView.setFooterLayout(new TmallFooterLayout(this));

        initRecyclerView();
    }

    /**
     * 初始化 RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mPullToRefreshRecyclerView.setScrollingWhileRefreshingEnabled(false);


        mAdapter = new RecyclerViewAdapter(this, mListItems);
        mWrapAdapter = new WrapAdapter<>(mAdapter);
        mWrapAdapter.adjustSpanSize(mRecyclerView);
        mRecyclerView.setAdapter(mWrapAdapter);

        // 添加头部广告轮播，这里用一张图片模拟实现。广告轮播图请参考AutoHome(汽车之家)中的实现方式
        ImageView loopViewImage = new ImageView(this);
        ViewGroup.LayoutParams loopViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loopViewImage.setLayoutParams(loopViewParams);
        loopViewImage.setBackgroundResource(R.mipmap.tm_picture0);
        mWrapAdapter.addHeaderView(loopViewImage);

        // 添加头部功能选择，这里用一张图片模拟实现。
        ImageView functionImage = new ImageView(this);
        ViewGroup.LayoutParams functionParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        functionImage.setLayoutParams(functionParams);
        functionImage.setBackgroundResource(R.mipmap.tm_picture1);
        mWrapAdapter.addHeaderView(functionImage);
    }

    private void initEvents() {
        mPullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new GetDataTask().execute("下拉刷新");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new GetDataTask().execute("上拉加载");
            }
        });
    }

    private class GetDataTask extends AsyncTask<String, Void, Integer[]> {

        @Override
        protected Integer[] doInBackground(String... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }

            if (params[0].equals("下拉刷新")) {
                return new Integer[0];
            } else {
                Integer[] a1 = {R.mipmap.tm_recycler1, R.mipmap.tm_recycler3, R.mipmap.tm_recycler5};
                return a1;
            }
        }

        @Override
        protected void onPostExecute(Integer[] result) {
            for (Integer id : result) {
                mListItems.addLast(id);
            }
            mWrapAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullToRefreshRecyclerView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    class RecyclerViewAdapter extends BaseRecyclerAdatper {


        public RecyclerViewAdapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

        @Override
        public int getContentView(int viewType) {
            return R.layout.layout_recycler_item;
        }

        @Override
        public void onInitView(RecyclerHolder holder, final int position) {
            ImageView iv = holder.getView(R.id.recycler_item_iv);
            iv.setBackgroundResource(mListItems.get(position));
            holder.setOnClickListener(iv.getId(), new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("onclick", position + "");
                }
            });
        }
    }


}
