package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.objectlayer.Topping;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/toppings" })
@MultipartConfig
public class ToppingServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718277299388664083L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public ToppingServlet(){
		super();
	}
	
	public void init() {
		super.init();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String create = (String) request.getParameter("create");
		boolean isCreate = "true".equals(create);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(isCreate){
			root.put("createOrUpdate", true);
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("toppings", toppings);
			root.put("hasToppings", CollectionUtils.isNotEmpty(toppings));
			root.put("createsubmenu", true);
			return;
		}
		String updateId = (String) request.getParameter("updateId");
		if(StringUtils.isNumeric(updateId)){
			int id = Integer.parseInt(updateId);
			Topping topping = createMenuItemController.getToppingById(id);
			root.put("createOrUpdate", true);
			if(topping == null){
				root.put("error", true);
				root.put("message", "Error while searching for topping with id " + id + ". Try again.");
				root.put("update", false);
			} else {
				root.put("update", true);
				root.put("topping", topping);
			}
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("toppings", toppings);
			root.put("hasToppings", CollectionUtils.isNotEmpty(toppings));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "toppings.ftl", root);
			return;
		}
		
		String deleteId = (String) request.getParameter("deleteId");
		if(StringUtils.isNumeric(deleteId)){
			int id = Integer.parseInt(deleteId);
			Topping topping = createMenuItemController.getToppingById(id);
			root.put("createOrUpdate", true);
			if(topping == null){
				root.put("error", true);
				root.put("message", "Error while searching for topping with id " + id + ". Try again.");
			} else {
				int row = createMenuItemController.deleteTopping(topping);
				if(row == 0){
					root.put("error", true);
					root.put("message", "Error while deleting topping with id " + id + ". Try again.");
				}
			}
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("toppings", toppings);
			root.put("hasToppings", CollectionUtils.isNotEmpty(toppings));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "toppings.ftl", root);
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
