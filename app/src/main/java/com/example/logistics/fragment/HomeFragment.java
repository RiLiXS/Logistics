package com.example.logistics.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logistics.R;
import com.example.logistics.base.BaseFragment;
import com.example.logistics.bean.ControlListBean;
import com.example.logistics.bean.HomeFunctionBean;
import com.example.logistics.bean.LoginBean;
import com.example.logistics.bean.QualityControlListBean;
import com.example.logistics.bean.QualityTypeListBean;
import com.example.logistics.bean.RequestQualityControlListBean;
import com.example.logistics.bean.RequestQualityTypeListBean;
import com.example.logistics.bean.RequestTotalCountBean;
import com.example.logistics.bean.TotalCountBean;
import com.example.logistics.eventbus.EventBusAction;
import com.example.logistics.eventbus.MessageEvent;
import com.example.logistics.home.HomeFunctionAdapter;
import com.example.logistics.home.adapter.HomeTestTaskAdapter;
import com.example.logistics.machining.activity.FeedingAddActivity;
import com.example.logistics.ok.OkHttpUtils;
import com.example.logistics.smartrefreshlayout.SwipeRefreshLayout;
import com.example.logistics.utils.C;
import com.example.logistics.utils.UserLocalData;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.app.Activity.RESULT_OK;


/**
 * ??????
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_pending, tv_completed;
    private EditText et_search;
    private RecyclerView rcy_function, rcy_task;
    private ArrayList<HomeFunctionBean> functionList = new ArrayList<>();
    private Spinner spinner_type;
    private ArrayList<String> typeList = new ArrayList<>();
    private int typePosition = 0;
    private List<QualityTypeListBean> qualityTypeList = null;
    private HomeTestTaskAdapter homeDeviceAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int REQUEST_CODE_SCAN = 100;
    private int page = 1;
    private boolean isSpinnerFirst = true;
    private boolean isFirst = true;
    private ImageView img_search;
    private boolean isFirstLoading = true;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {


        rcy_task = view.findViewById(R.id.rcy_task);//??????????????????

        rcy_function = view.findViewById(R.id.rcy_function);//????????????
        spinner_type = view.findViewById(R.id.spinner_type);//????????????
        et_search = view.findViewById(R.id.et_search);//??????
        refreshLayout = view.findViewById(R.id.refreshLayout);//??????
        tv_completed = view.findViewById(R.id.tv_completed);//?????????
        tv_pending = view.findViewById(R.id.tv_pending);//?????????
        img_search = view.findViewById(R.id.img_search);//??????
        tv_pending.setOnClickListener(this);
        tv_completed.setOnClickListener(this);
        img_search.setOnClickListener(this);
        tv_completed.setText("?????????\n" + "(0)");
        tv_pending.setText("?????????\n" + "(0)");
        initFunction();
        initTask();
        initType();//????????????
        getQualityTestList("", 1, "");
        initTotal();//??????????????????

    }

    private void initTotal() {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/qualitycontrol/qualitycontroltask/getCount/byCheckoutState")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestTotalCountBean requestTotalCountBean = new Gson().fromJson(data, RequestTotalCountBean.class);
                            if (requestTotalCountBean.getCode() == 0) {
                                List<TotalCountBean> totalCountBeanData = requestTotalCountBean.getData();
                                if (totalCountBeanData == null) return;

                                getCountList(totalCountBeanData);
                            } else {
                                Toast.makeText(getActivity(), "" + requestTotalCountBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(getActivity(), "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }

    private void initTotal2() {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/qualitycontrol/qualitycontroltask/getCount/byCheckoutState")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .addParam("barcode",TextUtils.isEmpty(et_search.getText().toString())?"":et_search.getText().toString()+"")
                    .addParam("checkoutType",TextUtils.isEmpty(qualityTypeList.get(typePosition).getValue())?"":qualityTypeList.get(typePosition).getValue()+"")
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestTotalCountBean requestTotalCountBean = new Gson().fromJson(data, RequestTotalCountBean.class);
                            if (requestTotalCountBean.getCode() == 0) {
                                List<TotalCountBean> totalCountBeanData = requestTotalCountBean.getData();
                                if (totalCountBeanData == null) return;

                                getCountList(totalCountBeanData);
                            } else {
                                Toast.makeText(getActivity(), "" + requestTotalCountBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(getActivity(), "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }

    /**
     * ??????????????????
     * @param totalCountBeanData
     */
    private void getCountList(List<TotalCountBean> totalCountBeanData) {
        if (totalCountBeanData.size()==0){
            tv_completed.setText("?????????\n" + "(0)");
            tv_pending.setText("?????????\n" + "(0)");
            return;
        }
        for (int i=0;i<totalCountBeanData.size();i++){
            if(Integer.valueOf(totalCountBeanData.get(i).getCheckoutState())==1){//?????????
                tv_completed.setText("?????????\n" + "(" +totalCountBeanData.get(i).getCount() + ")");
            }else  if(Integer.valueOf(totalCountBeanData.get(i).getCheckoutState())==0){//?????????
                tv_pending.setText("?????????\n" + "(" +totalCountBeanData.get(i).getCount() + ")");
            }
        }
    }

    @Override
    protected void initData(Context mContext) {


        /**
         * ??????
         */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                //????????????
                getQualityTestList("", 1, "");


            }
        });
        /**
         * ????????????
         */
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                page++;
                getQualityTestList("", page, "");
                //????????????
