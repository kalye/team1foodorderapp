package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.logiclayer.OrderController;
import edu.uga.cs4300.objectlayer.Cart;
import edu.uga.cs4300.objectlayer.CustomizableItem;
import edu.uga.cs4300.objectlayer.MenuCategory;
import edu.uga.cs4300.objectlayer.MenuItem;
import edu.uga.cs4300.objectlayer.OrderItem;
import edu.uga.cs4300.objectlayer.Side;
import edu.uga.cs4300.objectlayer.Topping;
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
		String query = (String) request.getParameter("addtocart");
		boolean isAddToCart = "true".equals(query);
		String menuid = request.getParameter("menuid");
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		if(isAddToCart && StringUtils.isNumeric(menuid)){
			MenuItem menuItem = createMenuItemController.getMenuItemById(Integer.parseInt(menuid));
			if(menuItem != null){
				OrderItem orderItem = getOrderItem(menuItem, request);
				if(cart.getOrder().getOrderNumber() == 0){
					cart.getOrder().setOrderNumber(Integer.parseInt(RandomStringUtils.randomNumeric(5)));
				}
				cart.addOrderItem(orderItem);
				request.getSession().setAttribute("cart", cart);
				root.put("cart", cart);
				renderTemplate(request, response, "shoppingcart.ftl", root);
				return;
			} else {
				root.put("error", true);
				root.put("message", "Error while adding item to cart. Please try again");
				root.put("menuitems", createMenuItemController.getAllMenuItems());
				root.put("catagories", createMenuItemController.getAllCatagories());
				root.put("cart", cart);
				root.put("addmenuitemtocart", true);
				request.getSession().setAttribute("cart", cart);
				renderTemplate(request, response, "menuitems.ftl", root);
				return;
			}
		}
		String changesizeforitem = request.getParameter("changesizeforitem");
		if(StringUtils.isNumeric(changesizeforitem)){
			int itemNumber = Integer.parseInt(changesizeforitem);
			String size = request.getParameter("size");
			if(StringUtils.isNumeric(size)){
				cart.changeSize(itemNumber, Integer.parseInt(size));
			} else {
				root.put("error", true);
				root.put("message", "Size is not valid number.");
				root.put("cart", cart);
				request.getSession().setAttribute("cart", cart);
				renderTemplate(request, response, "menuitems.ftl", root);
				return;
			}
			request.getSession().setAttribute("cart", cart);
			root.put("cart", cart);
			renderTemplate(request, response, "shoppingcart.ftl", root);
			return;
		}
		
	}
	private OrderItem getOrderItem(MenuItem menuItem, HttpServletRequest request) {
		OrderItem orderItem = new OrderItem();
		String size = (String) request.getParameter("size");
		if(StringUtils.isBlank(size) || !StringUtils.isNumeric(size)){
			size = "1";
		}
		orderItem.setSize(Integer.parseInt(size));
		orderItem.setMenuItem(menuItem);
		//generate random 6 digit number. For real database get next sequence
		orderItem.setItemNumber(Integer.parseInt(RandomStringUtils.randomNumeric(6)));
		if(menuItem.isCustomizable()){
			String[] customizableItemsForMenus = request.getParameterValues("customizableitemsformenu");
			String[] customizableItemsForMenuExtra = request.getParameterValues("customizableitemsformenuextra");
			List<String> customizableItemsForMenuExtraArray = new ArrayList<>();
			if(customizableItemsForMenuExtra != null){
				customizableItemsForMenuExtraArray.addAll(Arrays.asList(customizableItemsForMenuExtra));
			}
			if(customizableItemsForMenus != null){
				if(orderItem.getSelectedCustomizableItem() == null){
					orderItem.setSelectedCustomizableItem(new ArrayList<>());
				}
				if(orderItem.getSelectedExtraCustomizableItem() == null){
					orderItem.setSelectedExtraCustomizableItem(new ArrayList<>());
				}
				for(String custId: customizableItemsForMenus){
					CustomizableItem custItem = createMenuItemController.getCustomizableItemById(Integer.parseInt(custId));
					if(custItem != null){
						orderItem.setItemCustomized(true);
						orderItem.getSelectedCustomizableItem().add(custItem);
					}
					if(customizableItemsForMenuExtraArray.contains(custId) && custItem != null){
						orderItem.setHasExtraCustomizedItem(true);
						orderItem.getSelectedExtraCustomizableItem().add(custItem);
					}
				}
			}
		}
		if(menuItem.isHasToppings()){
			String[] toppingsformenu = request.getParameterValues("toppingsformenu");
			if(toppingsformenu != null){
				for(String topId: toppingsformenu){
					Topping topping = createMenuItemController.getToppingById(Integer.parseInt(topId));
					if(topping != null){
						orderItem.getSelectedToppings().add(topping);
					}
				}
			}
		}
		if(menuItem.isHasSide()){
			String[] sidesformenu = request.getParameterValues("sidesformenu");
			if(sidesformenu != null){
				for(String sideId: sidesformenu){
					Side side = createMenuItemController.getSideById(Integer.parseInt(sideId));
					if(side != null){
						orderItem.getSelectedSides().add(side);
					}
				}
			}
		}
		return orderItem;
	}
}
