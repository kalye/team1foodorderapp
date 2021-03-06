package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.logiclayer.OrderController;
import edu.uga.cs4300.objectlayer.Address;
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
	@SuppressWarnings("unused")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = (String) request.getParameter("catagorylists");
		String addmore = (String) request.getParameter("addmore");
		boolean isCatagoryList = query != null && query.equals("true") ? true : false;
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(isCatagoryList || StringUtils.isNotBlank(addmore)){
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
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		query = (String) request.getParameter("remove");
		if(StringUtils.isNumeric(query)){
			cart.removeItem(Integer.parseInt(query));
			request.getSession().setAttribute("cart", cart);
			root.put("cart", cart);
			renderTemplate(request, response, "shoppingcart.ftl", root);
			return;
		}
		query = (String) request.getParameter("edit");
		if(StringUtils.isNumeric(query)){
			int itemNumber = Integer.parseInt(query);
			OrderItem orderItem = cart.getOrderItem(itemNumber);
			if(orderItem != null){
				root.put("itemNumber", itemNumber);
				if(orderItem.getMenuItem().isHasSide()){
					List<Side> sides = createMenuItemController.getAllSides();
					updateSelectedSides(sides, orderItem.getSelectedSides());
					root.put("sides", sides);
				}
				if(orderItem.getMenuItem().isHasToppings()){
					List<Topping> toppings = createMenuItemController.getAllToppings();
					updateSelectedToppings(toppings, orderItem.getSelectedToppings());
					root.put("toppings", toppings);
				}
				if(orderItem.getMenuItem().isCustomizable()){
					List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
					updateSelectedCustomizableItems(customizableItems, orderItem.getSelectedCustomizableItems());
					root.put("customizableItems", customizableItems);
					root.put("extracustomizableitems", getSelectedExtraCustomizableItemsId(orderItem.getSelectedExtraCustomizableItems()));
				}
				root.put("cart", cart);
				renderTemplate(request, response, "cartedit.ftl", root);
				return;
			} else {
				root.put("error", true);
				root.put("message", "No item found in the cart with item number " + query + " .");
				root.put("cart", cart);
				request.getSession().setAttribute("cart", cart);
				renderTemplate(request, response, "menuitems.ftl", root);
				return;
			}
			
		}
		query = (String) request.getParameter("cartitems");
		if(StringUtils.isNotBlank(query)){
			request.getSession().setAttribute("cart", cart);
			root.put("cart", cart);
			renderTemplate(request, response, "shoppingcart.ftl", root);
			return;
		}
		query = (String) request.getParameter("checkout");
		if(StringUtils.isNotBlank(query)){
			request.getSession().setAttribute("cart", cart);
			root.put("cart", cart);
			renderTemplate(request, response, "shipping.ftl", root);
			return;
		}
		query = (String) request.getParameter("billing");
		if(StringUtils.isNotBlank(query)){
			request.getSession().setAttribute("cart", cart);
			root.put("cart", cart);
			renderTemplate(request, response, "shipping.ftl", root);
			return;
		}
		query = (String) request.getParameter("confirm");
		if(StringUtils.isNotBlank(query)){
			if(cart != null){
				sendEmailWithNewPassword(cart);
				request.getSession().setAttribute("cart", null);
				renderTemplate(request, response, "thankyou.ftl", root);
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("index.html");
				rd.forward(request, response);
				return;
			}
			
		}
	}
	private List<Integer> getSelectedExtraCustomizableItemsId(List<CustomizableItem> selectedExtraCustomizableItems) {
		List<Integer> selectedExtras = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(selectedExtraCustomizableItems)){
			selectedExtraCustomizableItems.stream().forEach(extraCustomizable ->{
				selectedExtras.add(extraCustomizable.getId());
			});
		}
		return selectedExtras;
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
		query = (String) request.getParameter("edit");
		if(StringUtils.isNumeric(query)){
			OrderItem orderItem = cart.getOrderItem(Integer.parseInt(query));
			if(orderItem != null){
				updateOrderItem(orderItem, request);
			} else {
				root.put("error", true);
				root.put("message", "No item found in the cart with item number " + query + " .");
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
		query = (String) request.getParameter("billing");
		if(StringUtils.isNotBlank(query)){
			request.getSession().setAttribute("cart", cart);
			boolean isValid = updateBillingShipping(cart, request, false, root);
			if(!isValid){
				root.put("cart", cart);
				renderTemplate(request, response, "billing.ftl", root);
				return;
			}
			root.put("cart", cart);
			renderTemplate(request, response, "confirmation.ftl", root);
			return;
		}
		query = (String) request.getParameter("shipping");
		if(StringUtils.isNotBlank(query)){
			request.getSession().setAttribute("cart", cart);
			boolean isValid = updateBillingShipping(cart, request, true, root);
			if(!isValid){
				root.put("cart", cart);
				renderTemplate(request, response, "shipping.ftl", root);
				return;
			}
			root.put("cart", cart);
			renderTemplate(request, response, "billing.ftl", root);
			return;
		}
		
	}
	private boolean updateBillingShipping(Cart cart, HttpServletRequest request, boolean shipping, SimpleHash root) {
		Address address = new Address();
		if(shipping){
			populateAddress(address, request);
			cart.getOrder().setShippingAddress(address);
			return validateAddress(address, root);
		} else {
			String billingAddressSameAsShipping = request.getParameter("populateinfo");
			if("true".equals(billingAddressSameAsShipping)){
				cart.getPaymentInfo().setBillingAddress(cart.getOrder().getShippingAddress());
				return true;
			} else {
				populateAddress(address, request);
				cart.getOrder().setShippingAddress(address);
				return validateAddress(address, root);
			}
		}
	}
	private boolean validateAddress(Address address, SimpleHash root) {
		if (StringUtils.isBlank(address.getFirstName())) {
			root.put("error", "First Name is Required.");
			return false;
		}
		if (StringUtils.isBlank(address.getLastName())) {
			root.put("error", "Last name is required.");
			return false;
		}
		if (StringUtils.isBlank(address.getAddress())) {
			root.put("error", "Street address is required.");
			return false;
		}
		if (StringUtils.isBlank(address.getCity())) {
			root.put("error", "City is required.");
			return false;
		}
		if (StringUtils.isBlank(address.getState())) {
			root.put("error", "State is required.");
			return false;
		}
		if (StringUtils.isBlank(address.getZipcode())) {
			root.put("error", "");
			return false;
		}
		if (StringUtils.isBlank(address.getCountry())) {
			root.put("error", "Country is required.");
			return false;
		}
		if (StringUtils.isBlank(address.getPhone())) {
			root.put("error", "Phone number is required.");
			return false;
		}
		if (StringUtils.isBlank(address.getEmail())) {
			root.put("error", "Email is Required.");
			return false;
		}
		
		if(!address.getEmail().equals(address.getConfirmemail())){
			root.put("error", "Emails provided don't match");
			return false;
		}
		return true;
	}
	private void populateAddress(Address address, HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address1 = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String confirmemail = request.getParameter("confirmemail");
		address.setFirstName(firstName);
		address.setLastName(lastName);
		address.setAddress(address1);
		address.setCity(city);
		address.setState(state);
		address.setZipcode(zipcode);
		address.setCountry(country);
		address.setPhone(phone);
		address.setEmail(email);
		address.setConfirmemail(confirmemail);
	}
	private void updateOrderItem(OrderItem orderItem, HttpServletRequest request) {
		String size = (String) request.getParameter("size");
		if(StringUtils.isBlank(size) || !StringUtils.isNumeric(size)){
			size = "1";
		}
		orderItem.setSize(Integer.parseInt(size));
		//reset existing selected item
		orderItem.setSelectedCustomizableItem(new ArrayList<>());
		orderItem.setSelectedExtraCustomizableItem(new ArrayList<>());
		orderItem.setSelectedSides(new ArrayList<>());
		orderItem.setSelectedToppings(new ArrayList<>());
		if(orderItem.getMenuItem().isCustomizable()){
			String[] customizableItemsForMenus = request.getParameterValues("customizableitemsformenu");
			String[] customizableItemsForMenuExtra = request.getParameterValues("customizableitemsformenuextra");
			List<String> customizableItemsForMenuExtraArray = new ArrayList<>();
			if(customizableItemsForMenuExtra != null){
				customizableItemsForMenuExtraArray.addAll(Arrays.asList(customizableItemsForMenuExtra));
			}
			if(customizableItemsForMenus != null){
				for(String custId: customizableItemsForMenus){
					CustomizableItem custItem = createMenuItemController.getCustomizableItemById(Integer.parseInt(custId));
					if(custItem != null){
						orderItem.setItemCustomized(true);
						orderItem.getSelectedCustomizableItems().add(custItem);
					}
					if(customizableItemsForMenuExtraArray.contains(custId) && custItem != null){
						orderItem.setHasExtraCustomizedItem(true);
						orderItem.getSelectedExtraCustomizableItems().add(custItem);
					}
				}
			}
		
		}
		
		if(orderItem.getMenuItem().isHasToppings()){
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
		if(orderItem.getMenuItem().isHasSide()){
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
				if(orderItem.getSelectedCustomizableItems() == null){
					orderItem.setSelectedCustomizableItem(new ArrayList<>());
				}
				if(orderItem.getSelectedExtraCustomizableItems() == null){
					orderItem.setSelectedExtraCustomizableItem(new ArrayList<>());
				}
				for(String custId: customizableItemsForMenus){
					CustomizableItem custItem = createMenuItemController.getCustomizableItemById(Integer.parseInt(custId));
					if(custItem != null){
						orderItem.setItemCustomized(true);
						orderItem.getSelectedCustomizableItems().add(custItem);
					}
					if(customizableItemsForMenuExtraArray.contains(custId) && custItem != null){
						orderItem.setHasExtraCustomizedItem(true);
						orderItem.getSelectedExtraCustomizableItems().add(custItem);
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