//                if (isSearch) {
//                    initProcessCard(page);
//                } else {
//                    initSearchProcessCard(page, et_search.getText().toString());
//                }


            }
        });

        /***
         * ????????????????????????????????????
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//???????????????

                    getQualityTestList("", 1, "");
                    initTotal2();
                    return true;
                }
                return false;
            }
        });


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFirst == false) return;
                if (!TextUtils.isEmpty(s)) {
                    getQualityTestList("", 1, "");//?????????????????????
                    isFirst = false;
                }
            }
        });


    }

    private void initType() {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/admin/dict/type/checkout_type")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestQualityTypeListBean requestQualityTypeListBean = new Gson().fromJson(data, RequestQualityTypeListBean.class);
                            if (requestQualityTypeListBean.getCode() == 0) {
                                List<QualityTypeListBean> qualityTypeListBeanData = requestQualityTypeListBean.getData();
                                if (qualityTypeListBeanData == null) return;
                                qualityTypeList = qualityTypeListBeanData;
                                getQualityTypeList(qualityTypeListBeanData);
                            } else {
                                Toast.makeText(getActivity(), "" + requestQualityTypeListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(getActivity(), "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }

    /**
     * ????????????
     *
     * @param qualityTypeListBeanData
     */
    private void getQualityTypeList(List<QualityTypeListBean> qualityTypeListBeanData) {
        typeList.clear();
        for (int i = 0; i < qualityTypeListBeanData.size(); i++) {
            typeList.add(qualityTypeListBeanData.get(i).getLabel());
        }

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, typeList);
        spinner_type.setAdapter(typeAdapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerFirst) {
                    //??????????????????spinner????????????????????????????????????????????????
                    //  view.setVisibility(View.INVISIBLE);
                    isSpinnerFirst = false;
                    return;
                }

                typePosition = position;

                getQualityTestList(qualityTypeListBeanData.get(typePosition).getValue(), 1, "");
                initTotal2();
                //????????????
                //   getQualityTestList(qualityTypeListBeanData.get(typePosition).getValue(), 1, "");
                //  Toast.makeText(FeedingAddActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        homeDeviceAdapter.getList(qualityTypeListBeanData);

    }

    /**
     * ??????????????????
     *
     * @param type
     */
    private void getQualityTestList(String type, int page, String state) {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/qualitycontrol/qualitycontroltask/page")
                    .addParam("current", page + "")
                    .addParam("barcode", et_search.getText().toString() + "")
                    .addParam("checkoutState", state + "")
                    .addParam("checkoutType", type + "")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestQualityControlListBean requestQualityControlListBean = new Gson().fromJson(data, RequestQualityControlListBean.class);
                            if (requestQualityControlListBean.getCode() == 0) {
                                List<ControlListBean> records = requestQualityControlListBean.getData().getRecords();
                                if (page == 1) {
                                    homeDeviceAdapter.initList(records);
                                    refreshLayout.finishRefresh();
                                } else {
                                    homeDeviceAdapter.addList(records);
                                    refreshLayout.finishLoadMore();
                                }
                                homeDeviceAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "" + requestQualityControlListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(getActivity(), "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }


                    });
        }
    }


    /**
     * ?????????????????????
     */
    private void initFunction() {
        HomeFunctionAdapter homeFunctionAdapter = new HomeFunctionAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        rcy_function.setLayoutManager(gridLayoutManager);
        rcy_function.setAdapter(homeFunctionAdapter);


        functionList.add(new HomeFunctionBean(R.drawable.first_inspection_icon, "????????????", 5));
        functionList.add(new HomeFunctionBean(R.drawable.onsite_inspection_icon, "????????????", 6));
//        functionList.add(new HomeFunctionBean(R.drawable.special_inspection_icon, "??????", 7));
//        functionList.add(new HomeFunctionBean(R.drawable.final_inspection_icon, "??????", 8));

        homeFunctionAdapter.initList(functionList);

    }

    /**
     * ???????????????????????????
     */
    private void initTask() {
        homeDeviceAdapter = new HomeTestTaskAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcy_task.setLayoutManager(linearLayoutManager);
        rcy_task.setAdapter(homeDeviceAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_completed://?????????
                getQualityTestList(qualityTypeList.get(typePosition).getValue(), 1, "1");
                break;
            case R.id.tv_pending://?????????
                getQualityTestList(qualityTypeList.get(typePosition).getValue(), 1, "0");
                break;
            case R.id.img_search://??????
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ???????????????/????????????
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                // ViewShowUtils.showShortToast(this, "" + content);
                //result.setText("??????????????????" + content);
                getQualityTestList2(content + "", 1);
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param
     */
    private void getQualityTestList2(String str, int page) {
        LoginBean login = UserLocalData.getLogin();//??????????????????
        String access_token = login.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            OkHttpUtils.builder().url(C.BASE_HOST + "/qualitycontrol/qualitycontroltask/page")
                    .addParam("barcode", str + "")
                    .addParam("current", page + "")
                    .addHeader("Authorization", C.Setting.author_id + access_token)
                    .get()
                    .async(new OkHttpUtils.ICallBack() {
                        @Override
                        public void onSuccessful(Call call, String data) {
                            RequestQualityControlListBean requestQualityControlListBean = new Gson().fromJson(data, RequestQualityControlListBean.class);
                            if (requestQualityControlListBean.getCode() == 0) {
                                List<ControlListBean> records = requestQualityControlListBean.getData().getRecords();
                                if (page == 1) {
                                    homeDeviceAdapter.initList(records);
                                    refreshLayout.finishRefresh();
                                } else {
                                    homeDeviceAdapter.addList(records);
                                    refreshLayout.finishLoadMore();
                                }
                                homeDeviceAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getActivity(), "" + requestQualityControlListBean.getMsg(), Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call call, String errorMsg) {
                            Toast.makeText(getActivity(), "" + errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoading) {
            //??????????????????????????????????????????
            getQualityTestList("", 1, "");
            initTotal();//??????????????????
        }

        isFirstLoading = false;
    }
}