package com.zy.c;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zy.domain.Category;
import com.zy.domain.Product;
import com.zy.service.ProductService;

@Controller
public class ProductC {

	@Autowired
	private ProductService service;
	
	@RequestMapping("/queryProducts.do")
	public ModelAndView queryProducts() {
		List<Product> products = service.queryProducts();
		System.out.println(products);
		ModelAndView mv = new ModelAndView();
		mv.addObject("products",products);
		mv.setViewName("/product-list");
		return mv;
	}
	
	@RequestMapping("/getInsertForm.do")
	public ModelAndView getInsertForm() {
		List<Category> categories = service.queryAllCategories();
		System.out.println(categories);
		ModelAndView mv = new ModelAndView();
		mv.addObject("categories",categories);
		mv.setViewName("/insert-form");
		return mv;
	}
	
	@RequestMapping("/insertProduct.do")
	public ModelAndView insertProduct(Product product) {
		System.out.println(product);
		int result = service.insertProduct(product);
		ModelAndView mv = new ModelAndView();
		if(result>0) {
			mv.setViewName("redirect:queryProducts.do");
		}else {
			mv.setViewName("/fail");
		}
		
		return mv;
	}
	
	@RequestMapping("/deleteProduct.do")
	public ModelAndView deleteProduct(String id) {
		System.out.println(id);
		
		int result = service.deleteProduct(id);
		ModelAndView mv = new ModelAndView();
		if(result>0) {
			mv.setViewName("redirect:queryProducts.do");
		}else {
			mv.setViewName("/fail");
		}
		return mv;
	}
	
	@RequestMapping("/getUpdateForm.do")
	public ModelAndView getUpdateForm(String id) {
		System.out.println(id);
		Product product = service.queryProduct(id);
		List<Category> allCategories = service.queryAllCategories();
		ModelAndView mv = new ModelAndView();
		mv.addObject("product",product);
		mv.addObject("allCategories",allCategories);
		mv.setViewName("/update-form");
		return mv;
	}
	
	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct(Product product) {
		System.out.println(product);
		int result = service.updateProduct(product);
		ModelAndView mv = new ModelAndView();
		if(result>0) {
			mv.setViewName("redirect:queryProducts.do");
		}else {
			mv.setViewName("/fail");
		}
		return mv;
	}
	
}
