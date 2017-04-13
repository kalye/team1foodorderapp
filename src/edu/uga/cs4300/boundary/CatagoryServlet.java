package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.objectlayer.MenuCategory;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/catagories" })
public class CatagoryServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7122341720546534417L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public CatagoryServlet(){
		super();
	}
	public void init(){
		super.init();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = (String) request.getParameter("createOrUpdate");
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(query != null){
			root.put("createOrUpdate", true);
			List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
			if(catagories != null && !catagories.isEmpty()){
				root.put("hasCatagory", true);
			}
			root.put("catagories", catagories);
			renderTemplate(request, response, "catagories.ftl", root);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
