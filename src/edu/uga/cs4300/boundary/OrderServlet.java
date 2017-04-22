package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.logiclayer.OrderController;
import edu.uga.cs4300.objectlayer.Cart;
import edu.uga.cs4300.objectlayer.MenuCategory;
import edu.uga.cs4300.objectlayer.MenuItem;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/orders" })
public class OrderServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4730262853655677803L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	private OrderController orderController = OrderController.getInstance();

	public OrderServlet(){
		super();
	}
	public void init() {
		super.init();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = (String) request.getParameter("catagorylists");
		boolean isCatagoryList = query != null && query.equals("true") ? true : false;
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(isCatagoryList){
			List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
			boolean hasCategory = (catagories == null || catagories.isEmpty()) ? false : true;
			if(!hasCategory){
				root.put("message", "Coming soon. Site under construction. ");
			} else {
				root.put("hasCategory", hasCategory);
			}
			root.put("catagories", catagories);
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			if(cart == null){
				cart = new Cart();
				request.getSession().setAttribute("cart", cart);
			}
			root.put("cart", cart);
			renderTemplate(request, response, "catagories.ftl", root);
			return;
		}
		query = (String) request.getParameter("catagoryid");
		if(StringUtils.isNumeric(query)){
			int id = Integer.parseInt(query);
			List<MenuItem> items = orderController.getMenuItemsForCatagory(id);
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			if(cart == null){
				cart = new Cart();
				request.getSession().setAttribute("cart", cart);
			}
			root.put("menuitems", items);
			root.put("catagories", createMenuItemController.getAllCatagories());
			root.put("cart", cart);
			root.put("addmenuitemtocart", true);
			renderTemplate(request, response, "menuitems.ftl", root);
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
