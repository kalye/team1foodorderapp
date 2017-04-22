package edu.uga.cs4300.boundary;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.cs4300.objectlayer.Cart;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/index.html" })
public class CustomerHomePage extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5779155854818013670L;
	
	public CustomerHomePage(){
		super();
	}
	public void init() {
		super.init();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		request.getSession().setAttribute("cart", cart);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		root.put("cart", cart);
		renderTemplate(request, response, "index.ftl", root);
		return;
	}

}
