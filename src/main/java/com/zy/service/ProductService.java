package com.zy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zy.dao.ProductMapper;
import com.zy.domain.Category;
import com.zy.domain.Product;

@Service
public class ProductService {

    @Autowired
    private ProductMapper mapper;

    public Product queryProduct(String id) {
        Product product = mapper.queryProduct(id);

        String pid = product.getId();
        List<Category> categories = mapper.queryCategories(pid);
        product.setCategories(categories);
        return product;
    }

    public List<Product> queryProducts() {
        List<Product> products = mapper.queryProducts();

        for (Product product : products) {
            String pid = product.getId();
            List<Category> categories = mapper.queryCategories(pid);
            product.setCategories(categories);
        }
        return products;
    }

    public int deleteProduct(String id) {
        // 中间表 根据当前产品id删除分类
        mapper.deleteProductCategory(id);
        return mapper.deleteProduct(id);
    }

    public int insertProduct(Product product) {
        mapper.insertProduct(product);

        String[] cids = product.getCids().split(",");
        String pid = product.getId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("cids", cids);
        map.put("pid", pid);
        int result = mapper.insertProductCategory(map);
        return result;
    }

    public int updateProduct(Product product) {
        mapper.deleteProductCategory(product.getId());
        String[] cids = product.getCids().split(",");
        String pid = product.getId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("cids", cids);
        map.put("pid", pid);
        int result = mapper.insertProductCategory(map);
        return result;
    }

    public List<Category> queryAllCategories() {
        return mapper.queryAllCategories();
    }

    public List<Category> queryCategories(String pid) {
        return mapper.queryCategories(pid);
    }

}
