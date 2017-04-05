package com.chhd.cniaoshops.biz;

import android.text.TextUtils;
import android.util.SparseArray;

import com.chhd.cniaoshops.bean.ShoppingCart;
import com.chhd.cniaoshops.util.LoggerUtils;
import com.chhd.per_library.util.JsonUtils;
import com.chhd.per_library.util.SpUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CWQ on 2017/4/3.
 */

public class CartBiz {

    private final String KEY_SHOPPING_CARTS = "shopping_carts";

    private SparseArray<ShoppingCart> array = new SparseArray<>();

    public CartBiz() {
        listToArray();
    }

    private void listToArray() {
        List<ShoppingCart> items = getDataFromLocal();
        for (ShoppingCart cart : items) {
            array.put(cart.getId().intValue(), cart);
        }
    }

    public void put(ShoppingCart cart) {
        ShoppingCart item = array.get(cart.getId().intValue());
        if (item != null) {
            item.setCount(item.getCount() + 1);
        } else {
            item = cart;
            item.setCount(1);
        }
        array.put(item.getId().intValue(), item);
        commit();
    }

    /**
     * listToJson
     */
    private void commit() {
        List<ShoppingCart> items = sparseToList();
        SpUtils.putString(KEY_SHOPPING_CARTS, JsonUtils.toJSON(items));
    }

    /**
     * SparseToList
     *
     * @return
     */
    private List<ShoppingCart> sparseToList() {
        List<ShoppingCart> items = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            items.add(array.valueAt(i));
        }
        return items;
    }

    public void update(ShoppingCart cart) {
        array.put(cart.getId().intValue(), cart);
        commit();
    }

    public void delete(ShoppingCart cart) {
        array.delete(cart.getId().intValue());
        commit();
    }

    public List<ShoppingCart> getAll() {
        return getDataFromLocal();
    }

    public List<ShoppingCart> getDataFromLocal() {
        String json = SpUtils.getString(KEY_SHOPPING_CARTS, "");
        List<ShoppingCart> items = new ArrayList<>();
        if (!TextUtils.isEmpty(json)) {
            items = JsonUtils.fromJson(json, new TypeToken<List<ShoppingCart>>() {
            }.getType());
        }
        return items;
    }
}
