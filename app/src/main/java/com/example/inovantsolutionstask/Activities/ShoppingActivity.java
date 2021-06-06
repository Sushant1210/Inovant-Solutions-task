package com.example.inovantsolutionstask.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inovantsolutionstask.Adapters.BannerViewPagerAdapter;
import com.example.inovantsolutionstask.Adapters.MoreItemsAdapter;
import com.example.inovantsolutionstask.Modal.MoreItemsModal;
import com.example.inovantsolutionstask.R;
import com.example.inovantsolutionstask.Utils.AutoCompleteDropDown;
import com.example.inovantsolutionstask.Utils.AutoScrollViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;

public class ShoppingActivity extends AppCompatActivity implements MoreItemsAdapter.ListItemClickListener {

    private String url = "http://mobicraftsv2.com/bloc49/api/v3/product-details?product_id=1812&lang=en&store=KW";
    ProgressDialog dialog;
    private String mainImageUrl, specification;
    private ArrayList<String> imageUrlList, sizeArrayList, colorArrayList;
    private ArrayList<MoreItemsModal> associateProductList;
    private AutoScrollViewPager bannerViewPager;
    private BannerViewPagerAdapter bannerViewPagerAdapter;
    private Button btnWishList, btnShare, btnAddToBag;
    CircleIndicator indicator;
    private ArrayList<String> itemListTemp;
    private ArrayList<ArrayList> itemList;
    private TextView tvBrandName, tvDressName, tvCurrencyCode, tvFinalAmt, tvRegularAmt, tvDiscription, tvComposition, tvShopNO, tvShopId, tvBadge;
    private AutoCompleteDropDown spnColor, spnSize;
    private ImageView imgDesc, imgBack, imgShoppingBag, ivComposition;
    private MoreItemsAdapter moreItemsAdapter;
    private RecyclerView moreItemsRecycler;
    private int badgeCounter = 0;
    private int bagCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        itemList = new ArrayList<>();
        initViews();
    }

    @Override
    protected void onResume() {
        makeServiceCall();
        super.onResume();
    }

    // Function to init the views and initialize values.
    private void initViews() {
        bannerViewPager = findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        tvBrandName = findViewById(R.id.tvBrandName);
        tvDressName = findViewById(R.id.tvDressName);
        tvCurrencyCode = findViewById(R.id.tvCurrencyCode);
        tvFinalAmt = findViewById(R.id.tvFinalAmt);
        tvRegularAmt = findViewById(R.id.tvRegularAmt);
        tvRegularAmt.setPaintFlags(tvRegularAmt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        spnColor = findViewById(R.id.spnColor);
        spnSize = findViewById(R.id.spnSize);
        tvDiscription = findViewById(R.id.tvdescription);
        tvComposition = findViewById(R.id.tvComposition);
        tvShopId = findViewById(R.id.tvShopId);
        tvShopNO = findViewById(R.id.tvShopNo);
        btnWishList = findViewById(R.id.btnWish);
        btnShare = findViewById(R.id.btnShare);
        btnAddToBag = findViewById(R.id.btnAddToBag);
        moreItemsRecycler = findViewById(R.id.my_recycler);
        imgBack = findViewById(R.id.backBtn);
        tvBadge = findViewById(R.id.tvBadge);
        if (badgeCounter == 0)
            tvBadge.setVisibility(View.GONE);
        ivComposition = findViewById(R.id.ivComp0sition);
        imgShoppingBag = findViewById(R.id.shoppingBagBtn);
        imgDesc = findViewById(R.id.ivDescription);
        imgDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgDesc.getDrawable().getConstantState() == getDrawable(R.drawable.ic_add).getConstantState()) {
                    tvDiscription.setVisibility(View.VISIBLE);
                    imgDesc.setImageResource(R.drawable.ic_minus);
                } else {
                    tvDiscription.setVisibility(View.GONE);
                    imgDesc.setImageResource(R.drawable.ic_add);
                }
            }
        });

        ivComposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivComposition.getDrawable().getConstantState() == getDrawable(R.drawable.ic_add).getConstantState()) {
                    tvComposition.setVisibility(View.VISIBLE);
                    ivComposition.setImageResource(R.drawable.ic_minus);
                } else {
                    tvComposition.setVisibility(View.GONE);
                    ivComposition.setImageResource(R.drawable.ic_add);
                }
            }
        });

        btnWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWishList.getText().equals("WISHLIST")) {
                    btnWishList.setText(R.string.str_wishlisted);
                    btnWishList.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_heartred), null, null, null);
                } else {
                    btnWishList.setText(R.string.str_wishlist);
                    btnWishList.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_heart), null, null, null);
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = tvDiscription.getText().toString() + " Price is " + tvCurrencyCode.getText().toString() + " " + tvFinalAmt.getText();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()) {
                    itemListTemp = new ArrayList<>();
                    itemListTemp.add(spnColor.getText() + "," + spnSize.getText());
                    if (!itemList.contains(itemListTemp)) {
                        itemList.add(itemListTemp);
                    }else {
                        Toast.makeText(getApplicationContext(), R.string.str_already_added, Toast.LENGTH_SHORT).show();
                    }
                    if (bagCounter > 0) {
                        for (int i = 0; i < itemList.size(); i++) {
                            if (!itemList.get(i).equals(itemListTemp)) {
                                tvBadge.setText(String.valueOf(itemList.size()));
                                tvBadge.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        bagCounter = bagCounter + 1;
                        badgeCounter = badgeCounter + 1;
                        tvBadge.setText(String.valueOf(badgeCounter));
                        tvBadge.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // Function to check validations.
    private boolean isValidForm() {
        if (spnColor.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.str_select_color, Toast.LENGTH_SHORT).show();
            return false;
        } else if (spnSize.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.str_select_size, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Function to make service call and get response
    private void makeServiceCall() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), R.string.str_error, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(ShoppingActivity.this);
        rQueue.add(request);
    }

    // Function to process the data received from response.
    private void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            Log.e("jsonString is ", "" + jsonString);
            String status = object.optString("status");
            boolean success = object.optBoolean("success");
            JSONObject dataObject = object.getJSONObject("data");
            JSONArray configurable_option = dataObject.getJSONArray("configurable_option");
            JSONObject sizeObject = (JSONObject) configurable_option.get(0);
            JSONArray sizeAttributeArray = sizeObject.getJSONArray("attributes");
            JSONArray associatedProductsArray = dataObject.getJSONArray("associated_products");

            JSONObject colorObject = (JSONObject) configurable_option.get(1);
            JSONArray colorAttributeArray = colorObject.getJSONArray("attributes");

            imageUrlList = new ArrayList<>();
            sizeArrayList = new ArrayList<>();
            colorArrayList = new ArrayList<>();
            associateProductList = new ArrayList<>();

            if (status.equals("200") && success == true) {
                mainImageUrl = dataObject.optString("image");
                for (int i = 0; i < dataObject.getJSONArray("images").length(); i++) {
                    imageUrlList.add(dataObject.getJSONArray("images").getString(i));
                }
                bannerViewPagerAdapter = new BannerViewPagerAdapter(getApplicationContext(), imageUrlList);
                bannerViewPager.setAdapter(bannerViewPagerAdapter);
                bannerViewPager.startAutoScroll();
                bannerViewPager.setInterval(3000);
                bannerViewPager.setCycle(true);
                bannerViewPager.setCurrentItem(0);
                indicator.setViewPager(bannerViewPager);

                for (int i = 0; i < sizeAttributeArray.length(); i++) {
                    JSONObject sizeAttributeObject = sizeAttributeArray.getJSONObject(i);
                    sizeArrayList.add(sizeAttributeObject.getString("value"));
                }
                setSpinnerWithList(spnSize, sizeArrayList);
                for (int i = 0; i < colorAttributeArray.length(); i++) {
                    JSONObject colorAttributeObject = colorAttributeArray.getJSONObject(i);
                    colorArrayList.add(colorAttributeObject.getString("value"));
                }
                setSpinnerWithList(spnColor, colorArrayList);

                for (int i = 0; i < associatedProductsArray.length(); i++) {
                    JSONObject associatedProductsObject = associatedProductsArray.getJSONObject(i);
                    associateProductList.add(new MoreItemsModal(associatedProductsObject.getString("brand_name"),
                            associatedProductsObject.getString("name"), associatedProductsObject.getString("currency_code"),
                            associatedProductsObject.getString("final_price"), associatedProductsObject.getString("regular_price"),
                            associatedProductsObject.getString("image")));
                }
                moreItemsRecycler.setHasFixedSize(true);
                moreItemsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                moreItemsAdapter = new MoreItemsAdapter(getApplicationContext(), associateProductList);
                moreItemsRecycler.setAdapter(moreItemsAdapter);

                tvDressName.setText(dataObject.optString("name"));
                tvCurrencyCode.setText(dataObject.optString("currency_code"));
                tvFinalAmt.setText(String.valueOf((Double.parseDouble(dataObject.optString("final_price")))));
                tvRegularAmt.setText(String.valueOf((Double.parseDouble(dataObject.optString("regular_price")))));
                if (dataObject.optString("description") != "")
                    tvDiscription.setText(dataObject.optString("description"));
                else
                    tvDiscription.setText(R.string.str_no_data);
                tvBrandName.setText(dataObject.optString("brand_name"));
                tvShopNO.setText("ID: " + dataObject.optString("shop_id"));
                tvShopId.setText(dataObject.optString("shop"));
                if (!dataObject.optString("specification").equals(""))
                    tvComposition.setText(dataObject.optString("specification"));
                else
                    tvComposition.setText(R.string.str_no_data);
                dialog.dismiss();
            }
            dialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Function to set spinner data
    private void setSpinnerWithList(AutoCompleteDropDown spinner, final ArrayList<String> list) {
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list);
        ad.setDropDownViewResource(R.layout.spinner_item_inside);
        spinner.setAdapter(ad);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.str_close)
                .setMessage(R.string.str_close_confirmatoion)
                .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.str_no, null)
                .show();
    }

    @Override
    public void onMoreItemsListClick(int clickedItemIndex) {

    }
}