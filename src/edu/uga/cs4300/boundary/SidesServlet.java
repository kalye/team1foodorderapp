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
import edu.uga.cs4300.objectlayer.Side;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/sides" })
@MultipartConfig
public class SidesServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2884859222596350374L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public SidesServlet(){
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
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("sides", sides);
			root.put("hasSides", CollectionUtils.isNotEmpty(sides));
			root.put("createsubmenu", true);
			return;
		}
		String updateId = (String) request.getParameter("updateId");
		if(StringUtils.isNumeric(updateId)){
			int id = Integer.parseInt(updateId);
			Side side = createMenuItemController.getSideById(id);
			root.put("createOrUpdate", true);
			if(side == null){
				root.put("error", true);
				root.put("message", "Error while searching for side id " + id + ". Try again.");
				root.put("update", false);
			} else {
				root.put("update", true);
				root.put("side", side);
			}
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("sides", sides);
			root.put("hasSides", CollectionUtils.isNotEmpty(sides));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "sides.ftl", root);
			return;
		}
		
		String deleteId = (String) request.getParameter("deleteId");
		if(StringUtils.isNumeric(deleteId)){
			int id = Integer.parseInt(deleteId);
			Side side = createMenuItemController.getSideById(id);
			root.put("createOrUpdate", true);
			if(side == null){
				root.put("error", true);
				root.put("message", "Error while searching for side with id " + id + ". Try again.");
			} else {
				int row = createMenuItemController.deleteSide(side);
				if(row == 0){
					root.put("error", true);
					root.put("message", "Error while deleting side with id " + id + ". Try again.");
				}
			}
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("sides", sides);
			root.put("hasSides", CollectionUtils.isNotEmpty(sides));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "sides.ftl", root);
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
